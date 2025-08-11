package com.jmuscles.springdemo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.jmuscles.springdemo.db.entity.Agent;
import com.jmuscles.springdemo.db.entity.Listing;
import com.jmuscles.springdemo.db.repository.AgentRepository;
import com.jmuscles.springdemo.db.repository.ListingRepository;
import com.jmuscles.springdemo.model.AgentDto;
import com.jmuscles.springdemo.model.ListingDto;
import com.jmuscles.springdemo.service.RealEstateService;

import jakarta.persistence.EntityNotFoundException;

@SpringBootTest
@AutoConfigureMockMvc
public class RealEstateIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Autowired
	private ObjectMapper objectMapper;

	@Autowired
	private ListingRepository listingRepository;

	@Autowired
	private AgentRepository agentRepository;

	@Autowired
	private RealEstateService realEstateService;

	@AfterEach
	void cleanup() {
		listingRepository.deleteAll();
		agentRepository.deleteAll();
	}

	@Test
	void testCreateListingSuccess() throws Exception {
		ListingDto dto = ListingDto.builder().title("Cozy apartment").description("Near downtown")
				.price(BigDecimal.valueOf(250000)).active(true).build();

		mockMvc.perform(post("/api/listings").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.title").value("Cozy apartment"))
				.andExpect(jsonPath("$.price").value(250000));
	}

	@Test
	void testCreateListingValidationFailureNegativePrice() throws Exception {
		ListingDto dto = ListingDto.builder().title("Cheap apartment").description("Fixer upper")
				.price(BigDecimal.valueOf(-1000)).active(true).build();

		mockMvc.perform(post("/api/listings").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isBadRequest())
				.andExpect(jsonPath("$.price").value("Price must be positive"));
	}

	@Test
	void testCreateAgentSuccess() throws Exception {
		AgentDto dto = AgentDto.builder().name("Alice Agent").email("alice@example.com").build();

		mockMvc.perform(post("/api/agents").contentType(MediaType.APPLICATION_JSON)
				.content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated())
				.andExpect(jsonPath("$.id").isNumber()).andExpect(jsonPath("$.name").value("Alice Agent"))
				.andExpect(jsonPath("$.email").value("alice@example.com"));
	}

	@Test
	void testAssignAgentToListingSuccess() throws Exception {
		Agent agent = new Agent();
		agent.setName("Bob Agent");
		agent.setEmail("bob@example.com");
		agent = agentRepository.save(agent);

		Listing listing = new Listing();
		listing.setTitle("Beach house");
		listing.setDescription("Ocean view");
		listing.setPrice(BigDecimal.valueOf(750000));
		listing.setLastActiveDate(LocalDateTime.now());
		listing = listingRepository.save(listing);

		mockMvc.perform(post("/api/listings/{listingId}/assign-agent/{agentId}", listing.getId(), agent.getId()))
				.andExpect(status().isOk());

		Listing updated = listingRepository.findById(listing.getId()).orElseThrow();
		assertNotNull(updated.getAgent());
		assertEquals(agent.getId(), updated.getAgent().getId());
	}

	@Test
	void testAssignAgentListingNotFound() {
		Agent agent = new Agent();
		agent.setName("Charles Agent");
		agent.setEmail("charles@example.com");
		Agent savedAgent = agentRepository.save(agent);

		Long nonExistingListingId = 999_999L;
		assertThrows(EntityNotFoundException.class, () -> {
			realEstateService.assignAgentToListing(nonExistingListingId, savedAgent.getId());
		});
	}
}

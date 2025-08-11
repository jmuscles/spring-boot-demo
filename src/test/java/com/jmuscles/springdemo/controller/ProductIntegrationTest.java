package com.jmuscles.springdemo.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
public class ProductIntegrationTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	void testAddAndGetProduct() throws Exception {
		String productJson = "{\"id\":10,\"name\":\"Keyboard\"}";

		mockMvc.perform(post("/products").contentType(MediaType.APPLICATION_JSON).content(productJson))
				.andExpect(status().isOk());

		mockMvc.perform(get("/products/10")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Keyboard"));
	}
}

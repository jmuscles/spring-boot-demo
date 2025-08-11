package com.jmuscles.springdemo.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.jmuscles.springdemo.db.entity.Agent;
import com.jmuscles.springdemo.db.entity.Listing;
import com.jmuscles.springdemo.db.repository.AgentRepository;
import com.jmuscles.springdemo.db.repository.ListingRepository;
import com.jmuscles.springdemo.model.AgentDto;
import com.jmuscles.springdemo.model.ListingDto;

import jakarta.persistence.EntityNotFoundException;

@Service
@Transactional
public class RealEstateService {

	private final ListingRepository listingRepository;
	private final AgentRepository agentRepository;

	public RealEstateService(ListingRepository listingRepository, AgentRepository agentRepository) {
		this.listingRepository = listingRepository;
		this.agentRepository = agentRepository;
	}

	public ListingDto createListing(ListingDto dto) {
		Listing entity = new Listing();
		entity.setTitle(dto.getTitle());
		entity.setDescription(dto.getDescription());
		entity.setPrice(dto.getPrice());
		entity.setActive(dto.isActive());
		entity.setLastActiveDate(dto.getLastActiveDate());

		if (dto.getAgent() != null && dto.getAgent().getId() != null) {
			Agent agent = agentRepository.findById(dto.getAgent().getId())
					.orElseThrow(() -> new EntityNotFoundException("Agent not found"));
			entity.setAgent(agent);
		}

		Listing saved = listingRepository.save(entity);

		return ListingDto.builder().id(saved.getId()).title(saved.getTitle()).description(saved.getDescription())
				.price(saved.getPrice()).active(saved.isActive()).creationDate(saved.getCreationDate())
				.lastActiveDate(saved.getLastActiveDate()).agent(saved.getAgent()).build();
	}

	public AgentDto createAgent(AgentDto dto) {
		Agent entity = new Agent();
		entity.setName(dto.getName());
		entity.setEmail(dto.getEmail());

		Agent saved = agentRepository.save(entity);

		return AgentDto.builder().id(saved.getId()).name(saved.getName()).email(saved.getEmail()).build();
	}

	public void assignAgentToListing(Long listingId, Long agentId) {
		Listing listing = listingRepository.findById(listingId)
				.orElseThrow(() -> new EntityNotFoundException("Listing not found"));

		Agent agent = agentRepository.findById(agentId)
				.orElseThrow(() -> new EntityNotFoundException("Agent not found"));

		listing.setAgent(agent);
		listingRepository.save(listing);
	}
}

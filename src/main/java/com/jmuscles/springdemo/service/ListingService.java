package com.jmuscles.springdemo.service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.jmuscles.springdemo.db.entity.Agent;
import com.jmuscles.springdemo.db.entity.Listing;
import com.jmuscles.springdemo.db.repository.AgentRepository;
import com.jmuscles.springdemo.db.repository.ListingRepository;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;

@Service
public class ListingService {

	private final ListingRepository listingRepository;
	private final AgentRepository agentRepository;

	public ListingService(ListingRepository listingRepository, AgentRepository agentRepository) {
		this.listingRepository = listingRepository;
		this.agentRepository = agentRepository;
	}

	@Transactional
	public Listing createListing(Listing listing) {
		if (listing.getPrice().compareTo(BigDecimal.ZERO) <= 0) {
			throw new IllegalArgumentException("Price must be positive");
		}
		listing.setLastActiveDate(LocalDateTime.now());
		return listingRepository.save(listing);
	}

	@Transactional
	public Agent createAgent(Agent agent) {
		return agentRepository.save(agent);
	}

	@Transactional
	public void assignAgentToListing(Long listingId, Long agentId) {
		Listing listing = listingRepository.findById(listingId)
				.orElseThrow(() -> new EntityNotFoundException("Listing not found"));

		Agent agent = agentRepository.findById(agentId)
				.orElseThrow(() -> new EntityNotFoundException("Agent not found"));

		listing.setAgent(agent);
		listing.setLastActiveDate(LocalDateTime.now());
		listingRepository.save(listing);
	}

	@Scheduled(cron = "0 0 0 * * ?") // Runs daily at midnight
	@Transactional
	public void deactivateInactiveListings() {
		LocalDateTime cutoffDate = LocalDateTime.now().minusDays(90);
		List<Listing> inactiveListings = listingRepository.findByActiveTrueAndLastActiveDateBefore(cutoffDate);

		for (Listing listing : inactiveListings) {
			listing.setActive(false);
		}
		listingRepository.saveAll(inactiveListings);
	}
}

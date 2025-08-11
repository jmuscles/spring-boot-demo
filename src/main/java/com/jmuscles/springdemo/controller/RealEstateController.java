package com.jmuscles.springdemo.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jmuscles.springdemo.model.AgentDto;
import com.jmuscles.springdemo.model.ListingDto;
import com.jmuscles.springdemo.service.RealEstateService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class RealEstateController {

	private final RealEstateService realEstateService;

	public RealEstateController(RealEstateService realEstateService) {
		this.realEstateService = realEstateService;
	}

	@PostMapping("/listings")
	public ResponseEntity<ListingDto> createListing(@Valid @RequestBody ListingDto dto) {
		ListingDto saved = realEstateService.createListing(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PostMapping("/agents")
	public ResponseEntity<AgentDto> createAgent(@Valid @RequestBody AgentDto dto) {
		AgentDto saved = realEstateService.createAgent(dto);
		return ResponseEntity.status(HttpStatus.CREATED).body(saved);
	}

	@PostMapping("/listings/{listingId}/assign-agent/{agentId}")
	public ResponseEntity<Void> assignAgent(@PathVariable Long listingId, @PathVariable Long agentId) {
		realEstateService.assignAgentToListing(listingId, agentId);
		return ResponseEntity.ok().build();
	}
}

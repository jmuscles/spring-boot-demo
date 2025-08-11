package com.jmuscles.springdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.jmuscles.springdemo.db.entity.Listing;

@SpringBootTest
public class ListingServiceTest {

	@Autowired
	private ListingService listingService;

	@Test
	public void testCreateListingSuccess() {
		Listing listing = new Listing();
		listing.setTitle("Cozy apartment");
		listing.setDescription("Near downtown");
		listing.setPrice(BigDecimal.valueOf(250000));

		Listing saved = listingService.createListing(listing);

		assertNotNull(saved.getId());
		assertEquals("Cozy apartment", saved.getTitle());
	}

	@Test
	public void testCreateListingPriceNegative() {
		Listing listing = new Listing();
		listing.setTitle("Cheap apartment");
		listing.setDescription("Needs repairs");
		listing.setPrice(BigDecimal.valueOf(-1000));

		assertThrows(IllegalArgumentException.class, () -> {
			listingService.createListing(listing);
		});
	}
}

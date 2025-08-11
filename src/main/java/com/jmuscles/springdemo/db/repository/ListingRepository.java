package com.jmuscles.springdemo.db.repository;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmuscles.springdemo.db.entity.Listing;

public interface ListingRepository extends JpaRepository<Listing, Long> {
	List<Listing> findByActiveTrueAndLastActiveDateBefore(LocalDateTime cutoffDate);

}

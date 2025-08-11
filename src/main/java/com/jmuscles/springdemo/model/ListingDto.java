package com.jmuscles.springdemo.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.jmuscles.springdemo.db.entity.Agent;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ListingDto {
	private Long id;
	@NotBlank(message = "Title is required")
	private String title;
	private String description;
	@Positive(message = "Price must be positive")
	private BigDecimal price;
	private boolean active;
	private LocalDateTime creationDate;
	private LocalDateTime lastActiveDate;
	private Agent agent;

}

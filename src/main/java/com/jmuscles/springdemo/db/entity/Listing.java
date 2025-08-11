package com.jmuscles.springdemo.db.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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
@Entity
public class Listing {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Title is required")
	private String title;

	private String description;

	@Positive(message = "Price must be positive")
	private BigDecimal price;

	private boolean active = true;

	@CreationTimestamp
	private LocalDateTime creationDate;

	private LocalDateTime lastActiveDate;

	@ManyToOne
	@JoinColumn(name = "agent_id")
	private Agent agent;

	// getters and setters
}

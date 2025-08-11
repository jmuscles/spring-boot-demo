package com.jmuscles.springdemo.model;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AgentDto {
	private Long id;

	@NotBlank(message = "Name is required")
	private String name;

	@Email(message = "Invalid email address")
	private String email;
}

package com.jmuscles.springdemo.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmuscles.springdemo.db.entity.Agent;

public interface AgentRepository extends JpaRepository<Agent, Long> {
}

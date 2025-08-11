package com.jmuscles.springdemo.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmuscles.springdemo.db.entity.Project;

public interface ProjectRepository extends JpaRepository<Project, Long> {
	
	List<Project> findByEmployeesId(Long employeeId);

}

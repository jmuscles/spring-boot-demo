package com.jmuscles.springdemo.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmuscles.springdemo.db.entity.Department;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
	
	
}

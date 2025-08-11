package com.jmuscles.springdemo.db.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jmuscles.springdemo.db.entity.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

	List<Employee> findByProjectsId(Long projectId);

}

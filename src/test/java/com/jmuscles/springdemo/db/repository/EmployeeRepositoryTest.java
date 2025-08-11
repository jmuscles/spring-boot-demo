package com.jmuscles.springdemo.db.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.jmuscles.springdemo.db.entity.Employee;
import com.jmuscles.springdemo.db.entity.Project;
import com.jmuscles.springdemo.db.repository.EmployeeRepository;
import com.jmuscles.springdemo.db.repository.ProjectRepository;

@DataJpaTest
public class EmployeeRepositoryTest {

	@Autowired
	private EmployeeRepository employeeRepository;

	@Autowired
	private ProjectRepository projectRepository;

	private Project project;
	private Employee employee1;
	private Employee employee2;

	@BeforeEach
	void setUp() {
		project = new Project();
		project.setProjectName("AI Development");
		projectRepository.save(project);

		employee1 = new Employee();
		employee1.setName("John Doe");
		employee1.setProjects(Set.of(project));

		employee2 = new Employee();
		employee2.setName("Jane Smith");
		employee2.setProjects(Set.of(project));

		employeeRepository.saveAll(List.of(employee1, employee2));
	}

	@Test
	void testFindEmployeesByProjectId() {
		List<Employee> employees = employeeRepository.findByProjectsId(project.getId());
		assertThat(employees).isNotNull();
		assertThat(employees.size()).isEqualTo(2);
		assertThat(employees).extracting("name").containsExactlyInAnyOrder("John Doe", "Jane Smith");
	}
}

package com.jmuscles.springdemo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.jmuscles.springdemo.db.entity.Department;
import com.jmuscles.springdemo.db.entity.Employee;
import com.jmuscles.springdemo.db.repository.DepartmentRepository;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired private DepartmentRepository departmentRepository;

    @Override
    public void run(String... args) {
        Department d = new Department();
        d.setName("Engineering");

        Employee e1 = new Employee();
        e1.setName("Alice");
        e1.setDepartment(d);

        Employee e2 = new Employee();
        e2.setName("Bob");
        e2.setDepartment(d);

        d.setEmployees(List.of(e1, e2));
        departmentRepository.save(d);
    }
}

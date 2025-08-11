package com.jmuscles.springdemo.db.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.jmuscles.springdemo.db.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}

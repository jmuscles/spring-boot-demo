package com.jmuscles.springdemo.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.stereotype.Service;

import com.jmuscles.springdemo.db.entity.Product;
import com.jmuscles.springdemo.db.repository.ProductRepository;

@Service
public class ProductService {

	private final ProductRepository productRepository;

	// Constructor injection
	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	public Product addProduct(Product product) {
		return productRepository.save(product);
	}

	public Product getProduct(Long id) {
		return productRepository.findById(id).orElseThrow(() -> new NoSuchElementException("Product not found"));
	}

	public List<Product> getAll() {
		return productRepository.findAll();
	}
}

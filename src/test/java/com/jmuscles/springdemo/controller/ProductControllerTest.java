package com.jmuscles.springdemo.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.jmuscles.springdemo.controller.ProductController;
import com.jmuscles.springdemo.db.entity.Product;
import com.jmuscles.springdemo.service.ProductService;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

	@Mock
	ProductService productService;

	@InjectMocks
	ProductController productController;

	@Test
	void testGet() {
		Product mockProduct = new Product(1L, "Phone");
		when(productService.getProduct(1L)).thenReturn(mockProduct);

		Product result = productController.get(1L);
		assertEquals("Phone", result.getName());
	}
}

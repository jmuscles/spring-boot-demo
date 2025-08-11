package com.jmuscles.springdemo.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import com.jmuscles.springdemo.db.entity.Product;
import com.jmuscles.springdemo.db.repository.ProductRepository;
import com.jmuscles.springdemo.service.ProductService;

@ActiveProfiles("test")
@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

	@Mock
	private ProductRepository productRepository;

	@InjectMocks
	private ProductService productService;

	@Test
	void testAddProduct() {
		Product product = new Product(1L, "Mouse");
		when(productRepository.save(product)).thenReturn(product);

		Product saved = productService.addProduct(product);

		assertEquals("Mouse", saved.getName());
		verify(productRepository, times(1)).save(product);
	}

	@Test
	void testGetProductFound() {
		Product product = new Product(2L, "Keyboard");
		when(productRepository.findById(2L)).thenReturn(Optional.of(product));

		Product found = productService.getProduct(2L);

		assertEquals("Keyboard", found.getName());
		verify(productRepository).findById(2L);
	}

	@Test
	void testGetProductNotFound() {
		when(productRepository.findById(99L)).thenReturn(Optional.empty());

		assertThrows(NoSuchElementException.class, () -> productService.getProduct(99L));
		verify(productRepository).findById(99L);
	}

	@Test
	void testGetAllProducts() {
		List<Product> products = Arrays.asList(new Product(1L, "Mouse"), new Product(2L, "Keyboard"));
		when(productRepository.findAll()).thenReturn(products);

		List<Product> result = productService.getAll();

		assertEquals(2, result.size());
		assertEquals("Mouse", result.get(0).getName());
		assertEquals("Keyboard", result.get(1).getName());
		verify(productRepository).findAll();
	}
}

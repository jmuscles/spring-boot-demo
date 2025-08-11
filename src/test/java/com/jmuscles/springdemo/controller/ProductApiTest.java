package com.jmuscles.springdemo.controller;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import com.jmuscles.springdemo.db.entity.Product;
import com.jmuscles.springdemo.service.ProductService;

@ActiveProfiles("test")
@WebMvcTest(ProductController.class)
class ProductApiTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private ProductService productService;

	@Test
	void testGetProductApi() throws Exception {
		Product product = new Product(1L, "Mouse");
		when(productService.getProduct(1L)).thenReturn(product);

		mockMvc.perform(get("/products/1")).andExpect(status().isOk()).andExpect(jsonPath("$.name").value("Mouse"));
	}
}

package com.fis.app.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fis.app.dto.ProductDto;
import com.fis.app.service.ProductService;

@RestController
public class ProductController {
	
	@Autowired
	private ProductService productService;
	
	@GetMapping("/api/products")
	@Transactional
	public List<ProductDto> getProduct () throws Exception {
		return productService.getProduct();
	}
	
	@PostMapping("/api/product")
	@Transactional
	public ProductDto submitProduct (@RequestBody ProductDto p) throws Exception {
		return productService.submitProduct(p);
	}
	
}

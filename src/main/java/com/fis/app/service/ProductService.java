package com.fis.app.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fis.app.dto.ProductDto;
import com.fis.app.entity.Product;
import com.fis.app.repository.ProductRepository;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Service
public class ProductService {

	@Autowired
	private ProductRepository productRepository;

	public List<ProductDto> getProduct(){
		
		log.info("incoming transaction getProduct");
		
		List<ProductDto> pr = new ArrayList<>();
		
		productRepository.findAll().forEach(a->{
			
			ProductDto d = new ProductDto();
			d.setSku(a.getSku());
			d.setDescription(a.getDescription());
			d.setName(a.getName());
			d.setPrice(a.getPrice());
			
			pr.add(d);
			
		});
		
		return pr;
		
	}
	
	
	public ProductDto submitProduct(ProductDto a) {
		
		Product d = new Product();
		d.setSku(a.getSku());
		d.setDescription(a.getDescription());
		d.setName(a.getName());
		d.setPrice(a.getPrice());
		
		productRepository.save(d);
		
		return a;
	}
	
}

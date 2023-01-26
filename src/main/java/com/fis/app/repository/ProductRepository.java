package com.fis.app.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fis.app.entity.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer> {

}

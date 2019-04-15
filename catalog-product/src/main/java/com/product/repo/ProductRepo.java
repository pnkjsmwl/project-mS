package com.product.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.product.entity.Product;
import com.product.exception.ProductNotFoundException;

public interface ProductRepo extends JpaRepository<Product, Long> {

	public Optional<Product> findProductByCode(String code) throws ProductNotFoundException;
}

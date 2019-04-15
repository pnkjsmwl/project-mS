package com.product.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.product.entity.Product;
import com.product.exception.ProductNotFoundException;
import com.product.service.ProductService;

import lombok.extern.slf4j.Slf4j;

@RestController
@Slf4j
@RequestMapping("/product")
public class ProductController {

	private final ProductService productService;
	private final Gson gson;

	@Autowired
	public ProductController(ProductService productService, RestTemplate restTemplate, Environment env, Gson gson)
	{
		this.productService = productService;
		this.gson = gson;
	}

	@GetMapping("/{productCode}")
	public Optional<Product> getProductByCode(@PathVariable String productCode) throws ProductNotFoundException
	{
		return productService.findProductByCode(productCode);
	}

	@GetMapping("/all")
	public List<Product> getAllProducts()
	{
		List<Product> allProducts = productService.findAllProducts();
		for(Product p : allProducts)
		{
			log.info("Product : "+p.getId());
		}
		return allProducts;
	}

	@PostMapping(path="/add", consumes="application/json")
	public Product addProduct(@RequestBody Product product)
	{
		log.info("Product to add:\n"+gson.toJson(product));
		return productService.addProduct(product);
	}

	@PatchMapping(path="/update/{productCode}", consumes="application/json")
	public Product updateProduct(@PathVariable String productCode, @RequestBody Product product)
	{
		log.info("Product to update:\n"+gson.toJson(product));
		return productService.updateProductByCode(productCode, product);
	}
}

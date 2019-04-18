package com.product;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class CatalogProductApplication {

	public static void main(String[] args) {
		SpringApplication.run(CatalogProductApplication.class, args);
	}

}

package com.inventory;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;

@EnableCircuitBreaker
@SpringBootApplication
public class CatalogInventoryApplication {
	
	public static void main(String[] args) {
		SpringApplication.run(CatalogInventoryApplication.class, args);
	}

}

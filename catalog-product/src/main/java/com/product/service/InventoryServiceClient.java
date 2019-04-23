package com.product.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.product.entity.ProductInventory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryServiceClient {

	private final Environment env;
	private final Gson gson;
	private final InventoryServiceFeignProxy inventoryServiceFeignProxy;
	//private final RestTemplate restTemplate;
	//private final String SINGLE_INVENTORY_URL = "http://inventory-service/inventory/code/";

	@Autowired
	public InventoryServiceClient(Environment env, Gson gson, InventoryServiceFeignProxy inventoryServiceFeignProxy)
	{
		//this.restTemplate = restTemplate;
		this.env = env;
		this.gson = gson;
		this.inventoryServiceFeignProxy = inventoryServiceFeignProxy;
		
	}
	/* Hystrix will scan @Component or @Service annotated classes for @HystixCommand annotated methods */
	@HystrixCommand(fallbackMethod="getProductInventory_fallback", 
			commandProperties =
		{
				//@HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
				@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"),
				@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60")
		})
	public Optional<ProductInventory> getProductInventory(String productCode)
	{
		log.info("In Product Inventory, response from port :"+env.getProperty("local.server.port"));
		// ResponseEntity<ProductInventory> responseEntity = restTemplate.getForEntity(SINGLE_INVENTORY_URL + "{productCode}", ProductInventory.class, productCode);

		/* Usage of Feign client, replaced RestTemplate */
		ResponseEntity<ProductInventory> responseEntity = inventoryServiceFeignProxy.getProductForCode(productCode);
		
		log.info("Body : "+gson.toJson(responseEntity.getBody()));
		if(responseEntity.getStatusCode().equals(HttpStatus.OK))
		{
			return Optional.ofNullable(responseEntity.getBody());
		}else {
			log.info("Error getting inventory for : "+productCode);
			return Optional.empty();
		}
	}

	public Optional<ProductInventory> getProductInventory_fallback(String productCode)
	{
		log.info("Inventory Service not responsive !! Returning default inventory count for : "+productCode);
		ProductInventory productInventory = new ProductInventory();
		productInventory.setProductCode(productCode);
		productInventory.setQuantity(1);

		return Optional.ofNullable(productInventory);
	}


}

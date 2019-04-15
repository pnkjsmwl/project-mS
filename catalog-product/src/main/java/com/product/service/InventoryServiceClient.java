package com.product.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.product.entity.ProductInventory;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryServiceClient {

	private final RestTemplate restTemplate;
	private final Environment env;
	private final Gson gson;
	private final String SINGLE_INVENTORY_URL = "http://inventory-service/inventory/code/";

	@Autowired
	public InventoryServiceClient(RestTemplate restTemplate, Environment env, Gson gson)
	{
		this.restTemplate = restTemplate;
		this.env = env;
		this.gson = gson;
	}

	@HystrixCommand(fallbackMethod="getDefaultInventory")
	public Optional<ProductInventory> getProductInventory(String productCode)
	{
		log.info("In Product Inventory, response from port :"+env.getProperty("local.server.port"));
		ResponseEntity<ProductInventory> responseEntity = restTemplate.getForEntity(SINGLE_INVENTORY_URL + "{productCode}", ProductInventory.class, productCode);

		log.info("Body : "+gson.toJson(responseEntity.getBody()));
		if(responseEntity.getStatusCode().equals(HttpStatus.OK))
		{
			return Optional.ofNullable(responseEntity.getBody());
		}else {
			log.info("Error getting inventory for : "+productCode);
			return Optional.empty();
		}
	}

	public Optional<ProductInventory> getDefaultProductInventory(String productCode)
	{
		log.info("Inventory Service not responsive !! Returning default inventory for : "+productCode);
		ProductInventory productInventory = new ProductInventory();
		productInventory.setProductCode(productCode);
		productInventory.setQuantity(1);

		return Optional.ofNullable(productInventory);
	}


}

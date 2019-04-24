package com.inventory.service;

import java.util.Optional;

import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.inventory.entity.InventoryStore;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryStoreServiceClient {

	/*private final String INVENTORY_STORE_URL = "http://inventory-store-service/store/";
	private final RestTemplate restTemplate;*/
	private final Environment env;
	private final Gson gson;
	private final InventoryStoreServiceFeignProxy inventoryStoreFeignProxy;

	public InventoryStoreServiceClient(InventoryStoreServiceFeignProxy inventoryStoreFeignProxy, Environment env, Gson gson)
	{
		this.inventoryStoreFeignProxy = inventoryStoreFeignProxy;
		this.env = env;
		this.gson = gson;
	}

	@HystrixCommand(fallbackMethod="getInventoryStoreByProductCode_fallback", 
			commandProperties = 
		{
				//@HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
				@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"),
				@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60")
		})
	public Optional<InventoryStore> getInventoryStoreByProductCode(String productCode)
	{
		log.info("In Inventory Store Service, response from port : "+env.getProperty("local.server.port"));

		//ResponseEntity<InventoryStore> respEntity = restTemplate.getForEntity(INVENTORY_STORE_URL+"{productCode}",InventoryStore.class, productCode);

		ResponseEntity<InventoryStore> respEntity = inventoryStoreFeignProxy.getInventoryStore(productCode);


		log.info("Body : "+gson.toJson(respEntity.getBody()));
		if(respEntity.getStatusCode().equals(HttpStatus.OK))
		{
			return Optional.ofNullable(respEntity.getBody());
		}else {
			return Optional.empty();
		}
	}

	public Optional<InventoryStore> getInventoryStoreByProductCode_fallback(String code)
	{
		log.info("Inventory Store Service not responsive, returning default Store !! Response from port : "+env.getProperty("local.server.port"));
		InventoryStore inventoryStore = new InventoryStore();
		inventoryStore.setProductCode(code);
		inventoryStore.setStore("default");
		return Optional.ofNullable(inventoryStore);
	}
}

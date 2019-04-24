package com.inventory.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.inventory.entity.InventoryStore;

@FeignClient(name="inventory-store-service")
@RequestMapping("/store")
public interface InventoryStoreServiceFeignProxy {

	@GetMapping("/{code}")
	public ResponseEntity<InventoryStore> getInventoryStore(@PathVariable (value="code") String code);
}

package com.store.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.store.entity.InventoryStore;
import com.store.service.InventoryStoreService;

@RestController
@RequestMapping("/store")
public class InventoryStoreController {

	private InventoryStoreService storeService;

	@Autowired
	public InventoryStoreController(InventoryStoreService storeService) {
		this.storeService = storeService;
	}

	@GetMapping("/{productCode}")
	public Optional<InventoryStore> getStoreByProductCode(@PathVariable String productCode)
	{
		return storeService.getInventoryStore(productCode);
		//.orElseThrow(() ->  new InventoryStoreNotFoundException("Store for Product : "+productCode+" not found !!"));
	}
}

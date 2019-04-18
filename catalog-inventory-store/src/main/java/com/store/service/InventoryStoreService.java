package com.store.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.store.entity.InventoryStore;
import com.store.exception.InventoryStoreNotFoundException;
import com.store.repo.InventoryStoreRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryStoreService {

	private final InventoryStoreRepo inventoryStoreRepo;
	private final Environment env;
	
	@Autowired
	public InventoryStoreService(InventoryStoreRepo inventoryStoreRepo, Environment env) {
		this.inventoryStoreRepo = inventoryStoreRepo;
		this.env = env;
	}

	public Optional<InventoryStore> getInventoryStore(String productCode) throws InventoryStoreNotFoundException
	{
		log.info("In Store Service, response from port : "+env.getProperty("local.server.port"));
		Optional<InventoryStore> store = inventoryStoreRepo.findInventoryStoreByProductCode(productCode);
		if(store.isPresent()){
			return store;

		}else {
			throw new InventoryStoreNotFoundException("Store for Product : "+productCode+" not found !!");
		}

	}

}

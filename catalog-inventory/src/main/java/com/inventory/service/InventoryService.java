package com.inventory.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.entity.Inventory;
import com.inventory.entity.InventoryStore;
import com.inventory.exception.InventoryNotFoundException;
import com.inventory.repo.InventoryRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService {
	private final InventoryRepo inventoryRepo;
	private final InventoryStoreServiceClient inventoryStoreServiceClient;

	@Autowired
	public InventoryService(InventoryRepo inventoryRepo, InventoryStoreServiceClient inventoryStoreServiceClient)
	{
		this.inventoryRepo = inventoryRepo;
		this.inventoryStoreServiceClient = inventoryStoreServiceClient;
	}

	public List<Inventory> findAllInventory()
	{
		return inventoryRepo.findAll();
	}
	
	public Optional<Inventory> findInventoryByProductCode(String code)
	{
		log.info("In Inventory Service class");
		Optional<Inventory> inventory = inventoryRepo.findInventoryByProductCode(code);
		//		.orElseThrow(() -> new InventoryNotFoundException("No inventory for Product Code : "+code));
		
		if(inventory.isPresent())
		{
			Optional<InventoryStore> inventoryStore = inventoryStoreServiceClient.getInventoryStoreByProductCode(code);
			
			if(inventoryStore.isPresent())
			{
				inventory.get().setStore(inventoryStore.get().getStore());
			}else {
				log.info("Unable to get Inventory Store data !!");
			}
		}else {
			throw new InventoryNotFoundException("No inventory for Product Code : "+code);
		}
		return inventory;
	}
}

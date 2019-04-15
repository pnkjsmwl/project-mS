package com.inventory.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.inventory.entity.Inventory;
import com.inventory.exception.InventoryNotFoundException;
import com.inventory.repo.InventoryRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InventoryService {
	private final InventoryRepo inventoryRepo;

	@Autowired
	public InventoryService(InventoryRepo inventoryRepo)
	{
		this.inventoryRepo = inventoryRepo;
	}

	public List<Inventory> findAllInventory()
	{
		return inventoryRepo.findAll();
	}
	public Inventory findInventoryByProductCode(String code)
	{
		log.info("In Inventory Service class");
		return inventoryRepo.findInventoryByProductCode(code)
				.orElseThrow(() -> new InventoryNotFoundException("No inventory for Product Code : "+code));
	}
}

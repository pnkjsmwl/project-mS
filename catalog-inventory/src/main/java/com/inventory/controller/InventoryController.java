package com.inventory.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.inventory.entity.Inventory;
import com.inventory.exception.InventoryNotFoundException;
import com.inventory.service.InventoryService;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

	private final InventoryService inventoryService;
	
	@Autowired
	public InventoryController(InventoryService inventoryService)
	{
		this.inventoryService = inventoryService;
	}
	
	@GetMapping("/code/{productCode}")
	public Optional<Inventory> getInventoryForProductCode(@PathVariable String productCode) throws InventoryNotFoundException
	{
		return inventoryService.findInventoryByProductCode(productCode);
	}
	
	@GetMapping("/all")
	public List<Inventory> getAllInventory()
	{
		return inventoryService.findAllInventory();
	}
}

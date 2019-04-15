package com.inventory.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.inventory.entity.Inventory;

public interface InventoryRepo extends JpaRepository<Inventory, Long> {

	public Optional<Inventory> findInventoryByProductCode(String code);
}

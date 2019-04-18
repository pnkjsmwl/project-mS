package com.store.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.store.entity.InventoryStore;

public interface InventoryStoreRepo extends JpaRepository<InventoryStore, Long>{

	public Optional<InventoryStore> findInventoryStoreByProductCode(String code);
}

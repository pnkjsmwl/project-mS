package com.store.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="INVENTORY_STORES")
public class InventoryStore {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="product_code", nullable=false)
	private String productCode;
	private String store;
}

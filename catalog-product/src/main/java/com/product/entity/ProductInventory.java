package com.product.entity;

import lombok.Data;

@Data
public class ProductInventory {

	private int id;
	private String productCode;
	private int quantity = 0;
	
}

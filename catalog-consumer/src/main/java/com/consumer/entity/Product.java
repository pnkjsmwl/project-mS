package com.consumer.entity;

import lombok.Data;

@Data
public class Product {

	private long id;
	private String code;
	private String description;
	private String name;
	private String price;
	
}
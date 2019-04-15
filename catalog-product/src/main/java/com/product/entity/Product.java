package com.product.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.DynamicUpdate;

import lombok.Data;

@Data
@Entity
@Table(name="PRODUCTS")
@DynamicUpdate
public class Product {

	@Id @GeneratedValue(strategy=GenerationType.SEQUENCE)
	private long id;
	@Column(nullable=false,unique=true)
	private String code;
	private String description;
	private String name;
	private String price;
	@Transient
	private boolean inStock = false;
	
}
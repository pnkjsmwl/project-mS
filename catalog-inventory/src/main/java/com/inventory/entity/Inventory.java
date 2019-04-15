package com.inventory.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="INVENTORY")
public class Inventory 
{
	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	@Column(name="product_code", nullable=false)
	private String productCode;
	private int quantity;
}

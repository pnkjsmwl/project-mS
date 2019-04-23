package com.security.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="ROLE")
public class Role {
	public Role() {
		
	}
	
	@Id @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "role_id")
	private Integer id;
    private String role;
}

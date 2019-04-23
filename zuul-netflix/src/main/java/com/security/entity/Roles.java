package com.security.entity;

import javax.persistence.Entity;

import lombok.Data;

@Data
@Entity
public class Roles {

	private Integer id;
    private String role;
}

package com.security.entity;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;


import lombok.Data;

@Data
@Entity
public class CustomUser {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	private String id;
	@Email(message = "*Please provide a valid email")
	@NotEmpty(message = "*Please provide an email")
	private String email;
	@NotEmpty(message = "*Please provide your password")
	private String password;
	@NotEmpty(message = "*Please provide your first name")
	private String firstName;
	@NotEmpty(message = "*Please provide your last name")
	private String lastName;
	private Integer active=1;
	@Transient
	private boolean isLocked=false;
	@Transient
	private boolean isExpired=false;
	@Transient
	private boolean isEnabled=true;

	private Set<Roles> roles;

	public CustomUser() {
	}

	public CustomUser(CustomUser customUser)
	{
		this.id = customUser.getId();
		this.email = customUser.getEmail();
		this.password = customUser.getPassword();
		this.firstName = customUser.getFirstName();
		this.lastName =customUser.getLastName();
		this.roles = customUser.getRoles();
		this.active = customUser.getActive();
		this.isEnabled = customUser.isEnabled();
		this.isExpired = customUser.isExpired();
	}
}

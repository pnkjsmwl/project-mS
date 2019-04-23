package com.security.entity;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import lombok.Data;

@Data
@Entity
@Table(name="USER")
public class CustomUser {

	@Id @GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "user_id")
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
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name = "USER_ROLE", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> role;

	public CustomUser() {
	}

	public CustomUser(CustomUser customUser)
	{
		this.id = customUser.getId();
		this.email = customUser.getEmail();
		this.password = customUser.getPassword();
		this.firstName = customUser.getFirstName();
		this.lastName =customUser.getLastName();
		this.role = customUser.getRole();
		this.active = customUser.getActive();
		this.isEnabled = customUser.isEnabled();
		this.isExpired = customUser.isExpired();
	}
}

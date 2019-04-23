package com.security.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.security.entity.CustomUser;

public interface UserRepo extends JpaRepository<CustomUser, String> {

	public Optional<CustomUser> findByEmail(String email);
}

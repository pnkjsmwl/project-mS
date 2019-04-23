package com.security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.security.entity.CustomUser;
import com.security.entity.CustomUserDetails;
import com.security.exception.CustomException;
import com.security.repo.UserRepo;

@Service
public class UserService implements UserDetailsService {

	@Autowired
	private UserRepo repo;

	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

		Optional<CustomUser> optCustomUser = repo.findByEmail(email);

		optCustomUser
		.orElseThrow(() -> new CustomException("Username or Password is not correct.", HttpStatus.UNAUTHORIZED));

		//return optCustomUser.map(user -> new CustomUserDetails(user)).get();
		return optCustomUser.map(CustomUserDetails::new).get();
	}
}
package com.security.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.security.repo.UserRepo;
import com.security.service.CustomUserDetailService;

@EnableGlobalMethodSecurity(prePostEnabled=true)
@EnableJpaRepositories(basePackageClasses=UserRepo.class)
@EnableWebSecurity
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

	//@Autowired
	//private BCryptPasswordEncoder bCryptPasswordEncoder;
	
	@Autowired
	private CustomUserDetailService customUserDetailService;

	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth
		.userDetailsService(customUserDetailService)
		.passwordEncoder(passwordEncoder());
	}

	private PasswordEncoder passwordEncoder() {
		return new PasswordEncoder() {
			
			@Override
			public boolean matches(CharSequence rawPassword, String encodedPassword) {
				return true;
			}
			
			@Override
			public String encode(CharSequence rawPassword) {
				return rawPassword.toString();
			}
		};
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {

		http
		.csrf()
		.disable();

		http
		.authorizeRequests()
		.antMatchers("**/secured/**").authenticated()
		.anyRequest().permitAll()
		.and()
		.formLogin().permitAll();

	}





}

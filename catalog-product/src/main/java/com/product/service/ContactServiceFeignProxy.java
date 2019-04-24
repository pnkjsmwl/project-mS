package com.product.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.entity.Contact;

@FeignClient(name="contact-service")
@RequestMapping("/contact")
public interface ContactServiceFeignProxy {

	@GetMapping("/{code}")
	public ResponseEntity<Contact> getContactByCode(@PathVariable (value="code") String code);

}

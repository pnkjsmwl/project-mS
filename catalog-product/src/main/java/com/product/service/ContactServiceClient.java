package com.product.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.product.entity.Contact;

@Service
public class ContactServiceClient {

	/*private String CONTACT_URL = "http://contact-service/contact/";
	private RestTemplate restTemp;*/
	private final ContactServiceFeignProxy contactServiceFeignProxy;

	@Autowired
	public ContactServiceClient(ContactServiceFeignProxy contactServiceFeignProxy) {
		this.contactServiceFeignProxy = contactServiceFeignProxy;
	}

	@HystrixCommand(fallbackMethod="getContactData_fallback", 
			commandProperties = 
		{
				//@HystrixProperty(name="execution.isolation.strategy", value="SEMAPHORE"),
				@HystrixProperty(name="execution.isolation.thread.timeoutInMilliseconds", value="3000"),
				@HystrixProperty(name="circuitBreaker.errorThresholdPercentage", value="60")
		})
	public Optional<Contact> getContactData(String code)
	{
		//ResponseEntity<Contact> respEntity = restTemp.getForEntity(CONTACT_URL+"{code}", Contact.class, code);

		ResponseEntity<Contact> respEntity = contactServiceFeignProxy.getContactByCode(code);

		if(respEntity.getStatusCode().equals(HttpStatus.OK))
		{
			return Optional.ofNullable(respEntity.getBody());
		}else {
			return Optional.empty();
		}

	}

	public Optional<Contact> getContactData_fallback(String code)
	{
		Contact contact = new Contact();
		contact.setCode(code);
		contact.setFname("Admin");
		contact.setLname("Admin");
		contact.setEmail("contact_desk@gmail.com");
		return Optional.ofNullable(contact);
	}
}

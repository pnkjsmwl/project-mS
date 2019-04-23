package com.ribbon.controller;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.ServiceInstance;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import lombok.Data;

@RestController
@RequestMapping("/ribbon")
public class RibbonController {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private DiscoveryClient discoveryClient;
	@Autowired
	private LoadBalancerClient loadBalancer;

	@GetMapping("/test")
	public Inv ribbonTest() {
		MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
		mappingJackson2HttpMessageConverter.setSupportedMediaTypes(Arrays.asList(MediaType.APPLICATION_JSON, MediaType.APPLICATION_OCTET_STREAM));
		restTemplate.getMessageConverters().add(mappingJackson2HttpMessageConverter);

		/* Discovery Client : we need to decide which URI to use now */
		List<ServiceInstance> instances = discoveryClient.getInstances("inventory-service");
		instances.stream().map(serviceInstance -> "DiscoveryClient URI : "+serviceInstance.getUri().toString()).forEach(System.out::println);

		/* LoadBalancer Client : Spring internally decides the URI to be used based on the internal algorithm such as Round-Robin */
		System.out.println("LoadBalancer URI : "+loadBalancer.choose("inventory-service").getUri().toString());

		ResponseEntity<Inv> forEntity = restTemplate.getForEntity("http://inventory-service/inventory/code/{code}", Inv.class, "P001");
		System.out.println("StatusCode :"+forEntity.getStatusCode());

		return forEntity.getBody();
	}

}

@Data
class Inv{
	private long id;
	private String productCode;
	private int quantity;

}
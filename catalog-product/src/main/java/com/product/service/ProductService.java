package com.product.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.google.gson.Gson;
import com.product.entity.Product;
import com.product.entity.ProductInventory;
import com.product.exception.ProductNotFoundException;
import com.product.repo.ProductRepo;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	private final ProductRepo productRepo;
	private final RestTemplate restTemplate;
	private final Environment env;
	private final Gson gson;
	private final String SINGLE_INVENTORY_URL = "http://inventory-service/inventory/code/";

	@Autowired
	public ProductService(ProductRepo productRepo,  RestTemplate restTemplate, Environment env, Gson gson)
	{
		this.productRepo = productRepo;
		this.restTemplate = restTemplate;
		this.env = env;
		this.gson = gson;
	}

	public Optional<Product> findProductByCode(String productCode) throws ProductNotFoundException
	{
		log.info("In Product Service, response from port :"+env.getProperty("local.server.port"));
		Optional<Product> product = productRepo.findProductByCode(productCode);
		//.orElseThrow(() -> new ProductNotFoundException("Product with code '"+code+"' not found"));

		if(product.isPresent())
		{
			ResponseEntity<ProductInventory> respEntity = restTemplate.getForEntity(SINGLE_INVENTORY_URL +productCode, ProductInventory.class, productCode);
			/*ttpHeaders headers = new HttpHeaders();
			List<MediaType> mediaTypeList = Arrays.asList(MediaType.APPLICATION_JSON);

			headers.setAccept(mediaTypeList);
			headers.setContentType(MediaType.APPLICATION_JSON);

			HttpEntity<String> requestEntity = new HttpEntity<String>(headers);*/

			//ResponseEntity<ProductInventory> respEntity = restTemplate.exchange(INVENTORY_URL +productCode, HttpMethod.GET, requestEntity, ProductInventory.class);

			if(respEntity.getStatusCode().equals(HttpStatus.OK))
			{
				log.info("Body : "+gson.toJson(respEntity.getBody()));
				int quantity = respEntity.getBody().getQuantity();
				log.info("Available quantity : "+respEntity);
				product.get().setInStock(quantity > 0);
			}else
			{
				log.error("Status Code : "+respEntity.getStatusCode()+", Unable to get inventory data !!");
			}
		}else {
			throw new ProductNotFoundException("Product with code '"+productCode+"' not found");
		}
		log.info("Product : "+gson.toJson(product));
		return product;
	}

	public List<Product> findAllProducts()
	{
		log.info("In Product Service, response from port :"+env.getProperty("local.server.port"));
		return productRepo.findAll();
	}

	public Product addProduct(Product product)
	{
		log.info("In Product Service, response from port :"+env.getProperty("local.server.port"));
		return productRepo.save(product);
	}

	public Product updateProductByCode(String code, Product product)
	{
		log.info("In Product Service, response from port :"+env.getProperty("local.server.port"));
		Product productFromDB = productRepo.findProductByCode(code)
				.orElseThrow(() -> new ProductNotFoundException("Product with code '"+code+"' not found, please add the product first."));

		product.setId(productFromDB.getId());
		product.setCode(code);
		log.info("Product to update:\n"+gson.toJson(product));
		BeanUtils.copyProperties(product, productFromDB);

		return productRepo.save(product); 	
	}
}

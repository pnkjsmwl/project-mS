package com.product.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.product.entity.Contact;
import com.product.entity.Product;
import com.product.entity.ProductInventory;
import com.product.exception.ProductNotFoundException;
import com.product.repo.ProductRepo;
import com.product.utils.CustomThreadLocalHolder;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ProductService {

	private final ProductRepo productRepo;
	private final InventoryServiceClient inventoryServiceClient;
	private final ContactServiceClient contactServiceClient;
	private final Environment env;
	private final Gson gson;

	@Autowired
	public ProductService(ProductRepo productRepo, Environment env, Gson gson, InventoryServiceClient inventoryServiceClient, ContactServiceClient contactServiceClient)
	{
		this.productRepo = productRepo;
		this.env = env;
		this.gson = gson;
		this.inventoryServiceClient = inventoryServiceClient;
		this.contactServiceClient = contactServiceClient;
	}

	public Optional<Product> findProductByCode(String productCode) throws ProductNotFoundException
	{
		log.info("In Product Service, response from port :"+env.getProperty("local.server.port"));
		String correlationId = UUID.randomUUID().toString();
		CustomThreadLocalHolder.setCorrelationId(correlationId);
		ThreadLocal<String> threadLocal = new ThreadLocal<>();
		threadLocal.set(correlationId);

		log.info("Before CorrelationID : "+threadLocal.get()+" , "+CustomThreadLocalHolder.getCorrelationId());
		Optional<Product> product = productRepo.findProductByCode(productCode);
		log.info("After CorrelationID : "+threadLocal.get()+" , "+CustomThreadLocalHolder.getCorrelationId());
		if(product.isPresent())
		{
			Optional<ProductInventory> productInventory = inventoryServiceClient.getProductInventory(productCode);
			if(productInventory.isPresent())
			{
				log.info("Inventory Data : "+gson.toJson(productInventory.get()));
				product.get().setInStock(productInventory.get().getQuantity() > 0);
				product.get().setStore(productInventory.get().getStore()!=null ? productInventory.get().getStore() : "default");

			}else{
				log.info("Unable to get inventory data !!");
			}

			Optional<Contact> contactData = contactServiceClient.getContactData(productCode);
			if(contactData.isPresent())
			{
				log.info("Contact data : "+gson.toJson(contactData.get()));
				product.get().setContact(contactData.get());

			}else {
				log.info("Unable to get contact data !!");
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

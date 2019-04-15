package com.consumer.client;

import java.util.Arrays;
import java.util.List;

import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import com.consumer.entity.Product;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ConsumerGETClient {

	static final String GET_PRODUCTS_ALL_URL = "http://product-service/demo/products/";

	static RestTemplate rt = new RestTemplate();

	public static void main(String[] args) {

		getDataAsEntity();
		//getDataAsString();
	}

	private static void getDataAsEntity() {

		HttpHeaders headers = new HttpHeaders();
		List<MediaType> mediaTypeList = Arrays.asList(MediaType.APPLICATION_JSON);

		headers.setAccept(mediaTypeList);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

		ResponseEntity<List<Product>> response = rt.exchange(GET_PRODUCTS_ALL_URL, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<List<Product>>() {});

		// 200 OK
		if(response.getStatusCode().equals(HttpStatus.OK))
		{
			List<Product> productList = response.getBody();

			if(!productList.isEmpty())
			{
				for(Product prod : productList)
				{
					log.info(prod.getCode()+" - "+prod.getDescription());
				}
			}
		}
	}


	private static void getDataAsString() {

		HttpHeaders headers = new HttpHeaders();
		List<MediaType> mediaTypeList = Arrays.asList(new MediaType[] {MediaType.APPLICATION_JSON});

		headers.setAccept(mediaTypeList);
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<String> requestEntity = new HttpEntity<String>(headers);

		//log.info(rt.getForObject(GET_PRODUCTS_ALL_URL, String.class));
		ResponseEntity<String> response = rt.exchange(GET_PRODUCTS_ALL_URL, HttpMethod.GET, requestEntity, String.class);

		log.info(response.getHeaders().toString()+"\n"+response.getStatusCode()+"\n"+response.getStatusCodeValue());
		log.info("Result:\n "+response.getBody());




		/*Gson gson = new GsonBuilder().setPrettyPrinting().create();
		log.info("Product to add:\n"+gson.toJson(rt.getForObject(GET_PRODUCTS_ALL_URL, String.class)));*/
	}



}

package com.product.service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.product.entity.ProductInventory;

@FeignClient(name="inventory-service")//, path="/inventory") // check how to configure url property, url="${feign.client.url}")
@RequestMapping("/inventory")
/* We only need to autowire this Proxy, the implementation will be created by Spring during runtime */
public interface InventoryServiceFeignProxy {

	
	/* When using @PathVariable for Feign Client, always use value property or it will give you the error java.lang.IllegalStateException: PathVariable annotation was 
	 * empty on param 0 */
	@GetMapping("/code/{code}")
	public ResponseEntity<ProductInventory> getProductForCode(@PathVariable (value="code") String code);
}

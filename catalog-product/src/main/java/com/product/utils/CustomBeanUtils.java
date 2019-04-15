package com.product.utils;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import lombok.extern.slf4j.Slf4j;

//@Component
@Slf4j
public class CustomBeanUtils {

	
//	@Bean
	public String[] returnEmptyPropertiesName(Object source)
	{
		final BeanWrapper wrapper = new BeanWrapperImpl(source);
		
		PropertyDescriptor[] props = wrapper.getPropertyDescriptors(); 
		Set<String> propSet = new HashSet<>();
		
		for(PropertyDescriptor prop : props)
		{
			Object sourceValue = wrapper.getPropertyValue(prop.getName());
			log.info("sourceValue : "+sourceValue);
			
			if(sourceValue==null)
				propSet.add(prop.getName());
		}
		return (String[]) propSet.toArray();
		
	}
	
}

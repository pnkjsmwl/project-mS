package com.product.exception;

import java.net.MalformedURLException;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.product.entity.ApiError;

import lombok.extern.slf4j.Slf4j;

@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {


	/* Product Not found */
	@ExceptionHandler(ProductNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	protected ResponseEntity<ApiError> handleProductNotFoundOnValidEntryData(RuntimeException ex, WebRequest webReq, HttpServletRequest request)
	{
		ProductNotFoundException pnfe = (ProductNotFoundException) ex;
		log.info("Handle "+pnfe.getClass().getSimpleName());
		return buildResponseEntity(new ApiError(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), pnfe.getMessage(), request.getRequestURL().toString(), pnfe));
	}

	/* Malformed URL -> Incorrect path */
	@ExceptionHandler(MalformedURLException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	protected ResponseEntity<ApiError> handleMalformedURL(MalformedURLException ex, WebRequest webReq, HttpServletRequest request)
	{
		log.info("Handle "+ex.getClass().getSimpleName());
		return buildResponseEntity(new ApiError(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), ex.getMessage(), request.getRequestURL().toString(), ex));
	}

	private ResponseEntity<ApiError> buildResponseEntity(ApiError apiError) 
	{
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<>(apiError, apiError.getStatus());
	}

}

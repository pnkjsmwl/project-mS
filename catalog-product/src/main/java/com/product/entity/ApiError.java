package com.product.entity;

import java.time.LocalDateTime;
import java.time.ZoneId;

import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ApiError {

	private HttpStatus status;
	private int statusCode;
	@JsonFormat(shape = JsonFormat.Shape.STRING)
	private String timestamp;
	private String message;
	private String debugMessage;
	private String url;
	//private List<ApiSubError> subErrors;

	private ApiError() {
		timestamp = LocalDateTime.now().atZone(ZoneId.of("US/Central")).toString();
	}

	public ApiError(HttpStatus status) {
		this();
		this.status = status;
	}

	public ApiError(HttpStatus status, Throwable ex) {
		this();
		this.status = status;
		this.message = "Unexpected error";
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ApiError(HttpStatus status, String message, Throwable ex) {
		this();
		this.status = status;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
	}

	public ApiError(HttpStatus status, int statusCode, String message, String url, Throwable ex) {
		this();
		this.status = status;
		this.statusCode = statusCode;
		this.message = message;
		this.debugMessage = ex.getLocalizedMessage();
		this.url = url;
	}
}

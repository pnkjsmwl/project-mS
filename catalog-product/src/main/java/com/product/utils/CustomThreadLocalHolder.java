package com.product.utils;

public class CustomThreadLocalHolder {

	private static final ThreadLocal<String> CORRELATION_ID = new ThreadLocal<>();

	public static String getCorrelationId() {
		return CORRELATION_ID.get();
	}
	public static void setCorrelationId(String id) {
		CORRELATION_ID.set(id);
	}

}

package com.store.exception;

public class InventoryStoreNotFoundException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InventoryStoreNotFoundException() {
	}

	public InventoryStoreNotFoundException(String message) {
		super(message);
	}

	public InventoryStoreNotFoundException(String message, Throwable cause) {
		super(message, cause);
	}

	public InventoryStoreNotFoundException(Throwable cause) {
		super(cause);
	}
}

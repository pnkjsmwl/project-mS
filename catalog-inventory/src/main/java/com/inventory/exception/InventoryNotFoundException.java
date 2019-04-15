package com.inventory.exception;

public class InventoryNotFoundException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InventoryNotFoundException() {
    }
 
    public InventoryNotFoundException(String message) {
        super(message);
    }
 
    public InventoryNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
 
    public InventoryNotFoundException(Throwable cause) {
        super(cause);
    }
}

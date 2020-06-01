package com.minko.myshop.exception;

public class InternalServerErrorException extends RuntimeException {

	private static final long serialVersionUID = 921420114283008384L;

	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause);
	}

	public InternalServerErrorException(String message) {
		super(message);
	}

	public InternalServerErrorException(Throwable cause) {
		super(cause);
	}
	
	
}

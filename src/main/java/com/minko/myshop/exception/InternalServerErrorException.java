package com.minko.myshop.exception;

import javax.servlet.http.HttpServletResponse;

public class InternalServerErrorException extends AbstractException {

	private static final long serialVersionUID = 921420114283008384L;

	public InternalServerErrorException(String message, Throwable cause) {
		super(message, cause, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	public InternalServerErrorException(String message) {
		super(message, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}

	public InternalServerErrorException(Throwable cause) {
		super(cause, HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
	}
	
	
}

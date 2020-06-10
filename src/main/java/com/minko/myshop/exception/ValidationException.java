package com.minko.myshop.exception;

import javax.servlet.http.HttpServletResponse;

public class ValidationException extends AbstractException {
	private static final long serialVersionUID = -6843925636139273536L;

	public ValidationException(String s) {
		super(s, HttpServletResponse.SC_BAD_REQUEST);
	}
}

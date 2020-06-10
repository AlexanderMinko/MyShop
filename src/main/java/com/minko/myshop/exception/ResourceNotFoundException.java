package com.minko.myshop.exception;

import javax.servlet.http.HttpServletResponse;

public class ResourceNotFoundException extends AbstractException {

	private static final long serialVersionUID = -2721877457105371704L;
	
	public ResourceNotFoundException(String s) {
		super(s, HttpServletResponse.SC_NOT_FOUND);
	}
}

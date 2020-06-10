package com.minko.myshop.exception;

import javax.servlet.http.HttpServletResponse;

public class AccessDeniedException extends AbstractException {

	private static final long serialVersionUID = 3089830036691531532L;
	
	public AccessDeniedException(String s) {
		super(s, HttpServletResponse.SC_FORBIDDEN);
	}
}

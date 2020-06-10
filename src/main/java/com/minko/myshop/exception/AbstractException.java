package com.minko.myshop.exception;

public class AbstractException extends IllegalArgumentException {

	private static final long serialVersionUID = -8972037752530572497L;
	private final int code;
	
	public AbstractException(String s, int code) {
		super(s);
		this.code = code;
	}

	public AbstractException(Throwable cause, int code) {
		super(cause);
		this.code = code;
	}

	public AbstractException(String message, Throwable cause, int code) {
		super(message, cause);
		this.code = code;
	}

	public int getCode() {
		return code;
	}
}

package com.minko.myshop.entity;

public class Account extends AbstractEntity<Integer> {

	private static final long serialVersionUID = -2161083408003040924L;
	private String name;
	private String email;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return String.format("Account [name=%s, email=%s]", getId(), name, email);
	}

}

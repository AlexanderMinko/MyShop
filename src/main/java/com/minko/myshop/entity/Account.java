package com.minko.myshop.entity;

import com.minko.myshop.model.CurrentAccount;

public class Account extends AbstractEntity<Integer> implements CurrentAccount {

	private static final long serialVersionUID = -2161083408003040924L;
	private String name;
	private String email;

	public Account() {

	}

	public Account(String name, String email) {
		this.name = name;
		this.email = email;
	}

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
	public String getDescription() {
		return name + "(" + email + ")";
	}

	@Override
	public String toString() {
		return String.format("Account [id=%s, name=%s, email=%s]", getId(), name, email);
	}

}

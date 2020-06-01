package com.minko.myshop.model;

import java.io.Serializable;

import com.minko.myshop.entity.Product;

public class ShoppingCartItem implements Serializable{
	private static final long serialVersionUID = 2339969828646609745L;
	private Product product;
	private int count;
	
	public ShoppingCartItem() {
		super();
	}
	
	public ShoppingCartItem(Product product, int count) {
		super();
		this.product = product;
		this.count = count;
	}

	public int getCount() {
		return count;
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public void setCount(int count) {
		this.count = count;
	}

	@Override
	public String toString() {
		return String.format("ShoppingCartItem [product=%s, count=%s]", product, count);
	}
}

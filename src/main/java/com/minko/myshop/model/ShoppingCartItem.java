package com.minko.myshop.model;

import java.io.Serializable;

public class ShoppingCartItem implements Serializable{
	private static final long serialVersionUID = 2339969828646609745L;
	private int idProduct;
	private int count;
	
	public ShoppingCartItem() {
		super();
	}
	
	public ShoppingCartItem(int idProduct, int count) {
		super();
		this.setIdProduct(idProduct);
		this.setCount(count);
	}

	public int getIdProduct() {
		return idProduct;
	}

	public void setIdProduct(int idProduct) {
		this.idProduct = idProduct;
	}

	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}
	
	@Override
	public String toString() {
		return "ShoppingCartItem [idProduct=" + idProduct + ", count=" + count + "]";
		
	}

}

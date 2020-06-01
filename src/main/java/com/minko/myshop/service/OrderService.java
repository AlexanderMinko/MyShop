package com.minko.myshop.service;

import com.minko.myshop.form.ProductForm;
import com.minko.myshop.model.ShoppingCart;

public interface OrderService {
	
	void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);

	void removeProductFromShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);
	
}

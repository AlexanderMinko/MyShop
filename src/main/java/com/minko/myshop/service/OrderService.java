package com.minko.myshop.service;

import java.util.List;

import com.minko.myshop.entity.Order;
import com.minko.myshop.form.ProductForm;
import com.minko.myshop.model.CurrentAccount;
import com.minko.myshop.model.ShoppingCart;
import com.minko.myshop.model.SocialAccount;

public interface OrderService {
	
	void addProductToShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);

	void removeProductFromShoppingCart(ProductForm productForm, ShoppingCart shoppingCart);
	
	String serializeShoppingCart(ShoppingCart shoppingCart);
	
	ShoppingCart deserializeShoppingCart(String string);
	
	CurrentAccount authentificate(SocialAccount socialAccount);

	long makeOrder(ShoppingCart shoppingCart, CurrentAccount currentAccount);
	
	Order findOrderItemsById(long id, CurrentAccount currentAccount);
	
	List<Order> listOrders(CurrentAccount currentAccount, int pange, int limit); 
	
	int countMyOrders(CurrentAccount currentAccount);
}

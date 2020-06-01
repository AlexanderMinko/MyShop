package com.minko.myshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.model.ShoppingCart;
import com.minko.myshop.util.SessionUtils;

@WebFilter(filterName="AutoRestoreShoppingCartFilter")
public class AutoRestoreShoppingCartFilter extends AbstractFilter{
	private static final String SHOPPING_CARD_DESERIALIZATION_DONE = "SHOPPING_CARD_DESERIALIZATION_DONE";


	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		if(req.getSession().getAttribute(SHOPPING_CARD_DESERIALIZATION_DONE) == null) {
			if(req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART) != null) {	
				Cookie cookie = null;
				Cookie[] cookies = req.getCookies();
				for(Cookie c : cookies) {
					if(cookie.getName().equals(Constants.Cookie.SHOPPING_CART.getName())) {
						cookie = c;
					}
				}
				if(cookie != null) {
					ShoppingCart shoppingCart = shoppingCartFromString(cookie.getValue());
					SessionUtils.setCurrentShoppingCart(req, shoppingCart);
				}
			}
			req.getSession().setAttribute(SHOPPING_CARD_DESERIALIZATION_DONE, Boolean.TRUE);
		}
		chain.doFilter(req, resp);
	}
	
//	findCookie(req, Constants.Cookie.SHOPPING_CART.getName());
//	public Cookie findCookie(HttpServletRequest req, String cookieName) {
//		Cookie[] cookies = req.getCookies();
//		if (cookies != null) {
//			for (Cookie c : cookies) {
//				if (c.getName().equals(cookieName)) {
//					if (c.getValue() != null && !"".equals(c.getValue())) {
//						return c;
//					}
//				}
//			}
//		}
//		return null;
//	}
	

	
	protected ShoppingCart shoppingCartFromString(String cookieValue) {
		ShoppingCart shoppingCart = new ShoppingCart();
		String[] items = cookieValue.split("\\|");
		for (String item : items) {
			String data[] = item.split("-");
			try {
				int idProduct = Integer.parseInt(data[0]);
				int count = Integer.parseInt(data[1]);
//				shoppingCart.addProduct(idProduct, count);
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		}
		return shoppingCart;
	}


}

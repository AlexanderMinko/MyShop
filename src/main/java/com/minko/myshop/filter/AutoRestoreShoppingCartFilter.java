package com.minko.myshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.model.ShoppingCart;
import com.minko.myshop.service.OrderService;
import com.minko.myshop.service.impl.ServiceManager;

@WebFilter(filterName = "AutoRestoreShoppingCartFilter")
public class AutoRestoreShoppingCartFilter extends AbstractFilter {
	private static final String SHOPPING_CARD_DESERIALIZATION_DONE = "SHOPPING_CARD_DESERIALIZATION_DONE";

	private OrderService orderService;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		orderService = ServiceManager.getInstance(filterConfig.getServletContext()).getOrderService();
	}

	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		if (req.getSession().getAttribute(SHOPPING_CARD_DESERIALIZATION_DONE) == null) {
			if (req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART) == null) {
				Cookie[] cookies = req.getCookies();
				if (cookies != null) {
					for (Cookie cookie : cookies) {
						if (cookie.getName().equals(Constants.Cookie.SHOPPING_CART.getName())) {
							ShoppingCart shoppingCart = orderService.deserializeShoppingCart(cookie.getValue());
							req.getSession().setAttribute(Constants.CURRENT_SHOPPING_CART, shoppingCart);
						}
					}
				}
			}
			req.getSession().setAttribute(SHOPPING_CARD_DESERIALIZATION_DONE, Boolean.TRUE);
		}
		chain.doFilter(req, resp);
	}
}

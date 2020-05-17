package com.minko.myshop.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

import com.minko.myshop.service.OrderService;
import com.minko.myshop.service.ProductService;
import com.minko.myshop.service.impl.ServiceManager;

public abstract class AbstractController extends HttpServlet{

	private static final long serialVersionUID = 8618938402091288198L;
	
	private ProductService productService;
	private OrderService orderService;
	
	public ProductService getProductService() {
		return productService;
	}

	public OrderService getOrderService() {
		return orderService;
	}
	
	@Override
	public final void init() throws ServletException {
		productService = ServiceManager.getInstance(getServletContext()).getProductService();
		orderService = ServiceManager.getInstance(getServletContext()).getOrderService();
	}

}

package com.minko.myshop.servlet;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;

import com.minko.myshop.form.ProductForm;
import com.minko.myshop.form.SearchForm;
import com.minko.myshop.service.OrderService;
import com.minko.myshop.service.ProductService;
import com.minko.myshop.service.impl.ServiceManager;

public class AbstractController extends HttpServlet{

	private static final long serialVersionUID = 8618938402091288198L;
	
	private ProductService productService;
	private OrderService orderService;
	
	public ProductService getProductService() {
		return productService;
	}

	public OrderService getOrderService() {
		return orderService;
	}
	
	public final int getPageCount(int totalItems, int itemsPerPage) {
		int result = totalItems / itemsPerPage;
		if(result * itemsPerPage != totalItems) {
			result++;
		}
		return result;
	}
	
	public final int getPage(HttpServletRequest request) {
		try {
			return Integer.parseInt(request.getParameter("page"));
		} catch (NumberFormatException e) {
			return 1;
		}
	}
	
	@Override
	public final void init() throws ServletException {
		productService = ServiceManager.getInstance(getServletContext()).getProductService();
		orderService = ServiceManager.getInstance(getServletContext()).getOrderService();
	}
	
	public final SearchForm createSearchForm(HttpServletRequest request) {
		return new SearchForm(
				request.getParameter("query"),
				request.getParameterValues("category"),
				request.getParameterValues("producer"));
	}
	
	public final ProductForm createProductForm(HttpServletRequest request) {
		return new ProductForm(
				Integer.parseInt(request.getParameter("idProduct")),
				Integer.parseInt(request.getParameter("count")));
	}
}

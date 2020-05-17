package com.minko.myshop.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.ServletContext;

import com.minko.myshop.service.OrderService;
import com.minko.myshop.service.ProductService;

public class ServiceManager {
	public static ServiceManager getInstance(ServletContext context) {
		ServiceManager instance = (ServiceManager) context.getAttribute("SERVICE_MANAGER");
		if (instance == null) {
			instance = new ServiceManager(context);
			context.setAttribute("SERVICE_MANAGER", instance);
		}
		return instance;
	}
	
	public ProductService getProductService() {
		return productService;
	}

	public OrderService getOrderService() {
		return orderService;
	}
	
	public String getApplecationProperty(String key) {
		return applicationProperties.getProperty(key);	
	}

	public void close() {
	}
	
	private final Properties applicationProperties = new Properties();
	private final ProductService productService;
	private final OrderService orderService;

	private ServiceManager(ServletContext context) {
		loadApplicationProperties();
		productService = new ProductServiceImpl();
		orderService = new OrderServiceImpl();
	}
	
	private void loadApplicationProperties() {
		try(InputStream in = ServiceManager.class.getClassLoader().getResourceAsStream("application.properties")){
			applicationProperties.load(in);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}

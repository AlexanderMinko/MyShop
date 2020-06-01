package com.minko.myshop.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletContext;

import org.apache.commons.dbcp2.BasicDataSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minko.myshop.service.OrderService;
import com.minko.myshop.service.ProductService;

public class ServiceManager {
	private static final Logger LOGGER = LoggerFactory.getLogger(ServiceManager.class);
	
	private final Properties applicationProperties = new Properties();
	private final BasicDataSource dataSource;
	private final ProductService productService;
	private final OrderService orderService;
	
	private ServiceManager(ServletContext context) {
		loadApplicationProperties();
		dataSource = createDataSource();
		productService = new ProductServiceImpl(dataSource);
		orderService = new OrderServiceImpl(dataSource);
	}
	
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
		try {
			dataSource.close();
		} catch (SQLException e) {
			LOGGER.error("Close datasource failed: " + e.getMessage() + e);
		}
	}
	
	private BasicDataSource createDataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDefaultAutoCommit(false);
		dataSource.setRollbackOnReturn(true);
		dataSource.setDriverClassName(getApplecationProperty("db.driver"));
		dataSource.setUrl(getApplecationProperty("db.url"));
		dataSource.setUsername(getApplecationProperty("db.username"));
		dataSource.setPassword(getApplecationProperty("db.password"));
		dataSource.setInitialSize(Integer.parseInt(getApplecationProperty("db.pool.initSize")));
		dataSource.setMaxTotal(Integer.parseInt(getApplecationProperty("db.pool.maxSize")));
		return dataSource;
	}
	
	private void loadApplicationProperties() {
		try(InputStream in = ServiceManager.class.getClassLoader().getResourceAsStream("application.properties")){
			applicationProperties.load(in);
		}catch(IOException e) {
			throw new RuntimeException(e);
		}
	}
}

package com.minko.myshop.listener;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.minko.myshop.Constants;
import com.minko.myshop.service.impl.ServiceManager;

@WebListener
public class MyShopApplicationListener implements ServletContextListener {
	private static final Logger LOGGER = LoggerFactory.getLogger(MyShopApplicationListener.class);
	private ServiceManager serviceManager;
	@Override
	public void contextInitialized(ServletContextEvent sce) {
		try {
			serviceManager = ServiceManager.getInstance(sce.getServletContext());
			sce.getServletContext().setAttribute(Constants.PRODUCERS, serviceManager.getProductService().listAllProducers());
			sce.getServletContext().setAttribute(Constants.CATEGORIES, serviceManager.getProductService().listAllCategories());
		} catch (RuntimeException e) {
			LOGGER.error("Web application 'myshop' init failed: " + e.getMessage(), e);
			throw e;
		}
		LOGGER.info("Web application 'myshop' initialized");
	}
	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		serviceManager.close();
		LOGGER.info("Web application 'myshop' destroyed");
	}
}
package com.minko.myshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;

@WebFilter(filterName = "SaveCurrentPageFilter", urlPatterns={ "/*" })
public class SaveCurrentPageFilter extends AbstractFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		String url = currentUrl(req);
		req.setAttribute(Constants.CURRENT_URL, url);
		chain.doFilter(req, resp);
	}
	
	public static String currentUrl(HttpServletRequest req) {
		String currentUrl;
		String query = req.getQueryString();
		if (query == null) {
			currentUrl = req.getRequestURI();
		} else {
			currentUrl = req.getRequestURI() + "?" + query;
		} 	
		return currentUrl;
	}
}

package com.minko.myshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;

@WebFilter(filterName = "AuthFilter", urlPatterns={ "/my-orders", "/order", "/ajax/html/more/my-orders" })
public class AuthFilter extends AbstractFilter {

	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		if (req.getSession().getAttribute(Constants.CURRENT_ACCOUNT) != null) {
			chain.doFilter(req, resp);
		} else {
			String url = SaveCurrentPageFilter.currentUrl(req);
			if (url.startsWith("/ajax/")) {
				resp.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
				resp.getWriter().print("401");
			} else {			
				req.getSession().setAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN, url);
				resp.sendRedirect("/sign-in");
			}
		}
	}
}

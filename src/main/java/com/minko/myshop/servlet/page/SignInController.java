package com.minko.myshop.servlet.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.servlet.AbstractController;

@WebServlet("/sign-in")
public class SignInController extends AbstractController{

	private static final long serialVersionUID = 7659608121992003958L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute(Constants.CURRENT_ACCOUNT) != null) {
			resp.sendRedirect("/my-orders");
		} else {
			req.setAttribute("currentPage", "page/sign-in.jsp");
			req.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(req, resp);
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute(Constants.CURRENT_ACCOUNT) != null) {
			resp.sendRedirect("/my-orders");
		} else {
			String targetUrl = req.getParameter("target");
			if(targetUrl != null) {
				req.getSession().setAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN, targetUrl);
			}
			resp.sendRedirect(getSocialService().getAutorizeUrl());
		}
	}
}

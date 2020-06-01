package com.minko.myshop.servlet.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.servlet.AbstractController;

@WebServlet("/error")
public class ErrorController extends AbstractController {

	private static final long serialVersionUID = 4648245845695963977L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		//req.setAttribute("currentPage", "page/error.jsp");
		req.getRequestDispatcher("WEB-INF/JSP/page/error.jsp").forward(req, resp);
	}

}

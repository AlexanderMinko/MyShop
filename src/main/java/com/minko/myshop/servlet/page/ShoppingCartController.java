package com.minko.myshop.servlet.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.servlet.AbstractController;

@WebServlet("/shopping-cart")
public class ShoppingCartController extends AbstractController {

	private static final long serialVersionUID = 7662073721431366287L;
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if(req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART) != null) {
			req.setAttribute("currentPage", "page/shopping-cart.jsp");
			req.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(req, resp);
		} else {
			resp.sendRedirect("/products");
		}
	}
}

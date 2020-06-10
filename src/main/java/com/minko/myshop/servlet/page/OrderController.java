package com.minko.myshop.servlet.page;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.entity.Order;
import com.minko.myshop.model.CurrentAccount;
import com.minko.myshop.model.ShoppingCart;
import com.minko.myshop.servlet.AbstractController;

@WebServlet("/order")
public class OrderController extends AbstractController {

	private static final long serialVersionUID = 7812667388074755693L;
	private static final String CURRENT_MESSAGE = "CURRENT_MESSAGE";
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String message = (String) req.getSession().getAttribute(CURRENT_MESSAGE);
		req.getSession().removeAttribute(CURRENT_MESSAGE);
		req.setAttribute(CURRENT_MESSAGE, message);
		long idOrder = Long.parseLong(req.getParameter("id"));
		CurrentAccount currentAccount = (CurrentAccount) req.getSession().getAttribute(Constants.CURRENT_ACCOUNT);
		Order order = getOrderService().findOrderItemsById(idOrder, currentAccount);
		req.setAttribute("order", order);
		req.setAttribute("currentPage", "page/order.jsp");
		req.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ShoppingCart shoppingCart = (ShoppingCart) req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART);
		CurrentAccount currentAccount = (CurrentAccount) req.getSession().getAttribute(Constants.CURRENT_ACCOUNT);
		long idOrder = getOrderService().makeOrder(shoppingCart, currentAccount);
		
		req.getSession().removeAttribute(Constants.CURRENT_SHOPPING_CART);
		Cookie cookie = new Cookie(Constants.Cookie.SHOPPING_CART.getName(), null);
		cookie.setMaxAge(0);
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		resp.addCookie(cookie);
		
		req.getSession().setAttribute(CURRENT_MESSAGE, "Order created, please, wait");
		resp.sendRedirect("/order?id=" + idOrder);
	}

}

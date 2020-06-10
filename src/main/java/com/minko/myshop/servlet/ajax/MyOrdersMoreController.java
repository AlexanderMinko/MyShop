package com.minko.myshop.servlet.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.entity.Order;
import com.minko.myshop.model.CurrentAccount;
import com.minko.myshop.servlet.AbstractController;

@WebServlet("/ajax/html/more/my-orders")
public class MyOrdersMoreController extends AbstractController {
	private static final long serialVersionUID = 6696788607397758290L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CurrentAccount currentAccount = (CurrentAccount) req.getSession().getAttribute(Constants.CURRENT_ACCOUNT);
		List<Order> orders = getOrderService().listOrders(currentAccount, getPage(req), Constants.ORDERS_PER_PAGE);
		req.setAttribute("orders", orders);
		req.getRequestDispatcher("/WEB-INF/JSP/fragment/my-orders-tbody.jsp").forward(req, resp);
	}
}
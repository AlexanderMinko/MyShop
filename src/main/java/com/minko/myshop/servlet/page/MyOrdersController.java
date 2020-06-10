package com.minko.myshop.servlet.page;

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

@WebServlet("/my-orders")
public class MyOrdersController extends AbstractController {

	private static final long serialVersionUID = 7812667388074755693L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		CurrentAccount currentAccount = (CurrentAccount) req.getSession().getAttribute(Constants.CURRENT_ACCOUNT);
		List<Order> orders = getOrderService().listOrders(currentAccount, 1, Constants.ORDERS_PER_PAGE);
		req.setAttribute("orders", orders);
		int orderCount = getOrderService().countMyOrders(currentAccount);
		req.setAttribute("pageCount", getPageCount(orderCount, Constants.ORDERS_PER_PAGE));
		req.setAttribute("currentPage", "page/my-orders.jsp");
		req.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(req, resp);
	}

}

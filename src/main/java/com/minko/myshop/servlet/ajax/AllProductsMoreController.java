package com.minko.myshop.servlet.ajax;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.entity.Product;
import com.minko.myshop.servlet.AbstractController;

@WebServlet("/ajax/html/more/products")
public class AllProductsMoreController extends AbstractController {

	private static final long serialVersionUID = 6696788607397758290L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Product> products = getProductService().listAllProducts(getPage(req), Constants.MAX_PRODUCTS_PER_PAGE);
		req.setAttribute("products", products);
		req.getRequestDispatcher("/WEB-INF/JSP/fragment/product-list.jsp").forward(req, resp);
	}
}
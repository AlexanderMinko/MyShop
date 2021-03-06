package com.minko.myshop.servlet.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.entity.Product;
import com.minko.myshop.servlet.AbstractController;

@WebServlet("/products")
public class AllProductsController extends AbstractController{

	private static final long serialVersionUID = 6696788607397758290L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		List<Product> products = getProductService().listAllProducts(1, Constants.MAX_PRODUCTS_PER_PAGE);
		req.setAttribute("products", products);
		int totalItems = getProductService().countAllProducts();
		req.setAttribute("pageCount", getPageCount(totalItems, Constants.MAX_PRODUCTS_PER_PAGE));
		req.setAttribute("currentPage", "page/products.jsp");
		req.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(req, resp);
	}

}

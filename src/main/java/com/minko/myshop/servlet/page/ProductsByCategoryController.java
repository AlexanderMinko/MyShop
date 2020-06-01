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

@WebServlet("/products/*")
public class ProductsByCategoryController extends AbstractController {

	private static final long serialVersionUID = 8263857220032001984L;
	private static final int SUBSTRING_INDEX = "/products".length();
	
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String categoryUrl = req.getRequestURI().substring(SUBSTRING_INDEX);
		List<Product> products = getProductService().listProductsByCategory(categoryUrl, 1, Constants.MAX_PRODUCTS_PER_PAGE);
		req.setAttribute("products", products);
		int totalItems = getProductService().countProductsByCategory(categoryUrl);
		System.out.println(totalItems);
		req.setAttribute("pageCount", getPageCount(totalItems, Constants.MAX_PRODUCTS_PER_PAGE));
		req.setAttribute("selectedCategoryUrl", categoryUrl);
		req.setAttribute("currentPage", "page/products.jsp");
		req.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(req, resp);
	}
	
}

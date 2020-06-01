package com.minko.myshop.servlet.page;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.entity.Product;
import com.minko.myshop.form.SearchForm;
import com.minko.myshop.servlet.AbstractController;

@WebServlet("/search")
public class SearchController extends AbstractController {

	private static final long serialVersionUID = -3188596115205683240L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		SearchForm searchForm = createSearchForm(req);	
		List<Product> products = getProductService().listProductsBySearchForm(searchForm, 1, Constants.MAX_PRODUCTS_PER_PAGE);
		req.setAttribute("products", products);		
		int totalItems = getProductService().countProductsBySearchForm(searchForm);
		req.setAttribute("pageCount", getPageCount(totalItems, Constants.MAX_PRODUCTS_PER_PAGE));		
		req.setAttribute("productCount", totalItems);
		req.setAttribute("searchForm", searchForm);
		req.setAttribute("currentPage", "page/search-result.jsp");
		req.getRequestDispatcher("WEB-INF/JSP/page-template.jsp").forward(req, resp);
	}
}

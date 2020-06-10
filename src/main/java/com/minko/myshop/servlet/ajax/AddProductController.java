package com.minko.myshop.servlet.ajax;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.minko.myshop.Constants;
import com.minko.myshop.form.ProductForm;
import com.minko.myshop.model.ShoppingCart;
import com.minko.myshop.servlet.AbstractController;

@WebServlet("/ajax/json/product/add")
public class AddProductController extends AbstractController {

	private static final long serialVersionUID = -3579921293788980517L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("\n" + req.getRequestURI());
		ProductForm productForm = createProductForm(req);
		
		ShoppingCart shoppingCart = (ShoppingCart) req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART);
		if(shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			req.getSession().setAttribute(Constants.CURRENT_SHOPPING_CART, shoppingCart);
		}
		getOrderService().addProductToShoppingCart(productForm, shoppingCart);
		
		String cookieValue = getOrderService().serializeShoppingCart(shoppingCart);
		Cookie cookie = new Cookie(Constants.Cookie.SHOPPING_CART.getName(), cookieValue);
		cookie.setMaxAge(Constants.Cookie.SHOPPING_CART.getTtl());
		cookie.setPath("/");
		cookie.setHttpOnly(true);
		resp.addCookie(cookie);
		
		JSONObject json = new JSONObject();
		json.put("totalCount", shoppingCart.getTotalCount());
		json.put("totalCost", shoppingCart.getTotalCost());
		
		resp.setContentType("application/json");
		resp.getWriter().println(json.toString());
		resp.getWriter().close();
	}
}

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

@WebServlet("/ajax/json/product/remove")
public class RemoveProductController extends AbstractController {

	private static final long serialVersionUID = 2075133505804760488L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		ProductForm productForm = createProductForm(req);

		ShoppingCart shoppingCart = (ShoppingCart) req.getSession().getAttribute(Constants.CURRENT_SHOPPING_CART);
		if (shoppingCart == null) {
			shoppingCart = new ShoppingCart();
			req.getSession().setAttribute(Constants.CURRENT_SHOPPING_CART, shoppingCart);
		}
		getOrderService().removeProductFromShoppingCart(productForm, shoppingCart);

		if (shoppingCart.getItems().size() == 0) {
			req.getSession().removeAttribute(Constants.CURRENT_SHOPPING_CART);
			
			System.out.println("Attribute CURRENT_SHOPPING_CART removed");
			Cookie cookie = new Cookie(Constants.Cookie.SHOPPING_CART.getName(), null);
			cookie.setHttpOnly(true);
			cookie.setMaxAge(0);
			cookie.setPath("/");
			resp.addCookie(cookie);
		} else {
			String cookieValue = getOrderService().serializeShoppingCart(shoppingCart);
			Cookie cookie = new Cookie(Constants.Cookie.SHOPPING_CART.getName(), cookieValue);
			cookie.setHttpOnly(true);
			cookie.setMaxAge(Constants.Cookie.SHOPPING_CART.getTtl());
			cookie.setPath("/");
			resp.addCookie(cookie);
			System.out.println("cookie SHOPPING_CART added");
		}

		JSONObject json = new JSONObject();
		json.put("totalCount", shoppingCart.getTotalCount());
		json.put("totalCost", shoppingCart.getTotalCost());

		resp.setContentType("application/json");
		resp.getWriter().println(json.toString());
		resp.getWriter().close();
	}
}

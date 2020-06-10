package com.minko.myshop.servlet.page;

import java.io.IOException;
import java.net.URLDecoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.minko.myshop.Constants;
import com.minko.myshop.model.CurrentAccount;
import com.minko.myshop.model.SocialAccount;
import com.minko.myshop.servlet.AbstractController;

@WebServlet("/from-social")
public class FromSocialController extends AbstractController {

	private static final long serialVersionUID = 7811952604166047406L;
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String code = req.getParameter("code");
		if(code != null) {
			SocialAccount socialAccount = getSocialService().getSocialAccount(code);
			CurrentAccount currentAccount = getOrderService().authentificate(socialAccount);
			req.getSession().setAttribute(Constants.CURRENT_ACCOUNT, currentAccount);
			String url = (String) req.getSession().getAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN);
			if(url != null) {
				System.out.println("SUCCESS_REDIRECT_URL_AFTER_SIGNIN: " + url);
				req.getSession().removeAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN);
				resp.sendRedirect(URLDecoder.decode(url, "UTF-8"));
			} else {
				resp.sendRedirect("/my-orders");
			}
		} else {
			LOGGER.warn("Parameter code not found");
			if(req.getSession().getAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN) != null){
				req.getSession().removeAttribute(Constants.SUCCESS_REDIRECT_URL_AFTER_SIGNIN);
			}
			resp.sendRedirect("/sign-in");
		}
	}
}

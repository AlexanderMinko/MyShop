package com.minko.myshop.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.json.JSONObject;

import com.minko.myshop.exception.AbstractException;
import com.minko.myshop.exception.AccessDeniedException;
import com.minko.myshop.exception.InternalServerErrorException;
import com.minko.myshop.exception.ResourceNotFoundException;
import com.minko.myshop.exception.ValidationException;
import com.minko.myshop.util.RoutingUtils;
import com.minko.myshop.util.UrlUtils;

@WebFilter(filterName = "ErrorHandlerFilter")
public class ErrorHandlerFilter extends AbstractFilter {
	private static final String INTERNAL_ERROR = "Internal error";
	@Override
	public void doFilter(HttpServletRequest req, HttpServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		try {
			chain.doFilter(req, new ThrowExceptionInsteadOfSendErrorResponse(resp));
		} catch (Throwable th) {
			String requestUrl = req.getRequestURI();
			if (th instanceof ValidationException) {
				LOGGER.warn("Request isn't valid");
			} else {
				LOGGER.error("Request " + requestUrl + " failed: " + th.getMessage(), th);
			}
			handException(requestUrl, th, req, resp);
		}
	}
	
	private int getStatusCode(Throwable th) {
		if(th instanceof AbstractException) {
			return ((AbstractException) th).getCode();
		} else {
			return HttpServletResponse.SC_INTERNAL_SERVER_ERROR;
		}
	}

	private void handException(String requestUrl, Throwable th, HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		int statusCode = getStatusCode(th);

		if (UrlUtils.isAjaxJsonUrl(requestUrl)) {
			JSONObject json = new JSONObject();
			json.put("message", th instanceof ValidationException ? th.getMessage() : INTERNAL_ERROR );
		} else if (UrlUtils.isAjaxHtmlUrl(requestUrl)) {
			RoutingUtils.sendHTMLFragment(INTERNAL_ERROR, req, resp);
		} else {
			req.setAttribute("statusCode", statusCode);
			req.setAttribute("currentPage", "page/error.jsp");
			req.getRequestDispatcher("/WEB-INF/JSP/page-template.jsp").forward(req, resp);
		}
	}
	
	private static class ThrowExceptionInsteadOfSendErrorResponse extends HttpServletResponseWrapper {
		public ThrowExceptionInsteadOfSendErrorResponse(HttpServletResponse response) {
			super(response);
		}
		@Override
		public void sendError(int sc) throws IOException {
			sendError(sc, INTERNAL_ERROR);
		}
		
		@Override
		public void sendError(int sc, String msg) throws IOException {
			switch(sc) {
			case 403 : {
				throw new AccessDeniedException(msg);
			}
			case 404  : {
				throw new ResourceNotFoundException(msg);
			}
			case 400 : {
				throw new ValidationException(msg);
			}
			default :
				throw new InternalServerErrorException(msg);
			}
		}
	}
}
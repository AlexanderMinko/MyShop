package com.minko.myshop.tag;

import java.io.IOException;
import java.net.URLEncoder;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.SimpleTagSupport;

public class URLEncodeTag extends SimpleTagSupport{
	
	private String var;
	private String url;
	
	@Override
	public void doTag() throws JspException, IOException {
		String encodedUrl = URLEncoder.encode(url, "UTF-8");
		getJspContext().setAttribute(var, encodedUrl);
	}

	public String getVar() {
		return var;
	}

	public void setVar(String var) {
		this.var = var;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}

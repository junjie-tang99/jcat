package org.jcat.servlet;

import java.util.HashMap;

public class ServletMapping {
	
	private String servletName;
	private String url;
	private Class<Servlet> clazz;
	
	public ServletMapping(String servletName,String url,Class<Servlet> clazz) {
		this.setServletName(servletName);
		this.setUrl(url);
		this.setClazz(clazz);
	}

	public String getServletName() {
		return servletName;
	}

	public void setServletName(String servletName) {
		this.servletName = servletName;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Class<Servlet> getClazz() {
		return clazz;
	}

	public void setClazz(Class<Servlet> clazz) {
		this.clazz = clazz;
	}
}

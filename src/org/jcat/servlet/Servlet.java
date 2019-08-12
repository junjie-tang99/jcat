package org.jcat.servlet;

import org.jcat.enumeration.RequestType;
import org.jcat.request.HttpRequest;
import org.jcat.response.HttpResponse;

public abstract class Servlet {
	
	public abstract void doGet(HttpRequest request,HttpResponse response);
	
	public abstract void doPost(HttpRequest request,HttpResponse response);
	
	public void service(HttpRequest request,HttpResponse response) {
		if (request.getMethod() == RequestType.GET)
			doGet(request,response);
		else if (request.getMethod() == RequestType.POST)
			doPost(request,response);
	}

}

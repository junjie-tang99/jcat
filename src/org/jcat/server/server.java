package org.jcat.server;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.jcat.classloader.WebappClassLoader;
import org.jcat.context.Context;
import org.jcat.context.ContextEntry;
import org.jcat.request.HttpRequest;
import org.jcat.response.HttpResponse;
import org.jcat.servlet.Servlet;
import org.jcat.servlet.ServletMapping;

public class server {
	int port = 8080;
	String webAppsPath = "./webapps";
	
	Context context =  new Context();
	
	public server() {

	}
	
	public void init() {
		initContext();
	}
	
	private void initContext() {
		context.initContext(webAppsPath);
	}
	
	public void start() {
		System.out.println("JCat is initing...");
		init();
		System.out.println("JCat is opening socket...");
		ServerSocket server =null;
		try {
			server = new ServerSocket(port);
			while(true) {
				Socket socket = server.accept();
				HttpRequest request = new HttpRequest(socket.getInputStream());
				HttpResponse response = new HttpResponse(socket.getOutputStream());
				dispatch(request,response);
				socket.close();
				
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if(server!=null && !server.isClosed()) {
				try {
					server.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	private void dispatch(HttpRequest request,HttpResponse response) {
		if (request == null)
			return;
		if (request.getUrl().equals("/favicon.ico"))
			return;
		try {
			
			String contextName = request.getUrl().split("/")[1];
			ContextEntry entry = context.getContext(contextName);
			if (entry == null) 
				return;
			
			ServletMapping mapping = entry.getServletMapping(request.getUrl());
			if (mapping!=null) {
				Class<Servlet> servletClazz = (Class<Servlet>)mapping.getClazz();
				servletClazz.newInstance().service(request, response);
			}
		} catch (InstantiationException | IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
}

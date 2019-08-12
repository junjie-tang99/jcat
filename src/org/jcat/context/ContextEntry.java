package org.jcat.context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import javax.swing.filechooser.FileFilter;

import org.jcat.classloader.WebappClassLoader;
import org.jcat.listener.ServletContextListener;
import org.jcat.listener.WebappWatchListener;
import org.jcat.servlet.Servlet;
import org.jcat.servlet.ServletMapping;
import org.jcat.util.ClassFileFilter;
import org.jcat.util.WebXMLFileFilter;


public class ContextEntry {
	private WebappClassLoader classLoader = null;
	private Context context;
	private String contextName;
	private String contextPath;
	private String webappPath;
	private String webXMLPath;
	private Set<ServletMapping> servletMappings = null;
	private Set<ServletContextListener> listeners = null;

	
	public ContextEntry(Context context,String contextName,String webappPath) throws MalformedURLException {
		this.context = context;
		this.contextName = contextName;
		this.contextPath = "/"+ contextName;
		this.webappPath = webappPath;
		this.webXMLPath = webappPath+File.separator+"web.xml";
		this.classLoader = new WebappClassLoader(new URL("file:"+webappPath));
	}
	
	public String getWebappPath() {
		return webappPath;
	}

	public void setWebappPath(String webappPath) {
		this.webappPath = webappPath;
	}

	public void initContext() {
		this.servletMappings = loadServletMapping(webXMLPath,contextPath);
		this.listeners = loadListeners(context,contextName,webXMLPath,webappPath);
		startListeners(this.listeners);
	}
	
	public void destoryContext() {
		stopListeners(this.listeners);
	}
	
	private Set<ServletMapping> loadServletMapping(String webXMLPath,String contextPath) {
		Set<ServletMapping> resutl = new HashSet<ServletMapping>();
		
		File webXMLFile = new File(webXMLPath);
		if (webXMLFile == null)
			return resutl;
		
		BufferedReader reader = null;
		try {
			String servletMappingStr = "";
			reader = new BufferedReader(new FileReader(webXMLFile));
			
			while((servletMappingStr = reader.readLine())!=null) {
				String servletUrl = servletMappingStr.split(",")[0];
				String servletName = servletMappingStr.split(",")[1];
				Class<Servlet> clazz = (Class<Servlet>) loadClass(servletName);
				ServletMapping servletMapping = new ServletMapping(servletName,contextPath+servletUrl,clazz);
				resutl.add(servletMapping);
			}
			
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			try {
				reader.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		return resutl;
	}
	
	public Set<ServletContextListener> loadListeners(Context context,String contextName,String webXMLPath,String webappPath){
		Set<ServletContextListener> resutl = new HashSet<ServletContextListener>();
		WebappWatchListener watchListener =  new WebappWatchListener(context,contextName,webappPath);
		resutl.add(watchListener);
		return resutl;
	}
	
	public void startListeners(Set<ServletContextListener> listeners) {
		for (ServletContextListener listener : listeners) {
			listener.contextInitialized(null);
		}
	}
	
	public void stopListeners(Set<ServletContextListener> listeners) {
		for (ServletContextListener listener : listeners) {
			listener.contextDestroyed(null);
		}
	}

	
	public Class<?> loadClass(String className) {
		try {
			return classLoader.loadClass(className);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public void addServletMapping(ServletMapping servletMapping) {
		this.servletMappings.add(servletMapping);
	}
	
	public ServletMapping getServletMapping(String url) {
		ServletMapping rtn = null;
		for (ServletMapping mapping:servletMappings) {
			if (mapping.getUrl().equals(url)) {
				rtn = mapping;
				break;
			}
		}
		return rtn;
	}
	
	public Set<ServletMapping> getAllServletMappings(){
		return servletMappings;
	}
}

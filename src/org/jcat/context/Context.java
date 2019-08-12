package org.jcat.context;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.HashSet;

import org.jcat.listener.ServletContextListener;
import org.jcat.listener.WebappWatchListener;
import org.jcat.servlet.ServletMapping;

public class Context {
	private HashMap<String,ContextEntry> contexts;
	
	public Context() {
		contexts = new HashMap<String,ContextEntry>();
	}
	
	public void initContext(String webAppsPath) {
		File webAppsDir = new File(webAppsPath);
		for(File file : webAppsDir.listFiles()) {
			if (file.isDirectory())
				addContext(file.getName(),file.getAbsolutePath());
		}
	}
	
	public void addContext(String contextName,String webAppPath) {
		ContextEntry entry;
		try {
			entry = new ContextEntry(this,contextName,webAppPath);
			entry.initContext();
			contexts.put(contextName, entry);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void reloadContext(String contextName) {
		if (contexts.containsKey(contextName)) {
			System.out.println("Reloading "+contextName);
			//获取老的Context的信息
			ContextEntry oldWebAppContext = contexts.get(contextName);
			String webAppPath = oldWebAppContext.getWebappPath();
			//对老的Context进行销毁
			oldWebAppContext.destoryContext();
			contexts.remove(contextName);
			//重新载入新的Context信息
			addContext(contextName,webAppPath);
			System.out.println(contextName+" reloaded!");
		}
		
	}
	
	public ContextEntry getContext(String contextName ) {
		return contexts.get(contextName);
	}
}

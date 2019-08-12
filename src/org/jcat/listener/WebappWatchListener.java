package org.jcat.listener;

import java.nio.file.WatchService;

import org.jcat.context.Context;

public class WebappWatchListener implements ServletContextListener {
	FileWatchThread watchThread;
	
	public WebappWatchListener(Context context,String contextName,String webappPath) {
		watchThread = FileWatchThreadFactory.generateThread(context,webappPath,contextName);
	}
	
	@Override
	public void contextInitialized(ServletContextEvent event) {
		// TODO Auto-generated method stub
		System.out.println("WebappWatchListener starting...");
		watchThread.start();
	}

	@Override
	public void contextDestroyed(ServletContextEvent event) {
		// TODO Auto-generated method stub
		watchThread.stopWatch();
	}

}

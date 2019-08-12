package org.jcat.listener;

public interface ServletContextListener  {
	public void contextInitialized(ServletContextEvent event);
	public void contextDestroyed(ServletContextEvent event);

}

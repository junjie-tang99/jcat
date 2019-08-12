package org.jcat.listener;

import java.util.concurrent.atomic.AtomicLong;

import org.jcat.context.Context;

public class FileWatchThreadFactory {

	private static AtomicLong num = new AtomicLong(0);
	
	public static FileWatchThread generateThread(Context context,String webappPath,String contextName) {
		FileWatchThread watchThread = new FileWatchThread(context,webappPath,contextName);
		watchThread.setName(contextName+"-WATCH-THREAD-"+num.incrementAndGet());
		watchThread.setDaemon(true);
		return watchThread;
	}
}

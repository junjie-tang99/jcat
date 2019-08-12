package org.jcat.listener;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.nio.file.attribute.BasicFileAttributes;

import org.jcat.context.Context;

public class FileWatchThread extends Thread {
	String filePath = "";
	String contextName = "";
	Context context = null;
	boolean flag = true;
	
	/**
	 * 
	 * @param filePath
	 */
	public FileWatchThread(Context context,String filePath,String contextName) {
		this.context = context;
		this.filePath = filePath;
		this.contextName = contextName;
	}
	
	
	@Override
    public void run()
    {
		
			
			try {
				System.out.println("["+Thread.currentThread().getName()+"] started!");
				
				WatchService watcher = FileSystems.getDefault().newWatchService(); 
				Path path = FileSystems.getDefault().getPath(filePath);
				//path.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_MODIFY,StandardWatchEventKinds.ENTRY_DELETE);
				Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
			         @Override
			         public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException
			         {
			             dir.register(watcher, StandardWatchEventKinds.ENTRY_CREATE,StandardWatchEventKinds.ENTRY_MODIFY, StandardWatchEventKinds.ENTRY_DELETE);
			             return FileVisitResult.CONTINUE;
			         }
			     });
				
				while(flag) {
					
					WatchKey watchKey = watcher.take();
					for (WatchEvent<?> event : watchKey.pollEvents()) {
                        String editFileName = event.context().toString();
                        //System.out.println(editFileName);
                        context.reloadContext(contextName);
                        break;
                    }
                    watchKey.reset();//完成一次监控就需要重置监控器一次
				}
				
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    }

	public void stopWatch() {
		this.flag = false;
	}
}

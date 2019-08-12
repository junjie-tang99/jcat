package org.jcat.classloader;

import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

public class WebappClassLoader extends ClassLoader {

	private URL classPath;
	
	public WebappClassLoader(URL classPath) {
		this.classPath = classPath;
	}
	
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		String path;
		Class<?> clazz = null; 
		BufferedInputStream in = null;
		ByteArrayOutputStream bos = null;
		try {
			path = classPath.toURI().getPath()+File.separator+name.replace('.', '/').concat(".class");
			File file = new File(path);
			
			bos = new ByteArrayOutputStream();
			in = new BufferedInputStream(new FileInputStream(file));
			int len = 0;
			byte[] buffer = new byte[1024];
			while((len = in.read(buffer)) != -1)
				bos.write(buffer,0,len);
			
			byte[] fileBytes = bos.toByteArray();
			clazz = this.defineClass(fileBytes, 0, fileBytes.length);
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			try {
				in.close();
				bos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return clazz;
		}
		
    }

}

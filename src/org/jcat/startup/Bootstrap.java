package org.jcat.startup;

import java.io.File;

import org.jcat.server.server;

public class Bootstrap {
	public static void main(String[] args) {
		server server = new server();
		server.start();
//		
//		File file = new File("/service1/text.txt");
//		
//		String classPath = System.getProperty("java.class.path");
//		System.out.println(classPath);
//		//使用字符流打印
//		try {
//			FileReader reader = new FileReader(file);
//			BufferedReader bufReader = new BufferedReader(reader);
//			String line=bufReader.readLine();
//			while(line != null ) {
//				System.out.println(line);
//				line=bufReader.readLine();
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//使用字节流打印
//		try {
//			byte[] b = new byte[10];
//			FileInputStream  inputStream = new FileInputStream(file);
//			BufferedInputStream buffInputStream = new BufferedInputStream(inputStream);
//			int num = buffInputStream.read(b, 0, b.length);
//			StringBuffer sb = new StringBuffer();
//			while(num!=-1) {
//				sb.append(new String(b));
//				num = buffInputStream.read(b, 0, b.length);
//			}
//			System.out.println(sb.toString());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//		
//		//设置url信息
//		try {
//			URL url = new URL("file:/service1/text.txt");
//			System.out.println(url.getFile());
//			System.out.println(url.getPath());
//			URI uri = url.toURI();
//			System.out.println(uri.getScheme());
//		} catch (MalformedURLException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (URISyntaxException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
	}
}

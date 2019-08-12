package org.jcat.request;

import java.io.IOException;

import java.io.InputStream;

import org.jcat.enumeration.RequestType;

public class HttpRequest {

	private String url;
	private RequestType method;
	
	//HTTP协议如下：
	//GET /xxx HTTP1.1
	//
	//
	
	public HttpRequest(InputStream inputStream) throws IOException {
		String request = "";
		byte[] requestBytes = new byte[1024];
		int readLength = inputStream.read(requestBytes);
		if (readLength>0)
			request = new String(requestBytes,0,readLength);
		
		String urlHeader = request.split("\n")[0];
		this.method = RequestType.valueOf(urlHeader.split("\\s")[0]);
		this.url = urlHeader.split("\\s")[1];
		System.out.println(this);
			
	}
	
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	
	public RequestType getMethod() {
		return method;
	}
	public void setMethod(RequestType method) {
		this.method = method;
	}
	
	@Override
	public String toString() {
		return "HttpRequest [url=" + url + ", method=" + method + "]";
	}
	
	
}

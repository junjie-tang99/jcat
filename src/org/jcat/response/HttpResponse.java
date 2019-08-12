package org.jcat.response;

import java.io.IOException;
import java.io.OutputStream;

public class HttpResponse {

	private OutputStream output;
	
	public HttpResponse(OutputStream output) {
		this.output = output;
	}
	
	
	public void write(String content) throws IOException {
		
		StringBuffer response = new StringBuffer();
		
		response.append("HTTP/1.1 200 OK\n");
		response.append("Context-Type: text/html\n");
		response.append("\r\n");
		response.append("<html><body>");
		response.append(content);
		response.append("</html></body>");
		
		
		output.write(response.toString().getBytes());
		
	}
}

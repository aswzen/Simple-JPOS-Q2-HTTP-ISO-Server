/**
 * Simple Payment Client Server 
 *
 * Jan 4, 2018 9:35:54 AM
 *
 * (C)opyright 2018 by Agus Sigit Wisnubroto (aswzen@gmail.com).
 * Only for basic understanding. All rights reserved.
 *
 */
package com;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import com.sun.net.httpserver.*;

public class HttpAdapterServer {

	public static void main(String[] args) {
		try {
			int port = 10012;
			HttpServer server;
			server = HttpServer.create(new InetSocketAddress(port), 0);
			System.out.println("Server [HTTP] Ready On 127.0.0.1:" + port);
			server.createContext("/", new RootHandler());
	        server.createContext("/test", new TestHandler());
			server.setExecutor(null);
			server.start();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

    static class RootHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
            String response = "Hello this is HTTP Server";
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }
    
    static class TestHandler implements HttpHandler {
        public void handle(HttpExchange t) throws IOException {
        	Map<String, Object> parameters = new HashMap<String, Object>();
            URI requestedUri = t.getRequestURI();
            String query = requestedUri.getRawQuery();
            
            String response = "This is the response. Your input: "+query;
            t.sendResponseHeaders(200, response.length());
            OutputStream os = t.getResponseBody();
            os.write(response.getBytes());
            os.close();
        }
    }

}

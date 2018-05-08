package com;

import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.Constructor;
import java.net.InetSocketAddress;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.jpos.q2.QBeanSupport;
import org.jpos.util.Log;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

public class HttpAdapterServerQ2 extends QBeanSupport{
	
	private int port;

	@SuppressWarnings("rawtypes")
	public void addHandler(HttpServer server, String urlContext, String sclass, boolean isPendingMode, String postUrl) throws Exception {
		log.info("add handler :\nURL context ["+urlContext+"]\nClass ["+sclass+"]");
		HttpHandler httpHandler = null;
		if(isPendingMode) {
			Constructor ct = Class.forName( sclass ).getConstructor(Log.class,Boolean.class,String.class);
			httpHandler = ( HttpHandler ) ct.newInstance(log,new Boolean(isPendingMode),postUrl);
		} else {
			Constructor ct = Class.forName( sclass ).getConstructor(Log.class);
			httpHandler = ( HttpHandler ) ct.newInstance(log);
		}
		server.createContext(urlContext, httpHandler);
	}
	
	@Override
	protected void startService() throws Exception {
		port = cfg.getInt("port");
		HttpServer server = HttpServer.create(new InetSocketAddress(port), 0);
		String[] arrHandler = cfg.get("handlers").split(",");
		for (String handler : arrHandler){
			String surlContext = cfg.get(handler+".urlContext");
			String sclass = cfg.get(handler+".class");
			boolean isPendingMode = "true".equals(cfg.get(handler+".pendingMode"));
			String postUrl =  cfg.get(handler+".postUrl");
			if (!surlContext.equals("") && !sclass.equals(""))
				addHandler(server, surlContext, sclass, isPendingMode, postUrl);
		}
		server.setExecutor(null); // creates a default executor
		log.info("http-server start at port "+port);
		server.start();
	}
}

class RootHandler implements HttpHandler {
	
	private Log log;
	public RootHandler(Log log) {
		this.log = log;
	}
	
    public void handle(HttpExchange t) throws IOException {
        String response = "Hello this is HTTP Server";
        t.sendResponseHeaders(200, response.length());
        OutputStream os = t.getResponseBody();
        os.write(response.getBytes());
        os.close();
    }
}

class TestHandler implements HttpHandler {
	
	private Log log;
	public TestHandler(Log log) {
		this.log = log;
	}
	
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
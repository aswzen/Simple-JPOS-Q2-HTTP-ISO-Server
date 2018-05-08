package com;

import javax.servlet.http.HttpServlet;

import org.apache.xmlrpc.server.PropertyHandlerMapping;
import org.apache.xmlrpc.server.XmlRpcServer;
import org.apache.xmlrpc.server.XmlRpcServerConfigImpl;
import org.apache.xmlrpc.webserver.WebServer;

public class RPCAdapterServer extends HttpServlet  {
	
	private static final long serialVersionUID = 1L;
	private static final int port = 10013;
    
	public static void main(String[] args) {
		try {
			  WebServer webServer = new WebServer(port);   
	          XmlRpcServer xmlRpcServer = webServer.getXmlRpcServer();
	          PropertyHandlerMapping phm = new PropertyHandlerMapping();

	          phm.load(Thread.currentThread().getContextClassLoader(), "XmlRPC.properties");
//	          phm.addHandler("CalculatorSum", RPCAdapterServer.class);

	          xmlRpcServer.setHandlerMapping(phm);
	        
	          XmlRpcServerConfigImpl serverConfig = (XmlRpcServerConfigImpl) xmlRpcServer.getConfig();
	          serverConfig.setEnabledForExtensions(true);
	          serverConfig.setContentLengthOptional(false);

	          webServer.start();
	         System.err.println("Server [RPC] Ready On 127.0.0.1:" + port);
	         
	      } catch (Exception exception){ 
	         System.err.println("JavaServer: " + exception);
	      }
	}
	
	
}

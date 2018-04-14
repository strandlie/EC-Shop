package org.web.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

public class JettyServer {
	private Server server;
	
	public JettyServer() throws Exception {
        server = new Server(8080);
        WebAppContext context = new WebAppContext();
        context.setDescriptor(context + "/WEB-INF/web.xml");
        context.setResourceBase("../web.server/src/main/webapp");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        server.setHandler(context);
	}
	
	public void start() throws Exception {
		server.start();
	}
	
	public void stop() throws Exception {
		server.stop();
	}
}
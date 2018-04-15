package org.web.server;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.webapp.WebAppContext;

/**
 * Simple wrapper around the Jetty server, which boots the system using the web.xml configuration file.
 */
public class JettyServer {
	/**
	 * The Jetty server.
	 */
	private Server server;
	
	/**
	 * Starts the Jetty server using our configuration files.
	 * @throws Exception
	 */
	public JettyServer() throws Exception {
		// Server running at port 8080
        server = new Server(8080);
        
        // Use WEB-INF with web.xml instead of production configuration files.
        WebAppContext context = new WebAppContext();
        context.setDescriptor(context + "/WEB-INF/web.xml");
        context.setResourceBase("../web.server/src/main/webapp");
        context.setContextPath("/");
        context.setParentLoaderPriority(true);
        
        server.setHandler(context);
	}
	
	/**
	 * Start the server.
	 * @throws Exception
	 */
	public void start() throws Exception {
		server.start();
	}
	
	/**
	 * Stop the server.
	 * @throws Exception
	 */
	public void stop() throws Exception {
		server.stop();
	}
}
package org.web.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

public abstract class AbstractServlet extends HttpServlet{
	
	/*
	private static final long serialVersionUID = 1L;
	*/
	
	protected Class c;
	
	protected boolean hasGet = true,
			hasPost = true,
			hasPut = true,
			hasDelete = true;
	
	protected JSONObject options = new JSONObject();
	
	protected abstract void setup();
		
	@Override
	public void init() throws ServletException {
		setup();
		super.init();
	}
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (! hasGet) resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		
		// TODO Make method
		super.doGet(req, resp);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (! hasPost) resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

		// TODO Make method
		super.doPost(req, resp);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (! hasPut) resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

		
		// TODO Make method
		super.doPut(req, resp);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (! hasDelete) resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);

		
		// TODO Make method
		super.doDelete(req, resp);
	}
	
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Add response with available methods
		resp.setContentType("application/json");
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.addHeader("Allow", 
				"OPTIONS" +
				(hasGet ? "GET" : "") +
				(hasPost ? "POST" : "") +
				(hasPut ? "PUT" : "") +
				(hasDelete ? "DELETE" : "")
				);
		resp.getWriter().print(options);
	}
	
}

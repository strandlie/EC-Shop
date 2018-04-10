package org.web.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;
import org.web.server.persistance.Persister;
import org.web.server.serializers.Serializer;

public abstract class AbstractServlet extends HttpServlet{
	
	/**
	 * Enum class for HTTP methods.
	 * @author Ruben Johannessen
	 */
	public enum HTTPMethod {
		GET("GET"),
		POST("POST"),
		PUT("PUT"),
		DELETE("DELETE");
		
		private final String method;
		
		private HTTPMethod(String method) {
			this.method = method;
		}
		
		public String toString() {
			return this.method;
		}
	}
	
	private final String JSONRespons = "application/json";
	
	@SuppressWarnings("rawtypes")
	protected Class cl;
	
	protected Serializer serializer = Serializer.init();
	protected Persister persister = Persister.init();
	
	protected boolean hasGet = true,
			hasPost = true,
			hasPut = true,
			hasDelete = true;
	protected JSONObject options = new JSONObject();
	
	/**
	 * Setup method called when servlet is created. Must set Class cl field.
	 */
	protected abstract void setup();
	
	@Override
	public void init() throws ServletException {
		setup();
		super.init();
	}
	
	private void authorize() {
		
	}
	
	/*
	 * Under is `do***` method for the five different request-methods we support.
	 * You can disable a method by setting the `has***` fields for the specific method to false in setup.
	 * `do***` methods can be overridden if needed.
	 * 
	 * Options method sends the `options` JSONObject with the response, witch can be used to describe wanted JSON syntax.
	 */
	//TODO: HTTP Errors
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (! hasGet) resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
		
		// Authorize
		int customerID = -1;
		try {
			 customerID = Integer.parseInt(req.getParameter("customer-id"));
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		} catch (Exception e) {
			System.out.println(e);
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		String json = "";
		try {
			json = persister.read(customerID, cl);
		} catch (IllegalArgumentException e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		if (json == null || json.equals("null")) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		resp.setContentType(JSONRespons);
		resp.setStatus(HttpServletResponse.SC_OK);
		resp.getWriter().println(json);
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (! hasPost) {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}
		
		// Authorize
		int customerID = -1;
		try {
			 customerID = Integer.parseInt(req.getParameter("customer-id"));
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		try {
			serializer.deserialize(req.getReader(), cl, HTTPMethod.POST);
		} catch (Exception e) {
			e.printStackTrace();
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		
		resp.setContentType(JSONRespons);
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (! hasPut) {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}
		
		// Authorize
		int customerID = -1;
		try {
			 customerID = Integer.parseInt(req.getParameter("customer-id"));
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		try {
			serializer.deserialize(req.getReader(), cl, HTTPMethod.PUT);
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		resp.setContentType(JSONRespons);
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		if (! hasDelete) {
			resp.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED);
			return;
		}
		
		// Authorize
		int customerID = -1;
		try {
			 customerID = Integer.parseInt(req.getParameter("customer-id"));
		} catch (NumberFormatException e) {
			resp.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			return;
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
			return;
		}
		
		try {
			serializer.deserialize(req.getReader(), cl, HTTPMethod.DELETE);
		} catch (Exception e) {
			resp.sendError(HttpServletResponse.SC_BAD_REQUEST);
			return;
		}
		resp.setContentType(JSONRespons);
		resp.setStatus(HttpServletResponse.SC_OK);
	}
	
	@Override
	protected void doOptions(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		// TODO Add example request
		resp.setContentType(JSONRespons);
		resp.setStatus(HttpServletResponse.SC_OK);
		
		String allow = "OPTIONS, " +
				(hasGet ? HTTPMethod.GET.toString() + ", " : "") +
				(hasPost ? HTTPMethod.POST.toString() + ", " : "") +
				(hasPut ? HTTPMethod.PUT.toString() + ", ": "") +
				(hasDelete ? HTTPMethod.DELETE.toString() + ", ": "");
		allow = allow.substring(0, allow.length() - 2);
		
		resp.addHeader("Allow", allow);
		resp.getWriter().print(options);
	}	
}

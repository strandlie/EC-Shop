package org.web.server;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tdt4140.gr1864.app.core.database.CreateDatabase;
import tdt4140.gr1864.app.core.database.DataLoader;

public class ShoppingTripServlet extends HttpServlet {
    
	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		super.init();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello Servlet</h1>");
        response.getWriter().println("session=" + request.getSession(true).getId());
    }
    
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	try {
    		String line = "";
    		BufferedReader reader = null;
			String jb = "";
			reader = request.getReader();
			while ((line = reader.readLine()) != null)
				jb += line;

    		DataLoader.loadShoppingTrip(jb);
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}
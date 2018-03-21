package org.web.server;

import java.io.BufferedReader;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tdt4140.gr1864.app.core.database.DataLoader;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	@Override
	public void init() throws ServletException {
		super.init();
	}

	// TODO:
	//	Handle GETs with query parameters with UserID
	//	Authentication????
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println("<h1>Hello UserServlet</h1>");
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

    		DataLoader.loadUserDemographics(jb);
			System.out.println("Got data");
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}

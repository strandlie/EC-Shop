package org.web.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.web.server.ReceiptServlet.ReceiptSerializer;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.module.SimpleModule;

import tdt4140.gr1864.app.core.Receipt;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ShoppingTripDatabaseController shoppingTripController = new ShoppingTripDatabaseController();

	@Override
	public void init() throws ServletException {
		super.init();
	}

	private class UserData {
		public List<Integer> shoppingTrips = new ArrayList<>();
		
		public UserData(List<ShoppingTrip> trips) {
			for (ShoppingTrip trip : trips) {
				shoppingTrips.add(trip.getShoppingTripID());
			}
		}
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        
		if (!request.getParameterMap().containsKey("id")) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        return;
		}
		
		int id = Integer.valueOf(request.getParameter("id"));

		List<ShoppingTrip> trips = shoppingTripController.getTripsForCustomer(id);
		
		if (trips == null) {
	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        return;
		}
		
        response.setStatus(HttpServletResponse.SC_OK);
		ObjectWriter writer = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String output = writer.writeValueAsString(new UserData(trips));
		response.getWriter().println(output);
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

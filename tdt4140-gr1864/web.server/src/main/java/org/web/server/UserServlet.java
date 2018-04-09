package org.web.server;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.web.server.serializers.Serializer;

import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class UserServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private CustomerDatabaseController customerController = new CustomerDatabaseController();
	private ShoppingTripDatabaseController shoppingTripController = new ShoppingTripDatabaseController();

	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        
		if (!request.getParameterMap().containsKey("id")) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        return;
		}
		
		int id = Integer.valueOf(request.getParameter("id"));

		Customer customer = customerController.retrieve(id);

		if (customer == null) {
			response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        return;
		}

		List<ShoppingTrip> trips = shoppingTripController.getTripsForCustomer(id);
		
		customer.setShoppingTrips(trips);
		
        response.setStatus(HttpServletResponse.SC_OK);
        response.getWriter().println(Serializer.init().serialize(customer, Customer.class));
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

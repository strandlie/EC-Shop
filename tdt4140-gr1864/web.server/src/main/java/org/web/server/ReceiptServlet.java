package org.web.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.web.server.serializers.Serializer;

import tdt4140.gr1864.app.core.Receipt;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class ReceiptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ShoppingTripDatabaseController shoppingTripController = new ShoppingTripDatabaseController();
	private ActionDatabaseController actionController = new ActionDatabaseController();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

		if (!request.getParameterMap().containsKey("trip")) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        return;
		}
		
		int id = Integer.valueOf(request.getParameter("trip"));

		ShoppingTrip trip = shoppingTripController.retrieve(id);
		
		if (trip == null) {
	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        return;
		}
		
		trip.setActions(actionController.retrieveAll(trip.getID()));
		
        response.setStatus(HttpServletResponse.SC_OK);
	    response.getWriter().println(Serializer.init().serialize(new Receipt(trip), Receipt.class));
  	}

}

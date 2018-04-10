package org.web.server;

import tdt4140.gr1864.app.core.ShoppingTrip;

public class ShoppingTripServlet extends AbstractServlet {
    
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void setup() {
		cl = ShoppingTrip.class;
		hasDelete = false;
	}
}

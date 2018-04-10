package org.web.server;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.web.server.serializers.Serializer;

import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.Receipt;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class ReceiptServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		cl = Receipt.class;
		hasDelete = false;
		hasPost = false;
		hasPut = false;
	}

}

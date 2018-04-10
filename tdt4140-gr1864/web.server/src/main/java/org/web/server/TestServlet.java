package org.web.server;

import tdt4140.gr1864.app.core.Customer;

public class TestServlet extends AbstractServlet {

	private static final long serialVersionUID = 69L;

	@Override
	protected void setup() {
		cl = Customer.class;
	}

}

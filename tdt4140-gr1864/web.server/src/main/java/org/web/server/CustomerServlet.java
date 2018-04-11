package org.web.server;

import tdt4140.gr1864.app.core.Customer;

public class CustomerServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		cl = Customer.class;
	}
}

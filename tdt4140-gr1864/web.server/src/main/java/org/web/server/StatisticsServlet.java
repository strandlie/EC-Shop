package org.web.server;

import tdt4140.gr1864.app.core.ProductAmount;

public class StatisticsServlet extends AbstractServlet{

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		cl = ProductAmount.class;
		hasPost = false;
		hasPut = false;
		hasDelete = false;
	}

}

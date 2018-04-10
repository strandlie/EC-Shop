package org.web.server;

import tdt4140.gr1864.app.core.Receipt;

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

package org.web.server;

import tdt4140.gr1864.app.core.Product;

public class RecommendationServlet extends AbstractServlet {

	private static final long serialVersionUID = 1L;

	@Override
	protected void setup() {
		cl = Product.class;
		hasDelete = false;
		hasPost = false;
		hasPut = false;
	}
}

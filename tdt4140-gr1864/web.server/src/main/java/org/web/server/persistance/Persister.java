package org.web.server.persistance;

import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;
import tdt4140.gr1864.app.core.interfaces.DatabaseCRUD;
import tdt4140.gr1864.app.core.interfaces.Model;

/**
 * Calls persistance-methods in app.core
 * @author vegarab
 */
public class Persister {
		
	/** DBController used by persister-methods */
	private static DatabaseCRUD controller = null;

	/** method-types based on HTTP-request */
	private static final int POST 	= 0;
	private static final int PUT 		= 1;
	private static final int DELETE 	= 2;
	
	/** Class names compared in switch-case */
	public static final String SHOPPING_TRIP = "tdt4140.gr1864.app.core.ShoppingTrip";
	public static final String CUSTOMER = "tdt4140.gr1864.app.core.Customer";
	public static final String RECEIPT = "tdt4140.gr1864.app.core.Receipt";
	
	/**
	 * Calls correct function to persist changes that were serialized
	 * @param object	Model object to persist
	 * @param c 		Class of model object
	 * @param method	Persist-method to perform (CRUD)
	 */
	@SuppressWarnings("rawtypes")
	public static void persist(Model object, Class c, int method) {

		switch(c.getName()) {
		case SHOPPING_TRIP:
			controller = new ShoppingTripDatabaseController();
			break;
		case CUSTOMER:
			controller = new CustomerDatabaseController();
			break;
		}
	
		switch(method) {
			case POST: 		Persister.create(object); break;
			case PUT: 		Persister.update(object); break;
			case DELETE: 	Persister.delete(object); break;
		}
	}

	/**
	 * Creates object in database
	 * @param object
	 */
	public static void create(Model object) {
		controller.create(object);
	}
	
	/**
	 * Updates object in database
	 * @param object
	 */
	public static void update(Model object) {
		controller.update(object);
	}

	/**
	 * Deleted object from database
	 * @param object
	 */
	public static void delete(Model object) {
		controller.delete(object.getID());
	}
}

package org.web.server.persistance;

import org.web.server.AbstractServlet.HTTPMethod;

import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.Receipt;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;
import tdt4140.gr1864.app.core.interfaces.DatabaseCRUD;
import tdt4140.gr1864.app.core.interfaces.Model;

/**
 * Calls persistance-methods in app.core
 * @author vegarab
 */
public class Persister {
			
	/** Class names compared in switch-case */
	public enum ModelClasses {
		SHOPPING_TRIP(ShoppingTrip.class.getName()),
		CUSTOMER(Customer.class.getName()),
		RECEIPT(Receipt.class.getName());
		
		private final String model;
		
		private ModelClasses(String model) {
			this.model = model;
		}
		
		public String toString() {
			return this.model;
		}
		
		public static ModelClasses fromClass(Class c) {
			for (ModelClasses m : ModelClasses.values()) {
				if (m.toString().equals(c.getName()))
					return m;
			}
			throw new IllegalArgumentException("No values for this in ModelClasses");
		}
	}
	
	/** DBController used by persister-methods */
	private static DatabaseCRUD controller = null;
	
	/* Used for singleton design */
	private static Persister persister;
	
	/**
	 * Private constructor for singleton design-pattern
	 */
	private Persister() {}
	
	/**
	 * Retrieves a Persister object based on singleton design-pattern
	 * @return Persister object
	 */
	public static Persister init() {
		if (persister == null) {
			persister = new Persister();
		}
		return persister;
	}
	
	/**
	 * Calls correct function to persist changes that were serialized
	 * @param object	Model object to persist
	 * @param c 		Class of model object
	 * @param method	Persist-method to perform (CRUD)
	 */
	@SuppressWarnings("rawtypes")
	public void persist(Object object, Class c, HTTPMethod method) {
		setController(c);
		
		switch(method) {
			case POST: 		create(object); break;
			case PUT: 		update(object); break;
			case DELETE: 	delete((Model) object); break;
		}
	}
	
	private void setController(Class c) {
		switch(ModelClasses.fromClass(c)) {
		case SHOPPING_TRIP:	controller = new ShoppingTripDatabaseController(); break;
		case CUSTOMER: controller = new CustomerDatabaseController(); break;
		}
	}
	
	public Object read(int id, Class c) {
		setController(c);
		return controller.retrieve(id);
	}

	/**
	 * Creates object in database
	 * @param object
	 */
	private void create(Object object) {
		controller.create(object);
	}
	
	/**
	 * Updates object in database
	 * @param object
	 */
	private void update(Object object) {
		controller.update(object);
	}

	/**
	 * Deleted object from database
	 * @param object
	 */
	private void delete(Model object) {
		controller.delete(object.getID());
	}
}

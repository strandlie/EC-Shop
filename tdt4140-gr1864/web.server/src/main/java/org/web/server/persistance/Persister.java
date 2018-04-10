package org.web.server.persistance;

import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

import org.web.server.AbstractServlet.HTTPMethod;
import org.web.server.serializers.Serializer;

import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.Coordinate;
import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.Receipt;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.StripeShoppingTrip;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CoordinateDatabaseController;
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
		ACTION(Action.class.getName()),
		COORDINATE(Coordinate.class.getName()),
		CUSTOMER(Customer.class.getName()),
		PRODUCT(Product.class.getName()),
		RECEIPT(Receipt.class.getName()),
		SHOP(Shop.class.getName()),
		SHOPPING_TRIP(ShoppingTrip.class.getName()),
		STRIPE_SHOPPING_TRIP(StripeShoppingTrip.class.getName());

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
			throw new IllegalArgumentException("No value for this class in ModelClasses");
		}
	}
	
	/** DBController used by persister-methods */
	private DatabaseCRUD controller = null;
	
	/* Used for singleton design */
	private static Persister persister;
	
	private Serializer serializer;
	
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
		switch(ModelClasses.fromClass(c)) {
		case CUSTOMER: controller = new CustomerDatabaseController(); break;
		case SHOPPING_TRIP:	controller = new ShoppingTripDatabaseController(); break;
		default:
			throw new IllegalArgumentException("No controller for this class");
		}
		
		switch(method) {
			case POST: 		create(object); break;
			case PUT: 		update(object); break;
			case DELETE: 	delete((Model) object); break;
		}
	}
	
	/**
	 * Reads and serializes data from the database, based on customerID and Class c
	 * @param customerID 	ID to retrieve data based on
	 * @param c 			Class of Servlet, decides how to read and serialize
	 * @return JSON String
	 * @throws IOException
	 */
	public String read(int customerID, Class c) throws IOException {
		String json;
		
		switch (ModelClasses.fromClass(c)) {
		case CUSTOMER: json = readCustomer(customerID); break;
		case RECEIPT: json = readReceipt(customerID); break;
		case SHOPPING_TRIP: json = readShoppingTrips(customerID); break;
		default:
			throw new IllegalArgumentException();
		}
		return json;
	}
	
	/**
	 * Reads ShoppingTrips from database based on customerID, serializes them
	 * and returns the JSON string
	 * @param customerID 	ID to retrieve Trips from
	 * @return JSON String
	 * @throws IOException
	 */
	private String readShoppingTrips(int customerID) throws IOException {
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		List<ShoppingTrip> trips = stdc.retrieveAllShoppingTripsForCustomer(customerID);

		// If there are no trips
		if (trips.isEmpty()) return "Empty";

		serializer  = Serializer.init();
		return serializer.serialize(trips, ShoppingTrip.class);
	}

	/**
	 * Reads Customer from database based on customerID, serializes it and
	 * returns the JSON string
	 * @param customerID	ID to retrieve Customer from
	 * @return JSON String
	 * @throws IOException
	 */
	private String readCustomer(int customerID) throws IOException {
		controller = new CustomerDatabaseController();
		return Serializer.init().serialize(controller.retrieve(customerID), Customer.class);
	}

	/**
	 * Reads all receipts for a given customer and serializes it.
	 * @param customerID The ID of the customer to find receipts for.
	 * @return The serialized list of receipts.
	 * @throws IOException
	 */
	public String readReceipt(int customerID) throws IOException {
		ShoppingTripDatabaseController shoppingTripController = new ShoppingTripDatabaseController();
		ActionDatabaseController actionController = new ActionDatabaseController();
		CoordinateDatabaseController coordinateController = new CoordinateDatabaseController();
		
		List<ShoppingTrip> trips = shoppingTripController.retrieveAllShoppingTripsForCustomer(customerID);
		
		if (trips == null) {
		    throw new IllegalArgumentException();
		}
		
		List<Receipt> receipts = new ArrayList<>();
		
		for (ShoppingTrip trip : trips) {
			trip.setActions(actionController.retrieveAll(trip.getID()));
			trip.setCoordinates(coordinateController.retrieveAll(trip.getID()));
			receipts.add(new Receipt(trip));
		}
		
		return Serializer.init().serialize(receipts, Receipt.class);
	}

	/**
	 * Creates object in database
	 * @param object
	 */
	private void create(Object object) {
		ShoppingTrip trip = (ShoppingTrip) object;
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

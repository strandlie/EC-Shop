package tdt4140.gr1864.app.core;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import tdt4140.gr1864.app.core.interfaces.IShoppingTripListener;

public class ShoppingTrip {
	
	/* start-time of ShoppingTrip in UNIX-time */
	private long start;
	/* end-time of ShoppingTrip in UNIX-time */
	private long end;
	
	/* customer and shop trip was made by and at */
	private Shop shop;
	private Customer customer;

	private int shoppingTripID;
	
	/* Coordinates that makes up the trip */
	private List<Coordinate> coordinates;
	/* Actions performed during trip */
	private List<Action> actions;

	private boolean charged;
	
	private static Collection<IShoppingTripListener> listeners = new ArrayList<>();
	
	/**
	 * Normal constructor with charged paramater.
	 * @param coordinates		list of coordinates making up the trip
	 * @param actions			list of actions performed during trip
	 * @param shoppingTripID	id provided by database
	 * @param charged			true if the card has been charged for this trip
	 */
	public ShoppingTrip(List<Coordinate> coordinates, List<Action> actions, int shoppingTripID, boolean charged) {
		this.coordinates = coordinates;
		this.actions = actions;
		this.shoppingTripID = shoppingTripID;
		this.start = findStart(coordinates);
		this.end = findEnd(coordinates);
		this.charged = charged;
		
		if (!this.charged) {
			charge();
			this.charged = true;
		}
		
		// Listener
		for (IShoppingTripListener listener : listeners) {
			listener.shoppingTripAdded(this);
		}
	}
	
	/**
	 * Normal constructor without charged parameter.
	 * @param coordinates		list of coordinates making up the trip
	 * @param actions			list of actions performed during trip
	 * @param shoppingTripID	id provided by database
	 */
	public ShoppingTrip(List<Coordinate> coordinates, List<Action> actions, int shoppingTripID) {
		this(coordinates, actions, shoppingTripID, false);
	}
	
	/**
	 * Constructor when missing ID.
	 * @param customer	customer performing trip
	 * @param shop		shop where trip was made
	 */
	public ShoppingTrip(Customer customer, Shop shop, boolean charged) {
		this.customer = customer;
		this.shop = shop;
		this.charged = charged;
	}
	
	/**
	 * Constructor used by ShoppingTripDatabaseController
	 * @param shoppingTripId	id provided by database
	 * @param customer			customer performing trip
	 * @param shop				shop where trip was made
	 */
	public ShoppingTrip(int shoppingTripId, Customer customer, Shop shop, boolean charged) {
		this.shoppingTripID = shoppingTripId;
		this.customer = customer;
		this.shop = shop;
		this.charged = charged;
	}
	
	private void charge() {
		Receipt receipt = new Receipt(this);
		// We currently use test card data. This is because security is a concern, and we
		// want to handle that by letting the card be provided by the user instead of storing it.
		// Therefore this needs to stay until the data streaming API is implemented.
		StripeShoppingTrip stripe = new StripeShoppingTrip("4242424242424242", 3, 2019, "314");
		stripe.charge((int) (receipt.getTotalPrice() * 100));
	}
	
	public boolean getCharged() {
		return charged;
	}
	
	/**
	 * @param coordinates list of coordinates that makes up the trip
	 * @return time of first data-point in the list of coordinates
	 */
	private long findStart(List<Coordinate> coordinates) {
		long min = coordinates.get(0).getTimeStamp();

		for (Coordinate coord : coordinates) {
			if (coord.getTimeStamp() < min) {
				min = coord.getTimeStamp();
			}
		}
		return min;
	}
	
	/**
	 * @param coordinates list of coordinates that makes up the trip
	 * @return time of last data-point in the list of coordinates
	 */
	private long findEnd(List<Coordinate> coordinates) {
		long max = coordinates.get(0).getTimeStamp();
		
		for (Coordinate coord : coordinates) {
			if (coord.getTimeStamp() > max) {
				max = coord.getTimeStamp();
			}
		}
		return max;
	}
	
	/**
	 * Sets actions after a ShoppingTrip is created
	 * @param actions the list of actions
	 */
	public void setActions(List<Action> actions) {
		this.actions = actions;
	}
	
	/**
	 * Sets coordinates after ShoppingTrip is created
	 * @param coordinates The list of coordinates
	 */
	private void setCoordinates(List<Coordinate> coordinates) {
		this.coordinates = coordinates;
	}

	public long getStart() {
		return start;
	}
	
	public long getEnd() {
		return end;
	}
	
	public List<Coordinate> getCoordinates() {
		return coordinates;
	}
	
	public List<Action> getActions() {
		return actions;
	}
	
	public Shop getShop() {
		return shop;
	}
	
	public Customer getCustomer() {
		return customer;
	}

	public int getShoppingTripID() {
		return shoppingTripID;
	}
	
	// Listeners
	
	public static void addListener(IShoppingTripListener listener) {
		listeners.add(listener);
	}
	
	public static void removeListener(IShoppingTripListener listener) {
		listeners.remove(listener);
	}
}
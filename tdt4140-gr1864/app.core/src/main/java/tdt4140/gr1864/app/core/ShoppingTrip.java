package tdt4140.gr1864.app.core;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import tdt4140.gr1864.app.core.interfaces.Model;

public class ShoppingTrip implements Model {
	
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
	private boolean anonymous;
	
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
		this.anonymous = customer.getAnonymous();
	}
	
	/**
	 * Constructor used by ShoppingTripDatabaseController
	 * @param shoppingTripId	id provided by database
	 * @param customer			customer performing trip
	 * @param shop				shop where trip was made
	 * @param anonymous			whether or not this trip should be counted in the statistics
	 */
	public ShoppingTrip(int shoppingTripId, Customer customer, Shop shop, boolean charged, boolean anonymous) {
		this.shoppingTripID = shoppingTripId;
		this.customer = customer;
		this.shop = shop;
		this.charged = charged;
		this.anonymous = anonymous;
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
	public void setCoordinates(List<Coordinate> coordinates) {
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

	public int getID() {
		return shoppingTripID;
	}
	
	public void setAnonymous(boolean anonymous) {
		this.anonymous = anonymous;
	}
	
	public boolean getAnonymous() {
		return this.anonymous;
	}
}
package tdt4140.gr1864.app.core;

import java.util.List;

import tdt4140.gr1864.app.core.storage.Shop;

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

	/**
	 * @param coordinates		list of coordinates making up the trip
	 * @param actions			list of actions performed during trip
	 * @param shoppingTripID	id provided by database
	 */
	public ShoppingTrip(List<Coordinate> coordinates, List<Action> actions, int shoppingTripID) {
		this.coordinates = coordinates;
		this.actions = actions;
		this.shoppingTripID = shoppingTripID;
		this.start = findStart(coordinates);
		this.end = findEnd(coordinates);
	}
	
	/**
	 * Constructor when missing ID
	 * @param customer	customer performing trip
	 * @param shop		shop where trip was made
	 */
	public ShoppingTrip(Customer customer, Shop shop) {
		this.customer = customer;
		this.shop = shop;
	}
	
	/**
	 * Constructor used by ShoppingTripDatabaseController
	 * @param shoppingTripId	id provided by database
	 * @param customer			customer performing trip
	 * @param shop				shop where trip was made
	 */
	public ShoppingTrip(int shoppingTripId, Customer customer, Shop shop) {
		this.shoppingTripID = shoppingTripId;
		this.customer = customer;
		this.shop = shop;
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
}
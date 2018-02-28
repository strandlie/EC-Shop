package tdt4140.gr1864.app.core;

import java.util.List;

import tdt4140.gr1864.app.core.storage.Shop;

public class ShoppingTrip {
	
	/* start-time of ShoppingTrip in UNIX-time */
	private long start;
	/* end-time of ShoppingTrip in UNIX-time */
	private long end;
	
	private Shop shop;
	private Customer customer;
	private int shoppingTripID;
	
	/* Coordinates that makes up the trip */
	private List<Coordinate> coordinates;
	/* Actions performed during trip */
	private List<Action> actions;

	public ShoppingTrip(List<Coordinate> coordinates, List<Action> actions, int shoppingTripID) {
		this.coordinates = coordinates;
		this.actions = actions;
		this.shoppingTripID = shoppingTripID;
		
		this.start = findStart(coordinates);
		this.end = findEnd(coordinates);
	}
	
	public ShoppingTrip(Customer customer, Shop shop) {
		this.customer = customer;
		this.shop = shop;
	}
	
	public ShoppingTrip(int shoppingTripId, Customer customer, Shop shop) {
		this.shoppingTripID = shoppingTripId;
		this.customer = customer;
		this.shop = shop;
	}
	
	/*
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
	
	/*
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

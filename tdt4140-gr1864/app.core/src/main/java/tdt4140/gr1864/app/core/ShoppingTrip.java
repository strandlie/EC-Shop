package tdt4140.gr1864.app.core;

import java.util.List;

public class ShoppingTrip {
	
	/* start-time of ShoppingTrip in UNIX-time */
	private long start;
	/* end-time of ShoppingTrip in UNIX-time */
	private long end;
	
	private int shopID;
	private int customerID;
	private int shoppingTripID;
	
	/* Coordinates that makes up the trip */
	private List<Coordinate> coordinates;
	/* Actions performed during trip */
	private List<Action> actions;

	public ShoppingTrip(List<Coordinate> coordinates, List<Action> actions) {
		this.coordinates = coordinates;
		this.actions = actions;
		
		this.start = findStart(coordinates);
		this.end = findEnd(coordinates);
	}
	
	public ShoppingTrip(int shoppingTripId, int customerId, int shopId) {
		this.shoppingTripID = shoppingTripId;
		this.customerID = customerId;
		this.shopID = shopId;
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
	
	public int getShopID() {
		return shopID;
	}
	
	public int getCustomerID() {
		return customerID;
	}

	public int getShoppingTripID() {
		return shoppingTripID;
	}
}

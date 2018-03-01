package tdt4140.gr1864.app.core;

public class Coordinate {
	
	private double x;
	private double y;
	private long timeStamp;
	private ShoppingTrip trip;
	
	/*
	 * Constructor for creating Coordinates in CoordinateDatabaseController.
	 * Needs to handle getting correct ShoppingTrip based on it's ID
	 */
	public Coordinate(double x, double y, String timeStamp, ShoppingTrip trip) {
		this.x = x;
		this.y = y;
		this.timeStamp = Long.parseLong(timeStamp);
		this.trip = trip;
	}
	
	public Coordinate(double x, double y, String timeStamp) {
		this.x = x;
		this.y = y;
		this.timeStamp = Long.parseLong(timeStamp);
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
	public ShoppingTrip getShoppingTrip() {
		return trip;
	}

}

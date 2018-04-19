package tdt4140.gr1864.app.core;

public class Coordinate {
	
	private double x;
	private double y;
	private long timeStamp;
	
	/* trip the coordinate is a part of */
	private ShoppingTrip trip;
	
	/**
	 * Constructor used by CoordinateDatabaseController 
	 * @param x				x position
	 * @param y				y position
	 * @param timeStamp		time of read, parsed to long
	 * @param trip			trip the coordinate is a part of
	 */
	public Coordinate(double x, double y, String timeStamp, ShoppingTrip trip) {
		this.x = x;
		this.y = y;
		this.timeStamp = Long.parseLong(timeStamp);
		this.trip = trip;
	}
	
	/**
	 * @param x				x position
	 * @param y				y position
	 * @param timeStamp		time of read, parsed to long
	 */
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

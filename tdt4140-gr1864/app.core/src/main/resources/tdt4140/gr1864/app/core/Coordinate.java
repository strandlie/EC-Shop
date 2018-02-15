package tdt4140.gr1864.app.core;

public class Coordinate {
	/**
	 * The x-coordinate of the point.
	 */
	private double x;
	
	/**
	 * The y-coordinate of the point.
	 */
	private double y;
	
	/**
	 * Create a new point with the given coordinates.
	 * @param x The x-coordinate of the point.
	 * @param y The y-coordinate of the point.
	 */
	public Coordinate(double x, double y) {
		this.x = x;
		this.y = y;
	}
	
	/**
	 * @return The x-coordinate of the point.
	 */
	public double getX() {
		return x;
	}
	
	/**
	 * @return The y-coordinate of the point.
	 */
	public double getY() {
		return y;
	}
	
	/**
	 * @param x The x-coordinate of the point.
	 */
	public void setX(double x) {
		this.x = x;
	}
	
	/**
	 * @param y The y-coordinate of the point.
	 */
	public void setY(double y) {
		this.y = y;
	}
}
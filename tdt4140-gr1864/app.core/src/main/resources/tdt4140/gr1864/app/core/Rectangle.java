package tdt4140.gr1864.app.core;

public class Rectangle {
	/**
	 * The lower-left corner of the rectangle.
	 */
	private Coordinate lower;
	
	/**
	 * The upper-right corner of the rectangle.
	 */
	private Coordinate upper;
	
	/**
	 * Create a new Rectangle with the given boundaries.
	 * @param lower The lower-left corner of the rectangle.
	 * @param upper The upper-right corner of the rectangle.
	 */
	public Rectangle(Coordinate lower, Coordinate upper) {
		this.lower = lower;
		this.upper = upper;
	}
	
	/**
	 * @return The lower-left corner of the rectangle.
	 */
	public Coordinate getLower() {
		return lower;
	}
	
	/**
	 * @return The upper-right corner of the rectangle.
	 */
	public Coordinate getUpper() {
		return upper;
	}
}
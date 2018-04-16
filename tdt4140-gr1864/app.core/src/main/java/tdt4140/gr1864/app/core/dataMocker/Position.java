package tdt4140.gr1864.app.core.dataMocker;

import java.util.Date;

public class Position extends Coordinate {
	/**
	 * The time at which the person was at this coordinate. A coordinate tuple
	 * and a timestamp defines a position.
	 */
	private Date timestamp;
	
	/**
	 * A Position is a combination of a coordinate and a given point in time.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param timestamp The time at which the coordinate was visited.
	 */
	public Position(double x, double y, Date timestamp) {
		super(x, y);
		
		this.timestamp = timestamp;
	}

	/**
	 * Alternative constructor which accepts a Coordinate and wraps it to create a Position.
	 * @param coordinate The Coordinate to wrap.
	 * @param timestamp The time at which the coordinate was visited. 
	 */
	public Position(Coordinate coordinate, Date timestamp) {
		super(coordinate.getX(), coordinate.getY());
		this.timestamp = timestamp;
	}
	
	/**
	 * @return The time at which the coordinate was visited.
	 */
	public Date getTimestamp() {
		return timestamp;
	}
}

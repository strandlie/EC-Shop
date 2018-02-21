package dataMocker;

import java.util.Date;

public class Position extends Coordinate {
	/**
	 * The time at which the person was at this coordinate. A coordinate tuple
	 * and a timestamp defines a position.
	 */
	private Date time;
	
	/**
	 * A Position is a combination of a coordinate and a given point in time.
	 * @param x The x-coordinate.
	 * @param y The y-coordinate.
	 * @param time The time at which the coordinate was visited.
	 */
	public Position(double x, double y, Date time) {
		super(x, y);
		
		this.time = time;
	}

	/**
	 * Alternative constructor which accepts a Coordinate and wraps it to create a Position.
	 * @param coordinate The Coordinate to wrap.
	 * @param time The time at which the coordinate was visited. 
	 */
	public Position(Coordinate coordinate, Date time) {
		super(coordinate.getX(), coordinate.getY());
		this.time = time;
	}
	
	/**
	 * @return The time at which the coordinate was visited.
	 */
	public Date getTime() {
		return time;
	}
}

package tdt4140.gr1864.app.core;

import java.util.List;

public class ShoppingTrip {
	
	/* start-time of ShoppingTrip in UNIX-time */
	private int start;
	/* end-time of ShoppingTrip in UNIX-time */
	private int end;
	
	/* Coordinates that makes up the trip */
	private List<Coordinate> coordinates;
	/* Actions perfored during trip */
	private List<Action> actions;

	public ShoppingTrip(List<Coordinate> coordinates, List<Action> actions) {
		this.coordinates = coordinates;
		this.actions = actions;
		
		long start = findStart(coordinates);
		long end = findEnd(coordinates);
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

	public int getStart() {
		return start;
	}
	
	public int getEnd() {
		return end;
	}
}

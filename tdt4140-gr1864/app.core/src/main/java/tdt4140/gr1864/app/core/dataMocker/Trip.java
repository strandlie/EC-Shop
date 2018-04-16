package tdt4140.gr1864.app.core.dataMocker;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import tdt4140.gr1864.app.core.dataMocker.Action;
import tdt4140.gr1864.app.core.dataMocker.Position;

public class Trip {
	/**
	 * The customerID of the person doing this trip
	 */
	private int customerID;
	
	/**
	 * The Path of the person this trip is describing.
	 */
	private List<Position> coordinates;
	
	/**
	 * Actions performed by the simulated user.
	 */
	private List<Action> actions;
	
	/**
	 * Data simulating user movements and actions through a shop.
	 * CustomerID (0-10) will be generated and will be set to "user" of this trip
	 * @param coordinates The Path of the user.
	 * @param actions The actions executed along the way.
	 */
	public Trip(List<Position> coordinates, List<Action> actions) {
		this.coordinates = coordinates;
		this.actions = actions;
		this.customerID = ThreadLocalRandom.current().nextInt(1, 11);
	}

	/**
	 * @return The Path of the simulated person.
	 */
	public List<Position> getCoordinates() {
		return coordinates;
	}
	
	/**
	 * @return The actions done along this particular path.
	 */
	public List<Action> getActions() {
		return actions;
	}
	
	public long getDuration() {
		return coordinates.get(coordinates.size() - 1).getTimestamp().getTime() - coordinates.get(0).getTimestamp().getTime();
	}
	
	/**
	 * @return The id of the customer doing this trip
	 */
	public int getCustomerID() {
		return this.customerID;
	}
}


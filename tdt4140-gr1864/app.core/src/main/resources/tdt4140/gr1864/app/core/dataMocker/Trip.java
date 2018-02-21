package tdt4140.gr1864.app.core.dataMocker;

import java.util.List;

public class Trip {
	/**
	 * The Path of the person this trip is describing.
	 */
	private List<Position> path;
	
	/**
	 * Actions performed by the simulated user.
	 */
	private List<Action> actions;
	
	/**
	 * Data simulating user movements and actions through a shop.
	 * @param path The Path of the user.
	 * @param actions The actions executed along the way.
	 */
	public Trip(List<Position> path, List<Action> actions) {
		this.path = path;
		this.actions = actions;
	}

	/**
	 * @return The Path of the simulated person.
	 */
	public List<Position> getPath() {
		return path;
	}
	
	/**
	 * @return The actions done along this particular path.
	 */
	public List<Action> getActions() {
		return actions;
	}
}

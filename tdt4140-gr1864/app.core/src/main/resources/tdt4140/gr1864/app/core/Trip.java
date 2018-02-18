package tdt4140.gr1864.app.core;

import java.util.List;

public class Trip {
	private List<Coordinate> path;
	private List<PickUpAction> actions;
	
	public Trip(List<Coordinate> path, List<PickUpAction> actions) {
		this.path = path;
		this.actions = actions;
	}

	public List<Coordinate> getPath() {
		return path;
	}
	
	public List<PickUpAction> getActions() {
		return actions;
	}
}

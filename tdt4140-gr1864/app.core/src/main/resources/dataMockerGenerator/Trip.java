package dataMockerGenerator;

import java.util.List;

public class Trip {
	private List<Position> path;
	private List<PickUpAction> actions;
	
	public Trip(List<Position> path, List<PickUpAction> actions) {
		this.path = path;
		this.actions = actions;
	}

	public List<Position> getPath() {
		return path;
	}
	
	public List<PickUpAction> getActions() {
		return actions;
	}
}

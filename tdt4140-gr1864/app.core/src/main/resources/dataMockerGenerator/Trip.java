package dataMockerGenerator;

import java.util.List;

public class Trip {
	private List<Position> path;
	private List<Action> actions;
	
	public Trip(List<Position> path, List<Action> actions) {
		this.path = path;
		this.actions = actions;
	}

	public List<Position> getPath() {
		return path;
	}
	
	public List<Action> getActions() {
		return actions;
	}
}

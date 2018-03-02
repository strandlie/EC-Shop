package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import java.util.ArrayList;
import java.util.List;

import tdt4140.gr1864.app.ui.Mode.Option.InteractionOption;

public abstract class VisualizationElement {
	
	private String name;
	private List<InteractionOption> options;
	
	public VisualizationElement(String name) {
		this.name = name;
		this.options = new ArrayList<InteractionOption>();
	}
	

	public String getName() {
		if (! name.equals(null)) {
			return name;
		}
		return null;
	}
	
	public void setOption(InteractionOption option) {
		if (! this.options.contains(option)) {
			this.options.add(option);
		}
	}
	
	public List<InteractionOption> getOptions() {
		return this.options;
	}
}

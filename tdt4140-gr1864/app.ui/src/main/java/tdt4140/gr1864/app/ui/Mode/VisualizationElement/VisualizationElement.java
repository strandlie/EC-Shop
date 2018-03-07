package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import java.util.List;

import tdt4140.gr1864.app.ui.Mode.Option.InteractionOption;

public abstract class VisualizationElement {
	/**
	 * Originally thought as a superclass of all the visualizationElements. This proved a wrong assumption, and it is not used now. 
	 */
	
	private String name;
	private boolean isActive;
	private List<InteractionOption> options;
	
	public VisualizationElement(String name) {
		if (name.equals(null)) {
			throw new IllegalArgumentException("The name for this VisualizationElement cannot be null");
		}
		this.name = name;
		this.isActive = false;
	}
	
	public VisualizationElement(String name, boolean isActive) { 
		this(name);
		this.isActive = isActive;
	}

	public String getName() {
		if (! name.equals(null)) {
			return name;
		}
		return null;
	}
	
	public boolean isActive() {
		return this.isActive;
	}
	
	public void setActive(boolean isActive) {
		this.isActive = isActive;
	}

}

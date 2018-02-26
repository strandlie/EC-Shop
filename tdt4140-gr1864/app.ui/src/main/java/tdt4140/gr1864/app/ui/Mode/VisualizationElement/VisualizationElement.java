package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

public class VisualizationElement {
	
	private String name;
	private boolean isActive;
	
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

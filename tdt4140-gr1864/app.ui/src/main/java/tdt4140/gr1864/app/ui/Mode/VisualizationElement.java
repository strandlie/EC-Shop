package tdt4140.gr1864.app.ui.Mode;

public abstract class VisualizationElement {
	
	String name;
	
	public VisualizationElement(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		if (name.equals(null)) {
			throw new IllegalArgumentException("VisualizationElement cannot have name null");
		}
	}

}

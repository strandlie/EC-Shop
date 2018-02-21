package tdt4140.gr1864.app.ui.Mode;

import java.util.List;

import tdt4140.gr1864.app.ui.Mode.Option.InteractionOption;

public abstract class VisualizationElement {
	
	private String name;
	private List<InteractionOption> options;
	
	public VisualizationElement(String name, List<InteractionOption> options) {
		this.name = name;
		this.options = options; // If this is null, this VisualizationElement has no options
	}
	
	public String getName() {
		return this.name;
	}
	public void setName(String name) {
		if (name.equals(null)) {
			throw new IllegalArgumentException("VisualizationElement cannot have name null");
		}
	}
	
	
	/**
	 * Adds option to the options list. If the list already contains the option, it does nothing
	 * @param option A subclass of the InteractionOption abstract class
	 */
	public void addOption(InteractionOption option) {
		if (! this.options.contains(option)) {
			this.options.add(option);
		}
	}
	
	
	/**
	 * Returns all the options 
	 * @return 
	 */
	public List<InteractionOption> getInteractionOptions() {
		return this.options;
	}
	
	
	public InteractionOption getInteractionOption(String name) {
		if (options.equals(null)) {
			throw new IllegalStateException("Cant get the InteractionOption because this VisualizationElement has no options");
		}
		for (int i = 0; i < options.size(); i++) {
			if (this.options.get(i).getName().equals(name)) {
				return this.options.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Gets the option at a specific index (importance). Throws exception if the Element has no options or if negative index is supplied
	 * @param index The index (importance) of the options we want
	 * @return The InteractionOption. Returns null if no element is found at the index
	 */
	public InteractionOption getOption(Integer index) {
		if (this.options.equals(null)) {
			throw new IllegalStateException("This VisualizationElement has no options");
		}
		else if (index < 0) {
			throw new IllegalArgumentException("Index " + index.toString() + " is a negative number. Indices cannot be negative");
		}
		else if (index >= this.options.size() || this.options.size() == 0) {
			return null;
		}
		return this.options.get(index);
	}

}

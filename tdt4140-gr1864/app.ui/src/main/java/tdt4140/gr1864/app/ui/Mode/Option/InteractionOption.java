package tdt4140.gr1864.app.ui.Mode.Option;

public abstract class InteractionOption {
	
	/**
	 * The name of this InteractionOption. Will be displayed in the UI
	 */
	private String name;
	
	/**
	 * @param name The name of this option
	 */
	public InteractionOption(String name) {
		this.setName(name);
	}

	/**
	 * @return The name of this option
	 */
	public String getName() { 
		return this.name;
	}
	
	/**
	 * Used to set the name of the option after object generation
	 * @param name The new name
	 */
	public void setName(String name) {
		if (name.equals(null)) {
			throw new IllegalArgumentException("Cannot set the name of an InteractionOption to null");
		}
		this.name = name;
		
	}

}

package tdt4140.gr1864.app.ui.Mode.Option;

public abstract class InteractionOption {
	
	private String name;
	
	public InteractionOption(String name) {
		this.setName(name);
	}

	public String getName() { 
		return this.name;
	}
	
	public void setName(String name) {
		if (name.equals(null)) {
			throw new IllegalArgumentException("Cannot set the name of an InteractionOption to null");
		}
		this.name = name;
		
	}

}

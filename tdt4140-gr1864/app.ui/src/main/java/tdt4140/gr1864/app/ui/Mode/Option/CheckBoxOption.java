package tdt4140.gr1864.app.ui.Mode.Option;

public class CheckBoxOption extends InteractionOption {


	private boolean isEnabled;
	
	public CheckBoxOption(String name, boolean enableStartState) {
		super(name);
		this.isEnabled = enableStartState;
	}
	
	public boolean isEnabled() {
		return this.isEnabled;
	}
	
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	

}

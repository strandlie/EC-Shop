package tdt4140.gr1864.app.ui.Mode.Option;

/**
 * 
 * @author Hakon Strandlie
 * Not yet used in the app. Exists for future. 
 *
 */

public class CheckBoxOption extends InteractionOption {

	/**
	 * The state variable. Is this CheckBox checked? Defaults to false.
	 */
	private boolean isEnabled;
	
	/**
	 * @param name The name of this CheckBoxOption. Is shown in the UI
	 * @param enableStartState The startState of this button
	 */
	public CheckBoxOption(String name, boolean enableStartState) {
		super(name);
		this.isEnabled = enableStartState;
	}
	
	
	/**
	 * The constructor for when startState is not specified. Defaults to false
	 * @param name The name for this CheckBox. Is shown in the UI
	 */
	public CheckBoxOption(String name) {
		super(name);
		this.isEnabled = false;
	}
	
	/**
	 * @return The state of this CheckBox
	 */
	public boolean isEnabled() {
		return this.isEnabled;
	}
	
	/**
	 * @param isEnabled Changes the state of this button
	 */
	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}
	

}

package tdt4140.gr1864.app.ui.globalUIModel; 

import java.util.Collection; 
import java.util.HashMap; 
import tdt4140.gr1864.app.ui.Mode.Mode;

public class ModeController {
	
	private HashMap<String, Mode> modes;
	private Mode defaultMode;
	private Mode currentMode;
	
	private MenuViewController menuViewController;
	private VisualizationViewController visualizationViewController;
	private InteractionViewController interactionViewController;
	
	/**
	 * 
	 */
	public ModeController() {
		this.modes = new HashMap<String, Mode>();
		this.defaultMode = new Mode("DefaultMode", null);
		addMode(defaultMode);
		setDefaultMode();
		
	}
	
	public void addMode(Mode mode) {
		if (! this.modes.containsKey(mode.getName())) {
			this.modes.put(mode.getName(), mode);
		}
	}
	
	public Mode getMode(String name) {
		return this.modes.getOrDefault(name, null);
	}
	
	public Mode getCurrentMode() {
		return this.currentMode;
	}
	
	private void setMode(Mode mode) {
		if (! isValidMode(mode.getName())) {
			throw new IllegalArgumentException(mode.getName() + " is not a valid mode");
		}
		this.currentMode = mode;
	}
	
	public boolean isValidMode(String mode) {
		if (this.modes.containsKey(mode)) {
			return true;
		}
		return false;
	}
	
	public void setDefaultMode() {
		setMode(this.defaultMode);
	}
	
	public void modeChanged(String newMode) {
		
	}
	

}

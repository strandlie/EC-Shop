package tdt4140.gr1864.app.ui.globalUIModel; 

import java.util.Collection; 
import java.util.HashMap; 
import tdt4140.gr1864.app.ui.Mode.Mode;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;

public class ModeController {
	
	private HashMap<String, Mode> modes;
	private Mode defaultMode;
	private Mode currentMode;
	
	@FXML
	private MenuViewController menuViewController;
	@FXML
	private VisualizationViewController visualizationViewController;
	
	private InteractionViewController interactionViewController;
	
	/**
	 * 
	 */
	
	@FXML
	public void initialize() {
		
		this.modes = new HashMap<String, Mode>();
		this.defaultMode = new Mode("DefaultMode", null);
		this.modes.put(defaultMode.getName(), defaultMode);
		setDefaultMode();
		
		this.menuViewController.setModeController(this);
		
		addMode(new Mode("Most Picked-Up Product", null));
		addMode(new Mode("Stock", null));
	}
	
	public void addMode(Mode mode) {
		if (! this.modes.containsKey(mode.getName())) {
			this.modes.put(mode.getName(), mode);
			this.menuViewController.addMenuItem(mode.getName());
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
		Mode mode = getMode(newMode);
		if (mode == null) {
			throw new IllegalStateException("modeChanged should not be called when there is no corresponding mode in modes");
		}
		this.currentMode = mode;
		
	}
	

}

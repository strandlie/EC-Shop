package tdt4140.gr1864.app.ui.globalUIModel; 

import java.util.ArrayList;
import java.util.Collection; 
import java.util.HashMap;
import java.util.List;

import tdt4140.gr1864.app.ui.Mode.Mode;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.ProductAggregate;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationElement;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.TableColumn;
import javafx.scene.layout.VBox;

public class ModeController {
	
	private HashMap<String, Mode> modes;
	private Mode defaultMode;
	private Mode currentMode;
	
	
	
	@FXML
	private MenuViewController menuViewController;
	
	// Attributes relating to the visualizationViewcontroller:
	@FXML
	private VisualizationViewController visualizationViewController;
	
	private InteractionViewController interactionViewController;
	
	@FXML
	public void initialize() {
		
		this.modes = new HashMap<String, Mode>();
		this.defaultMode = new Mode("DefaultMode", null);
		this.modes.put(defaultMode.getName(), defaultMode);
		setDefaultMode();
		
		this.menuViewController.setModeController(this);
		
		TableColumn<String, String> productNameCol = new TableColumn("Product Name");
		
		List<TableColumn<String, String>> columns = new ArrayList<TableColumn<String, String>>();
		columns.add(new TableColumn<String, String>("Product Name"));
		columns.add(new TableColumn<String, String>("Times picked up"));
		columns.add(new TableColumn<String, String>("Times put down"));
		columns.add(new TableColumn<String, String>("Times bought"));
		
		VisualizationElement table = new VisualizationTable("Most Picked-Up Product", columns);
		Mode mostPickedUp = new Mode("Most Picked Up", table);
		
		addMode(mostPickedUp);
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
		
		ArrayList<ProductAggregate> list = new ArrayList<ProductAggregate>();
		list.add(new ProductAggregate("Bolle", "3", "2", "1"));
		list.add(new ProductAggregate("Sjokolade", "4","3","2"));
		list.add(new ProductAggregate("Bille", "1", "0", "0"));
		
	}
	

}

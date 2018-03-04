package tdt4140.gr1864.app.ui.globalUIModel; 

import java.util.HashMap;

import tdt4140.gr1864.app.ui.Mode.Mode;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.Aggregate;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;
import javafx.fxml.FXML;

/**
 * Initializes the different modes, and handles the switching between them. Also hands of responsibility
 * to the sub-controllerclasses
 * @author Håkon Strandlie
 *
 */
public class ModeController {
	/**
	 * Different modes saved with their names as key
	 */
	private HashMap<String, Mode> modes;
	
	/**
	 * The current mode for easy getting
	 */
	private Mode currentMode;
	
	
	/**
	 * The controller responsible for the menu
	 */
	@FXML
	private MenuViewController menuViewController;
	
	/**
	 * The controller responsible for the visualizationView
	 */
	@FXML
	private VisualizationViewController visualizationViewController;
	
	/**
	 * The controller responsible for the interactionView. Not yet implemented, but will be when we get visualization
	 * views with options, such as graphs and diagrams
	 */
	@FXML
	private InteractionViewController interactionViewController;
	
	/**
	 * Is called automatically by JavaFX after the scene is set up and the @FXML-variables are connected
	 * Is used here to set up the different modes and set the initial mode
	 */
	@FXML
	public void initialize() {
		this.modes = new HashMap<String, Mode>();
		
		/**
		 * The menuViewController needs a reference to it	's mode controller to let it know when the user
		 * has selected a different Mode
		 */
		this.menuViewController.setModeController(this);
		
		
		VisualizationTable mostPickedUpTable = new VisualizationTable("Most Picked-Up Product");
		mostPickedUpTable.addData(new Aggregate("Bolle", "3", "2", "1"));
		mostPickedUpTable.addData(new Aggregate("Sjokolade", "4","3","2"));
		mostPickedUpTable.addData(new Aggregate("Bille", "1", "0", "0"));
		mostPickedUpTable.addColumn("productName");
		mostPickedUpTable.addColumn("numberOfPickUp");
		mostPickedUpTable.addColumn("numberOfPutDown");
		mostPickedUpTable.addColumn("numberOfPurchases");
		Mode mostPickedUp = new Mode("Most Picked Up", mostPickedUpTable);
		
		VisualizationTable stockTable = new VisualizationTable("Stock");
		stockTable.addData(new Aggregate("Bolle", "5"));
		stockTable.addData(new Aggregate("Sjokolade", "20"));
		stockTable.addColumn("productName");
		stockTable.addColumn("numberInStock");
		Mode stock = new Mode("Stock", stockTable);
		
		
		addMode(mostPickedUp);
		addMode(stock);
		
		setMode(mostPickedUp);
	}
	
	/**
	 * Adds a mode. Used by the initialize-methods. Does not allow Modes with equal names
	 * @param mode An already constructed mode
	 */
	public void addMode(Mode mode) {
		if (! this.modes.containsKey(mode.getName())) {
			this.modes.put(mode.getName(), mode);
			this.menuViewController.addMenuItem(mode.getName());
		}
	}
	
	/**
	 * Returns an already created mode from its name, or null if it does not exist
	 * @param name String The name of the wanted Mode
	 * @return Mode The mode, or null
	 */
	public Mode getMode(String name) {
		return this.modes.getOrDefault(name, null);
	}
	
	/**
	 * Gets the mode currently shown to the user
	 * @return Mode The currently shown Mode
	 */
	public Mode getCurrentMode() {
		return this.currentMode;
	}
	
	/**
	 * Checks if the Mode already exists for this ModeController. If it does it sets it, and shows it to the user
	 * Can be improved by setting the table as a listener on the currentMode-variable
	 * @param mode Mode the mode we wish to set
	 */
	private void setMode(Mode mode) {
		if (! isValidMode(mode.getName())) {
			throw new IllegalArgumentException(mode.getName() + " is not a valid mode");
		}
		this.currentMode = mode;
		this.visualizationViewController.setData(mode.getVisualizationElement().getData());
		this.visualizationViewController.setColumns(mode.getVisualizationElement().getColumns());
	}
	
	/**
	 * Checks if the mode is a mode already created
	 * @param mode Mode The mode we wish to show to the user
	 * @return boolean 'true' if is an already existing mode
	 */
	public boolean isValidMode(String mode) {
		if (this.modes.containsKey(mode)) {
			return true;
		}
		return false;
	}
	
	/**
	 * The method called by the menuViewController when the user selects a new item in the menu list
	 * @param newMode String The String of the ListItem in the menu selected by the user
	 */
	public void modeChanged(String newMode) {
		Mode mode = getMode(newMode);
		if (mode == null) {
			throw new IllegalStateException("modeChanged should not be called when there is no corresponding mode in modes");
		}
		this.currentMode = mode;
		setMode(this.currentMode);
	}
	

}

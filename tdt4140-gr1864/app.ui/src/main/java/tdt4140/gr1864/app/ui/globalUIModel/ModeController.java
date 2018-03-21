package tdt4140.gr1864.app.ui.globalUIModel; 

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.TestDataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;
import tdt4140.gr1864.app.ui.TableLoader;
import tdt4140.gr1864.app.ui.Mode.Mode;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;
import javafx.fxml.FXML;

/**
 * Initializes the different modes, and handles the switching between them. Also hands of responsibility
 * to the sub-controllerclasses. Also runs the dataloader
 * @author Håkon Strandlie
 *
 */
public class ModeController {
	/**
	 * Different modes saved with their names as key. Only valid Modes exist here. 
	 */
	private HashMap<String, Mode> modes;
	
	/**
	 * The current mode for easy getting and comparing.
	 */
	private Mode currentMode;
	
	
	/**
	 * The controller responsible for showing the menu to the user
	 */
	@FXML
	private MenuViewController menuViewController;
	
	/**
	 * The controller responsible for showing the visualizationView to the user
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
		
		// Create a table for mostPickedUp Mode and fill with data
		VisualizationTable mostPickedUpTable = new VisualizationTable("Most Picked-Up Product");
		mostPickedUpTable.addColumn("productName");
		mostPickedUpTable.addColumn("numberOfPickUp");
		mostPickedUpTable.addColumn("numberOfPutDown");
		mostPickedUpTable.addColumn("numberOfPurchases");
		// Create mostPickedUp Mode and add table
		Mode mostPickedUp = new Mode("Most Picked Up", mostPickedUpTable);
		
		// Create a table for stockMode and fill with data
		VisualizationTable stockTable = new VisualizationTable("Stock");

		stockTable.addColumn("productName");
		stockTable.addColumn("numberInStock");
		// Create stock Mode and add table
		Mode stock = new Mode("Stock", stockTable);
		
		new TestDataLoader();
		
		// Get data from shopping trip and add to TableView
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		ActionDatabaseController adc = new ActionDatabaseController();
		
		ShoppingTrip trip = stdc.retrieve(1);
		trip.setActions(adc.retrieveAll(1));
		ArrayList<ShoppingTrip> shoppingTripList = new ArrayList<>();
		shoppingTripList.add(trip);
		
		TableLoader tableLoader = new TableLoader();
		tableLoader.loadMostPickedUpTable(shoppingTripList, mostPickedUpTable);
		
		// Get data from Shop and add to StockMode
		ShopDatabaseController sdc = new ShopDatabaseController();
		Shop shop = sdc.retrieve(1);
		shop.refreshShop();
		
		Map<Integer, Integer> productIDsOnShelf = shop.getShelfs();
		Map<Integer, Integer> productIDsInStorage = shop.getStorage();
		
		tableLoader.loadStockTable(productIDsOnShelf, productIDsInStorage, stockTable);
		
		// OnShelves
		VisualizationTable onShelvesTable = new VisualizationTable("Shelves");
		onShelvesTable.addColumn("productName");
		onShelvesTable.addColumn("numberOnShelves");
		Mode shelves = new Mode("Shelves", onShelvesTable);
		
		tableLoader.loadInShelvesTable(productIDsOnShelf, onShelvesTable);
		
		//Add modes and set default
		addMode(mostPickedUp);
		addMode(stock);
		addMode(shelves);
		
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
	 * @param mode Mode the mode we wish to set
	 */
	private void setMode(Mode mode) {
		if (! isValidMode(mode.getName())) {
			throw new IllegalArgumentException(mode.getName() + " is not a valid mode");
		}
		this.currentMode = mode;
		this.visualizationViewController.setActiveElement(mode.getVisualizationElement());
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
	 * It also wipes the data tables of the modes and re-loads them from DB.
	 * 
	 * @param newMode String The String of the ListItem in the menu selected by the user
	 */
	public void modeChanged(String newMode) {
		// Create tableLoader
		TableLoader tableLoader = new TableLoader();
		
		Mode mode = getMode(newMode);
		if (mode == null) {
			throw new IllegalStateException("modeChanged should not be called when there is no corresponding mode in modes");
		}
		
		// Wipe and re-load tables
		if (mode.getName() == "Shelves") {
			
			// Retrieve the shop from DB and extract products on shelves from said shop
			ShopDatabaseController sdc = new ShopDatabaseController();
			Shop shop = sdc.retrieve(1);
			shop.refreshShop();
			Map<Integer, Integer> productIDsOnShelf = shop.getShelfs();
			
			// Wipe the data table in mode
			VisualizationTable onShelvesTable = (VisualizationTable) mode.getVisualizationElement();
			onShelvesTable.wipeTable();
			
			// Load the data table with the new values
			tableLoader.loadInShelvesTable(productIDsOnShelf, onShelvesTable);
		}
			
		else if (mode.getName() == "Stock") {
			// Retrieve the shop from DB and extract products on shelves and in storage from said shop
			ShopDatabaseController sdc = new ShopDatabaseController();
			Shop shop = sdc.retrieve(1);
			shop.refreshShop();
			Map<Integer, Integer> productIDsOnShelf = shop.getShelfs();
			Map<Integer, Integer> productIDsInStorage = shop.getStorage();
			
			// Wipe the data table in mode
			VisualizationTable stockTable = (VisualizationTable) mode.getVisualizationElement();
			stockTable.wipeTable();
			
			// Load the data table with the new values
			tableLoader.loadStockTable(productIDsOnShelf, productIDsInStorage, stockTable);
			}
		
		else if (mode.getName() == "Most Picked Up") {

			// Retrieve the shopping trips from DB and put in a list
			ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
			ActionDatabaseController adc = new ActionDatabaseController();
			ArrayList<ShoppingTrip> shoppingTripList = new ArrayList<>();
			int iterator = 1;
			while(true) {
				ShoppingTrip trip = stdc.retrieve(iterator);
				if (trip == null) {
					break;
				}
				trip.setActions(adc.retrieveAll(iterator));
				shoppingTripList.add(trip);
				iterator++;
			}
			
			// Wipe the data table in mode
			VisualizationTable mostPickedUpTable = (VisualizationTable) mode.getVisualizationElement();
			mostPickedUpTable.wipeTable();
			
			// Load the data table with new values
			tableLoader.loadMostPickedUpTable(shoppingTripList, mostPickedUpTable);
		}
		
		this.currentMode = mode;
		setMode(this.currentMode);
		
	}
	

}

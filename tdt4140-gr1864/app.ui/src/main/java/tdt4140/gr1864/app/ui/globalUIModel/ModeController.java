package tdt4140.gr1864.app.ui.globalUIModel; 

import java.util.*;
import tdt4140.gr1864.app.core.Customer;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CoordinateDatabaseController;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.*;
import tdt4140.gr1864.app.ui.TableLoader;
import tdt4140.gr1864.app.ui.Mode.Mode;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationHeatMap;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationSimplePlot;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;
import javafx.fxml.FXML;

/**
 * Initializes the different modes, and handles the switching between them. Also hands of responsibility
 * to the sub-controllerclasses
 * @author Hakon StrandliE
 *
 */
public class ModeController implements Observer {
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
	 * @throws SQLException 
	 */
	@FXML
	public void initialize() throws SQLException {
		this.modes = new HashMap<String, Mode>();
		
		/**
		 * The menuViewController needs a reference to it	's mode controller to let it know when the user
		 * has selected a different Mode
		 */
		menuViewController.setModeController(this);
		
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
		
		DataLoader.main(null);

		VisualizationTable demographicsTable = new VisualizationTable("Demographics");
		demographicsTable.addColumn("customerId");
		demographicsTable.addColumn("firstName");
		demographicsTable.addColumn("lastName");
		demographicsTable.addColumn("address");
		demographicsTable.addColumn("zip");
		Mode demographicsMode = new Mode("Demographics", demographicsTable);


		// Get data from shoppin trip and add to TableView
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		ActionDatabaseController adc = new ActionDatabaseController();
		CoordinateDatabaseController cdc = new CoordinateDatabaseController();
	
		ShoppingTrip trip = stdc.retrieve(1);
		trip.setActions(adc.retrieveAll(1));
		trip.setCoordinates(cdc.retrieveAll(1));
		ArrayList<ShoppingTrip> shoppingTripList = new ArrayList<>();
		shoppingTripList.add(trip);
		
		TableLoader tableLoader = new TableLoader();
		tableLoader.loadMostPickedUpTable(shoppingTripList, mostPickedUpTable);
		
		// Get data from Shop and add to StockMode
		ShopDatabaseController sdc = new ShopDatabaseController();
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		ProductDatabaseController pdc = new ProductDatabaseController();

		Shop shop = sdc.retrieve(1);
		for (int i = 1; i < 65; i++) {
			osdc.retrieve(shop, i);
		}

		Map<Integer, Integer> productIDsOnShelf = shop.getShelfs();
		Map<Integer, Integer> productIDsInStorage = shop.getStorage();
		
		tableLoader.loadStockTable(productIDsOnShelf, productIDsInStorage, stockTable);

		// get data from demographics and add to DemographicsMode
		CustomerDatabaseController customerDatabaseController = new CustomerDatabaseController();
		List<Customer> customers = customerDatabaseController.retrieveAll();

		// Adding an observer to each customer to listen for changes
		for (Customer customer : customers) {
			customer.addObserver(this);
		}
		tableLoader.loadDemographicsTable(customers, demographicsTable);

		//Adding modes
		addMode(mostPickedUp);
		addMode(stock);
		addMode(demographicsMode);
		tableLoader.loadStockTable(productIDsOnShelf, productIDsInStorage, stockTable);
		
		VisualizationHeatMap heatMap = new VisualizationHeatMap("Heatmap", shoppingTripList);
		Mode heatMapMode = new Mode("Heatmap", heatMap);
		
		VisualizationSimplePlot plot = new VisualizationSimplePlot("Plot", shoppingTripList);
		Mode plotMode = new Mode("Plot", plot);
		
		addMode(mostPickedUp);
		addMode(stock);
		addMode(heatMapMode);
		addMode(plotMode);

		setMode(mostPickedUp);
	}
	
	/**
	 * Adds a mode. Used by the initialize-methods. Does not allow Modes with equal names
	 * @param mode An already constructed mode
	 */
	public  void addMode(Mode mode) {
		if (! modes.containsKey(mode.getName())) {
			modes.put(mode.getName(), mode);
			menuViewController.addMenuItem(mode.getName());
		}
	}

	public  void removeMode(Mode mode) {
		if (modes.containsKey(mode)) {
			modes.remove(mode);
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

    /**
     * Updates rows of customer table when a customer gets updated
     * TODO: Should be refactored to not delete and instantiate a new table each time
     */


    /**
     * Getting called every time a cutomer object gets updated
     * @param o a reference to the updated customer object
     * @param arg an argument
     */
	@Override
	public void update(Observable o, Object arg) {
		this.updatedRows();
	}
}

package tdt4140.gr1864.app.ui.globalUIModel; 

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import java.sql.SQLException;

import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.Receipt;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CoordinateDatabaseController;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

import tdt4140.gr1864.app.ui.Mode.VisualizationElement.*;
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
	 * @throws SQLException 
	 */
	@FXML
	public void initialize() throws SQLException {
		this.modes = new HashMap<String, Mode>();

		/**
		 * The menuViewController needs a reference to it's mode controller to let it know when the user
		 * has selected a different Mode
		 */
		menuViewController.setModeController(this);
		
		/*
		 * MostPickedUp
		 */
		VisualizationTable mostPickedUpTable = new VisualizationTable("Most Picked-Up Product");
		mostPickedUpTable.addColumn("productName");
		mostPickedUpTable.addColumn("numberOfPickUp");
		mostPickedUpTable.addColumn("numberOfPutDown");
		mostPickedUpTable.addColumn("numberOfPurchases");
		Mode mostPickedUp = new Mode("Most Picked Up", mostPickedUpTable);
		
		/*
		 * Stock
		 */
		VisualizationTable stockTable = new VisualizationTable("Stock");
		stockTable.addColumn("productName");
		stockTable.addColumn("numberInStock");
		Mode stock = new Mode("Stock", stockTable);
		
		/*
		 * DEMOGRAPHICS MODE
		 */
		VisualizationTable demographicsTable = new VisualizationTable("Demographics");//, customerList);
		demographicsTable.addColumn("customerId");
		demographicsTable.addColumn("firstName");
		demographicsTable.addColumn("lastName");
		demographicsTable.addColumn("address");
		demographicsTable.addColumn("zip");
		Mode demographicsMode = new Mode("Demographics", demographicsTable);

		/*
		 * ON SHELF MODE
		 */
		VisualizationTable onShelvesTable = new VisualizationTable("Shelves");//, productIDsOnShelf);
		onShelvesTable.addColumn("productName");
		onShelvesTable.addColumn("numberOnShelves");
		Mode shelves = new Mode("Shelves", onShelvesTable);
		
		/*
		 * LOAD DATA
		 */
		// Load data into DB
		DataLoader.main(null);
		
		// Extract shopping trips(with actions and coordinates)
		ArrayList<ShoppingTrip> shoppingTripList = getShoppingTripsFromDB();
		// Load data into mostPickedUp
		TableLoader tableLoader = new TableLoader();
		tableLoader.loadMostPickedUpTable(shoppingTripList, mostPickedUpTable);
		
		// Get shop and update
		Shop shop = getShopFromDBAndUpdateFromTrips(shoppingTripList);
		// Load stock and shelves
		tableLoader.loadStockTable(shop.getShelfs(), shop.getStorage(), stockTable);
		tableLoader.loadInShelvesTable(shop.getShelfs(), onShelvesTable);

		// get data from demographics and add to DemographicsMode
		CustomerDatabaseController customerDatabaseController = new CustomerDatabaseController();
		List<Customer> customers = customerDatabaseController.retrieveAll();
		tableLoader.loadDemographicsTable(customers, demographicsTable);

		/*
		 * HEAT MAP MODE
		 */
		VisualizationHeatMap heatMap = new VisualizationHeatMap("Heatmap", shoppingTripList);
		Mode heatMapMode = new Mode("Heatmap", heatMap);
		
		/*
		 * PLOT MODE
		 */
		VisualizationSimplePlot plot = new VisualizationSimplePlot("Plot", shoppingTripList);
		Mode plotMode = new Mode("Plot", plot);

		
		//Adding modes
		addMode(mostPickedUp);
		addMode(stock);
		addMode(demographicsMode);
		addMode(shelves);
		addMode(heatMapMode);
		addMode(plotMode);
		
		setMode(mostPickedUp);
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		// scheduleAtFixedRate(?, Delay, periode, ?)
		executor.scheduleAtFixedRate(runnable, 1, 10, TimeUnit.SECONDS);
		


	}
	
	/**
	 * A method that extracts all trips from DB and fill them with their coordinates and actions
	 * @return	An ArrayList of ShoppingTrips
	 */
	public ArrayList<ShoppingTrip> getShoppingTripsFromDB(){
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		ActionDatabaseController adc = new ActionDatabaseController();
		CoordinateDatabaseController cdc = new CoordinateDatabaseController();
		
		ArrayList<ShoppingTrip> shoppingTripList = new ArrayList<>();
		int iterator = 1;
		while(true) {
			ShoppingTrip trip = stdc.retrieve(iterator);
			if (trip == null) {
				break;
			}
			trip.setActions(adc.retrieveAll(iterator));
			trip.setCoordinates(cdc.retrieveAll(iterator));
			shoppingTripList.add(trip);
			iterator++;
		}
		return shoppingTripList;
	}
	
	/**
	 * Exracts and update shop drom DB and update shelves from a list of all shoppingtrips
	 * @param shoppingTripList	An ArrayList of all shoppingtrips in DB
	 * @return					The updated shop object
	 */
	public Shop getShopFromDBAndUpdateFromTrips(ArrayList<ShoppingTrip> shoppingTripList) {
		ShopDatabaseController sdc = new ShopDatabaseController();
		Shop shop = sdc.retrieve(1);
		shop.refreshShop();
		// Update the shop object
		for (ShoppingTrip trip : shoppingTripList) {
			shop.updateShopFromReceipt(new Receipt(trip));
		}
		return shop;
	}

	/**
	 * Adds a mode. Used by the initialize-methods. Does not allow Modes with equal names
	 *
	 * @param mode An already constructed mode
	 */
	public void addMode(Mode mode) {
		if (!modes.containsKey(mode.getName())) {
			modes.put(mode.getName(), mode);
			menuViewController.addMenuItem(mode.getName());
		}
	}

	/**
	 * Removes a mode
	 * @param mode: mode to be removed
	 */
	public void removeMode(Mode mode) {
		if (modes.containsKey(mode)) {
			modes.remove(mode);
		}
	}

	/**
	 * Returns an already created mode from its name, or null if it does not exist
	 *
	 * @param name String The name of the wanted Mode
	 * @return Mode The mode, or null
	 */
	public Mode getMode(String name) {
		return this.modes.getOrDefault(name, null);
	}

	/**
	 * Gets the mode currently shown to the user
	 *
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
		if (!isValidMode(mode.getName())) {
			throw new IllegalArgumentException(mode.getName() + " is not a valid mode");
		}
		this.currentMode = mode;
		this.visualizationViewController.setActiveElement(mode.getVisualizationElement());
	}

	/**
	 * Checks if the mode is a mode already created
	 *
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
		Mode mode = getMode(newMode);
		if (mode == null) {
			throw new IllegalStateException("modeChanged should not be called when there is no corresponding mode in modes");
		}
		
		this.currentMode = mode;
		setMode(this.currentMode);
		
	}
	
	/**
     * Updates rows of all Modes that contains VisualizationTables
	 */
	@SuppressWarnings({ "static-access"})
	public void updateTableRows() {
		
		VisualizationTable table;
		
		for (String modeName : modes.keySet()) {
			Mode mode = getMode(modeName);
		
			if (mode.getName() == "Shelves") {
				
				// Retrieve the shopping trips from DB and put in a list
				ArrayList<ShoppingTrip> shoppingTripList = getShoppingTripsFromDB();
				// Retrieve shop from DB and update from shoppingTrips
				Shop shop = getShopFromDBAndUpdateFromTrips(shoppingTripList);
				// Extract table, wipe and load
				table = (VisualizationTable) mode.getVisualizationElement();
				table.wipeTable();
				TableLoader loader = new TableLoader();
				loader.loadInShelvesTable(shop.getShelfs(), table);
			}
				
			else if (mode.getName() == "Stock") {
				
				// Retrieve the shopping trips from DB and put in a list
				ArrayList<ShoppingTrip> shoppingTripList = getShoppingTripsFromDB();
				// Retrieve the shop from DB
				Shop shop = getShopFromDBAndUpdateFromTrips(shoppingTripList);
				// Extract table, wipe and load
				table = (VisualizationTable) mode.getVisualizationElement();
				table.wipeTable();
				TableLoader loader = new TableLoader();
				loader.loadStockTable(shop.getShelfs(), shop.getStorage(), table);
			}
			
			else if (mode.getName() == "Most Picked Up") {

				// Retrieve the shopping trips from DB and put in a list
				ArrayList<ShoppingTrip> shoppingTripList = getShoppingTripsFromDB();
				// Extract table, wipe and load
				table = (VisualizationTable) mode.getVisualizationElement();
				table.wipeTable();
				TableLoader loader = new TableLoader();
				loader.loadMostPickedUpTable(shoppingTripList, table);
			}
			else if (mode.getName() == "Demographics") {
				
				// Retrive list of customers
				CustomerDatabaseController cdc = new CustomerDatabaseController();
				List<Customer> customerList = cdc.retrieveAll();
				// Extract table, wipe and load
				table = (VisualizationTable) mode.getVisualizationElement();
				table.wipeTable();
				TableLoader loader = new TableLoader();
				loader.loadDemographicsTable(customerList, table);
			}
		}
	}

	/**
	 * Code to be run every few seconds. Are calling updaterows at a fixed interval
	 * Could be extended
	 */
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			updateTableRows();
		}
	};

}


package tdt4140.gr1864.app.ui.globalUIModel; 

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import tdt4140.gr1864.app.core.Customer;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import tdt4140.gr1864.app.core.Receipt;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CoordinateDatabaseController;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.*;
import tdt4140.gr1864.app.ui.scheduling.GUIUpdaterRunnable;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

import tdt4140.gr1864.app.ui.TableLoader;
import tdt4140.gr1864.app.ui.Mode.Mode;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;

import javafx.fxml.FXML;
import javafx.application.Platform;

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
		
		Mode durationMode = new Mode("", null);


		/*
		 * HEAT MAP MODE
		 */
		VisualizationHeatMap heatMap = new VisualizationHeatMap("Heatmap");
		Mode heatMapMode = new Mode("Heatmap", heatMap);
		
		/*
		 * PLOT MODE
		 */
		VisualizationSimplePlot plot = new VisualizationSimplePlot("Plot");
		Mode plotMode = new Mode("Plot", plot);

		
		addMode(durationMode); // Must be the first mode
		addMode(mostPickedUp);
		addMode(stock);
		addMode(demographicsMode);
		addMode(shelves);
		addMode(heatMapMode);
		addMode(plotMode);

		updateMostPickedUpTable();
		updateStockTable();
		updateDemographicsTable();
		updateShelvesTable();
		updateDurationModeField();
		updateHeatMap();
		updatePlot();
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(runnable, 10, 10, TimeUnit.SECONDS);
		setMode(mostPickedUp);
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
	 * Returns the mode with a similar name as the input string, or null
	 * @param name The string name to match against
	 * @return The Mode, or null
	 */
	public Mode getModeWithSimilarNameAs(String name) {
		for (String key : this.modes.keySet()) {
			if (key.contains(name)) {
				return getMode(key);
			}
		}
		return null;
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
		
		switch (getCurrentMode().getName()) {
			case "Demographics":
				updateDemographicsTable();
				break;
			case "Most Picked Up":
				updateMostPickedUpTable();
				break;
			case "Heatmap":
				updateHeatMap();
				break;
			case "Plot":
				updatePlot();
				break;
			case "Shelves":
				updateShelvesTable();
				break;
			case "Stock":
				updateStockTable();
				break;
			default:
				throw new IllegalStateException("Not a valid modename");
			
		}
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
	 * Method to get latest data from DB, and update the MostPickedUpTable with this new data. Also used for initializing the table.
	 */
	public void updateMostPickedUpTable() {
		VisualizationTable mostPickedUpTable = (VisualizationTable) getMode("Most Picked Up").getVisualizationElement();
	
		List<ShoppingTrip> shoppingTripList = getShoppingTripsFromDB();

		TableLoader.loadMostPickedUpTable(shoppingTripList, mostPickedUpTable);
	}
	
	/**
	 * Method to get latest data from DB, and update the StockTable with this new data. 
	 */
	public void updateStockTable() {
		ArrayList<ShoppingTrip> shoppingTripList = getShoppingTripsFromDB();
		Shop shop = getShopFromDBAndUpdateFromTrips(shoppingTripList);
		
		VisualizationTable stockTable = (VisualizationTable) getMode("Stock").getVisualizationElement();
		
		TableLoader.loadStockTable(shop.getShelfs(), shop.getStorage(), stockTable);
	}

	/**
	 * Method to get latest data from DB, and update the DemographicsTable with this new data. 
	 */
	public void updateDemographicsTable() {
		
			CustomerDatabaseController cdc = new CustomerDatabaseController();
			
			// Retrive list of customers
			List<Customer> customerList = cdc.retrieveAll();
			// Extract table, wipe and load
			VisualizationTable table = (VisualizationTable) getMode("Demographics").getVisualizationElement();
			TableLoader.loadDemographicsTable(customerList, table);
	}
	
	/**
	 * Method to get latest data from DB, and update the ShelvesTable with this new data. 
	 */
	public void updateShelvesTable() {
			// Retrieve the shopping trips from DB and put in a list
			ArrayList<ShoppingTrip> shoppingTripList = getShoppingTripsFromDB();
			// Retrieve shop from DB and update from shoppingTrips
			Shop shop = getShopFromDBAndUpdateFromTrips(shoppingTripList);
			// Extract table and load
			VisualizationTable table = (VisualizationTable) getMode("Shelves").getVisualizationElement();
			TableLoader.loadInShelvesTable(shop.getShelfs(), table);
		
	}
	
	
	/**
	 * Method to get latest data from DB, and update the DurationModeField with a new value from this new data. 
	 */
	public void updateDurationModeField() {
		long sumOfDurations = 0;
		int numberOfTrips = 0;
		List<ShoppingTrip> allShoppingTrips = getShoppingTripsFromDB();
		for (ShoppingTrip trip : allShoppingTrips) {
			numberOfTrips++;
			sumOfDurations += trip.getDuration();
		}
		
		double average = (double) sumOfDurations / numberOfTrips / 1000 / 60;
		DecimalFormat df = new DecimalFormat("#.#");
		String minutes = df.format(average);
		this.menuViewController.updateTopMenuItem("Average time in store: " + minutes + "min");
	}
	
	
	/**
	 * Method to get latest data from DB, and update the HeatMap from this new data. 
	 */
	public void updateHeatMap() {
		List<ShoppingTrip> shoppingTripList = getShoppingTripsFromDB();
		VisualizationHeatMap heatMap = (VisualizationHeatMap) getMode("Heatmap").getVisualizationElement();
		heatMap.setData(shoppingTripList);
	}
	
	
	/**
	 * Method to get latest data from DB, and update the Plot 
	 */
	public void updatePlot() {
		List<ShoppingTrip> shoppingTripList = getShoppingTripsFromDB();
		VisualizationSimplePlot plot = (VisualizationSimplePlot) getMode("Plot").getVisualizationElement();
		plot.setData(shoppingTripList);
	}
	
	/**
	 * Calls the appropriate method to sort the TableView. Is called after the GUi updates itself.
	 */
	public void visualizationViewTableViewSort() {
		this.visualizationViewController.sortTableView();
	}

	/**
	 * Code to be run every few seconds.
	 */
	Runnable guiUpdater = new GUIUpdaterRunnable(this);
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Platform.runLater(guiUpdater);
		}
	};
}


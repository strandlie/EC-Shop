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
import java.util.Map;

import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CoordinateDatabaseController;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.*;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.*;
import tdt4140.gr1864.app.ui.scheduling.GUIUpdaterRunnable;
import tdt4140.gr1864.app.core.database.TestDataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.OnShelfDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;
import tdt4140.gr1864.app.ui.TableLoader;
import tdt4140.gr1864.app.ui.Mode.Mode;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;
import javafx.fxml.FXML;
import javafx.application.Platform;

/**
 * Initializes the different modes, and handles the switching between them. Also hands of responsibility
 * to the sub-controllerclasses
 * @author Hakon StrandliE
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
		 * The menuViewController needs a reference to it	's mode controller to let it know when the user
		 * has selected a different Mode
		 */
		menuViewController.setModeController(this);
		DataLoader.main(null);
		
		
		VisualizationTable mostPickedUpTable = new VisualizationTable("Most Picked-Up Product");
		mostPickedUpTable.addColumn("productName");
		mostPickedUpTable.addColumn("numberOfPickUp");
		mostPickedUpTable.addColumn("numberOfPutDown");
		mostPickedUpTable.addColumn("numberOfPurchases");
		Mode mostPickedUp = new Mode("Most Picked Up", mostPickedUpTable);

		VisualizationTable stockTable = new VisualizationTable("Stock");
		stockTable.addColumn("productName");
		stockTable.addColumn("numberInStock");
		Mode stock = new Mode("Stock", stockTable);


		VisualizationTable demographicsTable = new VisualizationTable("Demographics");
		demographicsTable.addColumn("customerId");
		demographicsTable.addColumn("firstName");
		demographicsTable.addColumn("lastName");
		demographicsTable.addColumn("address");
		demographicsTable.addColumn("zip");
		Mode demographicsMode = new Mode("Demographics", demographicsTable);
		
		Mode durationMode = new Mode("", null);

		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		List<ShoppingTrip> shoppingTripList = stdc.retrieveAllShoppingTrips();

		VisualizationHeatMap heatMap = new VisualizationHeatMap("Heatmap", shoppingTripList);
		Mode heatMapMode = new Mode("Heatmap", heatMap);
		
		VisualizationSimplePlot plot = new VisualizationSimplePlot("Plot", shoppingTripList);
		Mode plotMode = new Mode("Plot", plot);
		
		addMode(durationMode); // Must be the first mode
		addMode(mostPickedUp);
		addMode(stock);
		addMode(demographicsMode);
		addMode(heatMapMode);
		addMode(plotMode);

		updateMostPickedUpTable();
		updateStockTable();
		updateDemographicsTable();
		updateDurationModeField();
		
		ScheduledExecutorService executor = Executors.newScheduledThreadPool(1);
		executor.scheduleAtFixedRate(runnable, 3, 3, TimeUnit.SECONDS);

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
	
	public void updateMostPickedUpTable() {
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		ActionDatabaseController adc = new ActionDatabaseController();
		CoordinateDatabaseController cdc = new CoordinateDatabaseController();
		VisualizationTable mostPickedUpTable = (VisualizationTable) getMode("Most Picked Up").getVisualizationElement();
	
		List<ShoppingTrip> shoppingTripList = new ArrayList<>();
		shoppingTripList.addAll(stdc.retrieveAllShoppingTrips());
		for (ShoppingTrip trip : shoppingTripList) {
			trip.setActions(adc.retrieveAll(1));
			trip.setCoordinates(cdc.retrieveAll(1));
		}

		TableLoader tableLoader = new TableLoader();
		tableLoader.loadMostPickedUpTable(shoppingTripList, mostPickedUpTable);
	}
	
	public void updateStockTable() {
		ShopDatabaseController sdc = new ShopDatabaseController();
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		
		VisualizationTable stockTable = (VisualizationTable) getMode("Stock").getVisualizationElement();

		Shop shop = sdc.retrieve(1);
		for (int i = 1; i < 65; i++) {
			osdc.retrieve(shop, i);
		}

		Map<Integer, Integer> productIDsOnShelf = shop.getShelfs();
		Map<Integer, Integer> productIDsInStorage = shop.getStorage();
		
		TableLoader tableLoader = new TableLoader();
		tableLoader.loadStockTable(productIDsOnShelf, productIDsInStorage, stockTable);
		
	}

	/**
     * Updates rows of demographicstable
	 */
	public void updateDemographicsTable() {
		
        CustomerDatabaseController cdc = new CustomerDatabaseController();
        List<Customer> customers = cdc.retrieveAll();

        VisualizationInterface tableInterface = getMode("Demographics").getVisualizationElement();
		VisualizationTable table = (VisualizationTable) tableInterface;
		table.wipeTable();
        TableLoader loader = new TableLoader();
        loader.loadDemographicsTable(customers, table);
	}
	
	
	public void updateDurationModeField() {
		CoordinateDatabaseController cdc = new CoordinateDatabaseController();
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		long sumOfDurations = 0;
		int numberOfTrips = 0;
		List<ShoppingTrip> allShoppingTrips = stdc.retrieveAllShoppingTrips();
		for (ShoppingTrip trip : allShoppingTrips) {
			trip.setCoordinates(cdc.retrieveAll(trip.getID()));
			numberOfTrips++;
			sumOfDurations += trip.getDuration();
		}
		
		double average = (double) sumOfDurations / numberOfTrips / 1000 / 60;
		DecimalFormat df = new DecimalFormat("#.#");
		String minutes = df.format(average);
		this.menuViewController.updateTopMenuItem("Average time in store: " + minutes + "min");
	}
	
	
	public void updateHeatMap() {
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		List<ShoppingTrip> shoppingTripList = stdc.retrieveAllShoppingTrips();
		VisualizationHeatMap heatMap = (VisualizationHeatMap) getMode("Heatmap").getVisualizationElement();
		heatMap.setData(shoppingTripList);
	}
	
	
	public void updatePlot() {
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		List<ShoppingTrip> shoppingTripList = stdc.retrieveAllShoppingTrips();
		VisualizationSimplePlot plot = (VisualizationSimplePlot) getMode("Plot").getVisualizationElement();
		plot.setData(shoppingTripList);
	}

	/**
	 * Code to be run every few seconds. Are calling updaterows at a fixed interval
	 * Could be extended
	 */
	Runnable guiUpdater = new GUIUpdaterRunnable(this);
	Runnable runnable = new Runnable() {
		@Override
		public void run() {
			Platform.runLater(guiUpdater);
		}
	};
	
}


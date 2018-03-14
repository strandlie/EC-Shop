package tdt4140.gr1864.app.ui.globalUIModel; 

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import eu.hansolo.fx.charts.heatmap.HeatMap;
import eu.hansolo.fx.charts.tools.Point;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.Coordinate;
import tdt4140.gr1864.app.core.databasecontrollers.CoordinateDatabaseController;
import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.OnShelfDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;
import tdt4140.gr1864.app.ui.TableLoader;
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
		this.menuViewController.setModeController(this);
		
		
		VisualizationTable mostPickedUpTable = new VisualizationTable("Most Picked-Up Product");
		mostPickedUpTable.addColumn("productName");
		mostPickedUpTable.addColumn("numberOfPickUp");
		mostPickedUpTable.addColumn("numberOfPutDown");
		mostPickedUpTable.addColumn("numberOfPurchases");
		Mode mostPickedUp = new Mode("Most Picked Up", mostPickedUpTable);
		
		VisualizationTable stockTable = new VisualizationTable("Stock");
		/*
		stockTable.addData(new Aggregate("Bolle", "5"));
		stockTable.addData(new Aggregate("Sjokolade", "20"));
		*/
		stockTable.addColumn("productName");
		stockTable.addColumn("numberInStock");
		Mode stock = new Mode("Stock", stockTable);
		
		new DataLoader();
		
		// Get data from shoppin trip and add to TableView
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		ActionDatabaseController adc = new ActionDatabaseController();
		CoordinateDatabaseController cdc = new CoordinateDatabaseController();
	
		ShoppingTrip trip = stdc.retrieve(1);
		trip.setActions(adc.retrieveAll(1));
		trip.setCoordinates(cdc.retrieveAll(1));
		ArrayList<ShoppingTrip> shoppingTripList = new ArrayList<>();
		shoppingTripList.add(trip);
		
		HeatMap map = new HeatMap(180, 100);
		
		List<Point> points = new ArrayList<>();
		
		for (ShoppingTrip shoppingTrip : shoppingTripList) {
			for (Coordinate coordinate : shoppingTrip.getCoordinates()) {
				points.add(new Point(coordinate.getX(), coordinate.getY()));
			}
		}
		
		map.addSpots(points);
		map.saveAsPng("map");
		
		new TableLoader(shoppingTripList, mostPickedUpTable);
		
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
		
		new TableLoader(productIDsOnShelf, productIDsInStorage, stockTable);
		
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

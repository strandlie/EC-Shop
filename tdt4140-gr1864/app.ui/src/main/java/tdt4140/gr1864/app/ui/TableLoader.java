package tdt4140.gr1864.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdt4140.gr1864.app.core.*;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.Row;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;

/**
 * This is the class that loades a table with data from a list of Trips. 
 * The visualizationTable that is an argument in both constructors is the
 * model-layer-representation of the table shown to the user in the GUI.
 * 
 * Can be accessed in a static way, or instanciated. Is instanciated in a VisualizationTable
 * @author hstrandlie
 *
 */
public class TableLoader {
	
	/**
	 * The list of shoppingtrips for MostPickedUpMode
	 */
	private List<ShoppingTrip> trips;
	
	/**
	 * The list of customers for DemographicsMode
	 */
	private List<Customer> customers;
	
	/**
	 * The Map of ProductIDs on shelf
	 */
	private Map<Integer, Integer> productIDsOnShelf;
	
	/**
	 * The Map of ProductIDs In Storage
	 */
	private Map<Integer, Integer> productIDsInStorage;
	
	/**
	 * The table that this TableLoader loads
	 */
	private VisualizationTable table;
	
	/**
	 * Empty constructor to support legacy
	 */
	public TableLoader() {}
	
	/**
	 * Constructor for the MostPickedUpMode VisualizationTable
	 * @param trips
	 * @param table
	 */
	public TableLoader(ArrayList<ShoppingTrip> trips, VisualizationTable table) {
		this.trips = trips;
		this.table = table;
		loadMostPickedUpTable();
	}
	
	/**
	 * Constructor for Stock Mode
	 * @param productIDsOnShelf
	 * @param productIDsInStorage
	 * @param table
	 */
	public TableLoader(Map<Integer, Integer> productIDsOnShelf, Map<Integer, Integer> productIDsInStorage, VisualizationTable table) {
		this.productIDsOnShelf = productIDsOnShelf;
		this.productIDsInStorage = productIDsInStorage;
		this.table = table;
		loadStockTable();
	}
	
	/**
	 * Constructor for OnShelf Mode
	 * @param productIDsOnShelf
	 * @param table
	 */
	public TableLoader(Map<Integer, Integer> productIDsOnShelf, VisualizationTable table) {
		this.productIDsOnShelf = productIDsOnShelf;
		this.table = table;
		loadInShelvesTable();
	}
	
	public TableLoader(List<Customer> customers, VisualizationTable table) {
		this.customers = customers;
		this.table = table;
		loadDemographicsTable();
	}
	
	public List<ShoppingTrip> getTrips() {
		return this.trips;
	}
	
	public void setTrips(List<ShoppingTrip> trips) {
		this.trips = trips;
	}
	
	public List<Customer> getCustomers() {
		return this.customers;
	}
	
	public void setCustomers(List<Customer> customers) {
		this.customers = customers;
	}
	
	public Map<Integer, Integer> getProductIDsOnShelf() {
		return this.productIDsOnShelf;
	}
	
	public void setProductIDsOnShelf(Map<Integer, Integer> productIDsOnShelf) {
		this.productIDsOnShelf = productIDsOnShelf;
	}
	
	public Map<Integer, Integer> getProductIDsInStorage() {
		return this.productIDsInStorage;
	}
	
	public void setProductIDsInStorage(Map<Integer, Integer> productIDsInStorage) {
		this.productIDsInStorage = productIDsInStorage;	
	}
	
	/**
	 * Load the table for MostPickedUpMode. 
	 * Only loads the data in the instance. Is called in the constructor. 
	 */
	public void loadMostPickedUpTable() {
		loadMostPickedUpTable(this.trips, this.table);
	}
	
	/**
	 * Load the table from MostPickedUpMode with data from new trips
	 * @param trips The new trips that is to be loaded into the table
	 */
	public void loadMostPickedUpTable(List<ShoppingTrip> trips) {
		this.trips = trips;
		loadMostPickedUpTable();
	}
	
	/**
	 * Wipes the table clear, and loads the data from the trips into the table to 
	 * @param trips A list of shopping trips to load the table from
	 * @param table A model-layer representation of a table shown to the user. Any changes made to this will reflect to the user immidiately
	 */
	public static void loadMostPickedUpTable(List<ShoppingTrip> trips, VisualizationTable table) {
		
		table.wipeTable();
		
		if (trips == null || table == null) {
			return;
		}
		Map<String, Integer> pickUps = new HashMap<String, Integer>();
		Map<String, Integer> putDowns = new HashMap<String, Integer>();
		Map<String, Integer> purchases = new HashMap<String, Integer>();
		
		for (ShoppingTrip trip : trips) {
			for (Action action : trip.getActions()) {
				if (action.getActionType() == Action.PICK_UP) {
					int previous = pickUps.containsKey(action.getProduct().getName()) ? pickUps.get(action.getProduct().getName()) : 0;
					pickUps.put(action.getProduct().getName(), previous + 1);
				}
				else {
					int previous = putDowns.containsKey(action.getProduct().getName()) ? putDowns.get(action.getProduct().getName()) : 0;
					putDowns.put(action.getProduct().getName(), previous + 1);
				}
			}
		}
		
		for (String productName : pickUps.keySet()) {
			// Assumes there are no products that are put down, that are not picked up
			if (! putDowns.containsKey(productName)) {
				// If there are no put downs, only pickups, the number of purchases is equal to the number of pickups
				purchases.put(productName, pickUps.get(productName));
			}
			else {
				// Number of purchases = (the number of pickups) - (the number of putdowns)
				purchases.put(productName, pickUps.get(productName) - putDowns.get(productName));
			}
		}
		
		for (String productNameString : pickUps.keySet()) {
			String pickupsString = pickUps.get(productNameString).toString();
			String putdownsString = putDowns.containsKey(productNameString) ? putDowns.get(productNameString).toString() : "0";
			String purchasesString = purchases.containsKey(productNameString) ? purchases.get(productNameString).toString() : "0";
			table.addData(new Row(productNameString, pickupsString, putdownsString, purchasesString));
		}
	}
	
	/**
	 * Load the data for the Stock Mode into the table
	 * Only loads the data contained in the instance. Is called in constructor. 
	 */
	public void loadStockTable() {
		loadStockTable(this.productIDsOnShelf, this.productIDsInStorage, this.table);
	}
	
	/**
	 * Load new data into the table of the instance
	 * @param productIDsOnShelf
	 * @param productIDsInStorage
	 */
	public void loadStockTable(Map<Integer, Integer> productIDsOnShelf, Map<Integer, Integer> productIDsInStorage) {
		this.productIDsOnShelf = productIDsOnShelf;
		this.productIDsInStorage = productIDsInStorage;
		loadStockTable();
	}
	
	/**
	 * @param productIDsOnShelf The Map of the productIDs with corresponding count on shelf
	 * @param productIDsInStorage The Map of the productIDs with corresponding count in storage
	 * @param table A model-layer representation of a table shown to the user. Any changes made to this will reflect to the user immidiately
	 */
	public static void loadStockTable(Map<Integer, Integer> productIDsOnShelf, Map<Integer, Integer> productIDsInStorage, VisualizationTable table) {
		
		table.wipeTable();
		
		if (productIDsOnShelf == null || productIDsInStorage == null || table == null) {
			return;
		}
		Map<String, Integer> stock = new HashMap<String, Integer>();
		ProductDatabaseController pdc = new ProductDatabaseController();
		ArrayList<Integer> seen = new ArrayList<>();
		for (Integer productID : productIDsOnShelf.keySet()) {
			Product product = pdc.retrieve(productID);
			int numberOnShelf = productIDsOnShelf.containsKey(productID) ? productIDsOnShelf.get(productID) : 0;
			int numberInStorage = productIDsInStorage.containsKey(productID) ? productIDsInStorage.get(productID) : 0;
			int sum = numberOnShelf + numberInStorage;
			stock.put(product.getName(), sum);
			seen.add(productID);
		}
		
		for (Integer productID : productIDsInStorage.keySet()) {
			if (! seen.contains(productID)) {
				Product product = pdc.retrieve(productID);
				stock.put(product.getName(), productIDsInStorage.get(productID));
			}
		}
		
		for (String productNameString : stock.keySet()) {
			String totalStock = stock.get(productNameString).toString();
			table.addData(new Row(productNameString, totalStock, "stock"));
		}
	}
	
	public void loadDemographicsTable() {
		loadDemographicsTable(this.customers, this.table);
	}
	
	public void loadDemographicsTable(List<Customer> customers) {
		this.customers = customers;
		loadDemographicsTable();
	}

	/**
	 *
	 * @param customers
	 * @param table
	 * @param inputZero to differentiate the differetn tableloaders, no real function
	 */
	public static void loadDemographicsTable(List<Customer> customers, VisualizationTable table) {
		if (customers == null || table == null) {
			return;
		}

		for (Customer customer: customers) {
			table.addData(new Row(Integer.toString(customer.getUserId()), customer.getFirstName(), customer.getLastName(), customer.getAddress(), Integer.toString(customer.getZip())));
		}
	}

	/**
	 * Load the data in the productIDsInShelf-Map into the table. C
	 * Only uses the data in the instance. Is called by the constructor. 
	 */
	public void loadInShelvesTable() {
		loadInShelvesTable(this.productIDsOnShelf, this.table);
	}
	
	/**
	 * Loads the table with new data from the productIDsOnShelf-Map
	 * @param productIDsOnShelf
	 */
	public void loadInShelvesTable(Map<Integer, Integer> productIDsOnShelf) {
		this.productIDsOnShelf = productIDsOnShelf;
		loadInShelvesTable();
	}
	
	/**
	 * 
	 * @param productIDsOnShelf		A map of productIDs and amount on shelves
	 * @param table					The visualization table for onShelfMode
	 */
	public static void loadInShelvesTable(Map<Integer, Integer> productIDsOnShelf, VisualizationTable table) {
		
		table.wipeTable();
		
		if (productIDsOnShelf == null || table == null) {
			return;
		}
		Map<String, Integer> shelves = new HashMap<String, Integer>();
		ProductDatabaseController pdc = new ProductDatabaseController();
		for (Integer productID : productIDsOnShelf.keySet()) {
			Product product = pdc.retrieve(productID);
			int numberOnShelf = productIDsOnShelf.containsKey(productID) ? productIDsOnShelf.get(productID) : 0;
			shelves.put(product.getName(), numberOnShelf);
		}
		for (String productNameString : shelves.keySet()) {
			String amount = shelves.get(productNameString).toString();
			table.addData(new Row(productNameString, amount, "onShelves"));
		}
	}
}

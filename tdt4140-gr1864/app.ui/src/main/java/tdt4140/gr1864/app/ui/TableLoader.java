package tdt4140.gr1864.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.Aggregate;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;

/**
 * This is the class that loades a table with data from a list of Trips. 
 * The visualizationTable that is an argument in both constructors is the
 * model-layer-representation of the table shown to the user in the GUI
 * @author hstrandlie
 *
 */
public class TableLoader {
	/*
	 * The number of PickUps computed
	 */
	private Map<String, Integer> pickUps;
	/*
	 * The number of PutDowns computed
	 */
	private Map<String, Integer> putDowns;
	/*
	 * The number of purchases computed
	 */
	private Map<String, Integer> purchases;
	/*
	 * The number of a product in Stock
	 */
	private Map<String, Integer> stock;
	/*
	 * The number of a product in shelves
	 */
	private Map<String, Integer> shelves;
	
	/**
	 * The constructor for the MostPickedUpMode
	 * @param trips A list of shopping trips to load the table from
	 * @param table A model-layer representation of a table shown to the user. Any changes made to this will reflect to the user immidiately
	 */
	public TableLoader(List<ShoppingTrip> trips, VisualizationTable table) {
		if (trips == null || table == null) {
			return;
		}
		this.pickUps = new HashMap<String, Integer>();
		this.putDowns = new HashMap<String, Integer>();
		this.purchases = new HashMap<String, Integer>();
		
		for (ShoppingTrip trip : trips) {
			for (Action action : trip.getActions()) {
				if (action.getActionType() == Action.PICK_UP) {
					int previous = this.pickUps.containsKey(action.getProduct().getName()) ? this.pickUps.get(action.getProduct().getName()) : 0;
					this.pickUps.put(action.getProduct().getName(), previous + 1);
				}
				else {
					int previous = this.putDowns.containsKey(action.getProduct().getName()) ? this.putDowns.get(action.getProduct().getName()) : 0;
					this.putDowns.put(action.getProduct().getName(), previous + 1);
				}
			}
		}
		
		for (String productName : this.pickUps.keySet()) {
			// Assumes there are no products that are put down, that are not picked up
			if (! putDowns.containsKey(productName)) {
				// If there are no put downs, only pickups, the number of purchases is equal to the number of pickups
				this.purchases.put(productName, this.pickUps.get(productName));
			}
			else {
				// Number of purchases = (the number of pickups) - (the number of putdowns)
				this.purchases.put(productName, this.pickUps.get(productName) - this.putDowns.get(productName));
			}
		}
		
		for (String productName : this.pickUps.keySet()) {
			String pickups = this.pickUps.get(productName).toString();
			String putdowns = this.putDowns.containsKey(productName) ? this.putDowns.get(productName).toString() : "0";
			String purchases = this.purchases.containsKey(productName) ? this.purchases.get(productName).toString() : "0";
			table.addData(new Aggregate(productName, pickups, putdowns, purchases));
		}
	}
	/**
	 * The constructor for the StockMode
	 * @param productIDsOnShelf The Map of the productIDs with corresponding count on shelf
	 * @param productIDsInStorage The Map of the productIDs with corresponding count in storage
	 * @param table A model-layer representation of a table shown to the user. Any changes made to this will reflect to the user immidiately
	 */
	public TableLoader(Map<Integer, Integer> productIDsOnShelf, Map<Integer, Integer> productIDsInStorage, VisualizationTable table) {
		if (productIDsOnShelf == null || productIDsInStorage == null || table == null) {
			return;
		}
		this.stock = new HashMap<String, Integer>();
		ProductDatabaseController pdc = new ProductDatabaseController();
		ArrayList<Integer> seen = new ArrayList<>();
		for (Integer productID : productIDsOnShelf.keySet()) {
			Product product = pdc.retrieve(productID);
			int numberOnShelf = productIDsOnShelf.containsKey(productID) ? productIDsInStorage.get(productID) : 0;
			int numberInStorage = productIDsInStorage.containsKey(productID) ? productIDsInStorage.get(productID) : 0;
			int sum = numberOnShelf + numberInStorage;
			this.stock.put(product.getName(), sum);
			seen.add(productID);
		}
		
		for (Integer productID : productIDsInStorage.keySet()) {
			if (! seen.contains(productID)) {
				Product product = pdc.retrieve(productID);
				this.stock.put(product.getName(), productIDsInStorage.get(productID));
			}
		}
		
		for (String productName : this.stock.keySet()) {
			String totalStock = this.stock.get(productName).toString();
			table.addData(new Aggregate(productName, totalStock));
		}
	}
	
	/**
	 * The constructor for the onShelfMode
	 * 
	 * @param productIDsOnShelf		A map of productIDs and amount on shelves
	 * @param table					The visualization table for onShelfMode
	 */
	public TableLoader(Map<Integer, Integer> productIDsOnShelf, VisualizationTable table) {
		if (productIDsOnShelf == null || table == null) {
			return;
		}
		this.shelves = new HashMap<String, Integer>();
		ProductDatabaseController pdc = new ProductDatabaseController();
		for (Integer productID : productIDsOnShelf.keySet()) {
			Product product = pdc.retrieve(productID);
			int numberOnShelf = productIDsOnShelf.containsKey(productID) ? productIDsOnShelf.get(productID) : 0;
			this.shelves.put(product.getName(), numberOnShelf);
		}
		for (String productName : this.shelves.keySet()) {
			String amount = this.shelves.get(productName).toString();
			table.addData(new Aggregate(productName, amount));
		}
	}
	
	/**
	 * A simple method used by the tableLoader test
	 * @return Map<String, Integer> The stock of this tableLoader
	 */
	public Map<String, Integer> getStock() {
		return this.stock;
	}
	
	

}

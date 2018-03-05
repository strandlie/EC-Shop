package tdt4140.gr1864.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.ProductDatabaseController;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.storage.Shop;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.Aggregate;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;

public class TableLoader {
	private Map<String, Integer> pickUps;
	private Map<String, Integer> putDowns;
	private Map<String, Integer> purchases;
	private Map<String, Integer> stock;
	
	public TableLoader(List<ShoppingTrip> trips, VisualizationTable table) {
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
	
	public TableLoader(Map<Integer, Integer> productIDsOnShelf, Map<Integer, Integer> productIDsInStorage, VisualizationTable table) {
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
	
	

}

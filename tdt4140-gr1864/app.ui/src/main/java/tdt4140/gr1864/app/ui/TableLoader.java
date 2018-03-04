package tdt4140.gr1864.app.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.Aggregate;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;

public class TableLoader {
	private Map<Product, Integer> pickUps;
	private Map<Product, Integer> putDowns;
	private Map<Product, Integer> purchases;
	
	public TableLoader(List<ShoppingTrip> trips, VisualizationTable table) {
		if (table.getName().equals("Most Picked-Up Product")) {
			this.pickUps = new HashMap<Product, Integer>();
			this.putDowns = new HashMap<Product, Integer>();
			this.purchases = new HashMap<Product, Integer>();
			
			for (ShoppingTrip trip : trips) {
				for (Action action : trip.getActions()) {
					if (action.getActionType() == Action.PICK_UP) {
						int previous = this.pickUps.containsKey(action.getProduct()) ? this.pickUps.get(action.getProduct()) : 0;
						this.pickUps.put(action.getProduct(), previous + 1);
					}
					else {
						int previous = this.putDowns.containsKey(action.getProduct()) ? this.putDowns.get(action.getProduct()) : 0;
						this.putDowns.put(action.getProduct(), previous + 1);
					}
				}
			}
			
			for (Product product : this.pickUps.keySet()) {
				// Assumes there are no products that are put down, that are not picked up
				if (! putDowns.containsKey(product)) {
					// If there are no put downs, only pickups, the number of purchases is equal to the number of pickups
					this.purchases.put(product, this.pickUps.get(product));
				}
				else {
					// Number of purchases = (the number of pickups) - (the number of putdowns)
					this.purchases.put(product, this.pickUps.get(product) - this.putDowns.get(product));
				}
			}
			
			for (Product product : this.pickUps.keySet()) {
				String pickups = this.pickUps.get(product).toString();
				String putdowns = this.putDowns.containsKey(product) ? this.putDowns.get(product).toString() : "0";
				String purchases = this.purchases.containsKey(product) ? this.purchases.get(product).toString() : "0";
				table.addData(new Aggregate(product.getName(), pickups, putdowns, purchases));
			}
		}
		else if (table.getName().equals("Stock")) {
			// TODO: Implement with shop
			
		}
		else {
			throw new IllegalArgumentException("Unknown table name");
		}
	}
	

}

package tdt4140.gr1864.app.core;

import java.util.HashMap;
import java.util.Map;

public class Receipt {
	/**
	 * The ShoppingTrip object which this Receipt computes the prices for.
	 */
	private ShoppingTrip shoppingTrip;
	
	/**
	 * A Map containing the amount of items with specific codes were bought. 
	 */
	private Map<Product, Integer> inventory = new HashMap<>();
	
	/**
	 * The computed prices for each item.
	 */
	private Map<Product, Double> prices = new HashMap<>();
	
	/**
	 * Creates a new Receipt object. A Receipt takes a ShoppingTrip and computes the 
	 * total prices for each Product purchased during the trip. 
	 * @param shoppingTrip The ShoppingTrip to compute prices for.
	 */
	public Receipt(ShoppingTrip shoppingTrip) {
		this.shoppingTrip = shoppingTrip;
		
		computePrices();
	}
	
	/**
	 * Compute the costs for products purchased during this trip.
	 */
	private void computePrices() {
		ProductDatabaseController database = new ProductDatabaseController();

		for (Action action : shoppingTrip.getActions()) {
			Product product = database.retrieve(action.getProductID());
			
			int previous = inventory.containsKey(product) ? inventory.get(product) : 0;
			
			if (action.getActionType() == Action.DROP) {
				inventory.put(product, previous - 1);
			} else if (action.getActionType() == Action.PICK_UP) {
				inventory.put(product, previous + 1);
			}
		}
				
		for (Product product : inventory.keySet()) {
			prices.put(product, product.getPrice() * inventory.get(product));
		}
		
		database.close();
	}
	
	/**
	 * @return The ShoppingTrip described by this Receipt.
	 */
	public ShoppingTrip getShoppingTrip() {
		return shoppingTrip;
	}
	
	public Map<Product, Integer> getInventory() {
		return inventory;
	}
	
	/**
	 * @return The Map containing Product objects and their total prices.
	 */
	public Map<Product, Double> getPrices() {
		return prices;
	}
	
	/**
	 * @return The total price for all the 
	 */
	public double getTotalPrice() {
		return prices.values().stream().mapToDouble(Double::valueOf).sum();
	}
}
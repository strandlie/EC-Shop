package tdt4140.gr1864.app.core;

import java.util.HashMap;
import java.util.Map;

public class Receipt {
	
	private ShoppingTrip shoppingTrip;
	
	private Map<Integer, Integer> inventory = new HashMap<>();
	
	public Receipt(ShoppingTrip shoppingTrip) {
		this.shoppingTrip = shoppingTrip;
		
		this.computeTotalPrice();
	}
	
	private void computeTotalPrice() {
		for (Action action : shoppingTrip.getActions()) { 
			// add and remove products from inventory
		}
		
		// return total cost
	}
	
	public double getTotalPrice() {
		return 0;
	}
}

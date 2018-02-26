package tdt4140.gr1864.app.core.dataMocker;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class Rack extends Rectangle {
	/**
	 * A collection of items being stored in this rack.
	 */
	private Collection<Product> items;
	
	/**
	 * Create a Rack, which is a Rectangle with a collection of items belonging to it.
	 * @param lower The lower-left corner of the rectangle.
	 * @param upper The upper-right corner of the rectangle.
	 * @param items Items belonging this rack
	 */
	public Rack(Coordinate lower, Coordinate upper, Collection<Product> items) {
		super(lower, upper);
		
		this.items = items;
	}
	
	/**
	 * @return The items stored in this rack.
	 */
	public Collection<Product> getItems() {
		return items;
	}
	
	/**
	 * @return A randomly chosen item stored in this rack.
	 */
	public Product getRandomItem() {
		int index = 0;
		
		int target = ThreadLocalRandom.current().nextInt(items.size());
		
		// Since only assume a Collection, we iterate through it until a randomly
		// chosen index is reached.
		for (Product item : items) {
			if  (index == target) {
				return item;
			}
			
			index += 1;
		}
		
		return null;
	}
}
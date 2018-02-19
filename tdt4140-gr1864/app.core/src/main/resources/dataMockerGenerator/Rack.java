package dataMockerGenerator;

import java.util.Collection;
import java.util.concurrent.ThreadLocalRandom;

public class Rack extends Rectangle {
	private Collection<Product> items;
	
	public Rack(Coordinate lower, Coordinate upper, Collection<Product> items) {
		super(lower, upper);
		
		this.items = items;
	}
	
	public Collection<Product> getItems() {
		return items;
	}
	
	public Product getRandomItem() {
		int index = 0;
		
		int target = ThreadLocalRandom.current().nextInt(items.size());
		
		for (Product item : items) {
			if  (index == target) {
				return item;
			}
			
			index += 1;
		}
		
		return null;
	}
}
package tdt4140.gr1864.app.core.dataMocker;

import java.util.HashMap;
import java.util.Map;

public class Inventory {
	private Map<Integer, Integer> products = new HashMap<>();
	
	public boolean contains(Product product) {
		return products.keySet().contains(product.getCode());
	}
	
	public int getCount(Product product) {
		return contains(product) ? products.get(product.getCode()) : 0;
	}
	
	public void add(Product product) {
		products.put(product.getCode(), getCount(product) + 1);
		product.pickUp();
	}
	
	public void remove(Product product) {
		products.put(product.getCode(), getCount(product) - 1);
		product.putDown();
	}
}

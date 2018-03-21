package tdt4140.gr1864.app.core.dataMocker;

public class Product {
	/**
	 * An unique ID for used to uniquely identify products.
	 */
	private static int productCounter = 1;
	
	/**
	 * The unique code for a specific product.
	 */
	private int code;
	
	/**
	 * The amount of products of this type left in stock.
	 */
	private int count;
	
	/**
	 * Create a new Product with a fresh ID.
	 */
	public Product(int count) {
		if (productCounter > 64) {
			throw new IllegalStateException("More than 64 products have been generated");
		}
		
		code = productCounter;
		productCounter += 1;
				
		this.count = count;
	}
	
	/**
	 * @return Whether or not there are items of this type left in stock.
	 */
	public boolean canPickUp() {
		return count > 0;
	}
	
	/**
	 * Picks up an item, therefore removing it from the stock.
	 */
	public void pickUp() {
		if (count == 0) {
			throw new IllegalStateException("Cannot pick up item which is not in stock");
		}
		
		count -= 1;
	}
	
	/**
	 * Puts down an item, adding one to the stock.
	 */
	public void putDown() {
		count += 1;
	}
	
	/**
	 * @return The code for this product.
	 */
	public int getCode() {
		return code;
	}
}

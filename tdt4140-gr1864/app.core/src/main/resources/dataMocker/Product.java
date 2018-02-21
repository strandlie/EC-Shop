package dataMocker;

public class Product {
	/**
	 * An unique ID for used to uniquely identify products.
	 */
	private static int productCounter = 0;
	
	/**
	 * The unique code for a specific product.
	 */
	private int code;
	
	/**
	 * The number of items of this Product which the user currently being simulated is carrying.
	 */
	private int carrying = 0;
	
	/**
	 * Create a new Product with a fresh ID.
	 */
	public Product() {
		code = productCounter;
		productCounter += 1;
	}

	/**
	 * @return The code for this product.
	 */
	public int getCode() {
		return code;
	}
	
	/**
	 * Increase the number of items being carried.
	 */
	public void pickUp() {
		carrying += 1;
	}
	
	/**
	 * Decrease the number of items being carried.
	 */
	public void drop() {
		carrying -= 1;
	}
	
	/**
	 * @return Whether or not we are carrying any items of this type.
	 */
	public boolean canBeDropped() {
		return carrying > 0;
	}
}

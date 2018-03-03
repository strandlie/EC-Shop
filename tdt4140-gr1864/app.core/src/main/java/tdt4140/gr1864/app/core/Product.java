package tdt4140.gr1864.app.core;

public class Product {

	private String name;
	private double price;
	private Integer productID;
	
	/**
	 * Constructor used by ProductDatabaseController
	 * @param productID		id provided by database
	 * @param name			name of product
	 * @param price			price of product
	 */
	public Product(Integer productID, String name, double price) {
		this.productID = productID;
		this.name = name;
		this.price = price;
	}
	
	/**
	 * @param name		name of product
	 * @param price		price of product
	 */
	public Product(String name, double price) {
		this.price = price;
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	public double getPrice() {
		return this.price;
	}
	
	public Integer getID() {
		return this.productID;
	}
}

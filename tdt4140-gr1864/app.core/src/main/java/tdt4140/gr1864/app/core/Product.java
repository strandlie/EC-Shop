package tdt4140.gr1864.app.core;

public class Product {

	private String name;
	private double price;
	
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
}

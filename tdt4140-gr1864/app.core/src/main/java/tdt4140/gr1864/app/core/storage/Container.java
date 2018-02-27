package tdt4140.gr1864.app.core.storage;

import interfaces.DatabaseCRUD;

import java.util.ArrayList;

/* An abstract class for something that can contain products, like storage or shelfs */
public abstract class Container implements DatabaseCRUD{
	
	/* Values */
	private ArrayList<Product> products;
	private ArrayList<Integer> amounts;
	
	
	/* Constructor */
	public Container() {
		this.products = new ArrayList<Product>();
		this.amounts = new ArrayList<>();
	}
	
	/* Adds amount of product, if not already in list it's added to the list */
	public void AddProducts(Product product, int amount) {
		int index = products.indexOf(product);
		if (index == -1) {
			products.add(product);
			amounts.add(amount);
		}
		else {
			int newAmount = amount + amounts.get(index);
			amounts.set(index,  newAmount);
		}
	}
	
	/* Remove an amount of a product from container
	 * @exception	If it tries to remove too much, an exception is thrown
	 * @exception	If it tries to remove a product that's not in storage, an exception is thrown */
	public void RemoveProducts(Product product, int amount) throws Exception {
		int index = products.indexOf(product);
		if (index == -1) {
			throw new Exception("Product not in Container, can't decrease amount");
		}
		else if (amount > amounts.get(index)) {
			throw new Exception("Removing more than is in Container");
		}
		else {
			int newAmount = amounts.get(index) - amount;
			amounts.set(index,  newAmount);
		}
	}
	
	/* Get the amount of a product in a container
	 * @exception If product isn't in the container */
	public int GetAmount(Product product) throws Exception{
		int index = products.indexOf(product);
		if (index == -1) {
			throw new Exception("Product not in container, can't get amount");
		}
		else {
			return amounts.get(index);
		}
	}
	
	/* Deletes a product from a container, meant to be used if product is discontinued
	 * @exception If product isn't in the container
	 * @exception If amount isn't zero */
	public void delete(Product product) throws Exception{
		int index = products.indexOf(product);
		if (index == -1) {
			throw new Exception("Product not in container, can't delete it");
		}
		else if (amounts.get(index) != 0) {
			throw new Exception("Amount isn't zero, can't delte the product");
		}
		else {
			products.remove(index);
			amounts.remove(index);
		}
	}
}

package tdt4140.gr1864.app.core.storage;

import java.util.ArrayList;

/* An abstract class for something that can contain products, like storage or shelfs
 * Illegal operations are simply ignored */
public abstract class Container{
	
	/* Values */
	private ArrayList<Product> products;
	private ArrayList<Integer> amounts;
	
	
	/* Constructor */
	public Container() {
		this.products = new ArrayList<Product>();
		this.amounts = new ArrayList<>();
	}

	
	/* Adds amount of product, if not already in list it's added to the list
	 * amount must be larger than zero */
	public void addProducts(Product product, int amount){
		if (amount <= 0) {
			System.out.println("Can't add a value less than or 0 to a container");
			return;
		}
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
	public void removeProducts(Product product, int amount){
		if (amount <= 0) {
			System.out.println("Can't remove a value less than or 0 from container");
			return;
		}
		int index = products.indexOf(product);
		if (index == -1) {
			System.out.println("Can't remove amount from product not in list");
			return;
		}
		else if (amount > amounts.get(index)) {
			return;
		}
		else {
			int newAmount = amounts.get(index) - amount;
			amounts.set(index,  newAmount);
		}
	}
	
	/* Get the amount of a product in a container
	 * returns -1 if product isn't in the container */
	public int getAmount(Product product){
		int index = products.indexOf(product);
		if (index == -1) {
			System.out.println("Prouct not in list");
			return -1;
		}
		else {
			return amounts.get(index);
		}
	}
	
	/* Deletes a product from a container, meant to be used if product is discontinued
	 * @exception If product isn't in the container
	 * @exception If amount isn't zero */
	public void delete(Product product){
		int index = products.indexOf(product);
		if (index == -1) {
			System.out.println("Can't delete product that doesn't exist");
			return;
		}
		else if (amounts.get(index) != 0) {
			return;
		}
		else {
			products.remove(index);
			amounts.remove(index);
		}
	}
}

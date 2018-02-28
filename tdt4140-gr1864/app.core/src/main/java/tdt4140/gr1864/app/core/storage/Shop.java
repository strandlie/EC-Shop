package tdt4140.gr1864.app.core.storage;

import java.util.ArrayList;
import java.util.List;

/* The shop that contains the storage and the shelfs
 * Use this class to manipulate the storage and shelfs */
public class Shop{
	
	/* Values */
	private int shopID;
	private String address;
	private int zip;
	
	/* List for OnShelf, index of inner list are:
	 * 0 = ProductID
	 * 1 = Storage
	 * 2 = OnShelfs */
	List<ArrayList<Integer>> amountsOfProducts = new ArrayList<ArrayList<Integer>>();
	

	public Shop(String address, int zip) {
		this.address = address;
		this.zip = zip;
	}
	
	/* Constructor used by DatabaseController */
	public Shop(String address, int zip, int shopId) {
		this.address = address;
		this.zip = zip;
		this.shopID = shopId;
	}
	
	public int getShopID() {
		return shopID;
	}
	
	public void setAdress(String address) {
		this.address = address;
	}
	
	public String getAddress() {
		return this.address;
	}
	
	public int getZip() {
		return zip;
	}
	
	public void setZip(int zip) {
		this.zip = zip;
	}
	
	
	/**
	 * Adds an amount of a product to the shop storage
	 * 
	 * Checks that amount is larger than zero
	 * If productID isn't in the list, it's added
	 * 
	 * @param productID		ID of product to be added
	 * @param amount		Amount of product to add
	 */
	public void addAmountToStorage(int productID, int amount) {
		if (amount > 0) {
			for (int i=0; i < amountsOfProducts.size(); i++) {
				/* Product allready in list */
				if (productID == amountsOfProducts.get(i).get(0)) {
					amountsOfProducts.get(i).set(1, amountsOfProducts.get(i).get(1) + amount);
					return;
				}
			}
			/* Product not allready in list */
			ArrayList<Integer> newList = new ArrayList<Integer>();
			newList.add(productID);
			newList.add(amount);
			newList.add(0);
			amountsOfProducts.add(newList);
		} else {
			System.out.println("Can't add less than 1 product to storage");
		}
	}
	
	/* Same as above, but adds to shop shelfs instead of storage */
	public void addAmountToShelfs(int productID, int amount) {
		if (amount > 0) {
			for (int i=0; i < amountsOfProducts.size(); i++) {
				if (productID == amountsOfProducts.get(i).get(0)) {
					amountsOfProducts.get(i).set(2, amountsOfProducts.get(i).get(2) + amount);
				}
			}
			ArrayList<Integer> newList = new ArrayList<Integer>();
			newList.add(productID);
			newList.add(0);
			newList.add(amount);
			amountsOfProducts.add(newList);
		} else {
			System.out.println("Can't add less than 1 product to storage");
		}
	}
	
	/**
	 * Retrieves the amount of a product in the shop storage, returns -1 if not found
	 * 
	 * @param productID		ID of product to retrieve amount of
	 * @return				The amount of the product, or -1 if not found
	 */
	public int getAmountInStorage(int productID) {
		for (int i = 0; i<amountsOfProducts.size(); i++) {
			if (productID == amountsOfProducts.get(i).get(0)) {
				return amountsOfProducts.get(i).get(1);
			}
		}
		System.out.println("ProductID not registered in storage");
		return -1;
	}
	
	/* Same as above, but gets amount in storage shelfs */
	public int getAmountInShelfs(int productID) {
		for (int i = 0; i<amountsOfProducts.size(); i++) {
			if (productID == amountsOfProducts.get(i).get(0)) {
				return amountsOfProducts.get(i).get(2);
			}
		}
		System.out.println("ProductID not registered in storage");
		return -1;
	}
}

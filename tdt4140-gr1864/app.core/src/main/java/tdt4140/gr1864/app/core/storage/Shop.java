package tdt4140.gr1864.app.core.storage;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import tdt4140.gr1864.app.core.Receipt;

/**
 * The java object Shop that corresponds to the shop table in DB
 * 
 * The shop also keeps track of the amount of different products that are in storage and on the shelfs.
 * This is done through a list within a list, where inner list represents productID, amount in storage and on shelfs.
 * 
 * @author stian
 */
public class Shop{
	
	/* Values */
	private int shopID;
	private String address;
	private int zip;
	
	/* List for OnShelf, index of inner list are:
	 * 0 = ProductID
	 * 1 = Storage
	 * 2 = OnShelfs */
	List<ArrayList<Integer>> amountsOfProducts;
	

	public Shop(String address, int zip) {
		this.address = address;
		this.zip = zip;
		
		this.amountsOfProducts = new ArrayList<ArrayList<Integer>>();
	}
	
	/* Constructor used by DatabaseController */
	public Shop(String address, int zip, int shopId) {
		this.address = address;
		this.zip = zip;
		this.shopID = shopId;
		
		this.amountsOfProducts = new ArrayList<ArrayList<Integer>>();
	}
	
	public int getShopID() {
		return shopID;
	}
	
	public void setAddress(String address) {
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
	 * Sets the amount of a product in the shop storage
	 * 
	 * Checks that amount is larger than zero
	 * If productID isn't in the list, it's added
	 * 
	 * @param productID		ID of product to be added
	 * @param amount		Amount of product to add
	 */
	public void setAmountInStorage(int productID, int amount) {
		if (amount >= 0) {
			for (int i=0; i < amountsOfProducts.size(); i++) {
				/* Product allready in list */
				if (productID == amountsOfProducts.get(i).get(0)) {
					amountsOfProducts.get(i).set(1, amount);
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
			System.out.println("Can't set amount in storage to be less than zero");
		}
	}
	
	/* Same as above, but adds to shop shelfs instead of storage */
	public void setAmountInShelfs(int productID, int amount) {
		if (amount >= 0) {
			for (int i=0; i < amountsOfProducts.size(); i++) {
				if (productID == amountsOfProducts.get(i).get(0)) {
					amountsOfProducts.get(i).set(2, amount);
				}
			}
			ArrayList<Integer> newList = new ArrayList<Integer>();
			newList.add(productID);
			newList.add(0);
			newList.add(amount);
			amountsOfProducts.add(newList);
		} else {
			System.out.println("Can't set amount in shelfs to be less than zero");
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
	
	/**
	 * Updates the amounts of a product on the shelfs in the shop object from the inventory
	 * in a receipt from a shoppingTrip
	 * 
	 * The products are assumed to be in the DB
	 * 
	 * @param receipt	A receipt from a shopping trip
	 */
	public void updateAmountInShelfsFromReceipt(Receipt receipt) {
		
		Map<Integer, Integer> inventory = receipt.getInventory();
		Iterator it = inventory.entrySet().iterator();
		
		while (it.hasNext()) {
			
			Map.Entry pair = (Map.Entry)it.next();
			int productID = (int)pair.getKey();
			int amount = (int)pair.getValue();
			int currentAmount = getAmountInShelfs(productID);
			
			setAmountInShelfs(productID, currentAmount - amount);
			
			OnShelfDatabaseController osdc = new OnShelfDatabaseController();
			osdc.update(this, productID);
			
			it.remove(); // avoids a ConcurrentModificationException
		}
	}
	
	/**
	 * Updates the amounts of products in the shop object according to the DB, if
	 * their ID's are in the list
	 * 
	 * @return		The updated Shop object
	 */
	public Shop refreshShop() {
		
		Shop temp = this;
		Shop temp2;
		
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		
		for (int i = 0; i < amountsOfProducts.size(); i++) {
			temp2 = osdc.retrieve(temp, amountsOfProducts.get(i).get(0));
			temp = temp2;
			System.out.println(amountsOfProducts);
		}
		return temp;
	}
}

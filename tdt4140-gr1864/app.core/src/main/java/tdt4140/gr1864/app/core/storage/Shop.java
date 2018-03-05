package tdt4140.gr1864.app.core.storage;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import tdt4140.gr1864.app.core.Receipt;

/**
 * The java object Shop that corresponds to the shop table in DB
 * 
 * The shop also keeps track of the amount of different products that are in storage and on the shelfs.
 * This is done with two hashmaps between productIDs and the amounts
 *  
 * @author stian
 */
public class Shop{
	
	/* Values */
	private int shopID;
	private String address;
	private int zip;
	
	private Map<Integer, Integer> shelfs;
	private Map<Integer, Integer> storage;

	public Shop(String address, int zip) {
		this.address = address;
		this.zip = zip;
		
		this.shelfs = new HashMap<>();
		this.storage = new HashMap<>();
	}
	
	/* Constructor used by DatabaseController */
	public Shop(String address, int zip, int shopId) {
		this.address = address;
		this.zip = zip;
		this.shopID = shopId;
		
		this.shelfs = new HashMap<>();
		this.storage = new HashMap<>();
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
	
	public Map<Integer, Integer> getShelfs() {
		return shelfs;
	}
	
	public Map<Integer, Integer> getStorage() {
		return storage;
	}
	
	/**
	 * Sets the amount of a product in storage to the desired amount
	 * 
	 * @param productID		ID of the product
	 * @param amount		The amount of the product to be set
	 */
	public void setAmountInStorage(int productID, int amount) {
			this.storage.put(productID,  amount);
	}
	
	/* Same as above, but for amount in shelfs */
	public void setAmountInShelfs(int productID, int amount) {
			this.shelfs.put(productID,  amount);
	}
	
	/**
	 * Retrieves the amount of a product in the shop storage, returns -1 if not found
	 * 
	 * @param productID		ID of product to retrieve amount of
	 * @return				The amount of the product, or 0 if not found
	 */
	public int getAmountInStorage(int productID) {
		Integer temp = this.storage.get(productID);
		if (null == temp) {
			return -1;
		}
		else {
			return temp;
		}
	}
	
	/* Same as above, but gets amount in shelfs */
	public int getAmountInShelfs(int productID) {
		Integer temp = this.shelfs.get(productID);
		if (null == temp) {
			return -1;
		}
		else {
			return temp;
		}
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
		Iterator<Entry<Integer, Integer>> it = inventory.entrySet().iterator();
		
		while (it.hasNext()) {
			
			Entry<Integer, Integer> pair = it.next();
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
		
		Iterator<Entry<Integer, Integer>> it = shelfs.entrySet().iterator();
		
		while (it.hasNext()) {
			
			Entry<Integer, Integer> pair = it.next();
			int productID = (int)pair.getKey();
			
			temp2 = osdc.retrieve(temp, productID);
			temp = temp2;
		
		}
		return temp;
	}
}

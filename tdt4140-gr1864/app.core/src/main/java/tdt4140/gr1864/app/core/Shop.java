package tdt4140.gr1864.app.core;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import tdt4140.gr1864.app.core.databasecontrollers.OnShelfDatabaseController;
//import tdt4140.gr1864.app.core.interfaces.IShoppingTripListener;

/**
 * The java object Shop that corresponds to the shop table in DB
 * 
 * The shop also keeps track of the amount of different products that are in storage and on the shelfs.
 * This is done with two hashmaps between productIDs and the amounts
 *  
 * @author stian
 */
public class Shop //implements IShoppingTripListener{
{
	
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
	 * Listener for a new registered shopping trip, updates the amounts of
	 * products on shelves in DB
	 * 
	 * @param trip	The newly added shopping trip
	 */
	public void shoppingTripAdded(ShoppingTrip trip) {
		Receipt receipt = new Receipt(trip);
		updateAmountInShelfsFromReceipt(receipt);
		System.out.println("Listener in shop.java triggered and finished");
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
		OnShelfDatabaseController osdbc = new OnShelfDatabaseController();
		for (Integer id : inventory.keySet()) {
			int amount = inventory.get(id);
			int currentAmount = osdbc.retrieve(this, id).getAmountInShelfs(id);
			
			setAmountInShelfs(id, currentAmount - amount);
			
			osdbc.update(this, id);
		}
	}
	
	/**
	 * Same as above, but doesn't update DB
	 * 
	 * @param receipt	A receipt from a shopping trip
	 */
	public void updateShopFromReceipt(Receipt receipt) {
		Map<Integer, Integer> inventory = receipt.getInventory();
		for (Integer productID : inventory.keySet()) {
			int amount = inventory.get(productID);
			int currentAmount = this.getAmountInShelfs(productID);
			setAmountInShelfs(productID, currentAmount - amount);
		}
	}
	
	/**
	 * Updates the amounts of products in the shop object according to the DB,
	 * used to get new shop objects up to date. Currently have to assume fixed
	 * amount of products, if not productDBctrl could support this
	 * 
	 * @return		The updated Shop object
	 */
	public Shop refreshShop() {
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		int numberOfProducts = 65;
		for (int i = 1; i < numberOfProducts; i++) {
			osdc.retrieve(this, i);
		}
		return this;
	}
}

package tdt4140.gr1864.app.core.storage;

/* The shop that contains the storage and the shelfs
 * Use this class to manipulate the storage and shelfs */
public class Shop{
	
	/* Values */
	private int shopID;
	private String address;
	
	public Shop(String address) {
		this.address = address;
	}
	
	/* Constructor used by DatabseController */
	public Shop(String address, int shopId) {
		this.address = address;
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
	
	/* Add function for mowing from storage to shelfs? Or just handle that with remove and add */
}

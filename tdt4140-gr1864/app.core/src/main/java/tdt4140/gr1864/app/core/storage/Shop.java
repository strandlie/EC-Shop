package tdt4140.gr1864.app.core.storage;

/* The shop that contains the storage and the shelfs
 * Use this class to manipulate the storage and shelfs */
public class Shop{
	
	/* Values */
	private int shopID;
	private String address;
	private int zip;
	

	public Shop(String address, int zip) {
		this.address = address;
		this.zip = zip;
	}
	
	/* Constructor used by DatabseController */
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
	
	/* Add function for mowing from storage to shelfs? Or just handle that with remove and add */
}

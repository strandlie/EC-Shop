package tdt4140.gr1864.app.core.storage;

/* The storage facility in a shop, only one object per shop */
public class Storage extends Container{
	private Integer storageID;
	private Integer shopID;
	
	
	public Storage() {
		storageID = -1;
		shopID = -1;
	}
	
	
	public void setID(int ID) {
		this.storageID = ID;
	}
	
	public Integer getID() {
		return this.storageID;
	}
	
	public void setShopID(int shopID) {
		this.shopID = shopID;
	}
	
	public Integer getShopID() {
		return this.shopID;
	}
};
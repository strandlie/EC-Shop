package tdt4140.gr1864.app.core.storage;

/* The shop that contains the storage and the shelfs */
public class Shop {
	
	/* Values */
	private int shopID;
	private String adress;
	private Storage storage;
	private Shelfs shelfs;
	
	
	public void Shop() {
		shopID = -1;
		adress = "";
		storage = null;
		shelfs = null;
	}
	
	public void setID(int shopID) {
		this.shopID = shopID;
	}
	
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public void setStorage(Storage storage) {
		this.storage = storage;
	}
}

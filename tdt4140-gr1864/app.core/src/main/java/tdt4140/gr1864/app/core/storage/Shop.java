package tdt4140.gr1864.app.core.storage;

/* The shop that contains the storage and the shelfs
 * Use this class to manipulate the storage and shelfs */
public class Shop{
	
	/* Values */
	private int ID;
	private String adress;
	private Storage storage;
	private Shelfs shelfs;
	
	
	/* Constructor */
	public Shop() {
		ID = -1;
		adress = new String();
		storage = new Storage();
		shelfs = new Shelfs();
	}
	
	public void setID(int ID) {
		this.ID = ID;
		storage.setShopID(ID);
	}
	
	public int getID() {
		return this.ID;
	}
	
	/* Set the address */
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public String getAddress() {
		return this.adress;
	}
	
	public void addProductToStorage(Product product, int amount) {
			storage.addProducts(product, amount);
	}
	
	public void addProductToShelfs(Product product, int amount) {
		shelfs.addProducts(product, amount);
	}
	
	public void removeProductFromStorage(Product product, int amount){
		storage.removeProducts(product, amount);
	}
	
	public void removeProductFromShelfs(Product product, int amount){
		shelfs.removeProducts(product, amount);
	}
	
	public void deleteProductFromStorage(Product product){
			storage.delete(product);
	}
	
	public void deleteProductFromShelfs(Product product){
			shelfs.delete(product);
	}
	
	public int getAmountInStorage(Product product){
			return storage.getAmount(product);
	}
	
	/* Add function for mowing from storage to shelfs? Or just handle that with remove and add */
}

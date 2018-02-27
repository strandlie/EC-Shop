package tdt4140.gr1864.app.core.storage;

/* The shop that contains the storage and the shelfs */
public class Shop {
	
	/* Values */
	private String adress;
	private Storage storage;
	private Shelfs shelfs;
	
	
	/* Constructor */
	public Shop() {
		adress = new String();
		storage = new Storage();
		shelfs = new Shelfs();
	}
	
	/* Set the address */
	public void setAdress(String adress) {
		this.adress = adress;
	}
	
	public void addProductToStorage(Product product, int amount) {
		storage.AddProducts(product, amount);
	}
	
	public void addProductToShelfs(Product product, int amount) {
		shelfs.AddProducts(product, amount);
	}
	
	public void removeProductFromStorage(Product product, int amount) throws Exception{
		try {
		storage.RemoveProducts(product, amount);
		}
		catch (Exception e1) {
			/* How should we handle exceptions? Re-throw and catch in test, or print to console? */
			throw e1;
		}
	}
	
	public void removeProductFromShelfs(Product product, int amount) throws Exception{
		try {
		shelfs.RemoveProducts(product, amount);
		}
		catch (Exception e1) {
			/* How should we handle exceptions? Re-throw and catch in test, or print to console? */
			throw e1;
		}
	}
	
	public void deleteProductFromStorage(Product product) throws Exception{
		
	}
	
	public void deleteProductFromShelfs(Product product) throws Exception{
		
	}
	
	public int getAmountInStorage(Product product) throws Exception {
		try {
			return storage.GetAmount(product);
		}
		catch (Exception e2) { throw e2;}
	}
	/* Add function for mowing from storage to shelfs? Or just handle that with remove and add */
}

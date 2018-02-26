package tdt4140.gr1864.app.core.storage;

import interfaces.DatabaseCRUD;
import java.util.List;

/* Java class for the storage facility in a store
 * The storage keeps a list of product IDs, and list of the amounts.
 * Won't include a SetStorageID as it's not to be changed after creation */
public class Storage implements DatabaseCRUD{
	
	/* Values */
	private int StorageID;
	private List<Integer> ProductIDs;
	private List<Integer> Amounts;
	
	
	/* Constructor for empty storage
	 * @param StorageID The desired ID for the storage */
	public Storage(int StorageID) {
		this.StorageID = StorageID;
	};
	
	/* Constructor for storage with lists - probably not to be used
	 * @param StorageID		The desired ID for the storage
	 * @param ProductIDs 	A list of product IDs
	 * @param Amounts 		The corresponding amounts of the products */
	public Storage(int StorageID, List<Integer> ProductIDs, List<Integer> Amounts) {
		this.StorageID = StorageID;
		this.ProductIDs = ProductIDs;
		this.Amounts = Amounts;
	};
	
	/* Get the StorageID */
	public int GetStorageID() {
		return this.StorageID;
	};
	
	/* private? */
	/* Check if a productID is in the ProductIDs list
	 * @param ProductID 	The ID of the product it checks if exists in storage
	 * @return 				If product exist it returns index, if not it returns -1 */
	public int ProductIDInStorage(int ProductID) {
		for (int i = 0; i < ProductIDs.size(); i++) {
			if (ProductIDs.get(i) == ProductID) {
				return i;
			}
		}
		return -1;
	};
	
	/* Add an amount of a product to storage
	 * If the product doesn't exist its ID is appended to ProductIDs
	 * @param ProductID	ID of the product to be added
	 * @param Amount	How many of the product to be added */
	public void AddProducts(int ProductID, int Amount) {
		int Index = ProductIDInStorage(ProductID);
		if (Index == -1) {
			ProductIDs.add(ProductID);
			Amounts.add(Amount);
		}
		else {
			Amount += Amounts.get(Index);
			Amounts.set(Index, Amount);
		}
	};
	
	/* Remove an amount of a product from storage
	 * If the new amount is zero, the productID is to be removed from the ProductIDs
	 * @exception	If it tries to remove too much, an exception is thrown
	 * @exception	If it tries to remove a product that's not in storage, an exception is thrown */
	public void RemoveProducts(int ProductID, int Amount) {
		int Index = ProductIDInStorage(ProductID);
		if (Index == -1) {
			throw new Exception("Product not in storage");
		}
		else if (Amount > Amounts.get(Index)) {
			throw new Exception("Removing more than is in storage");
		}
		else if (Amount == Amounts.get(Index)){
			ProductIDs.remove(Index);
			Amounts.remove(Index);
		}
		else {
			int NewAmount = Amounts.get(Index) - Amount;
			Amounts.set(Index,  NewAmount);
		}
	};
}

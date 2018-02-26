package tdt4140.gr1864.app.core.storage;

import interfaces.DatabaseCRUD;
import java.util.List;

/* An abstract class for something that can contain products */
public abstract class Container implements DatabaseCRUD{
	
	/* Values */
	private int containerID;
	private List<Integer> productIDs;
	private List<Integer> amounts;
	
	
	/* Constructor for empty container
	 * @param StorageID The desired ID for the storage */
	public Container(int StorageID) {
		this.containerID = StorageID;
		this 
	};
	
	/* Constructor for container with lists - probably not to be used
	 * @param StorageID		The desired ID for the storage
	 * @param ProductIDs 	A list of product IDs
	 * @param Amounts 		The corresponding amounts of the products */
	public Container(int StorageID, List<Integer> ProductIDs, List<Integer> Amounts) {
		this.containerID = StorageID;
		this.productIDs = ProductIDs;
		this.amounts = Amounts;
	};
	
	/* Get the StorageID */
	public int GetContainerID() {
		return this.containerID;
	};
	
	/* Check if a productID is in the ProductIDs list
	 * @param ProductID 	The ID of the product it checks if exists in storage
	 * @return 				If product exist it returns index, if not it returns -1 */
	private int ProductInContainer(int ProductID) {
		for (int i = 0; i < productIDs.size(); i++) {
			if (productIDs.get(i) == ProductID) {
				return i;
			}
		}
		return -1;
	};
	
	/* Add an amount of a product to storage
	 * If the product doesn't exist its ID is appended to ProductIDs
	 * @param ProductID	ID of the product to be added
	 * @param Amount	How many of the product to be added */
	public void AddProducts(int ProductID, int amount) {
		int Index = ProductInContainer(ProductID);
		if (Index == -1) {
			productIDs.add(ProductID);
			amounts.add(amount);
		}
		else {
			amount += amounts.get(Index);
			amounts.set(Index, amount);
		}
	};
	
	/* Remove an amount of a product from container
	 * @exception	If it tries to remove too much, an exception is thrown
	 * @exception	If it tries to remove a product that's not in storage, an exception is thrown */
	public void RemoveProducts(int productID, int amount) throws Exception {
		int index = ProductInContainer(productID);
		if (index == -1) {
			throw new Exception("Product not in Container");
		}
		else if (amount > amounts.get(index)) {
			throw new Exception("Removing more than is in Container");
		}
		else {
			int newAmount = amounts.get(index) - amount;
			amounts.set(index,  newAmount);
		}
	};
	
	
	public int GetAmount(int productID) {
		int index = ProductInContainer(productID);
		if (index == -1) {
			return 0;
		}
		else {
			return amounts.get(index);
		}
	};
}

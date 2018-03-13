package tdt4140.gr1864.app.ui.Mode.VisualizationElement;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class that makes up the content in the cells of a table. Because of the JavaFX-implmentation this 
 * needs to have all the variables and corresponding methods for all possible columns in any table. 
 * 
 * Aggregates with two values now take a keyword in the constructor to set the right values.
 * 
 * @author Håkon Strandlie
 *
 */
public class Aggregate {
	
	// Common variables for mostPickedUpMode and stockMode
	private StringProperty productName;
	
	// The variables for MostPickedUpMode
	private StringProperty numberOfPickUp;
	private StringProperty numberOfPutDown;
	private StringProperty numberOfPurchases;
	
	// The variables for stockMode
	private StringProperty numberInStock;
	
	// The variables for onShelvesMode
	private StringProperty numberOnShelves;
	
	/**
	 * The size of this Aggregate
	 */
	private int size;
	
	
	/**
	 * The constructor for the mostPickedUpMode
	 * @param productName The product name of the product
	 * @param numberOfPickUp The number of times it was picked up
	 * @param numberOfPutDownThe number of times it was put down
	 * @param numberOfPurchases The number of purchases (the sum of the two above)
	 */
	public Aggregate(String productName, String numberOfPickUp, String numberOfPutDown, String numberOfPurchases) {
		setProductName(productName);
		setNumberOfPickUp(numberOfPickUp);
		setNumberOfPutDown(numberOfPutDown);
		setNumberOfPurchases(numberOfPurchases);
		
		this.size = 4;
	}
	
	/**
	 * The constructor for modes with two columns
	 * @param productName
	 * @param numberInStock
	 * @param type 				the mode type
	 */
	public Aggregate(String productName, String amount, String type) {
		switch (type) {
		
		case "stock":
			setProductName(productName);
			setNumberInStock(amount);
			break;
			
		case "onShelves":
			setProductName(productName);
			setNUmberOnShelves(amount);
			break;
		}
		
		
		this.size = 2;
	}
	
	/**
	 * Gets the number of columns in this Aggregate
	 * @return
	 */
	public int getSize() {
		return this.size;
		
	}
	
	// Common propertyMethods
	
	/**
	 * The propertyGenerating-method for ProductName. Used by the TableView to fill the cell
	 * @return StringProperty The string property for the Product Name
	 */
	public StringProperty productNameProperty() {
		if (productName == null) {
			productName = new SimpleStringProperty(this, "productName");
		}
		return productName;
	}
	
	/**
	 * Sets the productName
	 * @param name String The new product name
	 */
	public void setProductName(String name) {
		this.productNameProperty().setValue(name);
	}
	
	/**
	 * Gets the productName
	 * @return String the current Product Name
	 */
	public String getProductName() {
		return productNameProperty().getValue();
	}
	
	
	// Propertymethods for mostPickedUpMode
	// Does exactly the same as for ProductName above, and is not documented for that reason
	
	public StringProperty numberOfPickUpProperty() {
		if (numberOfPickUp == null) {
			numberOfPickUp = new SimpleStringProperty(this, "numberOfPickUp");
		}
		return numberOfPickUp;
	}
	
	public void setNumberOfPickUp(String numberOfPickUp) {
		this.numberOfPickUpProperty().setValue(numberOfPickUp);
	}
	
	public String getNumberOfPickUp() {
		return numberOfPickUpProperty().getValue();
	}
	
	
	public StringProperty numberOfPutDownProperty() {
		if (numberOfPutDown == null) {
			numberOfPutDown = new SimpleStringProperty(this, "numberOfPutDown");
		}
		return numberOfPutDown;
	}
	
	public void setNumberOfPutDown(String numberOfPutDown) {
		this.numberOfPutDownProperty().setValue(numberOfPutDown);
	}
	
	public String getNumberOfPutDown() {
		return numberOfPutDownProperty().getValue();
	}
	
	
	public StringProperty numberOfPurchasesProperty() {
		if (numberOfPurchases == null) {
			numberOfPurchases = new SimpleStringProperty(this, "numberOfPurchases");
		}
		return numberOfPurchases;
	}
	
	public void setNumberOfPurchases(String number) {
		this.numberOfPurchasesProperty().setValue(number);
	}
	
	public String getNumberOfPurchases() {
		return numberOfPurchasesProperty().getValue();
	}
	
	// Propertymethods for inStockMode
	
	public StringProperty numberInStockProperty() {
		if (numberInStock == null) {
			numberInStock = new SimpleStringProperty(this, "numberInStock");
		}
		return numberInStock;
	}
	
	public void setNumberInStock(String number) {
		this.numberInStockProperty().setValue(number);
	}
	
	public String getNumberInStock() {
		return numberInStockProperty().getValue();
	}
	
	// Property methods for onShelves
	
	public StringProperty numberOnShelvesProperty() {
		if (numberOnShelves == null) {
			numberOnShelves = new SimpleStringProperty(this, "numberOnShelves");
		}
		return numberOnShelves;
	}
	
	public void setNUmberOnShelves(String number) {
		this.numberOnShelvesProperty().setValue(number);
	}
	
	public String getNumberOnShelves() {
		return numberOnShelvesProperty().getValue();
	}

}

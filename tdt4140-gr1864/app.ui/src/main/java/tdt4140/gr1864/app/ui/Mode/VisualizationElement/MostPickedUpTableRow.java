package tdt4140.gr1864.app.ui.Mode.VisualizationElement;


import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The class that makes up the content in the cells of a table. Because of the JavaFX-implmentation this 
 * needs to have all the variables and corresponding methods for all possible columns in any table. 
 * 
 * In furture versions this model needs updating. It doesnt allow modes with same number of columns
 * @author Hakon Strandlie
 *
 */
public class MostPickedUpTableRow implements TableRow {
	
	private StringProperty productName;
	private StringProperty numberOfPickUp;
	private StringProperty numberOfPutDown;
	private StringProperty numberOfPurchases;
	
	private int size;

	/**
	 * The constructor for the mostPickedUpMode
	 * @param productName The product name of the product
	 * @param numberOfPickUp The number of times it was picked up
	 * @param numberOfPutDownThe number of times it was put down
	 * @param numberOfPurchases The number of purchases (the sum of the two above)
	 */
	public MostPickedUpTableRow(String productName, String numberOfPickUp, String numberOfPutDown, String numberOfPurchases) {
		setProductName(productName);
		setNumberOfPickUp(numberOfPickUp);
		setNumberOfPutDown(numberOfPutDown);
		setNumberOfPurchases(numberOfPurchases);
		
		this.size = 4;
	}
	
	/**
	 * The constructor for the mostPickedUpMode with numberOfPurchases calculated automatically
	 * @param productName The product name of the product
	 * @param numberOfPickUp The number of times it was picked up
	 * @param numberOfPutDownThe number of times it was put down
	 */
	public MostPickedUpTableRow(String productName, String numberOfPickUp, String numberOfPutDown) {
		setProductName(productName);
		setNumberOfPickUp(numberOfPickUp);
		setNumberOfPutDown(numberOfPutDown);
		
		this.size = 4;
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
		if (this.productName == null) {
			this.productName = new SimpleStringProperty(this, "productName");
		}
		return this.productName;
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
			numberOfPickUp.setValue("0");
		}
		return numberOfPickUp;
	}
	
	public void setNumberOfPickUp(String numberOfPickUp) {
		this.numberOfPickUpProperty().setValue(numberOfPickUp);
		calculateNumberOfPurchases();
	}
	
	public String getNumberOfPickUp() {
		return numberOfPickUpProperty().getValue();
	}
	
	
	public StringProperty numberOfPutDownProperty() {
		if (numberOfPutDown == null) {
			numberOfPutDown = new SimpleStringProperty(this, "numberOfPutDown");
			numberOfPutDown.setValue("0");
		}
		return numberOfPutDown;
	}
	
	public void setNumberOfPutDown(String numberOfPutDown) {
		this.numberOfPutDownProperty().setValue(numberOfPutDown);
		calculateNumberOfPurchases();
	}
	
	public String getNumberOfPutDown() {
		return numberOfPutDownProperty().getValue();
	}
	
	
	public StringProperty numberOfPurchasesProperty() {
		if (numberOfPurchases == null) {
			numberOfPurchases = new SimpleStringProperty(this, "numberOfPurchases");
			numberOfPurchases.setValue("0");
		}
		return numberOfPurchases;
	}
	
	private void setNumberOfPurchases(String number) {
		this.numberOfPurchasesProperty().setValue(number);
	}
	
	public String getNumberOfPurchases() {
		return numberOfPurchasesProperty().getValue();
	}
	
	private void calculateNumberOfPurchases() {
		int numberOfPickUps = Integer.parseInt(this.getNumberOfPickUp());
		int numberOfPutDowns = Integer.parseInt(this.getNumberOfPutDown());
		int numberOfPurchases = numberOfPickUps - numberOfPutDowns;
		if (numberOfPurchases < 0) {
			throw new IllegalStateException("Cannot have higher number of putDowns than pickUps");
		}
		this.setNumberOfPurchases(Integer.toString(numberOfPurchases));
	}
}

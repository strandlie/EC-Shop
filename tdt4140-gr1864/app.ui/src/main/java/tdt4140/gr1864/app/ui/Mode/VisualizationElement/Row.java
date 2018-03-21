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
public class Row {
	
	// Common variables for mostPickedUpMode and stockMode
	private StringProperty productName;
	
	// The variables for MostPickedUpMode
	private StringProperty numberOfPickUp;
	private StringProperty numberOfPutDown;
	private StringProperty numberOfPurchases;
	
	// The variables for stockMode
	private StringProperty numberInStock;

	// Variables for visualizationMode
	private StringProperty customerId;
	private StringProperty firstName;
	private StringProperty lastName;
	private StringProperty address;
	private StringProperty zipCode;
	
	/**
	 * The size of this Aggregate
	 */
	private int size;
	
	
	/**
	 * The constructor for the mostPickedUpMode
	 * @param productName The product name of the product
	 * @param numberOfPickUp The number of times it was picked up
	 * @param numberOfPutDown The number of times it was put down
	 * @param numberOfPurchases The number of purchases (the sum of the two above)
	 */
	public Row(String productName, String numberOfPickUp, String numberOfPutDown, String numberOfPurchases) {
		setProductName(productName);
		setNumberOfPickUp(numberOfPickUp);
		setNumberOfPutDown(numberOfPutDown);
		setNumberOfPurchases(numberOfPurchases);
		
		this.size = 4;
	}
	
	/**
	 * The constructor for stockMode
	 * @param productName
	 * @param numberInStock
	 */
	public Row(String productName, String numberInStock) {
		setProductName(productName);
		setNumberInStock(numberInStock);
		
		this.size = 2;
	}

	/**
	 * The constructor for visualizationMode
 	 * @param customerId
	 * @param firstName
	 * @param lastName
	 * @param address
	 * @param zip
	 */
	public Aggregate(String customerId, String firstName, String lastName, String address, String zip) {
		setCustomerId(customerId);
	    setFirstName(firstName);
	    setLastName(lastName);
	    setAddress(address);
	    setZipCode(zip);

	    this.size = 5;
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

	public StringProperty customerIdProperty() {
		if (customerId == null) {
			customerId = new SimpleStringProperty(this, "customerId");
		}
		return customerId;
	}

	public void setCustomerId(String customerId) {
		this.customerIdProperty().setValue(customerId);
	}

	public String getCustomerId() {
		return customerIdProperty().getValue();
	}

	public StringProperty firstNameProperty() {
		if (firstName == null) {
			firstName = new SimpleStringProperty(this, "firstName");
		}
		return firstName;
	}

	public void setFirstName(String name) {
		this.firstNameProperty().setValue(name);
	}

	public String getFirstName() {
		return this.firstNameProperty().getValue();
	}

	public StringProperty lastNameProperty() {
		if (lastName == null) {
			lastName = new SimpleStringProperty(this, "lastName");
		}
		return lastName;
	}

	public void setLastName(String name) {
		this.lastNameProperty().setValue(name);
	}

	public String getLastName() {
		return this.lastNameProperty().getValue();
	}

	public StringProperty addressProperty() {
		if (address == null) {
			address = new SimpleStringProperty(this, "address");
		}
		return address;
	}

	public void setAddress(String address) {
		this.addressProperty().setValue(address);
	}

	public String getAddress() {
		return this.addressProperty().getValue();
	}

	public StringProperty zipCodeProperty() {
		if (zipCode == null) {
			zipCode = new SimpleStringProperty(this, "zipCode");
		}
		return zipCode;
	}

	public void setZipCode(String zipCode) {
		this.zipCodeProperty().setValue(zipCode);
	}

	public String getZipCode() {
		return this.zipCodeProperty().getValue();
	}
}

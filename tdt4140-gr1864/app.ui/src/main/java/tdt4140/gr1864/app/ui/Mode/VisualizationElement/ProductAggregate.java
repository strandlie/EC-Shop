package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProductAggregate {
	
	private StringProperty productName;
	private StringProperty numberOfPickUp;
	private StringProperty numberOfPutDown;
	private StringProperty numberOfPurchases;
	
	public ProductAggregate(String productName, String numberOfPickUp, String numberOfPutDown, String numberOfPurchases) {
		setProductName(productName);
		setNumberOfPickUp(numberOfPickUp);
		setNumberOfPutDown(numberOfPutDown);
		setNumberOfPurchases(numberOfPurchases);
	}
	
	public StringProperty productNameProperty() {
		if (productName == null) {
			productName = new SimpleStringProperty(this, "productName");
		}
		return productName;
	}
	
	public void setProductName(String name) {
		this.productNameProperty().setValue(name);
	}
	
	public String getProductName() {
		return productNameProperty().getValue();
	}
	
	
	
	public StringProperty numberOfPickUpProperty() {
		if (numberOfPickUp == null) {
			numberOfPickUp = new SimpleStringProperty(this, "numberOfPickups");
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

}

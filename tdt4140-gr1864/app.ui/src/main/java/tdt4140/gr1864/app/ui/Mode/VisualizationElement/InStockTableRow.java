package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class InStockTableRow implements TableRow {
	
	private StringProperty productName;
	private StringProperty numberInStock;
	
	/**
	 * The size of this TableRow
	 */
	private int size;

	public InStockTableRow(String productName, String numberInStock) {
		setProductName(productName);
		setNumberInStock(numberInStock);
		
		this.size = 2;
	}

	@Override
	public int getSize() {
		return this.size;
	}
	
	
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
	
	public StringProperty numberInStockProperty() {
		if (this.numberInStock == null) {
			this.numberInStock = new SimpleStringProperty(this, "numberInStock");
		}
		return this.numberInStock;
	}
	
	public void setNumberInStock(String number) {
		this.numberInStockProperty().setValue(number);
	}
	
	public String getNumberInStock() {
		return numberInStockProperty().getValue();
	}
}
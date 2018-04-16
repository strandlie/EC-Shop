package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 * The table row for OnShelves mode, based on InStockTableRow
 * 
 * @author stianbm
 */
public class OnShelvesTableRow implements TableRow{
	
	private StringProperty productName;
	private StringProperty numberOnShelves;
	
	private int size;
	
	
	public OnShelvesTableRow(String productName, String numberOnShelves) {
		setProductName(productName);
		setNumberOnShelves(numberOnShelves);
		
		this.size = 2;
	}
	
	
	@Override
	public int getSize() {
		return this.size;
	}
	
	
	public StringProperty productNameProperty() {
		if (this.productName == null) {
			this.productName = new SimpleStringProperty(this, "productName");
		}
		return this.productName;
	}
	
	
	public void setProductName(String name) {
		this.productNameProperty().setValue(name);
	}
	
	
	public String getProductName() {
		return productNameProperty().getValue();
	}
	
	
	public StringProperty numberOnShelvesProperty() {
		if (this.numberOnShelves == null) {
			this.numberOnShelves = new SimpleStringProperty(this, "numberOnShelves");
		}
		return this.numberOnShelves;
	}
	
	
	public void setNumberOnShelves(String number) {
		this.numberOnShelvesProperty().setValue(number);
	}
	
	
	public String getNumberOnShelves() {
		return numberOnShelvesProperty().getValue();
	}
}

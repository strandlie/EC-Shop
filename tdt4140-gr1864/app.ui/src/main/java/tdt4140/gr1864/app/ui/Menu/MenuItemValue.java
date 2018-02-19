package tdt4140.gr1864.app.ui.Menu;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

// This is the model-representation of the contents of a cell in the Menu

// This is assumed will never change, and therefore does not implement 
// any listeners

public class MenuItemValue {
	
	private Property<String> textProperty = new SimpleStringProperty();
	
	public MenuItemValue(String text) {
		if (text.equals(null)) {
			this.setText("<no text");
		}
		else {
			this.setText(text);
		}
	}
	
	public String toString() {
		return this.getText();
	}
	
	public String getText() {
		return this.textProperty.getValue();
	}
	
	public void setText(String text) {
		this.textProperty.setValue(text);
	}

}

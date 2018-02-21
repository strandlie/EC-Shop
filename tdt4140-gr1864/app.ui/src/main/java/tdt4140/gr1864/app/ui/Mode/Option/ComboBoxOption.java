package tdt4140.gr1864.app.ui.Mode.Option;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

public class ComboBoxOption extends InteractionOption {

	private List<Property<String>> options;
	
	public ComboBoxOption(String name) {
		super(name);
		this.options = new ArrayList<Property<String>>();
	}
	
	public ComboBoxOption(String name, ArrayList<Property<String>> options) {
		super(name);
		this.options = options;
		
	}

}

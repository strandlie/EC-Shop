package tdt4140.gr1864.app.ui.Mode.Option;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;

public class ComboBoxOption extends InteractionOption {

	/**
	 * A list of the option that can be chosen from within this ComboBoxOption
	 */
	private List<Property<String>> options;
	
	/**
	 * When initialied without options we just create an empty optionslist
	 * @param name The name of this ComboBox
	 */
	public ComboBoxOption(String name) {
		super(name);
		this.options = new ArrayList<Property<String>>();
	}
	
	/**
	 * Initilizer with an existing list of options. Fails if options is null
	 * @param name The name of this ComboBox
	 * @param options The list of options. Cannot be null
	 */
	public ComboBoxOption(String name, ArrayList<Property<String>> options) {
		super(name);
		if (options.equals(null)) {
			throw new IllegalArgumentException("The ComboBoxOption object cannot be initialized with null-list of objects. Use the ComboBoxOption(String name) instead.");
		}
		this.options = options;
		
	}
	
	/**
	 * A private method to see if the the list has an option. Based on .equals() comparison
	 * @param p1 The option we want to check
	 * @return boolean. True if the ComboBox has the option
	 */
	private boolean hasOption(Property<String> p1) {
		for (Property<String> option : options) {
			if (p1.equals(option)) {
				return true;
			}
		}
		return false;
	}
	
	
	/**
	 * Find if a list has an option from the name of the option only
	 * @param optionName The string of the option wanted
	 * @return Boolean
	 */
	private boolean hasOption(String optionName) {
		return hasOption(new SimpleStringProperty(optionName));
	}
	
	/**
	 * Add an option that does not already exist
	 * @param option The option that is to be added
	 */
	public void addOption(String option) {
		Property<String> newOption = new SimpleStringProperty(option);
		if (! hasOption(newOption)) {
			options.add(newOption);
		}
	}
	
	/**
	 * Gets an option from the name of the option. 
	 * @param option The name of the option we want to get
	 * @return The option if it exists, or null if not
	 */
	public Property<String> getOption(String option) {
		Property<String> newOption = new SimpleStringProperty(option);
		
		for (Property<String> listMember : options) {
			if (listMember.equals(newOption)) {
				return listMember;
			}
		}
		return null;
	}
	
	/**
	 * Removes an option  based on Property Objects
	 * @param option The property object to be removed. 
	 */
	public void removeOption(Property<String> option) {
		if (hasOption(option)) {
			options.remove(option);
		}
	}
	
	


}

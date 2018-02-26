package tdt4140.gr1864.app.ui.Mode;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationElement;

/**
 * 
 * @author 	Håkon Strandlie haakstr@stud.ntnu.no
 * @version	1.0
 * @since	1.0
 *
 */

public class Mode {
	
	/**
	 * The string that is shown in the Mode-menu
	 */
	private Property<String> nameProperty;
	
	/**
	 * The list of the VisualizationElements that is shown in the Visualization Area for this mode
	 * Implemented as List to get an implicit ordering of elements
	 */
	private List<VisualizationElement> visuElements;
	
	/**
	 * 
	 * @param name The name shown in Mode-menu
	 * @param visuElements The list of the VisualizationElements
	 */
	public Mode(String name, ArrayList<VisualizationElement> visuElements) {
		
		this.nameProperty = new SimpleStringProperty();
		
		setName(name); // setName() handles catching nulls
		this.visuElements = visuElements;
	}
	
	
	public void setName(String name) {
		if (name.equals(null)) {
			nameProperty.setValue("<no name>");
		}
		else {
			nameProperty.setValue(name);
		}
	}
	public String getName() {
		return this.nameProperty.getValue();
	}
	
	/**
	 * 
	 * @param element The element to be added to this modes VisualizationElements. Does not accept null
	 */
	public void addVisualizationElement(VisualizationElement element) {
		if (element.equals(null)) {
			throw new IllegalArgumentException("Mode cannot add a null element to its VisualizationElements");
		}
		this.visuElements.add(element);
	}
	
	
	/**
	 * Returns all the VisualizationElements
	 * @return
	 */
	public List<VisualizationElement> getVisualizationElements() {
		return this.visuElements;
	}
	
	/**
	 * Return some specific VisualizationElement instance
	 * @param name The name of the VisualizationElement we are seeking
	 * @return Visualization element, or null. Returns null if not found
	 */
	public VisualizationElement getVisualizationElement(String name) {
		for (int i = 0; i < visuElements.size(); i++) {
			if (this.visuElements.get(i).getName().equals(name)) {
				return this.visuElements.get(i);
			}
		}
		return null;
	}
	
	/**
	 * Gets a VisualizationElement based on its relative importance. Throws an Exception if asking for nonexistant index 
	 * @param index The index (importance) of the visualizationElements. 0 is the most important
	 * @return The visualization element
	 */
	public VisualizationElement getVisualizationElement(Integer index) {
		if (this.visuElements.size() == 0 || index >= visuElements.size() || index < 0) {
			throw new IllegalArgumentException("There is no element at index " + index.toString()  + " in visualizationElements");
		}
		return this.visuElements.get(index);
	}
}
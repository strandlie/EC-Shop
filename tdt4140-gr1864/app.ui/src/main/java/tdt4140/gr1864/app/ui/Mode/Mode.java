package tdt4140.gr1864.app.ui.Mode;

import java.util.ArrayList;
import java.util.List;

import javafx.beans.property.Property;
import javafx.beans.property.SimpleStringProperty;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationElement;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;

/**
 * 
 * @author 	Håkon Strandlie haakstr@stud.ntnu.no
 * @version	1.0
 * @since	1.0
 *
 */

public class Mode {
	
	/**
	 * The string that is shown in the Mode-menu that the user can select from
	 */
	private String name;
	
	/**
	 * The list of the VisualizationElements that is shown in the Visualization Area for this mode
	 * Implemented as List to get an implicit ordering of elements
	 */
	private VisualizationTable visualizationElement;
	
	/**
	 * 
	 * @param name The name shown in Mode-menu
	 * @param visuElements The list of the VisualizationElements
	 */
	public Mode(String name, VisualizationTable visualizationElement) {
		
		
		this.name = name;
		this.visualizationElement = visualizationElement;
	}
	
	
	public String getName() {
		return this.name;
	}
	
	public VisualizationTable getVisualizationElement() {
		return this.visualizationElement;
	}
}	
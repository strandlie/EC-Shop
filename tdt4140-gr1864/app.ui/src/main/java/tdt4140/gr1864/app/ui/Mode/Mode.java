package tdt4140.gr1864.app.ui.Mode;

import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationInterface;

/**
 * 
 * @author Hakon Strandlie haakstr@stud.ntnu.no
 * @version 1.0
 * @since 1.0
 *
 */

public class Mode {

	/**
	 * The string that is shown in the Mode-menu that the user can select from
	 */
	private String name;

	/**
	 * The list of the VisualizationElements that is shown in the Visualization Area
	 * for this mode Implemented as List to get an implicit ordering of elements
	 */
	private VisualizationInterface visualizationElement;
	
	/**
	 * 
	 * @param name
	 *            The name shown in Mode-menu
	 * @param visualizationElement
	 *            The list of the VisualizationElements
	 */
	public Mode(String name, VisualizationInterface visualizationElement) {
		this.name = name;
		this.visualizationElement = visualizationElement;
	}

	public String getName() {
		return this.name;
	}

	/**
	 * Gets the visualizationElement that is contained in this Mode
	 * @return VisualizationInterface (VisualizationElement implements this)
	 */
	public VisualizationInterface getVisualizationElement() {
		return this.visualizationElement;
	}
}

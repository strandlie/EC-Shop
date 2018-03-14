package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import java.util.List;
/**
 * 
 * @author anders
 * An interface to be implemented by all visualization objects
 * Should contain all basic methods used by a controller to 
 * communicate with a visualization object
 */
public interface VisualizationInterface {
	
	String getName();
	
	boolean isActive();
	
	void setActive(boolean isActive);
	
	void setData(List<Aggregate> data);
	
	List<Aggregate> getData();
	
	/*
	 * TODO: Add more methods
	 */

}

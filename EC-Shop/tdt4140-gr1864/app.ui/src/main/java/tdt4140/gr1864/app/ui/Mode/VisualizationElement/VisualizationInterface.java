package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import java.util.List;

import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import tdt4140.gr1864.app.ui.globalUIModel.VisualizationViewController;
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
	
	void setData(Object object);
	
	Object getData();
	
	/**
	 * Used to load data from the VisualizationElement instance and into
	 * the appropriate view
	 * @param tableView The tabelView in the current scene
	 * @param imageView The imageView in the current scene
	 */
	void loadData(TableView<TableRow> tableView, ImageView imageView);
	
	/**
	 * Is called when the VisualizationViewController sets the instance as
	 * an active element, and loads the data into the appropriate views
	 * @param tableView The TableView in the current scene
	 * @param imageView The ImageView in the current scene
	 */
	void setAsActiveElement(VisualizationViewController vvc, TableView<TableRow> tableView, ImageView imageView);
	
	/*
	 * TODO: Add more methods
	 */

}

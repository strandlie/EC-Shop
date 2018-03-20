package tdt4140.gr1864.app.ui.globalUIModel;

import javafx.fxml.FXML;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.MostPickedUpTableRow;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.TableRow;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationInterface;

/**
 * The controller in charge of the VisualizationElement, the largest part of the app, which shows tables 
 * and other visualizations of data 
 * 
 * The controller initializes all the possible views, and hides and shows them
 * when needed.
 * 
 * @author Hakon Strandlie
 *
 */
public class VisualizationViewController {
	
	/**
	 * The reference to the actual TableView shown to the user
	 */
	@FXML
	private TableView<TableRow> tableView;
	
	/**
	 * The reference to the ImageView used to show pictures
	 */
	@FXML
	private ImageView imageView;
	
	/**
	 * The method called after the TableView has been created. Only used here to make it not-editable
	 */
	@FXML
	public void initialize() { 
		tableView.setEditable(false);
	}
	
	
	/**
	 * Takes an element that implements the VisualizationInterface, and
	 * loads the data from it into the GUI
	 * @param element
	 */
	public void setData(VisualizationInterface element) {
		element.loadData(this.tableView, this.imageView);
	}
	
	/**
	 * Changes the currently active VisualizationElement
	 * @param element The element that is to be active now
	 */
	public void setActiveElement(VisualizationInterface element) {
		element.setAsActiveElement(this, this.tableView, this.imageView);
	}
	
	/**
	 * Show, or not show the ImageView
	 * @param disable boolean
	 */
	public void imageViewSetDisable(boolean disable) {
		this.imageView.setDisable(disable);
		this.imageView.setVisible(!disable);
	}
	
	/**
	 * Show or not show the TableView
	 * @param disable boolean
	 */
	public void tableViewSetDisable(boolean disable) {
		this.tableView.setDisable(disable);
		this.tableView.setVisible(!disable);
	}
}

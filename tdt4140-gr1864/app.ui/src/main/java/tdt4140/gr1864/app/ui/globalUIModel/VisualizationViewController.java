package tdt4140.gr1864.app.ui.globalUIModel;

import java.util.ArrayList;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.Aggregate;

/**
 * The controller in charge of the VisualizationElement, the largest part of the app, which shows tables 
 * and other visualizations of data 
 * 
 * In good, agile spirit this class is now tailored for the TableView. Later it will probably have to be
 * made abstract and extended for the different types of visualiztions 
 * @author Håkon Strandlie
 *
 */
public class VisualizationViewController {
	
	/**
	 * The reference to the actual TableView shown to the user
	 */
	@FXML
	private TableView<Aggregate> visualizationView;
	
	/**
	 * The method called after the TableView has been created. Only used here to make it not-editable
	 */
	@FXML
	public void initialize() { 
		
		visualizationView.setEditable(false);
	}
	
	/**
	 * Creates the columns and fills in the ColumnNames
	 * @param columns ArrayList A list of TableColumns already created
	 */
	public void setColumns(ArrayList<TableColumn<Aggregate, String>> columns) {
		this.visualizationView.getColumns().setAll(columns);
	}
	
	/**
	 * Sets the data that populates the table. The table observes this data, and updates if the data is changed
	 * @param data ObservableList The list from the VisualizationTable from the model of the table. If the 
	 * 							  VisualizationTable is changed the table changes for the user
	 */
	public void setData(ObservableList<? extends Aggregate> data) {
		this.visualizationView.setItems((ObservableList<Aggregate>) data);
	}
}

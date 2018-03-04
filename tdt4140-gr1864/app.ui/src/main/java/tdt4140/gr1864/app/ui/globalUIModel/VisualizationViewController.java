package tdt4140.gr1864.app.ui.globalUIModel;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationElement;

/**
 * In good, agile spirit this class is now tailored for the TableView. Later it will probably have to be
 * made abstract and extended for the different types of visualiztions 
 * @author hstrandlie
 *
 */
public class VisualizationViewController {
	
	
	@FXML
	private TableView<String> visualizationView;
	
	@FXML
	public void initialize() {
	}
	

}

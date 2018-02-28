/**
 * 
 */
package tdt4140.gr1864.app.ui.globalUIModel;

import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 * @author hstrandlie
 *
 */
public class MenuViewController  {
	
	@FXML
	private ListView<String> menuListView;
	
	private ObservableList<String> items;
	
	@FXML
	private ModeController modeController;
	
	
	public void setModeController(ModeController modeController) {
		this.modeController = modeController;
	}
	
	public void initialize() {
		menuListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

				public ListCell<String> call(ListView<String> list) {
					return new MenuCell();
				}
			}
		);
		//menuListView.setEditable(false);
		
		this.items = menuListView.getItems();
		
		menuListView.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> property, String oldValue, String newValue) {
					if (newValue == null) {
						System.out.println("Set default mode");
						//modeController.setDefaultMode();
					}
					else if (! newValue.equals(oldValue)){
						/* if (modeController.isValidMode(newValue)) {
						 modeController.modeChanged(newValue);
						}
						else {
							throw new IllegalStateException("Cannot change mode to " + newValue + " from " + oldValue);
						}*/
						System.out.println("New selection");
						System.out.println("New value: " + newValue);
					}
					else {
						System.out.println("Nothing was done");
						return;
					}
				}
			}
		);
		
		items.add("Tool1");
		items.add("Tool2");
	}
	
	private void addMenuItem(String item) {
		items.add(item);
	}
	
	
				

}

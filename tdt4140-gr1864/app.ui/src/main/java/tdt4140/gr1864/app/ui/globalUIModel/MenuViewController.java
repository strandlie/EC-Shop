/**
 * 
 */
package tdt4140.gr1864.app.ui.globalUIModel;

import java.util.List;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;

/**
 * @author hstrandlie
 *
 */
public class MenuViewController {
	
	@FXML
	private ListView<String> menuListView;
	
	private List<String> items;
	
	private ModeController modeController;
	
	public MenuViewController(ModeController modeController) {
		if (modeController.equals(null)) {
			throw new IllegalArgumentException("MenuViewController cannot be instanciated with null as modeController. Needs access");
		}
		this.modeController = modeController;
	}
	
	public void initialize() {
		menuListView.setCellFactory(TextFieldListCell.forListView());
		menuListView.setEditable(false);
		
		this.items = menuListView.getItems();
		
		menuListView.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> property, String oldValue, String newValue) {
					if (oldValue.equals(newValue)) {
						return;
					}
					else if (newValue.equals(null)) {
						modeController.setDefaultMode();
					}
					else {
						if (modeController.isValidMode(newValue)) {
						modeController.modeChanged(newValue);
						}
						else {
							throw new IllegalStateException("Cannot change mode to " + newValue + " from " + oldValue);
						}
					}
				}
			}
		);
		
		
	}
	
				

}

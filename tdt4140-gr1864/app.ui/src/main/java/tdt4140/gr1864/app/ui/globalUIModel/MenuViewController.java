/**
 * 
 */
package tdt4140.gr1864.app.ui.globalUIModel;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;

/**
 * @author Hakon Strandlie
 * The Controller for the menu View. Also contains a reference to the ModeController to be able to let it know when the user selects a different menuItem
 *
 */
public class MenuViewController  {
	
	/*
	 * The reference to the modeController as specified above
	 */
	private ModeController modeController;
	
	/**
	 * The list of items that populate the menu. In this case just strings (Names of modes)
	 */
	private ObservableList<String> items;
	
	/**
	 * The reference to the actual menuListView shown to the user
	 */
	@FXML
	private ListView<String> menuListView;
	
	/**
	 * Is called after the GUI is created and the reference to the menuListView is created. Is used to specify the cellFactory
	 */
	@FXML
	public void initialize() {
		menuListView.setCellFactory(new Callback<ListView<String>, ListCell<String>>() {

				public ListCell<String> call(ListView<String> list) {
					return new MenuCell();
				}
			}
		);
		menuListView.setEditable(false);
		
		this.items = menuListView.getItems();
		
		menuListView.getSelectionModel().selectedItemProperty().addListener(
			new ChangeListener<String>() {
				public void changed(ObservableValue<? extends String> property, String oldValue, String newValue) {
					if (! newValue.equals(oldValue)){
						if (modeController.isValidMode(newValue)) {
						 modeController.modeChanged(newValue);
						}
						else {
							throw new IllegalStateException("Cannot change mode to " + newValue + " from " + oldValue);
						}
					}
					else {
						System.out.println("Nothing was done");
						return;
					}
				}
			}
		);
	}
	
	/**
	 * Used to add another item to the menu. Just a String. Should match the name of a mode, or else the modeController will throw an error when the item is selected
	 * Is called by the ModeController when a new mode is created. 
	 * @param item The name of the new MenuItem
	 */
	protected void addMenuItem(String item) {
		items.add(item);
	}
	
	/**
	 * Used by the modeController during its initialize()-method to register itself. The menuViewController uses it to let the modeController know that the user
	 * selected a new mode
	 * @param modeController the modeController that controls this menuViewController
	 */
	protected void setModeController(ModeController modeController) {
		if (modeController == null) {
			throw new IllegalArgumentException("modeController cannot be null");
		}
		this.modeController = modeController;
	}

}

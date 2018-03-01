package tdt4140.gr1864.app.ui.globalUIModel;

import javafx.scene.control.ListCell;

public class MenuCell extends ListCell<String> {

	
	@Override
	protected void updateItem(String name, boolean empty) {
		super.updateItem(name, empty);
		if (name == null || empty) {
			setText(null);
		}
		else {
			setText(name);
		}
	}
	
	

}

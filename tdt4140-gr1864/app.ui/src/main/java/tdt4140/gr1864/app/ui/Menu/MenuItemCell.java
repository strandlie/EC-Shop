package tdt4140.gr1864.app.ui.Menu;

import javafx.scene.control.ListCell;

public class MenuItemCell extends ListCell<MenuItemValue> {
		
	@Override
	protected void updateItem(MenuItemValue item, boolean empty) {
		super.updateItem(item, empty);
		
		MenuItemValue oldItem = this.getItem();
		if (oldItem == item) {
			return;
		}
		if (empty || item == null) {
			this.setText(null);
			this.setGraphic(null);
		}
		else {
			this.setText(item.toString());
		}
	
		
	}
	
}

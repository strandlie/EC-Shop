/**
 * 
 */
package tdt4140.gr1864.app.ui.globalUIModel;

import java.util.List;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.control.cell.TextFieldListCell;
import javafx.util.StringConverter;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;
import tdt4140.gr1864.app.ui.Menu.MenuItemValue;

/**
 * @author hstrandlie
 *
 */
public class MenuViewController {
	
	@FXML
	private ListView<MenuItemValue> menuListView;
	private List<MenuItemValue> cells;
	
	public void initialize() {
		menuListView.setCellFactory(
			TextFieldListCell.forListView(new StringConverter<MenuItemValue>() {

				@Override
				public MenuItemValue fromString(String arg0) {
					// TODO Implement when the representation of a cell is defined
					throw new NotImplementedException();
				}

				@Override
				public String toString(MenuItemValue arg0) {
					// TODO Auto-generated method stub
					return null;
				}
				
			}));
	}

}

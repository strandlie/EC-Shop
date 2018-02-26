package tdt4140.gr1864.app.ui.globalUIModel;

import java.util.ArrayList;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class VisualizationViewController {
	
	/**
	 * The attribute representing the Tableview inside the VisualizationView 
	 */
	// TODO: Object needs to be changed to Product when merged
	TableView<Object> table;
	
	/**
	 * The list of products. The TableView listens to this, such that when it is changed the TableView is notified
	 */
	ObservableList<Object> products;
	
	/**
	 * The constructor of a view controller without any objects in the table
	 */
	public VisualizationViewController() {
		this.table  = new TableView<Object>();
		this.products = FXCollections.observableArrayList(this.products);
	}
	
	/**
	 * The constructor of the view controller with products in the table
	 * @param products An ArrayList of products, that is put into the ObservableList
	 */
	public VisualizationViewController(ArrayList<Object> products) {
		this.table = new TableView<Object>();
		this.products = FXCollections.observableArrayList(this.products);
		
	}
	
	public void initialize() {
		TableColumn<Object, String> productNameCol = new TableColumn<Object, String>("Product Name");
		productNameCol.setCellValueFactory(new PropertyValueFactory("productName"));
		// TODO: Check that there is noe yellow line above here after conversion from Object to Product
		
		TableColumn<Object, String> quantitySoldCol = new TableColumn<Object, String>("Quantity");
		quantitySoldCol.setCellValueFactory(new PropertyValueFactory("quantity"));
		
		this.table.getColumns().setAll(productNameCol, quantitySoldCol);
		
	}

}

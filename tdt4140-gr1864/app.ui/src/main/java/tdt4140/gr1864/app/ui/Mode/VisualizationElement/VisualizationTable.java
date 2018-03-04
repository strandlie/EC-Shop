package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;

public class VisualizationTable extends VisualizationElement {
	
	private final static Map<String, String> allowedColumnNames;
	static {
		allowedColumnNames = new HashMap<String, String>();
		allowedColumnNames.put("productName", "Product Name");
		allowedColumnNames.put("numberOfPickUp", "Number Of Pick Ups");
		allowedColumnNames.put("numberOfPutDown", "Number Of Put Down");
		allowedColumnNames.put("numberOfPurchases", "Number Of Purchases");
		allowedColumnNames.put("numberInStock", "In Stock");
	}
	
	private ArrayList<TableColumn<Aggregate, String>> columns;
	private ObservableList<Aggregate> data;

	public VisualizationTable(String name) {
		super(name);
		this.columns = new ArrayList<TableColumn<Aggregate, String>>();
		this.data = FXCollections.observableArrayList(new ArrayList<Aggregate>());
	}
	
	
	public void setData(ArrayList<Aggregate> data) {
		this.data = FXCollections.observableArrayList(data);
	}
	
	public void addData(Aggregate a) {
		this.data.add(a);
	}
	
	public ObservableList<Aggregate> getData() {
		return this.data;
	}
	
	
	public ArrayList<TableColumn<Aggregate, String>> getColumns() {
		return this.columns;
	}
	
	
	/**
	 * Takes a list of Aggregate-Property-names and creates the corresponding TableColumns and CellValueFactories
	 * Assumes that columnnames already has been verified
	 * @param columns ArrayList of strings of columnnames
	 */
	public void addColumns(ArrayList<String> columnNames) {
		for (String column : columnNames) {
			addColumn(column);
		}
	}
	
	public void addColumn(String columnID) {
		if (! allowedColumnNames.containsKey(columnID)) {
			throw new IllegalArgumentException(columnID + " is not an allowable Column Identifier");
		}
		
		String columnName = allowedColumnNames.get(columnID);
		TableColumn<Aggregate, String> tempColumn = new TableColumn<Aggregate, String>(columnName);
		tempColumn.setCellValueFactory(new PropertyValueFactory(columnID));
		columns.add(tempColumn);
		
	}
}
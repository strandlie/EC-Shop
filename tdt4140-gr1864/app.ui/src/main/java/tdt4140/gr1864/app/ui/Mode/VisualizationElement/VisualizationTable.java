package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.ui.TableLoader;
import tdt4140.gr1864.app.ui.globalUIModel.VisualizationViewController;

public class VisualizationTable extends VisualizationElement {
	/**
	 * 
	 * @author Hakon Strandlie
	 * The model-class of the Visualization Table. Has proved to not serve the function originally thought, and wil probably deprecate in the future. 
	 */
	
	/**
	 * The allowed column names of a table. Needs to be manually updated as of now, but exists for safety. 
	 */
	private final static Map<String, String> allowedColumnNames;
	/**
	 * A static method that creates the allowed columnNames hashMap before creation of the VisualizationTable
	 */
	static {
		allowedColumnNames = new HashMap<String, String>();
		allowedColumnNames.put("productName", "Product Name");
		allowedColumnNames.put("numberOfPickUp", "Number Of Pick Ups");
		allowedColumnNames.put("numberOfPutDown", "Number Of Put Down");
		allowedColumnNames.put("numberOfPurchases", "Number Of Purchases");
		allowedColumnNames.put("numberInStock", "In Stock");
		//OnShelves
		allowedColumnNames.put("numberOnShelves", "On shelves");
		allowedColumnNames.put("customerId", "Customer ID");
		allowedColumnNames.put("firstName", "First Name");
		allowedColumnNames.put("lastName", "Last Name");
		allowedColumnNames.put("address", "Address");
		allowedColumnNames.put("zip", "ZIP");
		allowedColumnNames.put("name", "Name");
		allowedColumnNames.put("gender", "Gender");
		allowedColumnNames.put("age", "Age");
		allowedColumnNames.put("numOfPersonInHouse", "Number Of Persons In Household");
		
	}
	
	/**
	 * Container for the columns in this table
	 */
	private ArrayList<TableColumn<TableRow, String>> columns;
	/**
	 * Container for the data used to populate the table in the GUI
	 */
	private ObservableList<TableRow> data;


	/**
	 * Constructor creating the lists
	 * @param name String the name of the Table
	 */
	public VisualizationTable(String name) {
		super(name);
		this.columns = new ArrayList<TableColumn<TableRow, String>>();
		this.data = FXCollections.observableArrayList(new ArrayList<TableRow>());
	}
	
	/**
	 * Sets the data of the table. Setting this will reflect to the table shown to the user, since the list is observable
	 * and only references are passed
	 * @param data ArrayList The list of the data used to populate the table
	 */
	@Override
	public void setData(Object data) {
		List<TableRow> list = objectIsList(data);
		this.data = FXCollections.observableArrayList(list);
	}
	
	/**
	 * Add a single row to the table
	 * @param MostPickedTableRow the new row. Immidiately shown to the user if the mode is active
	 */
	public void addData(TableRow a) {
		this.data.add(a);
	}
	
	/**
	 * Gets the items currently shown to the user
	 * @return ObservableList the reference to the data currently populating the table
	 */
	public ObservableList<TableRow> getData() {
		return this.data;
	}
	
	/**
	 * Implementation of the interfaceMethod. Loads the data from this VisualizationTable into 
	 * the TableView
	 */
	public void loadData(TableView<TableRow> tableView, ImageView imageView) {
		tableView.setItems(this.data);
	}
	
	/**
	 * Implementation of the interface method. Shows the TableView, hides the ImageView
	 * Also loads data and Columns into the TableView
	 */
	public void setAsActiveElement(VisualizationViewController vvc, TableView<TableRow> tableView, ImageView imageView) {
		vvc.imageViewSetDisable(true);
		vvc.tableViewSetDisable(false);
		loadData(tableView, imageView);
		tableView.getColumns().setAll(this.getColumns());
	}
	
	
	/**
	 * Gets the Columns of this table. Changing this will not immidiately reflect to the user, as this is controlled by the ModeController
	 * @return ArrayList
	 */
	public ArrayList<TableColumn<TableRow, String>> getColumns() {
		return this.columns;
	}
	
	/**
	 * Takes a list of Row-Property-names and creates the corresponding TableColumns and CellValueFactories
	 * Assumes that columnnames already has been verified
	 * @param columns ArrayList of strings of columnnames
	 */
	public void addColumns(ArrayList<String> columnNames) {
		for (String column : columnNames) {
			addColumn(column);
		}
	}
	
	/**
	 * Adds a column to this VisualizationTable. Called by the ModeController during initialize()
	 * @param columnID The columnID of the new column. Must be one of the predefined valid columnIDs from the static variable
	 */
	public void addColumn(String columnID) {
		if (! allowedColumnNames.containsKey(columnID)) {
			throw new IllegalArgumentException(columnID + " is not an allowable Column Identifier");
		}
		
		String columnName = allowedColumnNames.get(columnID);
		TableColumn<TableRow, String> tempColumn = new TableColumn<TableRow, String>(columnName);
		tempColumn.setCellValueFactory(new PropertyValueFactory(columnID));
		columns.add(tempColumn);
	}
	
	/**
	 * Gets the size of this table, e.g. the numberOfColumns
	 * @return int The number of Column
	 */
	public int getSize() {
		return this.columns.size();
	}	
	
	
	/**
	 * Tests if this table has a column with the name
	 * @param name String the name shown to the user
	 * @return boolean true if has the column name
	 */
	public boolean hasColumn(String name) {
		for (TableColumn<TableRow, String> column : columns) {
			if (column.getText().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	@SuppressWarnings("unchecked")
	private List<TableRow> objectIsList(Object object) throws ClassCastException {
		return (List<TableRow>) object;
	}
	
	public void wipeTable() {
		this.data.clear();
	}
}
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
	}
	
	/**
	 * Container for the columns in this table
	 */
	private ArrayList<TableColumn<Row, String>> columns;
	/**
	 * Container for the data used to populate the table in the GUI
	 */
	private ObservableList<Row> data;
	
	private TableLoader tableLoader;


	/**
	 * Constructor creating the lists
	 * @param name String the name of the Table
	 */
	public VisualizationTable(String name) {
		super(name);
		this.columns = new ArrayList<TableColumn<Row, String>>();
		this.data = FXCollections.observableArrayList(new ArrayList<Row>());
	}
	
	/**
	 * Constructor for MostPickedUpModeVisualizationTable
	 * @param trips
	 */
	public VisualizationTable(String name, ArrayList<ShoppingTrip> trips) {
		super(name);
		this.columns = new ArrayList<TableColumn<Row, String>>();
		this.data = FXCollections.observableArrayList(new ArrayList<Row>());
		this.tableLoader = new TableLoader(trips, this);
	}
	
	/**
	 * Constructor for StockModeVisualizationTable
	 * @param name
	 * @param productIDsOnShelf
	 * @param productIDsInStorage
	 */
	public VisualizationTable(String name, Map<Integer, Integer> productIDsOnShelf, Map<Integer, Integer> productIDsInStorage) {
		super(name);
		this.columns = new ArrayList<TableColumn<Row, String>>();
		this.data = FXCollections.observableArrayList(new ArrayList<Row>());
		this.tableLoader = new TableLoader(productIDsOnShelf, productIDsInStorage, this);
	}
	
	/**
	 * Constructor for OnShelvesModeVisualizationTable
	 * @param name
	 * @param productIDsOnShelf
	 */
	public VisualizationTable(String name, Map<Integer, Integer> productIDsOnShelf) {
		super(name);
		this.columns = new ArrayList<TableColumn<Row, String>>();
		this.data = FXCollections.observableArrayList(new ArrayList<Row>());
		this.tableLoader = new TableLoader(productIDsOnShelf, this);
	}
	
	public VisualizationTable(String name, List<Customer> customerList) {
		super(name);
		this.columns = new ArrayList<TableColumn<Row, String>>();
		this.data = FXCollections.observableArrayList(new ArrayList<Row>());
		this.tableLoader = new TableLoader(customerList, this);
	}
	
	/**
	 * Sets the data of the table. Setting this will reflect to the table shown to the user, since the list is observable
	 * and only references are passed
	 * @param data ArrayList The list of the data used to populate the table
	 */
	@Override
	public void setData(Object data) {
		List<Row> list = objectIsList(data);
		this.data = FXCollections.observableArrayList(list);
	}
	
	/**
	 * Add a single row to the table
	 * @param Row the new row. Immidiately shown to the user if the mode is active
	 */
	public void addData(Row a) {
		this.data.add(a);
	}
	
	/**
	 * Gets the items currently shown to the user
	 * @return ObservableList the reference to the data currently populating the table
	 */
	public ObservableList<Row> getData() {
		return this.data;
	}
	
	/**
	 * Implementation of the interfaceMethod. Loads the data from this VisualizationTable into 
	 * the TableView
	 */
	public void loadData(TableView<Row> tableView, ImageView imageView) {
		tableView.setItems(this.data);
	}
	
	/**
	 * Implementation of the interface method. Shows the TableView, hides the ImageView
	 * Also loads data and Columns into the TableView
	 */
	public void setAsActiveElement(VisualizationViewController vvc, TableView<Row> tableView, ImageView imageView) {
		vvc.imageViewSetDisable(true);
		vvc.tableViewSetDisable(false);
		loadData(tableView, imageView);
		tableView.getColumns().setAll(this.getColumns());
	}
	
	
	/**
	 * Gets the Columns of this table. Changing this will not immidiately reflect to the user, as this is controlled by the ModeController
	 * @return ArrayList
	 */
	public ArrayList<TableColumn<Row, String>> getColumns() {
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
		TableColumn<Row, String> tempColumn = new TableColumn<Row, String>(columnName);
		tempColumn.setCellValueFactory(new PropertyValueFactory<>(columnID));
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
		for (TableColumn<Row, String> column : columns) {
			if (column.getText().equals(name)) {
				return true;
			}
		}
		return false;
	}
	
	public TableLoader getTableLoader() {
		return this.tableLoader;
	}
	
	public void wipeTable() {
		this.data.clear();
	}
	
	@SuppressWarnings("unchecked")
	private List<Row> objectIsList(Object object) throws ClassCastException {
		return (List<Row>) object;
	}
}
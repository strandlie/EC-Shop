package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import java.util.ArrayList;
import java.util.List;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;

public class VisualizationTable extends VisualizationElement {
	
	private List<TableColumn<ProductAggregate, String>> columns;
	private ObservableList<ProductAggregate> data;

	public VisualizationTable(String name) {
		super(name);
		this.columns = new ArrayList<TableColumn<ProductAggregate, String>>();
		this.data = FXCollections.observableArrayList();
	}
	
	
	public VisualizationTable(String name, ArrayList<TableColumn<ProductAggregate, String>> columns, ArrayList<ProductAggregate> data) {
		super(name);
		this.columns = columns;
		this.data = FXCollections.observableArrayList(data);
	}
	
	public void setData(ObservableList<ProductAggregate> data) {
		this.data = data;
	}
	
	public ObservableList<ProductAggregate> getData() {
		return this.data;
	}
	
	public void setColumns(List<TableColumn<ProductAggregate, String>> columns) {
		this.columns = columns;
	}
	
	public List<TableColumn<ProductAggregate, String>> getColumns () {
		return this.columns;
	}
}

package tdt4140.gr1864.app.ui.globalUIModel;

import java.util.ArrayList;
import java.util.List;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.ProductAggregate;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationElement;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;
/**
 * In good, agile spirit this class is now tailored for the TableView. Later it will probably have to be
 * made abstract and extended for the different types of visualiztions 
 * @author hstrandlie
 *
 */
public class VisualizationViewController extends Application{
	
	private ObservableList<ProductAggregate> data;
	
	@FXML
	private TableView<ProductAggregate> visualizationView;
	
	@FXML
	public void initialize() {
		ArrayList<ProductAggregate> list = new ArrayList<ProductAggregate>();
		list.add(new ProductAggregate("Bolle", "3", "2", "1"));
		list.add(new ProductAggregate("Sjokolade", "4","3","2"));
		list.add(new ProductAggregate("Bille", "1", "0", "0"));
		
		this.data = FXCollections.observableArrayList(list);
		this.visualizationView.setItems(data);
		
		visualizationView.setEditable(false);
		
		TableColumn<ProductAggregate, String> productNameCol = new TableColumn<ProductAggregate, String>("Product Name");
		productNameCol.setCellValueFactory(new PropertyValueFactory("productName"));
		
		TableColumn<ProductAggregate, String> numberOfPickUpCol = new TableColumn<ProductAggregate, String>("Number Of Pick Ups");
		numberOfPickUpCol.setCellValueFactory(new PropertyValueFactory("numberOfPickUp"));
		
		TableColumn<ProductAggregate, String> numberOfPutDownCol = new TableColumn<ProductAggregate, String>("Number Of Put Downs");
		numberOfPutDownCol.setCellValueFactory(new PropertyValueFactory("numberOfPutDown"));
		
		TableColumn<ProductAggregate, String> numberOfPurchasesCol = new TableColumn<ProductAggregate, String>("Number Of Purchases");
		numberOfPurchasesCol.setCellValueFactory(new PropertyValueFactory("numberOfPurchases"));
		
		ArrayList<TableColumn<ProductAggregate, String>> newList = new ArrayList<TableColumn<ProductAggregate, String>>();
		newList.add(productNameCol);
		newList.add(numberOfPickUpCol);
		newList.add(numberOfPutDownCol);
		newList.add(numberOfPurchasesCol);
		
		
		this.visualizationView.getColumns().setAll(newList);
	}
	
	public void setColumns(ArrayList<TableColumn> columns) {
		
	}
	
	public void setTable(VisualizationTable table) {
		//this.visualizationView.getColumns().addAll(table.getColumns());
	}
	
	private void manualInitialize() {
		
	}


	@Override
	public void start(Stage primaryStage) throws Exception {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("./tdt4140/gr1864/app/ui/globalUIModel/VisualizationView.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
	
}

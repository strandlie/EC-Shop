package tdt4140.gr1864.app.ui;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tdt4140.gr1864.app.ui.globalUIModel.InteractionViewController;
import tdt4140.gr1864.app.ui.globalUIModel.MenuViewController;
import tdt4140.gr1864.app.ui.globalUIModel.ModeController;
import tdt4140.gr1864.app.ui.globalUIModel.VisualizationViewController;

public class OwnerApp extends Application{
	
	
	public void start(Stage primaryStage) throws IOException {
		
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("./tdt4140/gr1864/app/ui/globalUIModel/OwnerApp.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		
		
	}
	public static void main(String[] args) {
		launch(args);
	}

}

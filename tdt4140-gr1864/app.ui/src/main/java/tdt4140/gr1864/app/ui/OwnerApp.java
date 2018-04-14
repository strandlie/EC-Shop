package tdt4140.gr1864.app.ui;

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;

import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class OwnerApp extends Application{
	/**
	 * Collection of listeners which get called when you close the application.
	 */
	private static Collection<AppListener> listeners = new HashSet<>();
	
	public static void addListener(AppListener listener) {
		listeners.add(listener);
	}	
	
	public void start(Stage primaryStage) throws IOException {
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent root = fxmlLoader.load(getClass().getClassLoader().getResource("./tdt4140/gr1864/app/ui/globalUIModel/OwnerApp.fxml"));
		primaryStage.setScene(new Scene(root));
		primaryStage.show();
		
		// Run all listeners listening for when the app closes.
		primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
			public void handle(WindowEvent we) {
				for (AppListener listener : OwnerApp.listeners) {
					try {
						listener.appClosed();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		});

	}
	public static void main(String[] args) {
		launch(args);
	}

}

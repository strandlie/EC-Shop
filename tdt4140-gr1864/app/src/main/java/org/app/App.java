package org.app;

import org.web.server.JettyServer;

import tdt4140.gr1864.app.core.dataMocker.DataMocker;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.ui.AppListener;
import tdt4140.gr1864.app.ui.OwnerApp;

/**
 * The App class starts the entire system and closes all parts of the
 * system cleanly when the owner application is closed. This needs to
 * be done in a separate module to prevent circular dependencies, as
 * starting the application depends on all the other modules.
 */
public class App implements AppListener {
	/**
	 * A common shared App instance.
	 */
	private static App app;
	
	/**
	 * The Thread for running the DataMocker.
	 */
	private Thread mockerThread;
	
	/**
	 * A simple wrapper around the Jetty server.
	 */
	private JettyServer server;
	
	/**
	 * @return A shared instance of App.
	 * @throws Exception
	 */
	public static App initialize() throws Exception {
		if (app != null) {
			return app;
		}
		
		return app = new App();
	}
	
	/**
	 * Starts all parts of the application.
	 * @throws Exception
	 */
	private App() throws Exception {
		// Populate database with data.
		DataLoader.main(null);

		// Listen for closing.
		OwnerApp.addListener(this);
		
		// Start the data mocker in a separate thread.
        // DataMocker mocker = new DataMocker();
        // mockerThread = new Thread(mocker);
        // mockerThread.start();
        
        server = new JettyServer();
        server.start();
	}
	
	/**
	 * Closes the application by stopping the mocker and the server when the UI closes.
	 */
	public void appClosed() throws Exception {
		// mockerThread.interrupt();
		server.stop();
		System.exit(0);
	}
	
    public static void main(String[] args) throws Exception {
    	App.initialize();
    	OwnerApp.launch(OwnerApp.class, args);
    }
}

package org.app;

import org.web.server.JettyServer;

import tdt4140.gr1864.app.core.dataMocker.DataMocker;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.ui.AppListener;
import tdt4140.gr1864.app.ui.OwnerApp;

public class App implements AppListener {
	private static App app;
	
	private Thread mockerThread;
	
	private JettyServer server;
	
	public static App initialize() throws Exception {
		if (app != null) {
			return app;
		}
		
		return app = new App();
	}
	
	private App() throws Exception {
		DataLoader.main(null);
		OwnerApp.addListener(this);
        DataMocker mocker = new DataMocker();
        mockerThread = new Thread(mocker);
        mockerThread.start();
        server = new JettyServer();
        server.start();
	}
	
	public void appClosed() throws Exception {
		mockerThread.interrupt();
		server.stop();
		System.exit(0);
	}
	
    public static void main(String[] args) throws Exception {
    	new App();
    	OwnerApp.launch(OwnerApp.class, args);
    }
}
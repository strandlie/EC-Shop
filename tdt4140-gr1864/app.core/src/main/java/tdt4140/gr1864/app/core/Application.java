package tdt4140.gr1864.app.core;

import tdt4140.gr1864.app.core.dataMocker.DataMocker;
import tdt4140.gr1864.app.core.database.DataLoader;

public class Application {
	
	public static void main(String[] args) {
		DataLoader.main(null);
		DataMocker mocker = new DataMocker();
		new Thread(mocker).start();
	}
}

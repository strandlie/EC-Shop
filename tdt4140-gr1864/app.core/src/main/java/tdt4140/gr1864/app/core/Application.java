package tdt4140.gr1864.app.core;

import java.io.IOException;

import tdt4140.gr1864.app.core.dataMocker.DataMocker;
import tdt4140.gr1864.app.core.database.DataLoader;

public class Application {
	
	public static void main(String[] args) {
		DataLoader.main(null);
		try {
			DataMocker.main(null);
			DataMocker.main(null);
			DataMocker.main(null);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

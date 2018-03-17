package tdt4140.gr1864.app.core;

import tdt4140.gr1864.app.core.dataMocker.DataMocker;

public class Application {
	
	public static void main(String[] args) {
		DataMocker mocker = new DataMocker();
		new Thread(mocker).start();
	}
}

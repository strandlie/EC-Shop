package tdt4140.gr1864.app.ui.scheduling;

import javafx.application.Platform;
import tdt4140.gr1864.app.ui.globalUIModel.ModeController;

public class GUIUpdaterRunnable implements Runnable {
	
	private ModeController mc;
	
	public GUIUpdaterRunnable(ModeController mc) {
		this.mc = mc;
	}

	@Override
	public void run() {
		mc.updateDemographicsTable();
		mc.updateDurationModeField();
	}

}

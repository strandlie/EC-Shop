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
		mc.updateDurationModeField();
		switch (mc.getCurrentMode().getName()) {
			case "Demographics":
				mc.updateDemographicsTable();
				break;
			case "Most Picked Up":
				mc.updateMostPickedUpTable();
				break;
			case "Heatmap":
				mc.updateHeatMap();
				break;
			case "Plot":
				mc.updatePlot();
				break;
			case "Shelves":
				mc.updateShelvesTable();
				break;
			case "Stock":
				mc.updateStockTable();
				break;
			default:
				throw new IllegalStateException("Not a valid modename");
			
		}
	}

}

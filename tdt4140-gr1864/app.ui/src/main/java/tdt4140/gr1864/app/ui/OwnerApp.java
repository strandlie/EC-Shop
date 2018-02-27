package tdt4140.gr1864.app.ui;

import tdt4140.gr1864.app.ui.globalUIModel.InteractionViewController;
import tdt4140.gr1864.app.ui.globalUIModel.MenuViewController;
import tdt4140.gr1864.app.ui.globalUIModel.ModeController;
import tdt4140.gr1864.app.ui.globalUIModel.VisualizationViewController;

public class OwnerApp {
	
	ModeController modeController;
	
	public OwnerApp(ModeController mc, InteractionViewController ivc, VisualizationViewController vvc, MenuViewController mvc) {
		this.modeController = mc;
		
	}
	
	public static void main(String[] args) {
		ModeController mc = new ModeController();
	}

}

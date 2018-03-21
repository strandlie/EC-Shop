package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;

import java.util.ArrayList;
import java.util.List;

import eu.hansolo.fx.charts.heatmap.HeatMap;
import eu.hansolo.fx.charts.tools.Point;
import tdt4140.gr1864.app.core.Coordinate;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.ui.globalUIModel.VisualizationViewController;

/**
 * This is the model-layer of the HeatMap. It is contained in a Mode, and has
 * methods for showing the resulting image to the ImageView
 * @author Håkon Strandlie
 *
 */
public class VisualizationHeatMap extends VisualizationElement {
	
	/**
	 * The heatmap generated from the data
	 */
	private HeatMap map;

	/**
	 * THe constructor when there already is a list of ShoppingTrips
	 * @param name The name of this VisualizationHeatmap
	 * @param shoppingTripList The list of shoppingtrips that this heatmap should be generated from 
	 */
	public VisualizationHeatMap(String name, List<ShoppingTrip> shoppingTripList) {
		super(name);
		this.map = new HeatMap();
		map.addSpots(getPoints(shoppingTripList));
	}

	/**
	 * Set the points to the heatmap, after validating
	 */
	@Override
	public void setData(Object object) {
		List<ShoppingTrip> shoppingTripList = objectIsList(object);
		map.addSpots(getPoints(shoppingTripList));
	}

	/**
	 * Get the heatmap
	 */
	@Override
	public HeatMap getData() {
		// TODO Auto-generated method stub
		return this.map;
	}
	/**
	 * Load the data from this Heatmap into the imageView. The tableView is passed 
	 * to conform to the infacemethod, but never used.
	 */
	@Override
	public void loadData(TableView<Row> tableView, ImageView imageView) {
		imageView.setImage(this.map.getImage());
	}

	/**
	 * Called when this heatmap is set as the active element in the VisualizationView.
	 * Shows the ImageView and hides the tableView
	 */
	@Override
	public void setAsActiveElement(VisualizationViewController vvc, TableView<Row> tableView, ImageView imageView) {
		vvc.imageViewSetDisable(false);
		vvc.tableViewSetDisable(true);
		loadData(tableView, imageView);
		
	}
	
	/**
	 * Gets the points from a list of shoppingtrips. Used when generating the heatmap
	 * @param shoppingTripList
	 * @return List<Points>
	 */
	private List<Point> getPoints(List<ShoppingTrip> shoppingTripList) {
		List<Point> points = new ArrayList<>();
		for (ShoppingTrip shoppingTrip : shoppingTripList) {
			for (Coordinate coordinate : shoppingTrip.getCoordinates()) {
				points.add(new Point(coordinate.getX(), coordinate.getY()));
			}
		}
		return points;
	}
	
	/**
	 * Validation method. Throws Exception if object is not List
	 * @param object An object that should be an instance of List<Shopping> trip. 
	 * @return List<ShoppingTrip>
	 */
	private List<ShoppingTrip> objectIsList(Object object) {
		return (List<ShoppingTrip>) object;
	}

}

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


public class VisualizationHeatMap extends VisualizationElement {
	
	private HeatMap map;

	public VisualizationHeatMap(String name, List<ShoppingTrip> shoppingTripList) {
		super(name);
		this.map = new HeatMap();
		map.addSpots(getPoints(shoppingTripList));
	}

	@Override
	public void setData(Object object) {
		List<ShoppingTrip> shoppingTripList = objectIsList(object);
		map.addSpots(getPoints(shoppingTripList));
	}

	@Override
	public HeatMap getData() {
		// TODO Auto-generated method stub
		return this.map;
	}

	@Override
	public void loadData(TableView<Row> tableView, ImageView imageView) {
		imageView.setImage(this.map.getImage());
	}

	@Override
	public void setAsActiveElement(VisualizationViewController vvc, TableView<Row> tableView, ImageView imageView) {
		vvc.imageViewSetDisable(false);
		vvc.tableViewSetDisable(true);
		loadData(tableView, imageView);
		
	}
	
	private List<Point> getPoints(List<ShoppingTrip> shoppingTripList) {
		List<Point> points = new ArrayList<>();
		for (ShoppingTrip shoppingTrip : shoppingTripList) {
			for (Coordinate coordinate : shoppingTrip.getCoordinates()) {
				points.add(new Point(coordinate.getX(), coordinate.getY()));
			}
		}
		return points;
	}
	
	private List<ShoppingTrip> objectIsList(Object object) {
		return (List<ShoppingTrip>) object;
	}

}

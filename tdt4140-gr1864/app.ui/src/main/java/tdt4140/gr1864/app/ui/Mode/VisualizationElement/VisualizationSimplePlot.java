package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import java.util.Date;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.TableView;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Transform;
import tdt4140.gr1864.app.core.Coordinate;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.ui.globalUIModel.VisualizationViewController;

public class VisualizationSimplePlot extends VisualizationElement {
	private Canvas canvas;
	private GraphicsContext ctx;
	
	private static int ARROW_SIZE = 2;
	
	public VisualizationSimplePlot(String name, List<ShoppingTrip> shoppingTrips) {
		super(name);
		
		canvas = new Canvas(180, 100);
		ctx = canvas.getGraphicsContext2D();
		
		ctx.setStroke(Color.BLACK);
		ctx.setStroke(Color.BLACK);
		
		setData(shoppingTrips);
	}

	private void drawArrow(int x1, int y1, int x2, int y2) {
	    double dx = x2 - x1, dy = y2 - y1;
	    double angle = Math.atan2(dy, dx);
	    int len = (int) Math.sqrt(dx * dx + dy * dy);
	    Transform transform = Transform.translate(x1, y1);
	    transform = transform.createConcatenation(Transform.rotate(Math.toDegrees(angle), 0, 0));
	    ctx.setTransform(new Affine(transform));
	    ctx.strokeLine(0, 0, len, 0);
	    ctx.fillPolygon(new double[]{len, len - ARROW_SIZE, len - ARROW_SIZE, len}, new double[]{0, -ARROW_SIZE, ARROW_SIZE, 0}, 4);
	}
	
	@Override
	public void setData(Object shoppingTrips) {
		ctx.clearRect(0, 0, 160, 100);
		
		Coordinate previous = new Coordinate(0.0, 0.0, "0");
		
		for (ShoppingTrip shoppingTrip : (List<ShoppingTrip>) shoppingTrips) {
			for (Coordinate coordinate : shoppingTrip.getCoordinates()) {
				drawArrow((int) previous.getX(), (int) previous.getY(), (int) coordinate.getX(), (int) coordinate.getY());
				previous = coordinate;
			}
		}
	}

	@Override
	public void loadData(TableView<TableRow> tableView, ImageView imageView) {
		imageView.setImage(canvas.snapshot(null, null));
	}

	@Override
	public void setAsActiveElement(VisualizationViewController vvc, TableView<TableRow> tableView, ImageView imageView) {
		vvc.imageViewSetDisable(false);
		vvc.tableViewSetDisable(true);
		loadData(tableView, imageView);
	}

	@Override
	public Canvas getData() {
		return canvas;
	}
}

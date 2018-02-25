package tdt4140.gr1864.app.core;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/* Uses test-data.json for testing */
public class DataLoaderTest {
	
	String path;
	DataLoader loader;
	
	@Before
	public void setup() {
		loader = new DataLoader();
		path = "../../src/main/resources/test-data.json";
	}

	@Test
	public void testReadingFromJsonFileExpectNoException() {
		loader.loadData(path);
	}
	
	@Test
	public void testCoordinateLoadingFromFileExpectFirstCoordFromDataFile() {
		ShoppingTrip shoppingTrip = loader.loadData(path);
		List<Coordinate> coords = shoppingTrip.getCoordinates();
		Coordinate coord = coords.get(0);
		double expectedX = 8.622905145346992;
		double expectedY = 4.569762307274866;
		long expectedTime = 1519216783919L;

		Assert.assertEquals(expectedX, coord.getX(), 0);
		Assert.assertEquals(expectedY, coord.getY(), 0);
		Assert.assertEquals(expectedTime, coord.getTimeStamp());
	}
	
	@Test
	public void testActionLoadingFromFileExpectFirstActionFromDataFile() {
		ShoppingTrip shoppingTrip  = loader.loadData(path);
		List<Action> actions = shoppingTrip.getActions();
		Action action = actions.get(0);
		long expectedTime = 1519220923919L;
		long expectedType = 1L;
		long expectedProduct = 52L;
		
		Assert.assertEquals(expectedTime, action.getTimeStamp());
		Assert.assertEquals(expectedType, action.getActionType());
		Assert.assertEquals(expectedProduct, action.getProductID());
	}
}

package tdt4140.gr1864.app.core;

import java.util.List;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.database.TestDataLoader;

import org.junit.Assert;

public class ShoppingTripTest {
	
	static ShoppingTrip shoppingTrip;
	static TestDataLoader loader;
	
	@BeforeClass
	public static void setup() {
		loader = new TestDataLoader();
		shoppingTrip = loader.getTrip();
	}

	@Test
	public void testGetStartExpectFirstTimeFromTestDataFile() {
		long expectedTime = 1523367344888L;
		Assert.assertEquals(expectedTime, shoppingTrip.getStart());
	}
	
	@Test
	public void testGetEndExpectedLastTimeFromTestDataFile() {
		long expectedTime = 1523367353438L;
		Assert.assertEquals(expectedTime, shoppingTrip.getEnd());
	}
	
	@Test
	public void testCoordinateListContainsCorrectCoordinatesExpectedSameAsTestDataFile() {
		List<Coordinate> coords = shoppingTrip.getCoordinates();
		Coordinate coord = coords.get(1);
		double expectedX = 6.900839491066374; 
		double expectedY = 1.040854256948999;
		long expectedTime = 1523367344963L;
		
		Assert.assertEquals(expectedX, coord.getX(), 0);
		Assert.assertEquals(expectedY, coord.getY(), 0);
		Assert.assertEquals(expectedTime, coord.getTimeStamp());
	}

	@Test
	public void testActionListContainsCorrectActionsExpectedSameAsTestDataFile() {
		List<Action> actions = shoppingTrip.getActions();
		Action action = actions.get(1);
		long expectedTime = 1523367347513L;
		int expectedType = 0;
		int expectedProduct = 64;
		
		Assert.assertEquals(expectedTime, action.getTimeStamp());
		Assert.assertEquals(expectedType, action.getActionType());
		Assert.assertEquals(expectedProduct, (int)action.getProduct().getID());
	}
}
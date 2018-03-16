package tdt4140.gr1864.app.core;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1864.app.core.database.TestDataLoader;

import org.junit.Assert;

public class ShoppingTripTest {
	
	ShoppingTrip shoppingTrip;
	
	@Before
	public void setup() {
		TestDataLoader loader = new TestDataLoader();
		shoppingTrip = loader.getTrip();
	}

	@Test
	public void testGetStartExpectFirstTimeFromTestDataFile() {
		long expectedTime = 1520861376132L;
		Assert.assertEquals(expectedTime, shoppingTrip.getStart());
	}
	
	@Test
	public void testGetEndExpectedLastTimeFromTestDataFile() {
		long expectedTime = 1520893926132L;
		Assert.assertEquals(expectedTime, shoppingTrip.getEnd());
	}
	
	@Test
	public void testCoordinateListContainsCorrectCoordinatesExpectedSameAsTestDataFile() {
		List<Coordinate> coords = shoppingTrip.getCoordinates();
		Coordinate coord = coords.get(1);
		double expectedX = 7.12325463182378;
		double expectedY = 4.9002427944221205;
		long expectedTime = 1520861586132L;

		Assert.assertEquals(expectedX, coord.getX(), 0);
		Assert.assertEquals(expectedY, coord.getY(), 0);
		Assert.assertEquals(expectedTime, coord.getTimeStamp());
	}

	@Test
	public void testActionListContainsCorrectActionsExpectedSameAsTestDataFile() {
		List<Action> actions = shoppingTrip.getActions();
		Action action = actions.get(1);
		long expectedTime = 1520868726132L;
		int expectedType = 1;
		int expectedProduct = 18;
		
		Assert.assertEquals(expectedTime, action.getTimeStamp());
		Assert.assertEquals(expectedType, action.getActionType());
		Assert.assertEquals(expectedProduct, (int)action.getProduct().getID());
	}
}
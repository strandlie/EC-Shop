package tdt4140.gr1864.app.core;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1864.app.core.database.DataLoader;

import org.junit.Assert;

public class ShoppingTripTest {
	
	ShoppingTrip shoppingTrip;
	
	@Before
	public void setup() {
		DataLoader loader = new DataLoader();
		shoppingTrip = loader.getTrip();
	}

	@Test
	public void testGetStartExpectFirstTimeFromTestDataFile() {
		long expectedTime = 1520861376132L;
		Assert.assertEquals(expectedTime, shoppingTrip.getStart());
	}
	
	@Test
	public void testGetEndExpectedLastTimeFromTestDataFile() {
		long expectedTime = 1519239823919L;
		Assert.assertEquals(expectedTime, shoppingTrip.getEnd());
	}
	
	@Test
	public void testCoordinateListContainsCorrectCoordinatesExpectedSameAsTestDataFile() {
		List<Coordinate> coords = shoppingTrip.getCoordinates();
		Coordinate coord = coords.get(1);
		double expectedX = 8.12313777180195;
		double expectedY = 4.330388562062142;
		long expectedTime = 1520861376132L;

		Assert.assertEquals(expectedX, coord.getX(), 0);
		Assert.assertEquals(expectedY, coord.getY(), 0);
		Assert.assertEquals(expectedTime, coord.getTimeStamp());
	}

	@Test
	public void testActionListContainsCorrectActionsExpectedSameAsTestDataFile() {
		List<Action> actions = shoppingTrip.getActions();
		Action action = actions.get(1);
		long expectedTime = 1520865366132L;
		int expectedType = 1;
		int expectedProduct = 44;
		
		Assert.assertEquals(expectedTime, action.getTimeStamp());
		Assert.assertEquals(expectedType, action.getActionType());
		Assert.assertEquals(expectedProduct, (int)action.getProduct().getID());
	}
}
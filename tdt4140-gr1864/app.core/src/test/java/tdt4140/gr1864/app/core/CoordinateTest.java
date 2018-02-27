package tdt4140.gr1864.app.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class CoordinateTest {

	Coordinate coord;

	@Before
	public void setup() {
		coord = new Coordinate(1.0, 2.0, "123");
	}
	
	@Test
	public void testGetXExpectOne() {
		Assert.assertEquals(1.0, coord.getX(), 0);
	}
	
	@Test
	public void testGetYExpectTwo() {
		Assert.assertEquals(2.0, coord.getY(), 0);
	}
	
	@Test
	public void testGetTimeStampExpectOneHundredTwentyThree() {
		Assert.assertEquals(123L, coord.getTimeStamp());
	}
}

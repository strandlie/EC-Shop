package tdt4140.gr1864.app.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ActionTest {
	
	Action action;
	
	@Before
	public void setup() {
		Product p1 = new Product("Name", 10.0);
		action = new Action("1", 1, p1);
	}
	
	@Test
	public void testGetTimeStampExpectOne() {
		Assert.assertEquals(1L, action.getTimeStamp());
	}
	
	@Test
	public void testGetActionTypeExpectOne() {
		Assert.assertEquals(1, action.getActionType());
	}
	
	@Test
	public void testGetProductIdExpectedTwo() {
		Assert.assertEquals("Name", action.getProduct().getName());
		Assert.assertEquals(10.0, action.getProduct().getPrice(), 0);
	}
}

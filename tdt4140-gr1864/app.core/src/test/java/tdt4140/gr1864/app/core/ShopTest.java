package tdt4140.gr1864.app.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1864.app.core.storage.Shop;

public class ShopTest {
	
	Shop shop;
	Shop shop2;
	
	@Before
	public void setup() {
		shop = new Shop("Kings Road 2");
		shop2 = new Shop("Kings Road 4", 1);
	}
	
	@Test
	public void testGetAddressExpectKingsRoadTwo() {
		String expected = "Kings Road 2";
		Assert.assertEquals(expected, shop.getAddress());
	}
	
	@Test
	public void testSetAddressExpectKingsRoadThree() {
		String expected = "Kings Road 3";
		shop.setAdress(expected);
		Assert.assertEquals(expected, shop.getAddress());
	}
	
	@Test
	public void testGetIdExpectOne() {
		int expected = 1;
		Assert.assertEquals(expected, shop2.getShopID());
	}
}

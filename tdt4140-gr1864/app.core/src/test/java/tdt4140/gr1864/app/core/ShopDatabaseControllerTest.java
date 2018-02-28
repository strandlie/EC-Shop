package tdt4140.gr1864.app.core;

import static org.junit.Assert.*;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1864.app.core.storage.Shop;
import tdt4140.gr1864.app.core.storage.ShopDatabaseController;

public class ShopDatabaseControllerTest {
	
	Shop shop;
	ShopDatabaseController sdc;
	
	@Before
	public void setup() {
		shop = new Shop("Kings Road 2", 10);
		sdc = new ShopDatabaseController();
	}

	@Test
	public void testCreateExpectPresistedObject() {
		Shop expected = shop;
		int id = sdc.create(shop);
		Shop newShop = sdc.retrieve(id);
		Assert.assertEquals(shop.getAddress(), newShop.getAddress());
		Assert.assertEquals(shop.getZip(), newShop.getZip());
	}

}

package tdt4140.gr1864.app.core;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.storage.Shop;
import tdt4140.gr1864.app.core.storage.ShopDatabaseController;

public class ShopDatabaseControllerTest {
	
	Shop shop;
	ShopDatabaseController sdc;
	
	@BeforeClass
	public static void createDatabase() throws IOException {
		DatabaseWiper viper = new DatabaseWiper();
		viper.wipe();
	}
	
	@Before
	public void setup() throws IOException {
		sdc = new ShopDatabaseController();
		shop = new Shop("Kings Road 2", 10);
	}

	@Test
	public void testCreateExpectPersistedObject() {
		Shop newShop = new Shop("Kings Road 2", 10, sdc.create(shop));
		
		Assert.assertEquals(shop.getAddress(), newShop.getAddress());
		Assert.assertEquals(shop.getZip(), newShop.getZip());
	}
	
	@Test
	public void testRetrieveExepctedPersistedObject() {
		Shop shop3 = new Shop("Kings Road 2", 10, sdc.create(shop));
		Shop newShop = (Shop) sdc.retrieve(shop3.getShopID());
		
		Assert.assertEquals(shop3.getAddress(), newShop.getAddress());
		Assert.assertEquals(shop3.getZip(), newShop.getZip());
	}
	
	@Test
	public void testDeleteExpectedNull() {
		Shop shop4 = new Shop("Kings Road 2", 10, sdc.create(shop));
		sdc.delete(shop4.getShopID());
		
		Assert.assertEquals(null, sdc.retrieve(shop4.getShopID()));
	}
	
	@Test
	public void testUpdateExpectNewlyPresistedObject() {
		shop = new Shop(shop.getAddress(), shop.getZip(), sdc.create(shop));
		shop = new Shop("Kings Road 3", 11, shop.getShopID());
		sdc.update(shop);
		Shop newShop = sdc.retrieve(shop.getShopID());
		
		Assert.assertEquals("Kings Road 3", newShop.getAddress());
		Assert.assertEquals(11, newShop.getZip());
	}
}

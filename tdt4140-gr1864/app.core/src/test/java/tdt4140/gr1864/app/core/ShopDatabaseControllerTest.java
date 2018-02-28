package tdt4140.gr1864.app.core;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
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
		Path path = Paths.get("database.db");
		
		if (! Files.exists(path)) {
			CreateDatabase.main(null);
		}
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
	
	/*
	 * Deleting database after running test
	 */
	@AfterClass
	public static void finish() {
		Path path = Paths.get("database.db");
		try {
		    Files.delete(path);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
	}

}

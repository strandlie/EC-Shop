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
import tdt4140.gr1864.app.core.storage.Product;
import tdt4140.gr1864.app.core.storage.OnShelfDatabaseController;

public class OnShelfDatabaseControllerTest {
	Shop shop;
	Product product;
	OnShelfDatabaseController osdc;
	
	
	//Set up a DB for use in the tests
	@BeforeClass
	public static void createDatabase() throws IOException {
		Path path = Paths.get("database.db");
		
		if (! Files.exists(path)) {
			CreateDatabase.main(null);
		}
	}

	
	@Before
	public void setup() throws IOException {
		osdc = new OnShelfDatabaseController();
		
		product = new Product();
		product.setID(5);
		
		shop = new Shop("Kings Road 2", 7050, 16);
		shop.setAmountInShelfs(product.getID(), 7);
		shop.setAmountInStorage(product.getID(), 93);
	}
	
	
	@Test
	public void testCreateAndRetrieveExcpectSuccess() {
		osdc.create(shop, product);
		Shop retrievedShop = osdc.retrieve(shop, product);
		
		Assert.assertEquals(7, retrievedShop.getAmountInShelfs(product.getID()));
		Assert.assertEquals(93, retrievedShop.getAmountInStorage(product.getID()));
		Assert.assertEquals(16, retrievedShop.getShopID());
	}
	
	@Test
	public void testUpdateExcpectSuccess() {
		osdc.create(shop, product);
		shop.setAmountInShelfs(product.getID(), 10);
		shop.setAmountInStorage(product.getID(), 90);
		osdc.update(shop, product);
		Shop retrievedShop = osdc.retrieve(shop, product);
		
		Assert.assertEquals(10, retrievedShop.getAmountInShelfs(product.getID()));
		Assert.assertEquals(90, retrievedShop.getAmountInStorage(product.getID()));
		Assert.assertEquals(16, retrievedShop.getShopID());
	}
	
	
	@Test
	public void testDeleteExcpectNull() {
		osdc.create(shop, product);
		osdc.delete(shop.getShopID(), product.getID());
		Shop retrievedShop = osdc.retrieve(shop, product);
		
		Assert.assertEquals(null, retrievedShop);
	}
	
	
	//Delete the DB
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

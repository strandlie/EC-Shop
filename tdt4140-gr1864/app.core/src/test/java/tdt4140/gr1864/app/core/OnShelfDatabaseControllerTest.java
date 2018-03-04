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
import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.storage.OnShelfDatabaseController;

public class OnShelfDatabaseControllerTest {
	Shop shop, shop2;
	Product product, product2;
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
		
		product = new Product(15, "MocProduct", 22);
		product2 = new Product(23, "Potato", 555);
		
		shop = new Shop("Kings Road 2", 7050, 16);
		shop.setAmountInShelfs(product.getID(), 7);
		shop.setAmountInStorage(product.getID(), 93);
		
		shop2 = new Shop("Somwhere", 7060, 22);
		shop2.setAmountInShelfs(product2.getID(), 66);
		shop2.setAmountInStorage(product2.getID(), 44);
	}
	
	
	@Test
	public void testCreateAndRetrieveExcpectSuccess() {
		osdc.create(shop, product);
		Shop retrievedShop = osdc.retrieve(shop, product);

		Assert.assertEquals(shop.getAmountInShelfs(product.getID()), retrievedShop.getAmountInShelfs(product.getID()));
		Assert.assertEquals(shop.getAmountInStorage(product.getID()), retrievedShop.getAmountInStorage(product.getID()));
		Assert.assertEquals(shop.getShopID(), retrievedShop.getShopID());
	}
	
	@Test
	public void testUpdateExcpectSuccess() {
		osdc.create(shop2, product2);
		shop2.setAmountInShelfs(product2.getID(), 10);
		shop2.setAmountInStorage(product2.getID(), 90);
		osdc.update(shop2, product2);
		Shop retrievedShop = osdc.retrieve(shop2, product2);
		
		Assert.assertEquals(shop2.getAmountInShelfs(product2.getID()), retrievedShop.getAmountInShelfs(product2.getID()));
		Assert.assertEquals(shop2.getAmountInStorage(product2.getID()), retrievedShop.getAmountInStorage(product2.getID()));
		Assert.assertEquals(shop2.getShopID(), retrievedShop.getShopID());
	}
	
	@Test
	public void testDeleteExcpectNull() {
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
		    System.out.println("DB deleted");
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

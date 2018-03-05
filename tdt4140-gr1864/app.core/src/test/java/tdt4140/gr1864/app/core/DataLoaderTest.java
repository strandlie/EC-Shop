package tdt4140.gr1864.app.core;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.storage.OnShelfDatabaseController;
import tdt4140.gr1864.app.core.storage.Shop;

/* Uses test-data.json for testing */
public class DataLoaderTest {
	
	String pathToShoppingTrip;
	String pathToProducts;
	DataLoader loader;
	ProductDatabaseController pdc;

	ShoppingTrip trip;
	List<Coordinate> coords;
	List<Product> products;
	List<Action> actions;
	
	/*
	 * Setting up database before running tests
	 * Checks for occurence of database before creating one
	 */
	@BeforeClass
	public static void setupDatabase() throws IOException {
		Path path = Paths.get("database.db");
		
		if (! Files.exists(path)) {
			CreateDatabase.main(null);
		}
	}
	
	@Before
	public void setupDataloader() {
		loader = new DataLoader();
		pdc = new ProductDatabaseController();
		coords = loader.getCoordinates();
		products = loader.getProducts();
		actions = loader.getActions();
		trip = loader.getTrip();
	}

	
	@Test
	public void testCoordinateLoadingFromFileExpectFirstCoordFromDataFile() {
		Coordinate coord = coords.get(0);
		double expectedX = 8.622905145346992;
		double expectedY = 4.569762307274866;
		long expectedTime = 1519216783919L;

		Assert.assertEquals(expectedX, coord.getX(), 0);
		Assert.assertEquals(expectedY, coord.getY(), 0);
		Assert.assertEquals(expectedTime, coord.getTimeStamp());
	}
	
	@Test
	public void testGroceryLoadingFromFileExcpectFirstGroceryFromDataFile() {
		Product prod = products.get(0);
		String expectedName = "Pork - Back, Long Cut, Boneless";
		double expectedPrice = 0.55;

		Assert.assertEquals(expectedName, prod.getName());
		Assert.assertEquals(expectedPrice, prod.getPrice(), 0);
	}
	
	@Test
	public void testActionLoadingFromFileExpectFirstActionFromDataFile() {
		Action action = actions.get(0);
		long expectedTime = 1519220923919L;
		int expectedType = 1;
		int expectedProduct = 52;
		
		Assert.assertEquals(expectedTime, action.getTimeStamp());
		Assert.assertEquals(expectedType, action.getActionType());
		Assert.assertEquals(expectedProduct, (int) action.getProduct().getID());
	}
	
	@Test
	public void testProductsOnSHelf(){
		Shop shop = loader.getShop();
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		
		//Assert.assertEquals(shop.get, actual);
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

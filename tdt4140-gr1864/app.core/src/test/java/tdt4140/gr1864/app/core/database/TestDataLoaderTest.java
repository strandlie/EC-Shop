package tdt4140.gr1864.app.core.database;

import java.io.IOException;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.database.TestDataLoader;
import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.Coordinate;
import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DatabaseWiper;
import tdt4140.gr1864.app.core.databasecontrollers.OnShelfDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;;

/* Uses test-data.json for testing */
public class TestDataLoaderTest {
	
	static String pathToShoppingTrip;
	static String pathToProducts;
	static TestDataLoader loader;
	static ProductDatabaseController pdc;

	static ShoppingTrip trip;
	static List<Coordinate> coords;
	static List<Product> products;
	static List<Action> actions;
	
	/*
	 * Setting up database before running tests
	 * Checks for occurence of database before creating one
	 */
	@BeforeClass
	public static void setupDatabase() throws IOException {
		DatabaseWiper viper = new DatabaseWiper();
		viper.wipe();
		loader = new TestDataLoader();
		pdc = new ProductDatabaseController();
		coords = loader.getCoordinates();
		products = loader.getProducts();
		actions = loader.getActions();
		trip = loader.getTrip();
	}
	
	@Test
	public void testCoordinateLoadingFromFileExpectFirstCoordFromDataFile() {
		Coordinate coord = coords.get(0);
		double expectedX = 5.988600917321573;
		double expectedY = 1.6073608851323462;
		long expectedTime = 1523367344888L;

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
		long expectedTime = 1523367346313L;
		int expectedType = 1;
		int expectedProduct = 55;
		
		Assert.assertEquals(expectedTime, action.getTimeStamp());
		Assert.assertEquals(expectedType, action.getActionType());
		Assert.assertEquals(expectedProduct, (int) action.getProduct().getID());
	}
	
	@Test
	public void testProductsOnSHelf(){
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		int productID = products.get(0).getID();
		int amount = 5;
		Shop shop = loader.getShop();
		shop.setAmountInShelfs(productID, amount);
		osdc.create(shop, productID);
		Shop testShop = new Shop("lol", 101, shop.getID());
		Shop retrievedShop = osdc.retrieve(testShop, productID);
		
		Assert.assertEquals(shop.getAmountInShelfs(productID), retrievedShop.getAmountInShelfs(productID));
	}
}

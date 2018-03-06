package tdt4140.gr1864.app.core;

import java.io.IOException;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.storage.Shop;
import tdt4140.gr1864.app.core.storage.OnShelfDatabaseController;;

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
		DatabaseWiper viper = new DatabaseWiper();
		viper.wipe();
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
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		int productID = products.get(0).getID();
		Shop shop = loader.getShop();
		Shop testShop = new Shop("lol", 101, shop.getShopID());
		Shop retrievedShop = osdc.retrieve(testShop, productID);
		
		Assert.assertEquals(shop.getAmountInShelfs(productID), retrievedShop.getAmountInShelfs(productID));
		
	}

}

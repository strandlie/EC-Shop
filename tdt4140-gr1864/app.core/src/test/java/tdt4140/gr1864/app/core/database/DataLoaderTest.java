package tdt4140.gr1864.app.core.database;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.Coordinate;
import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CoordinateDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.OnShelfDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;

public class DataLoaderTest {

	private final String[] NAMES = {	"Reina Novoa",
										"Dyan Linkous",
										"Kenyatta Horning",
										"August Eastwood",
										"Kasi Fredricks",
										"Sherilyn Salamanca",
										"Jean Oslund",
										"Maxima Hargreaves",
										"Isabell Jarrell",
										"Debbi Fuquay" };
	
	@BeforeClass
	public static void setupDatabase() throws IOException {
		DatabaseWiper wiper  = new DatabaseWiper();
		wiper.wipe();
		DataLoader.main(null);
	}
	
	@Test
	public void testCustomersAreLoadedExpectTenCustomers() {
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		for (int i = 1; i < 11; i++) {
			Customer c1 = cdc.retrieve(i);
			Assert.assertEquals(NAMES[i-1], c1.getFirstName() + " " + c1.getLastName());
		}
	}
	
	@Test
	public void testProductsAreLoadedExpectFirstProductFromFile() {
		ProductDatabaseController pdc = new ProductDatabaseController();
		String expectedName = "Pork - Back, Long Cut, Boneless";
		double expectedPrice = 0.55;

		Product p = pdc.retrieve(1);
		Assert.assertEquals(expectedName, p.getName());
		Assert.assertEquals(expectedPrice, p.getPrice(), 0);
	}
	
	@Test
	public void testCoordinateLoadingFromFileExpectFirstCoordFromDataFile() {
		CoordinateDatabaseController cdc = new CoordinateDatabaseController();
		Coordinate coord = cdc.retrieve(1, 1523367344888L);
		double expectedX = 5.988600917321573;
		double expectedY = 1.6073608851323462;
		long expectedTime = 1523367344888L;

		Assert.assertEquals(expectedX, coord.getX(), 0);
		Assert.assertEquals(expectedY, coord.getY(), 0);
		Assert.assertEquals(expectedTime, coord.getTimeStamp());
	}

	@Test
	public void testActionLoadingFromFileExpectFirstActionFromDataFile() {
		ActionDatabaseController adc = new ActionDatabaseController();
		Action action = adc.retrieve(1, 1523367346313L);
		long expectedTime = 1523367346313L;
		int expectedType = 1;
		int expectedProduct = 55;
		
		Assert.assertEquals(expectedTime, action.getTimeStamp());
		Assert.assertEquals(expectedType, action.getActionType());
		Assert.assertEquals(expectedProduct, (int) action.getProduct().getID());
	}
	
	@Test
	public void testOnShelfLoadingFromFileExpectFirstProductFromDataFile() {
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		ShopDatabaseController sdc = new ShopDatabaseController();
		Shop shop = sdc.retrieve(1);
		int expected_shop_id = 1;
		int expected_product_id = 2;
		int expected_amount_in_shelfs;
	}
	
	@Test
	public void testLoadShoppingtripExpectNewShoppingTripInDatabase() throws FileNotFoundException, IOException, ParseException {
		String path = "../../src/main/resources/test-data.json";
		String relativePath;
		//Finds path by getting URL and converting to URI and then to path 
		try {
			URI rerelativeURI = getClass().getClassLoader().getResource(".").toURI();
			relativePath = Paths.get(rerelativeURI).toFile().toString() + "/";
			
		} catch (URISyntaxException e1) {
			//If fail to convert to URI use URL path instead
			relativePath = getClass().getClassLoader().getResource(".").getPath();
		}
		// Read JSON from file
		FileReader fr = new FileReader(relativePath+path);
		BufferedReader reader = new BufferedReader(fr);		
		String json = "";
		String line;
		while ((line = reader.readLine()) != null) {
				json += line;
			}	
		reader.close();
		ShoppingTrip trip = DataLoader.loadShoppingTrip(json);

		Assert.assertEquals(2, trip.getID());
	}
}	

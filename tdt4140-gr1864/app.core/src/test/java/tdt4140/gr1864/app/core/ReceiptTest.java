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


public class ReceiptTest {
	
	Receipt receipt;
	
	ShoppingTrip shoppingTrip;

	@BeforeClass
	public static void createDatabase() throws IOException {
		Path path = Paths.get("database.db");
		
		if (! Files.exists(path)) {
			CreateDatabase.main(null);
		}
	}
	
	@Before
	public void setup() {
		// Load the test shopping trip data.
		DataLoader loader = new DataLoader();
		String path = "../../src/main/resources/test-data.json";
		shoppingTrip = loader.loadShoppingTrips(path);
		
		receipt = new Receipt(shoppingTrip);
	}
	
	@Test
	public void testGetShoppingTrip() {
		Assert.assertEquals(shoppingTrip, receipt.getShoppingTrip());
	}
	
	@Test
	public void testGetPrices() {
		boolean allOne = true;

		for (Integer count : receipt.getInventory().values()) {
			if (count != 1) {
				allOne = false;
				break;
			}
		}
		
		Assert.assertTrue(allOne);
	}
	
	@Test
	public void testGetTotalPrice() {
		Assert.assertEquals(33.32, receipt.getTotalPrice(), 10e-5);
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

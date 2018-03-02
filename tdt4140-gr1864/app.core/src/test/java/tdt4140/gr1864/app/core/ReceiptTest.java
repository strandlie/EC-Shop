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


public class ReceiptTest {
	
	private static Receipt receipt;
	
	private static ShoppingTrip shoppingTrip;

	@BeforeClass
	public static void setup() throws IOException {
		DataLoader loader = new DataLoader();
		shoppingTrip = loader.getTrip();
		receipt = new Receipt(shoppingTrip);
	}
	
	@Test
	public void testGetShoppingTrip() {
		Assert.assertEquals(shoppingTrip, receipt.getShoppingTrip());
	}
	
	@Test
	public void testGetInventory() {
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

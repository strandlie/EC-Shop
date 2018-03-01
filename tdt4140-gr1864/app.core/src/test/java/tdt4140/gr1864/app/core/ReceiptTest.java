package tdt4140.gr1864.app.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ReceiptTest {
	
	Receipt receipt;
	
	ShoppingTrip shoppingTrip;
	
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
}

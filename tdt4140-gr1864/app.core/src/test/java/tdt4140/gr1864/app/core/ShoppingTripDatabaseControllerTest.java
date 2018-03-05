
package tdt4140.gr1864.app.core;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.storage.Shop;
import tdt4140.gr1864.app.core.storage.ShopDatabaseController;

public class ShoppingTripDatabaseControllerTest {
	
	ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
	CustomerDatabaseController cdc = new CustomerDatabaseController();
	ShopDatabaseController sdc = new ShopDatabaseController();
	ShoppingTrip t1, t2, t3;
	Customer c1, c2, c3;
	Shop s1, s2, s3;

	@BeforeClass
	public static void createDatabase() throws IOException {
		Path path = Paths.get("database.db");
		
		if (! Files.exists(path)) {
			CreateDatabase.main(null);
		}
	}
	
	@Before
	public void setup() {
		s1 = new Shop("Kings Road 2", 10);
		s1 = new Shop(s1.getAddress(),s1.getZip(), sdc.create(s1));

		c1 = new Customer("Ola", "Normann");
		c1 = new Customer(c1.getFirstName(), c1.getLastName(), cdc.create(c1));
		
		c2 = new Customer("Kari", "Hansen");
		c2 = new Customer(c2.getFirstName(), c2.getLastName(), cdc.create(c2));

		t1 = new ShoppingTrip(c1, s1, true);
		List<ShoppingTrip> trips = new ArrayList<ShoppingTrip>();
		trips.add(t1);
		c1.setShoppingTrips(trips);		
	}

	@Test
	public void testCreateExpectPresistedObject() {
		t2 = new ShoppingTrip(stdc.create(t1), t1.getCustomer(), t1.getShop(), true);
		Assert.assertEquals(t1.getCustomer().getUserId(), t2.getCustomer().getUserId());
		Assert.assertEquals(t1.getShop().getShopID(), t2.getShop().getShopID());
		
	}
	
	@Test
	public void testRetrieveExpectPersistedObject() {
		t2 = new ShoppingTrip(stdc.create(t1), t1.getCustomer(), t1.getShop(), true);
		t3 = (ShoppingTrip) stdc.retrieve(t2.getShoppingTripID());
		
		Assert.assertEquals(t2.getCustomer().getUserId(), t3.getCustomer().getUserId());
		Assert.assertEquals(t2.getCustomer().getUserId(), t3.getCustomer().getUserId());
		
	}
	
	@Test
	public void testDeleteExpectNull() {
		ShoppingTrip t3 = new ShoppingTrip(c1, s1, true);
		t3 = new ShoppingTrip(stdc.create(t3), t3.getCustomer(), t3.getShop(), true);
		stdc.delete(t3.getShoppingTripID());
		
		Assert.assertEquals(null, stdc.retrieve(t3.getShoppingTripID()));
	}
	
	@Test
	public void testUpdateExpectNewlyPresistedObject() {
		t1 = new ShoppingTrip(stdc.create(t1), c1, s1, true);
		t1 = new ShoppingTrip(t1.getShoppingTripID(), c2, s1, true);
		stdc.update(t1);
		t2 = stdc.retrieve(t1.getShoppingTripID());
		
		Assert.assertEquals(c2.getUserId(), t2.getCustomer().getUserId());;
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
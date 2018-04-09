
package tdt4140.gr1864.app.core.databasecontroller;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DatabaseWiper;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class ShoppingTripDatabaseControllerTest {
	
	ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
	CustomerDatabaseController cdc = new CustomerDatabaseController();
	ShopDatabaseController sdc = new ShopDatabaseController();
	static DatabaseWiper viper = new DatabaseWiper();
	ShoppingTrip t1, t2, t3;
	Customer c1, c2, c3;
	Shop s1, s2, s3;

	@BeforeClass
	public static void createDatabase() throws IOException {
		viper = new DatabaseWiper();
		viper.wipe();
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
		t2 = new ShoppingTrip(c1, s1, true);
		t3 = new ShoppingTrip(c2, s1, true);
	}

	@Test
	public void testCreateExpectPresistedObject() {
		t2 = new ShoppingTrip(stdc.create(t1), t1.getCustomer(), t1.getShop(), true, true);
		Assert.assertEquals(t1.getCustomer().getID(), t2.getCustomer().getID());
		Assert.assertEquals(t1.getShop().getID(), t2.getShop().getID());
		Assert.assertEquals(true, t2.getAnonymous());

	}
	
	@Test
	public void testRetrieveExpectPersistedObject() {
		t2 = new ShoppingTrip(stdc.create(t1), t1.getCustomer(), t1.getShop(), true, false);
		t3 = (ShoppingTrip) stdc.retrieve(t2.getID());
		
		Assert.assertEquals(t2.getCustomer().getID(), t3.getCustomer().getID());
		Assert.assertEquals(t2.getShop().getID(), t3.getShop().getID());
		Assert.assertEquals(false, t3.getAnonymous());
	}
	
	@Test
	public void testDeleteExpectNull() {
		ShoppingTrip t3 = new ShoppingTrip(c1, s1, true);
		t3 = new ShoppingTrip(stdc.create(t3), t3.getCustomer(), t3.getShop(), true, false);
		stdc.delete(t3.getID());

		Assert.assertEquals(null, stdc.retrieve(t3.getID()));
	}
	
	@Test
	public void testUpdateExpectNewlyPresistedObject() {
		t1 = new ShoppingTrip(stdc.create(t1), c1, s1, true);
		t1 = new ShoppingTrip(t1.getID(), c2, s1, true);

		stdc.update(t1);
		t2 = stdc.retrieve(t1.getID());
		
		Assert.assertEquals(c2.getID(), t2.getCustomer().getID());
	}
	
	@Test
	public void testRetrieveAllShoppingTripsExpectSizeEqualOne() {
		List<ShoppingTrip> trips = stdc.retrieveAllShoppingTrips();
		Assert.assertEquals(2, trips.size());
			
	}
	
	@Test 
	public void testRetrieveAllShoppingTripsExpectCustomerShoppingTrips() {
		t1 = new ShoppingTrip(stdc.create(t1), c1, s1, true);
		t1 = new ShoppingTrip(t1.getID(), c2, s1, true);

		stdc.update(t1);
		t2 = stdc.retrieve(t1.getID());
		
		List<ShoppingTrip> customerTrips = stdc.retrieveAllShoppingTripsForCustomer(c2.getID());		
		List<ShoppingTrip> expectedTrips = Arrays.asList(t1);
		
		boolean wrongList = true;
		
		for (ShoppingTrip trip : customerTrips) {
			for (ShoppingTrip exTrip : expectedTrips) {
				if (trip.getCustomer().getID() == exTrip.getCustomer().getID() && 
						trip.getCharged() == exTrip.getCharged() && 
						trip.getID() == exTrip.getID() && 
						trip.getShop().getID() == exTrip.getShop().getID()) {
					wrongList = false;
				}
			}
		}
		
		Assert.assertEquals(false, wrongList);
	}
	
	@Test
	public void testRetrieveAllShoppingTripsExpectAllShoppingTrips() {
		t1 = stdc.retrieve(1);
		
		List<ShoppingTrip> expectedTrips = Arrays.asList(t1);
	
		List<ShoppingTrip> trips = stdc.retrieveAllShoppingTrips();
		
		boolean wrongList = true;
		
		for (ShoppingTrip trip : trips) {
			for (ShoppingTrip exTrip : expectedTrips) {
				if (trip.getCustomer().getID() == exTrip.getCustomer().getID() && 
						trip.getCharged() == exTrip.getCharged() && 
						trip.getID() == exTrip.getID() && 
						trip.getShop().getID() == exTrip.getShop().getID()) {
					wrongList = false;
				}
			}
		}

		Assert.assertEquals(false, wrongList);
	}
	
	@Test
	public void wTestRetrieveAllShoppingTripsForCustomerExpectSizeEqualOne() {
		t1 = new ShoppingTrip(stdc.create(t1), c1, s1, true);	
		List<ShoppingTrip> customerTrips = stdc.retrieveAllShoppingTripsForCustomer(c1.getID());

		Assert.assertEquals(1, customerTrips.size());
	}
}

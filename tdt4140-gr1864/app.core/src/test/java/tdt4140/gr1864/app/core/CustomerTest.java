package tdt4140.gr1864.app.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import tdt4140.gr1864.app.core.database.DatabaseWiper;
import tdt4140.gr1864.app.core.database.TestDataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class CustomerTest {

	ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
	CustomerDatabaseController cdc = new CustomerDatabaseController();
	ProductDatabaseController pdc = new ProductDatabaseController();
	ShopDatabaseController sdc = new ShopDatabaseController();
	ActionDatabaseController adc = new ActionDatabaseController();
	static TestDataLoader tdl;
	static DatabaseWiper viper = new DatabaseWiper();
	ShoppingTrip t1, t2, t3;
	Customer c1, c2, c3;
	Product p1, p2, p3;
	Shop s1;
	Action a1, a2;
	
	@BeforeClass
	public static void createDatabase() {
		viper = new DatabaseWiper();
		tdl = new TestDataLoader();
		viper.wipe();
	}
	
	public void setup() {
		s1 = new Shop("Kings Road 2", 10);
		s1 = new Shop(s1.getAddress(),s1.getZip(), sdc.create(s1));

		c1 = new Customer("Ola", "Nordmann");
		c1 = new Customer(c1.getFirstName(), c1.getLastName(), cdc.create(c1));
		
		c2 = new Customer("Kari", "Hansen");
		c2 = new Customer(c2.getFirstName(), c2.getLastName(), cdc.create(c2));

		c3 = new Customer("Nils", "Nordmann");
		c3 = new Customer(c3.getFirstName(), c3.getLastName(), cdc.create(c3));
		
		t1 = new ShoppingTrip(c1, s1, true);
		t2 = new ShoppingTrip(c2, s1, true);
		t3 = new ShoppingTrip(c3, s1, true);

		p1 = new Product("Produt1", 1.0);
		p1 = new Product(pdc.create(p1), p1.getName(), p1.getPrice());
		p2 = new Product("Produt2", 2.0);
		p2 = new Product(pdc.create(p2), p2.getName(), p2.getPrice());
		p3 = new Product("Produt3", 3.0);
		p3 = new Product(pdc.create(p3), p3.getName(), p3.getPrice());
		
		t1 = new ShoppingTrip(c1, s1, true);
		t1 = new ShoppingTrip(stdc.create(t1), c1, s1, true, false);
		List<ShoppingTrip> trips = new ArrayList<ShoppingTrip>();
		trips.add(t1);
		c1.setShoppingTrips(trips);		
		cdc.update(c1);

		a1 = new Action("1", 1, p1, t1);
		adc.create(a1);
		a1 = new Action("2", 1, p2, t1);
		adc.create(a1);
		a1 = new Action("3", 1, p2, t1);
		adc.create(a1);
		a1 = new Action("4", 1, p3, t1);
		adc.create(a1);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#Customer(int, java.lang.String, java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	public void testCustomer() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertNotEquals(c1, null);
	}
	
	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		c2 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(c1.hashCode(), c2.hashCode());
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		c2 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(c1, c2);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#getID()}.
	 */
	@Test
	public void testGetID() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(c1.getID(), 1);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setCustomerId(int)}.
	 */
	@Test
	public void testSetID() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		c1.setCustomerID(2);
		assertEquals(c1.getID(), 2);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#getFirstName()}.
	 */
	@Test
	public void testGetFirstName() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(c1.getFirstName(), "Roger");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setFirstName(java.lang.String)}.
	 */
	@Test
	public void testSetFirstName() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		c1.setFirstName("Benny");
		assertEquals(c1.getFirstName(), "Benny");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#getLastName()}.
	 */
	@Test
	public void testGetAddress() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		assertEquals(c1.getAddress(), "NTNU");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetAddress() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		c1.setAddress("UiB");
		assertEquals(c1.getAddress(), "UiB");
	}

	@Test
	public void testGetZip() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		assertEquals(c1.getZip(), 7047);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetZip() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		c1.setZip(0001);
		assertEquals(c1.getZip(), 0001);
	}

	@Test
	public void testGetGender() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		assertEquals(c1.getGender(), "Unspecified");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetGender() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		c1.setGender("Male");
		assertEquals(c1.getGender(), "Male");
	}

	@Test
	public void testGetAge() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		assertEquals(c1.getAge(), 44);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetAge() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		c1.setAge(50);
		assertEquals(c1.getAge(), 50);
	}

	@Test
	public void testGetLastName() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		assertEquals(c1.getLastName(), "Len");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetLastName() {
		c1 = new Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, 2);
		c1.setLastName("Knudsen");
		assertEquals(c1.getLastName(), "Knudsen");
	}
	
	@Test
	public void testGiveRecommendationBoughtExpectProductIDEqualOne() {
		viper.wipe();
		setup();
		int expectedProductID = 1;
		c1.giveRecommendation();
		Assert.assertEquals(expectedProductID, c1.getRecommendedProductID());
	}
	
	@Test
	public void testGiveRecommendationWhenNotBoughtExpectMostPopularEqualTwo() {
		viper.wipe();
		setup();
		int expectedProductID = 2;
		c2.giveRecommendation();
		Assert.assertEquals(expectedProductID, c2.getRecommendedProductID());
	}

	@Test
	public void testGiveRecommendationWhenCustomerAreAnonymousExpectProductIDEqualsMostPopularEqualsOne() {
		viper.wipe();
		setup();
		int expectedProductID = 1;
		c1.setAnonymous(true);
		c1.giveRecommendation();
		Assert.assertEquals(expectedProductID, c1.getRecommendedProductID());
	}
	
	@Test
	public void testGiveRecommendationWhenShoppingTripsAreAnonymousExpectProductIDEqualZero() {
		viper.wipe();
		setup();
		int expectedProductID = 0;
		t1.setAnonymous(true);
		stdc.update(t1);
		c2.giveRecommendation();
		Assert.assertEquals(expectedProductID, c2.getRecommendedProductID());
	}
	
	@Test
	public void testGetStatisticsForAmountBoughtExpectSortedList() {
		viper.wipe();
		setup();
		
		List<ProductAmount> actualArray = c1.getStatisticsForAmountBought();
		
		List<ProductAmount> expectedArray = new ArrayList<>();
		// index for max value = 2 (in DB), maxamount = 2
		ProductAmount pa1 = new ProductAmount(2, pdc.retrieve(2));

		// index for max value = 1 (in DB), maxamount = 1
		ProductAmount pa2 = new ProductAmount(1, pdc.retrieve(1));

		// index for max value = 3 (in DB), maxamount = 1
		ProductAmount pa3 = new ProductAmount(1, pdc.retrieve(3));
		
		expectedArray.add(pa1);
		expectedArray.add(pa2);
		expectedArray.add(pa3);
		
		boolean correctList = true;
		for (int i = 0; i < 3; i++) {
			if (expectedArray.get(i).getAmount() != actualArray.get(i).getAmount() || 
				expectedArray.get(i).getProduct().getID() != actualArray.get(i).getProduct().getID()) {
				correctList = !correctList;
			}
		}
		
		Assert.assertEquals(true, correctList);
	}
}

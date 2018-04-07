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
	
	@BeforeClass
	public static void createDatabase() {
		viper = new DatabaseWiper();
		tdl = new TestDataLoader();
		viper.wipe();
	}
	
	@Before
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
		t2 = new ShoppingTrip(c1, s1, true);
		t3 = new ShoppingTrip(c2, s1, true);
		
		p1 = new Product("chicken", 12);
		p2 = new Product("beef", 32);
		p3 = new Product("fish", 122);
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
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#getUserId()}.
	 */
	@Test
	public void testGetUserId() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(c1.getUserId(), 1);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setUserId(int)}.
	 */
	@Test
	public void testSetUserId() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		c1.setUserId(2);
		assertEquals(c1.getUserId(), 2);
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
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		assertEquals(c1.getAddress(), "NTNU");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetAddress() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		c1.setAddress("UiB");
		assertEquals(c1.getAddress(), "UiB");
	}

	@Test
	public void testGetZip() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		assertEquals(c1.getZip(), 7047);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetZip() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		c1.setZip(0001);
		assertEquals(c1.getZip(), 0001);
	}

	@Test
	public void testGetGender() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		assertEquals(c1.getGender(), "Unspecified");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetGender() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		c1.setGender("Male");
		assertEquals(c1.getGender(), "Male");
	}

	@Test
	public void testGetAge() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		assertEquals(c1.getAge(), 44);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetAge() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		c1.setAge(50);
		assertEquals(c1.getAge(), 50);
	}

	@Test
	public void testGetLastName() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		assertEquals(c1.getLastName(), "Len");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetLastName() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		c1.setLastName("Knudsen");
		assertEquals(c1.getLastName(), "Knudsen");
	}

	@Test
	public void testGetNumberOfPersonsInHousehold() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		assertEquals(c1.getNumberOfPersonsInHousehold(), 3);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetNumberOfPersonsInHousehold() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44, 3, true);
		c1.setNumberOfPersonsInHousehold(1);
		assertEquals(c1.getNumberOfPersonsInHousehold(), 1);
	}

	@Test
	public void testGiveRecommendationWhenBought() {
		t1 = new ShoppingTrip(stdc.create(t1), c1, s1, true, false);
		t1 = new ShoppingTrip(t1.getShoppingTripID(), c2, s1, true, false);
		t1.setActions(tdl.getActions());
		stdc.update(t1);
		
		t2 = new ShoppingTrip(stdc.create(t2), c3, s1, true, false);
		t2 = new ShoppingTrip(t2.getShoppingTripID(), c3, s1, true, false);
		t2.setActions(tdl.getActions());
		stdc.update(t2);
		
		/*
		t3 = new ShoppingTrip(stdc.create(t3), c3, s1, true);
		t3 = new ShoppingTrip(t3.getShoppingTripID(), c3, s1, true);
		stdc.update(t3);
		t3 = stdc.retrieve(t3.getShoppingTripID());
		*/
		
		System.out.println(stdc.retrieve(t1.getShoppingTripID()).getActions().size() + " t1's trip amount of actions");

		
		System.out.println(c1.giveRecommendation() + " recommendation for c1");
		
		System.out.println(c2.giveRecommendation() + " recommendation for c2");
		
		//Assert.assertEquals(1, c2.getRecommendedProductID());
	}
	
	@Test
	public void testGiveRecommendationWhenNotBought() {
		System.out.println(c3.giveRecommendation() + " recommendation for c3 not bought");
	}
}

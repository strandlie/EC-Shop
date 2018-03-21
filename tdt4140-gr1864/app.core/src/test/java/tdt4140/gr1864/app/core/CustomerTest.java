/**
 * 
 */
package tdt4140.gr1864.app.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import tdt4140.gr1864.app.core.database.TestDataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class CustomerTest {

	private static TestDataLoader loader;
	private static ShoppingTrip trip;
	private static Customer c1;
	private static Customer c2;
	
	@BeforeClass
	public static void setup() {
		loader = new TestDataLoader();
		trip = loader.getTrip();
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
	public void testGetLastName() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(c1.getLastName(), "Nilsen");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetLastName() {
		c1 = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		c1.setLastName("Knudsen");
		assertEquals(c1.getLastName(), "Knudsen");
	}
	
	@Test
	public void testGiveRecommendationWhenNotBought() {
		//ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		
		//System.out.println(c1.giveRecommendation());
	}
	
	@Test
	public void testGiveRecommendationWhenBought() {
		//c1 = new Customer(1, "Karen", "Sommerville", trips);
	}
}

/**
 * 
 */
package tdt4140.gr1864.app.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import org.junit.*;

/**
 * @author anders
 *
 */
public class CustomerTest {

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#Customer(int, java.lang.String, java.lang.String, java.util.ArrayList)}.
	 */
	@Test
	public void testCustomer() {
		Customer a = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertNotEquals(a, null);
	}
	
	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#hashCode()}.
	 */
	@Test
	public void testHashCode() {
		Customer a = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		Customer b = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(a.hashCode(), b.hashCode());
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#equals(java.lang.Object)}.
	 */
	@Test
	public void testEqualsObject() {
		Customer a = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		Customer b = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(a, b);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#getUserId()}.
	 */
	@Test
	public void testGetUserId() {
		Customer a = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(a.getUserId(), 1);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setUserId(int)}.
	 */
	@Test
	public void testSetUserId() {
		Customer a = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		a.setUserId(2);
		assertEquals(a.getUserId(), 2);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#getFirstName()}.
	 */
	@Test
	public void testGetFirstName() {
		Customer a = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(a.getFirstName(), "Roger");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setFirstName(java.lang.String)}.
	 */
	@Test
	public void testSetFirstName() {
		Customer a = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		a.setFirstName("Benny");
		assertEquals(a.getFirstName(), "Benny");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#getLastName()}.
	 */
	@Test
	public void testGetLastName() {
		Customer a = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		assertEquals(a.getLastName(), "Nilsen");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetLastName() {
		Customer a = new Customer(1, "Roger", "Nilsen", new ArrayList<ShoppingTrip>());
		a.setLastName("Knudsen");
		assertEquals(a.getLastName(), "Knudsen");
	}
}
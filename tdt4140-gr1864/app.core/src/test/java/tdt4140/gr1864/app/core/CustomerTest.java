package tdt4140.gr1864.app.core;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.*;

import tdt4140.gr1864.app.core.database.DatabaseWiper;
import tdt4140.gr1864.app.core.database.TestDataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class CustomerTest {

	ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
	CustomerDatabaseController cdc = new CustomerDatabaseController();
	ProductDatabaseController pdc = new ProductDatabaseController();
	ShopDatabaseController sdc = new ShopDatabaseController();
	static DatabaseWiper viper = new DatabaseWiper();
	ShoppingTrip t1, t2, t3;
	Customer c1, c2, c3;
	Product p1, p2, p3;
	Shop s1;
	
	List<Action> actions;
	List<Coordinate> coords;
	
	@BeforeClass
	public static void createDatabase() {
		viper = new DatabaseWiper();
		//tdl = new TestDataLoader();
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
		
		actions = new ArrayList<>();
		coords = new ArrayList<>();
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
				"Unspecified", 44,  3);
		assertEquals(c1.getAddress(), "NTNU");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetAddress() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		c1.setAddress("UiB");
		assertEquals(c1.getAddress(), "UiB");
	}

	@Test
	public void testGetZip() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		assertEquals(c1.getZip(), 7047);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetZip() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		c1.setZip(0001);
		assertEquals(c1.getZip(), 0001);
	}

	@Test
	public void testGetGender() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		assertEquals(c1.getGender(), "Unspecified");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetGender() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		c1.setGender("Male");
		assertEquals(c1.getGender(), "Male");
	}

	@Test
	public void testGetAge() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		assertEquals(c1.getAge(), 44);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetAge() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		c1.setAge(50);
		assertEquals(c1.getAge(), 50);
	}

	@Test
	public void testGetLastName() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		assertEquals(c1.getLastName(), "Len");
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetLastName() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		c1.setLastName("Knudsen");
		assertEquals(c1.getLastName(), "Knudsen");
	}

	@Test
	public void testGetNumberOfPersonsInHousehold() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		assertEquals(c1.getNumberOfPersonsInHousehold(), 3);
	}

	/**
	 * Test method for {@link tdt4140.gr1864.app.core.Customer#setLastName(java.lang.String)}.
	 */
	@Test
	public void testSetNumberOfPersonsInHousehold() {
		c1 = new Customer("Ben", "Len", 2, "NTNU", 7047,
				"Unspecified", 44,  3);
		c1.setNumberOfPersonsInHousehold(1);
		assertEquals(c1.getNumberOfPersonsInHousehold(), 1);
	}

	@Test
	public void testGiveRecommendationWhenNotBought() {
		/* work in progress
		Action a1 = new Action("4352364321", 1, new Product("soup", 12));
		Action a2 = new Action("4351642321", 2, new Product("beef", 32));
		Action a3 = new Action("4356412321", 3, new Product("chick", 11));
		
		actions.add(a1);
		actions.add(a2);
		actions.add(a3);
		
		Coordinate coord1 = new Coordinate(1, 2, "232135123");
		Coordinate coord2 = new Coordinate(2, 4, "232135234");
		
		coords.add(coord1);
		coords.add(coord2);
		
		t1 = new ShoppingTrip(coords, actions, 1);
		t1 = new ShoppingTrip(stdc.create(t1), c1, s1, true);
		t1 = new ShoppingTrip(t1.getShoppingTripID(), c2, s1, true);
		
		stdc.update(t1);
		t2 = stdc.retrieve(t1.getShoppingTripID());
		
		t3 = new ShoppingTrip(stdc.create(t3), c3, s1, true);
		t3 = new ShoppingTrip(t3.getShoppingTripID(), c3, s1, true);
		stdc.update(t3);
		t3 = stdc.retrieve(t3.getShoppingTripID());
		*/
		/*
		 * shopping_trip_id integer,
		 * timestamp varchar(255),
		 * action_type integer not null,
		 * product_id integer not null,
		 
		System.out.println(stdc.retrieve(t3.getShoppingTripID()).getActions().size() + " t3's trip amount of actions");
		
		System.out.println(c2.giveRecommendation());
		*/
		//Assert.assertEquals(1, c2.getRecommendedProductID());
	}
	
	@Test
	public void testGiveRecommendationWhenBought() {
		//c1 = new Customer(1, "Karen", "Sommerville", trips);
	}
}

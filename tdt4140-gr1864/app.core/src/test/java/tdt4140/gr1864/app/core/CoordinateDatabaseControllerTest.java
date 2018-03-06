package tdt4140.gr1864.app.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.storage.Shop;
import tdt4140.gr1864.app.core.storage.ShopDatabaseController;

public class CoordinateDatabaseControllerTest {
	
	CoordinateDatabaseController cdc = new CoordinateDatabaseController();
	ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
	CustomerDatabaseController customerdc = new CustomerDatabaseController();
	ShopDatabaseController sdc = new ShopDatabaseController();
	ProductDatabaseController pdc = new ProductDatabaseController();

	Coordinate c1, c2, c3;

	ShoppingTrip t1, t2;
	Customer customer1;
	Shop s1;

	Product p1, p2;

	@BeforeClass
	public static void createDatabase() throws IOException {
		DatabaseWiper viper = new DatabaseWiper();
		viper.wipe();
	}
	
	@Before
	public void setup() {
		s1 = new Shop("Kings Road 2", 10);
		s1 = new Shop(s1.getAddress(),s1.getZip(), sdc.create(s1));

		customer1 = new Customer("Ola", "Normann");
		customer1 = new Customer(customer1.getFirstName(), customer1.getLastName(), customerdc.create(customer1));
		
		p1 = new Product("Produt", 1.0);
		p1 = new Product(pdc.create(p1), p1.getName(), p1.getPrice());
		
		t1 = new ShoppingTrip(customer1, s1, true);
		t1 = new ShoppingTrip(stdc.create(t1), customer1, s1, true);
		List<ShoppingTrip> trips = new ArrayList<ShoppingTrip>();
		trips.add(t1);
		customer1.setShoppingTrips(trips);		

		c1 = new Coordinate(1, 1, "1", t1);
	}

	@Test
	public void testCreateExpectPresistedObject() {
		c1 = new Coordinate(
				c1.getX(),
				c1.getY(),
				Long.toString(c1.getTimeStamp()),
				stdc.retrieve(cdc.create(c1))
			);

		Assert.assertEquals(t1.getShoppingTripID(), c1.getShoppingTrip().getShoppingTripID());
	}
	
	@Test
	public void testRetrieveExpecetPersistedObject() {
		c1 = new Coordinate(
				c1.getX(),
				c1.getY(),
				Long.toString(c1.getTimeStamp()),
				stdc.retrieve(cdc.create(c1))
			);

		c2 = cdc.retrieve(c1.getShoppingTrip().getShoppingTripID(), c1.getTimeStamp());
		
		Assert.assertEquals(c1.getShoppingTrip().getShoppingTripID(), c2.getShoppingTrip().getShoppingTripID());
		Assert.assertEquals(c1.getTimeStamp(), c2.getTimeStamp());
	}
	
	@Test
	public void testDeleteExpectNull() {
		c1 = new Coordinate(
				c1.getX(),
				c1.getY(),
				Long.toString(c1.getTimeStamp()),
				stdc.retrieve(cdc.create(c1))
			);
		cdc.delete(c1.getShoppingTrip().getShoppingTripID(), c1.getTimeStamp());
		
		Assert.assertEquals(null, cdc.retrieve(c1.getShoppingTrip().getShoppingTripID(), c1.getTimeStamp()));
	}
	
	@Test
	public void testUpdateExpectNewlyPresistedObject() {
		c1 = new Coordinate(
				c1.getX(),
				c1.getY(),
				Long.toString(c1.getTimeStamp()),
				stdc.retrieve(cdc.create(c1))
			);
	
		c1 = new Coordinate(
				0,
				0,
				Long.toString(c1.getTimeStamp()),
				c1.getShoppingTrip()
			);
		cdc.update(c1);
		c2 = cdc.retrieve(c1.getShoppingTrip().getShoppingTripID(), c1.getTimeStamp());
		
		Assert.assertEquals(c1.getX(), c2.getX(), 0);
		Assert.assertEquals(c1.getY(), c2.getY(), 0);
		Assert.assertEquals(c1.getTimeStamp(), c2.getTimeStamp());
		Assert.assertEquals(c1.getShoppingTrip().getShoppingTripID(), c2.getShoppingTrip().getShoppingTripID());
	}
}

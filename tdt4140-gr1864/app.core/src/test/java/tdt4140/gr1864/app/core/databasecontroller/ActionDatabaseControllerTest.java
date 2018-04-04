package tdt4140.gr1864.app.core.databasecontroller;

import java.io.IOException;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DatabaseWiper;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class ActionDatabaseControllerTest {
	
	ActionDatabaseController adc = new ActionDatabaseController();
	ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
	CustomerDatabaseController cdc = new CustomerDatabaseController();
	ShopDatabaseController sdc = new ShopDatabaseController();
	ProductDatabaseController pdc = new ProductDatabaseController();

	Action a1, a2;

	ShoppingTrip t1, t2;
	Customer c1;
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

		c1 = new Customer("Ola", "Normann");
		c1 = new Customer(c1.getFirstName(), c1.getLastName(), cdc.create(c1));
		
		p1 = new Product("Produt", 1.0);
		p1 = new Product(pdc.create(p1), p1.getName(), p1.getPrice());
		
		t1 = new ShoppingTrip(c1, s1, true);
		t1 = new ShoppingTrip(stdc.create(t1), c1, s1, true);
		List<ShoppingTrip> trips = new ArrayList<ShoppingTrip>();
		trips.add(t1);
		c1.setShoppingTrips(trips);		

		a1 = new Action("1", 1, p1, t1);
	}

	@Test
	public void testCreateExpectPresistedObject() {
		a1 = new Action(
				Long.toString(a1.getTimeStamp()),
				a1.getActionType(), 
				a1.getProduct(),
				stdc.retrieve(adc.create(a1))
			);

		Assert.assertEquals(t1.getID(), a1.getShoppingTrip().getID());
	}
	
	@Test
	public void testRetrieveExpecetPersistedObject() {
		a1 = new Action(
				Long.toString(a1.getTimeStamp()),
				a1.getActionType(), 
				a1.getProduct(),
				stdc.retrieve(adc.create(a1))
			);
		a2 = adc.retrieve(a1.getShoppingTrip().getID(), a1.getTimeStamp());
		
		Assert.assertEquals(a1.getShoppingTrip().getID(), a2.getShoppingTrip().getID());
		Assert.assertEquals(a1.getTimeStamp(), a2.getTimeStamp());
	}
	
	@Test
	public void testDeleteExpectNull() {
		a1 = new Action(
				Long.toString(a1.getTimeStamp()),
				a1.getActionType(), 
				a1.getProduct(),
				stdc.retrieve(adc.create(a1))
			);
		adc.delete(a1.getShoppingTrip().getID(), a1.getTimeStamp());
		
		Assert.assertEquals(null, adc.retrieve(a1.getShoppingTrip().getID(), a1.getTimeStamp()));
	}
	
	@Test
	public void testUpdateExpectNewlyPresistedObject() {
		a1 = new Action(
				Long.toString(a1.getTimeStamp()),
				a1.getActionType(), 
				a1.getProduct(),
				stdc.retrieve(adc.create(a1))
			);
		a1 = new Action(
				Long.toString(a1.getTimeStamp()),
				0, 
				a1.getProduct(),
				a1.getShoppingTrip()
			);
		adc.update(a1);
		a2 = adc.retrieve(a1.getShoppingTrip().getID(), a1.getTimeStamp());
		
		Assert.assertEquals(a1.getActionType(), a2.getActionType());
		Assert.assertEquals(a1.getTimeStamp(), a2.getTimeStamp());
		Assert.assertEquals(a1.getShoppingTrip().getID(), a2.getShoppingTrip().getID());
	}
}

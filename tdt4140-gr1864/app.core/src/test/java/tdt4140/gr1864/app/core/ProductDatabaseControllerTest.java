package tdt4140.gr1864.app.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;


public class ProductDatabaseControllerTest {
	
	ProductDatabaseController pdc;
	Product p1;
	Product p2;
	
	@Before
	
	public void setup() {
		pdc = new ProductDatabaseController();
		p1 = new Product("tomatoes", 9.23);
		p2 = new Product("beef", 50.5);
	}
	
	@Test
	public void testCreateAndRetrieveExpectCreatedProduct1() {
		int id = pdc.create(p1);
		Product storedP1 = (Product) pdc.retrieve(id);
		Assert.assertEquals(p1.getName(), storedP1.getName());
		Assert.assertEquals(p1.getPrice(), storedP1.getPrice(), 0);
	}
	
	@Test
	public void testCreateAndRetrieveExpectCreatedProduct2() {
		int id2 = pdc.create(p2);
		Product storedP2 = (Product) pdc.retrieve(id2);
		Assert.assertEquals(p2.getName(), storedP2.getName());
		Assert.assertEquals(p2.getPrice(), storedP2.getPrice(), 0);
	}
}

package tdt4140.gr1864.app.core;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class ProductDatabaseControllerTest {
	
	ProductDatabaseController pdc;
	Product p1;
	Product p2;
	Product p3;
	
	@BeforeClass
	public static void setup() throws IOException {
		DatabaseWiper viper = new DatabaseWiper();
		viper.wipe();
	}
	
	@Before
	public void setupProducts() {
		pdc = new ProductDatabaseController();
		p1 = new Product("tomatoes", 9.23);
		p2 = new Product("beef", 50.5);
		p3 = new Product("cucumber", 10.0);
	}
	
	@Test
	public void AtestCreateAndRetrieveExpectCreatedProduct() {
		p1 = new Product(pdc.create(p1), "tomatoes", 9.23);
		Product storedP1 = (Product) pdc.retrieve(p1.getID());
		Assert.assertEquals(p1.getName(), storedP1.getName());
		Assert.assertEquals(p1.getPrice(), storedP1.getPrice(), 0);
	}
	
	@Test
	public void BtestCreateAndRetrieveExpectCreatedProduct2() {
		p2 = new Product(pdc.create(p2), "beef", 50.5);
		Product storedP2 = (Product) pdc.retrieve(p2.getID());
		Assert.assertEquals(p2.getName(), storedP2.getName());
		Assert.assertEquals(p2.getPrice(), storedP2.getPrice(), 0);
	}
	
	@Test
	public void CtestCreateAndUpdateExpectUpdatedProduct() {
		p2 = new Product(pdc.create(p2), p2.getName(), p2.getPrice());
		p2 = new Product(p2.getID(), p3.getName(), p3.getPrice());
		pdc.update(p2);
		Product updatedP2 = (Product) pdc.retrieve(p2.getID());

		Assert.assertEquals(p2.getID(), updatedP2.getID());
		Assert.assertEquals(p3.getName(), updatedP2.getName());
		Assert.assertEquals(p3.getPrice(), updatedP2.getPrice(), 0);
	}
	
	@Test
	public void DtestDeleteExpectNull() {
		p1 = new Product(pdc.create(p1), p1.getName(), p1.getPrice());
		pdc.delete(p1.getID());
		Assert.assertEquals(pdc.retrieve(p1.getID()), null);
	}
}

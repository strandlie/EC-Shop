package tdt4140.gr1864.app.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {

	Product product;
	Product product2;
	
	@Before
	public void setup() {
		product = new Product("tomatoes", 9.23);
		product2 = new Product(1, "tomatoes", 9.23);
	}
	
	@Test
	public void testGetNameExcpectTomatoes() {
		Assert.assertEquals("tomatoes", product.getName());
	}
	
	@Test
	public void testGetPriceExcpectNinePointTwentyThree() {
		Assert.assertEquals(9.23, product.getPrice(), 0);
	}
	
	@Test
	public void testGetProductIDExcpectOne() {
		Assert.assertEquals(1, (int) product2.getID());
	}
}

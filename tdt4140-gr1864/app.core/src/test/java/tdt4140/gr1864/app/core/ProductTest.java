package tdt4140.gr1864.app.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ProductTest {
	
	Product product;
	
	@Before
	public void setup() {
		product = new Product("tomatoes", 9.23);
	}
	
	@Test
	public void testGetNameExcpectTomatoes() {
		Assert.assertEquals("tomatoes", product.getName());
	}
	
	@Test
	public void testGetPriceExcpectNinePointTwentyThree() {
		Assert.assertEquals(9.23, product.getPrice(), 0);
	}
}

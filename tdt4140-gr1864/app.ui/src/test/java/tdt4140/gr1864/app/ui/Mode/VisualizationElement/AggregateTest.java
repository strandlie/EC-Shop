package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class AggregateTest {
	
	Row row4;
	Row row2;
	Row row1;
	
	@Before
	public void setup() {
		this.row4 = new Row("Bolle", "1", "2", "1");
		this.row2 = new Row("Bille", "200", "onShelves");
		this.row1 = new Row("Banan", "60", "onShelves");
	}
	
	@Test
	public void testAggregateHasExpectedProductName() {
		Assert.assertEquals("Bolle", this.row4.getProductName());
		Assert.assertEquals("Bille", this.row2.getProductName());
	}
	
	@Test
	public void testAggregateHasExpectedNumberOfPickUp() {
		Assert.assertEquals("1", this.row4.getNumberOfPickUp());
	}
	
	@Test
	public void testAggregateHasExpectedNumberOfPutDowns() {
		Assert.assertEquals("2", this.row4.getNumberOfPutDown());
	}
	
	@Test
	public void testAggregateHasExpectedNumberOfPurchases() {
		Assert.assertEquals("1", this.row4.getNumberOfPurchases());
	}
	
	@Test
	public void testAggregateHasExpectedSize() {
		Assert.assertEquals(4, this.row4.getSize());
		Assert.assertEquals(2, this.row2.getSize());
	}
	
	@Test
	public void testAggregateHasExpectedNumberInStock() {
		Assert.assertEquals("200", this.row2.getNumberInStock());
	}
	
	@Test
	public void testAggregateDoesNotHaveUnapplicableFields() {
		Assert.assertNull(this.row2.getNumberOfPickUp());
		Assert.assertNull(this.row4.getNumberInStock());
	}
	
	@Test
	public void testAggregateHasExpectedNumberOnSHelves() {
		Assert.assertEquals("60", row1.getNumberOnShelves());
	}
}

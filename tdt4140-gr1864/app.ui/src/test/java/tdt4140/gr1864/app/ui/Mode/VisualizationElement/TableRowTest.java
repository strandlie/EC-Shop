package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class TableRowTest {
	
	MostPickedUpTableRow aggregate4;
	InStockTableRow aggregate2;
	
	@Before
	public void setup() {
		this.aggregate4 = new MostPickedUpTableRow("Bolle", "1", "2", "1");
		this.aggregate2 = new InStockTableRow("Bille", "200");
		
	}
	
	@Test
	public void testAggregateHasExpectedProductName() {
		Assert.assertEquals("Bolle", this.aggregate4.getProductName());
		Assert.assertEquals("Bille", this.aggregate2.getProductName());
	}
	
	@Test
	public void testAggregateHasExpectedNumberOfPickUp() {
		Assert.assertEquals("1", this.aggregate4.getNumberOfPickUp());
	}
	
	@Test
	public void testAggregateHasExpectedNumberOfPutDowns() {
		Assert.assertEquals("2", this.aggregate4.getNumberOfPutDown());
	}
	
	@Test
	public void testAggregateHasExpectedNumberOfPurchases() {
		Assert.assertEquals("1", this.aggregate4.getNumberOfPurchases());
	}
	
	@Test
	public void testAggregateHasExpectedSize() {
		Assert.assertEquals(4, this.aggregate4.getSize());
		Assert.assertEquals(2, this.aggregate2.getSize());
	}
	
	@Test
	public void testAggregateHasExpectedNumberInStock() {
		Assert.assertEquals("200", this.aggregate2.getNumberInStock());
	}
}

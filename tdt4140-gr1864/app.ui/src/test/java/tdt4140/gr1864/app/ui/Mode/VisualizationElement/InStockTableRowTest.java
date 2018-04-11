package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class InStockTableRowTest {
	
	private InStockTableRow inStockRow;
	
	@Before
	public void setup() {
		this.inStockRow = new InStockTableRow("Bille", "200");
	}
	
	@Test
	public void testGetProductNameExpectBille() {
		Assert.assertEquals("Bille", this.inStockRow.getProductName());
	}
	
	@Test
	public void testGetSizeExpectSize() {
		Assert.assertEquals(2, this.inStockRow.getSize());
	}
	
	@Test
	public void testGetNumberInStockExpect200() {
		Assert.assertEquals("200", this.inStockRow.getNumberInStock());
	}

}

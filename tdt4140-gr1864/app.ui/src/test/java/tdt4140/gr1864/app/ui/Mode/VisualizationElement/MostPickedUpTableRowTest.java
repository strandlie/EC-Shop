package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class MostPickedUpTableRowTest {
	
	private MostPickedUpTableRow mostPickedUpRow;
	
	@Before
	public void setup() {
		this.mostPickedUpRow = new MostPickedUpTableRow("Bolle", "1", "2", "1");
	}
	
	@Test
	public void testGetProductNameExpectProductName() {
		Assert.assertEquals("Bolle", this.mostPickedUpRow.getProductName());
	}
	
	@Test
	public void testGetNumberOfPickupsExpectOne() {
		Assert.assertEquals("1", this.mostPickedUpRow.getNumberOfPickUp());
	}
	
	@Test
	public void testGetNumberOfPutDownExpectTwo() {
		Assert.assertEquals("2", this.mostPickedUpRow.getNumberOfPutDown());
	}
	
	@Test
	public void testGetNumberOfPurchasesExpectOne() {
		Assert.assertEquals("1", this.mostPickedUpRow.getNumberOfPurchases());
	}
	
	@Test
	public void testGetSizeExpectSize() {
		Assert.assertEquals(4, this.mostPickedUpRow.getSize());
	}
	
	@Test
	public void testSetNewNumberOfPutDown() {
		this.mostPickedUpRow.setNumberOfPutDown("3");
		
	}
}

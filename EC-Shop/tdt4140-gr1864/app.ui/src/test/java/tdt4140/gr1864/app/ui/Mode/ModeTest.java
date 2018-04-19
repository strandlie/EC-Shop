package tdt4140.gr1864.app.ui.Mode;

import org.junit.Before;
import org.junit.Test;


import org.junit.Assert;

import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;

public class ModeTest {
	Mode mode;
	VisualizationTable table;
	
	@Before
	public void setup() {
		table = new VisualizationTable("TestTable");
		mode = new Mode("Test", table);
	}
	
	@Test
	public void testVisualizationElementContainsTable() {
		Assert.assertEquals(this.table, this.mode.getVisualizationElement());
	}
	
	@Test
	public void testVisualizationTableHasName() {
		Assert.assertEquals("Test", this.mode.getName());
	}
	
	
	
}

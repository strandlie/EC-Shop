package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;

import org.junit.Assert;

public class VisualizationTableTest {
	
	VisualizationTable table;
	ArrayList<String> columnNames;
	
	
	@Before
	public void setup() {
		this.table = new VisualizationTable("TestTable");
		this.columnNames = new ArrayList<String>();
		this.columnNames.add("productName");
		this.columnNames.add("numberInStock");
		this.table.addColumns(columnNames);
	}
	
	@Test
	public void testTableCanAddValidColumn() {
		this.table.addColumn("numberOfPickUp");
		Assert.assertTrue(this.table.hasColumn("Number Of Pick Ups"));
	}
	
	@Test
	public void testTableCanNotAddInvalidColumn() {
		try {
			this.table.addColumn("notValidName");
		}
		catch (Exception e) {}
		
		Assert.assertFalse(this.table.hasColumn("Not valid"));
	}
	
	@Test
	public void testTableHasExpectedNumberOfColumns() {
		Assert.assertEquals(2, this.table.getSize());
		this.table.addColumn("numberOfPickUp");
		Assert.assertEquals(3, this.table.getSize());
	}
	
	@Test
	public void canAddData() {
		Assert.assertTrue(this.table.getData().size() == 0);
<<<<<<< HEAD
		Row row = new Row("Bolle", "1", "onShelves");
=======
		InStockTableRow agg = new InStockTableRow("Bolle", "1");
>>>>>>> sprint-3
		
		this.table.addData(row);
		Assert.assertEquals(row, this.table.getData().get(0));
	}
}

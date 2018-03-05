package tdt4140.gr1864.app.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1864.app.core.ActionDatabaseController;
import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.ShoppingTripDatabaseController;
import tdt4140.gr1864.app.core.storage.Shop;
import tdt4140.gr1864.app.ui.Mode.VisualizationElement.VisualizationTable;

public class TableLoaderTest {
	
	private TableLoader tableLoader;
	private List<ShoppingTrip> trips;
	private VisualizationTable table;
	private Map<Integer, Integer> productIDs;
	
	@Before
	public void setup() {
		Shop shop = new Shop("Gateveien 1", 203);
		Customer customer = new Customer("Mann", "Damesen");
		
		ActionDatabaseController adc = new ActionDatabaseController();
		
		ShoppingTrip s1 = new ShoppingTrip(customer, shop, true);
		s1.setActions(adc.retrieve(1));
		this.trips = new ArrayList<ShoppingTrip>();
		this.trips.add(s1);
		
		this.table = new VisualizationTable("TestTable");
		this.table.addColumn("productName");
		this.table.addColumn("numberOfPickUp");
		
		this.productIDs = new HashMap<Integer, Integer>();
		this.productIDs.put(1, 10);
		this.productIDs.put(2, 20);
	}
	
	@Test
	public void testMostPickedUpTableHasData() {
		Assert.assertEquals(0, this.table.getData().size());
		this.tableLoader = new TableLoader(this.trips, this.table);
		Assert.assertEquals(6, this.table.getData().size());
	}
	
	@Test
	public void testCantCreateInvalidTable() {
		this.tableLoader = new TableLoader(null, null, null);
		Assert.assertNull(this.tableLoader.getStock());
		this.tableLoader = new TableLoader(null, null);
		Assert.assertNull(this.tableLoader.getStock());
	}
	
	@Test
	public void testStockTableHasData() {
		Assert.assertEquals(0, this.table.getData().size());
		this.tableLoader = new TableLoader(productIDs, productIDs, this.table);
		Assert.assertEquals(2, this.table.getData().size());
	}
	
	

}

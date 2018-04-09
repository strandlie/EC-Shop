package tdt4140.gr1864.app.core.database;

import org.junit.Assert;
import org.junit.Test;

import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;

public class DBPathTest {

	@Test
	public void testDatabasePathExpectPathToSQLiteFile() {
		String actualPath = new DBPath().getPath();
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		
		Assert.assertEquals(cdc.dbPath, actualPath);
	}

}

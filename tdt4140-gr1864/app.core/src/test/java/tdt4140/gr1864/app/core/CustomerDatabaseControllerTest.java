package tdt4140.gr1864.app.core;

import static org.junit.Assert.*;

import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class CustomerDatabaseControllerTest {
	
	Customer c1;
	CustomerDatabaseController cdc;
	
	@BeforeClass
	public static void createDatabase() throws IOException {
		Path path = Paths.get("database.db");
		
		if (! Files.exists(path)) {
			CreateDatabase.main(null);
		}
	}

	@Before
	public void setup() {
		c1 = new Customer("Ola", "Normann");
		cdc = new CustomerDatabaseController();
	}
	
	@Test
	public void testCreateExpectPresistedObject() {
		Customer c2 = new Customer("Ola", "Normann", cdc.create(c1));
		
		Assert.assertEquals(c1.getFirstName(), c2.getFirstName());
		Assert.assertEquals(c1.getLastName(), c2.getLastName());
	}
	
	@Test
	public void testRetrieveExpectPresistedObject() {
		Customer c2 = new Customer("Ola", "Normann", cdc.create(c1));
		Customer c3 = (Customer) cdc.retrieve(c1.getUserId());
		
		Assert.assertEquals(c2.getFirstName(), c3.getFirstName());
		Assert.assertEquals(c2.getLastName(), c3.getLastName());
	}
	
	@Test
	public void testUpdateExpectedNewlyPresistedObject() {
		c1 = new Customer(c1.getFirstName(), c1.getLastName(), cdc.create(c1));
		c1 = new Customer("Kari", "Hansen", c1.getUserId());
		cdc.update(c1);
		Customer c2 = cdc.retrieve(c1.getUserId());
		
		Assert.assertEquals("Kari", c2.getFirstName());
		Assert.assertEquals("Hansen",  c2.getLastName());
	}
	
	@Test
	public void testDeleteExpectNull() {
		Customer c2 = new Customer("Ola", "Normann", cdc.create(c1));
		cdc.delete(c2.getUserId());
		
		Assert.assertEquals(null, cdc.retrieve(c2.getUserId()));
	}
	
	/*
	 * Deleting database after running test
	 */
	@AfterClass
	public static void finish() {
		Path path = Paths.get("database.db");
		try {
		    Files.delete(path);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
	}
}

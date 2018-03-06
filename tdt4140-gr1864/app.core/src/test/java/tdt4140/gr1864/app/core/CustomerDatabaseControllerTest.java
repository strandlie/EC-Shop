package tdt4140.gr1864.app.core;

import java.io.IOException;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerDatabaseControllerTest {
	
	Customer c0;
	CustomerDatabaseController cdc;

	@BeforeClass
	public static void createDatabase() throws IOException {
		DatabaseWiper viper = new DatabaseWiper();
		viper.wipe();
	}

	@Before
	public void setup() {
		c0 = new Customer("Ola", "Normann");
		cdc = new CustomerDatabaseController();
	}
	
	@Test
	public void AtestCreateExpectPresistedObject() {
		Customer c2 = new Customer("Ola", "Normann", cdc.create(c0));
		
		
		Assert.assertEquals(c0.getFirstName(), c2.getFirstName());
		Assert.assertEquals(c0.getLastName(), c2.getLastName());
	}
	
	@Test
	public void BtestRetrieveExpectPresistedObject() {
		Customer c2 = new Customer("Ola", "Normann", cdc.create(c0));
		Customer c3 = (Customer) cdc.retrieve(c2.getUserId());
		
		Assert.assertEquals(c2.getFirstName(), c3.getFirstName());
		Assert.assertEquals(c2.getLastName(), c3.getLastName());
	}
	
	@Test
	public void CtestUpdateExpectedNewlyPresistedObject() {
		Customer c1 = new Customer(c0.getFirstName(), c0.getLastName(), cdc.create(c0));
		c1 = new Customer("Kari", "Hansen", c1.getUserId());
		cdc.update(c1);
		Customer c2 = cdc.retrieve(c1.getUserId());
		
		Assert.assertEquals("Kari", c2.getFirstName());
		Assert.assertEquals("Hansen",  c2.getLastName());
	}
	
	@Test
	public void DtestDeleteExpectNull() {
		Customer c2 = new Customer("Ola", "Normann", cdc.create(c0));
		cdc.delete(c2.getUserId());
		
		Assert.assertEquals(null, cdc.retrieve(c2.getUserId()));
	}
}

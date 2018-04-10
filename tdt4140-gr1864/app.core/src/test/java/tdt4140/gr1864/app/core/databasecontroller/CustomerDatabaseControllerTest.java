package tdt4140.gr1864.app.core.databasecontroller;

import java.io.IOException;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.database.DatabaseWiper;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class CustomerDatabaseControllerTest {
	
	Customer c0;
	Customer extendedCustomer;
	CustomerDatabaseController cdc;

	@BeforeClass
	public static void createDatabase() throws IOException {
		DatabaseWiper viper = new DatabaseWiper();
		viper.wipe();
	}

	@Before
	public void setup() {
		c0 = new Customer("Ola", "Normann");
		extendedCustomer = new  Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true);
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
		Customer c3 = (Customer) cdc.retrieve(c2.getID());
		
		Assert.assertEquals(c2.getFirstName(), c0.getFirstName());
		Assert.assertEquals(c2.getLastName(), c0.getLastName());
	}
	
	@Test
	public void CtestUpdateExpectedNewlyPresistedObject() {
		Customer c1 = new Customer(c0.getFirstName(), c0.getLastName(), cdc.create(c0));
		c1 = new Customer("Kari", "Hansen", c1.getID());
		cdc.update(c1);
		Customer c2 = cdc.retrieve(c1.getID());
		
		Assert.assertEquals("Kari", c2.getFirstName());
		Assert.assertEquals("Hansen",  c2.getLastName());
		Assert.assertEquals(false, c2.getAnonymous());
	}
	
	@Test
	public void DtestDeleteExpectNull() {
		Customer c2 = new Customer("Ola", "Normann", cdc.create(c0));
		cdc.delete(c2.getID());
		
		Assert.assertEquals(null, cdc.retrieve(c2.getID()));
	}
	
	@Test
	public void EtestCountCustomerExpectFour() {
		new Customer("hans", "nordmann", cdc.create(c0));
		int countCustomer = cdc.countCustomers();
		Assert.assertEquals(4, countCustomer);
	}
	
	@Test
	public void FtestRetrieveAllExpectAllCustomers() {
		List<Customer> customers = cdc.retrieveAll();
		
		Assert.assertEquals("Ola", customers.get(0).getFirstName());
		Assert.assertEquals("Kari", customers.get(2).getFirstName());
	}
	
	@Test
	public void GtestCreateUserWithAllFields() {
		Customer c2 = new  Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, cdc.create(extendedCustomer));
		cdc.create(c2);
		
		Assert.assertEquals(c2.getFirstName(), "Ben");
		Assert.assertEquals(c2.getLastName(), "Len");
		Assert.assertEquals(c2.getAddress(), "NTNU");
		Assert.assertEquals(c2.getZip(), 7047);
		Assert.assertEquals(c2.getNumberOfPersonsInHousehold(), 3);
		Assert.assertEquals(c2.getGender(), "Unspecified");
		Assert.assertEquals(c2.getAge(), 44);
		Assert.assertEquals(c2.getNumberOfPersonsInHousehold(), 3);
		Assert.assertEquals(c2.getAnonymous(), true);
	}
	
	@Test
	public void HtestRetrieveUserWithAllFields() {
		Customer c1 = new  Customer("Ben", "Len", "NTNU", 7047,
				"Unspecified", 44, 3, true, cdc.create(extendedCustomer));

		Customer c2 = cdc.retrieve(c1.getID());
		Assert.assertEquals(c1.getFirstName(), c2.getFirstName());
		Assert.assertEquals(c1.getLastName(), c2.getLastName());
		Assert.assertEquals(c1.getAddress(), c2.getAddress());
		Assert.assertEquals(c1.getAge(), c2.getAge());
		Assert.assertEquals(c1.getNumberOfPersonsInHousehold(), c2.getNumberOfPersonsInHousehold());
		Assert.assertEquals(c1.getGender(), c2.getGender());
		Assert.assertEquals(c1.getAnonymous(), c2.getAnonymous());
	}
}

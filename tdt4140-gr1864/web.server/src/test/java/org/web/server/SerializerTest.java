package org.web.server;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.web.server.serializers.Serializer;

import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.database.TestDataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;

public class SerializerTest {
	
	@BeforeClass
	public static void setup() {
		new TestDataLoader();
	}

	@Test
	public void testSerializerWithCustomerExpectCorrectFirstName() throws FileNotFoundException {
		FileReader fr = new FileReader("customer.json");
		BufferedReader reader = new BufferedReader(fr);		
		Serializer sr = new Serializer();
		sr.serialize(reader, Customer.class, 1);
		Customer customer = (Customer) sr.getObject();

		CustomerDatabaseController cdc = new CustomerDatabaseController();
		customer = cdc.retrieve(1);

		Assert.assertEquals("fornavn", customer.getFirstName());
	}
	
	@Test
	public void testDeserializeCustomerExpectCorrectJson() throws IOException {
		Customer customer = new Customer();
		customer.setCustomerId(1);
		customer.setFirstName("fornavn");
		customer.setLastName("etternvan");
		customer.setAddress("addresse");
		customer.setZip(10);
		FileReader fr = new FileReader("customer-deserialize-test.json");
		BufferedReader reader = new BufferedReader(fr);		
		String expected = reader.readLine();
		reader.close();
		
		Assert.assertEquals(expected, new Serializer().deserialize(customer, customer.getClass()));
	}
}

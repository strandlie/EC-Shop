package tdt4140.gr1864.app.core;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import tdt4140.gr1864.app.core.storage.*;

/* This test also cover the functionality for Shelfs as they're both essentially the abstract class Container */
public class StorageTest {
	
	Storage storage;
	Product product;
	Product product2;
	
	@Before
	public void setup() {
		storage = new Storage();
		product = new Product();
		product2 = new Product();
		storage.addProducts(product, 5);
		storage.addProducts(product2, 6);
	}
	
	@Test
	public void testGetAmount() {
			Assert.assertEquals(5, storage.getAmount(product));
	}
	
	@Test
	public void testAddMore() {
			storage.addProducts(product, 3);
			Assert.assertEquals(8, storage.getAmount(product));
	}
	
	@Test
	public void testAddNegative() {
		storage.addProducts(product2, -5);
		Assert.assertEquals(6, storage.getAmount(product2));
	}
	
	@Test
	public void testRemove() {
		storage.removeProducts(product2, 2);
		Assert.assertEquals(4, storage.getAmount(product2));
	}
	
	@Test
	public void testDelete() {
		storage.removeProducts(product, 5);
		storage.delete(product);
		Assert.assertEquals(-1, storage.getAmount(product));
	}
}

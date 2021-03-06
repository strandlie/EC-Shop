package tdt4140.gr1864.app.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.database.DatabaseWiper;
import tdt4140.gr1864.app.core.databasecontrollers.OnShelfDatabaseController;

public class ShopTest {
	
	Shop shop;
	Shop shop2;
	Shop shop3;
	
	Receipt receipt;
	
	Map<Integer, Integer> inventory;
	
	Product p1, p2;
	
	OnShelfDatabaseController osdc;
	
	//Set up a DB for use in the tests
	@BeforeClass
	public static void createDatabase() throws IOException {
		DatabaseWiper viper = new DatabaseWiper();
		viper.wipe();
	}
	
	@Before
	public void setup() {
		shop = new Shop("Kings Road 2", 1020);
		shop2 = new Shop("Kings Road 4", 1020, 1);
		shop3 = new Shop("Somwhere inf", 4343, 0);
	}
	
	@Test
	public void testGetAddressExpectKingsRoadTwo() {
		String expected = "Kings Road 2";
		Assert.assertEquals(expected, shop.getAddress());
	}
	
	@Test
	public void testGetZipExpectedThousandTwenty() { 
		int expected = 1020;
		Assert.assertEquals(expected, shop.getZip());
	}
	
	@Test
	public void testSetZipExpectedTousandThirty() {
		int expected = 1030;
		shop.setZip(expected);
		Assert.assertEquals(expected, shop.getZip());
	}
	
	@Test
	public void testSetAddressExpectKingsRoadThree() {
		String expected = "Kings Road 3";
		shop.setAddress(expected);
		Assert.assertEquals(expected, shop.getAddress());
	}
	
	@Test
	public void testGetIdExpectOne() {
		int expected = 1;
		Assert.assertEquals(expected, shop2.getID());
	}
	
	
	@Test
	public void testSetAndGetProductInStorage() {
		int productID = 5;
		int amount = 23;
		shop.setAmountInStorage(productID, amount);
		
		Assert.assertEquals(23, shop.getAmountInStorage(productID));
	}
	
	@Test
	public void testSetAndGetProductInShelfs() {
		int productID = 5;
		int amount = 23;
		shop.setAmountInShelfs(productID, amount);
		
		Assert.assertEquals(23, shop.getAmountInShelfs(productID));
	}
	
	@Test
	public void testUpdateFromReceiptAndRefreshExpectSuccess() {
		p1 = new Product(1, "p1", 1);
		p2 = new Product(2, "p2", 2);
		
		inventory = new HashMap<>();
		inventory.put(p1.getID(), 5);
		inventory.put(p2.getID(), 10);
		
		receipt = new Receipt(inventory);
		
		shop.setAmountInShelfs(p1.getID(), 60);
		shop.setAmountInShelfs(p2.getID(), 30);
		
		osdc = new OnShelfDatabaseController();
		osdc.create(shop, p1.getID());
		osdc.create(shop, p2.getID());
		
		shop.updateAmountInShelfsFromReceipt(receipt);
		shop3 = shop3.refreshShop();
		
		
		Assert.assertEquals(55, shop3.getAmountInShelfs(p1.getID()));
		Assert.assertEquals(20, shop3.getAmountInShelfs(p2.getID()));
	}
	
	@Test
	public void testUpdateShopFromReceiptExpectSuccess() {
		p1 = new Product(1, "p1", 1);
		p2 = new Product(2, "p2", 2);
		
		inventory = new HashMap<>();
		inventory.put(p1.getID(), 5);
		inventory.put(p2.getID(), 10);
		receipt = new Receipt(inventory);
		
		shop.setAmountInShelfs(p1.getID(), 60);
		shop.setAmountInShelfs(p2.getID(), 30);
		
		shop.updateShopFromReceipt(receipt);
		
		
		Assert.assertEquals(55, shop.getAmountInShelfs(p1.getID()));
		Assert.assertEquals(20, shop.getAmountInShelfs(p2.getID()));
	}
}

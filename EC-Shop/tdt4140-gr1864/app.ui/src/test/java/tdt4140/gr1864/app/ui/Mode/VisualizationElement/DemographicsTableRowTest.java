package tdt4140.gr1864.app.ui.Mode.VisualizationElement;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class DemographicsTableRowTest {
	
	private DemographicsTableRow demographicsRow;

	@Before
	public void setup() {
		this.demographicsRow = new DemographicsTableRow(1, "Billy", "Marsvinsen", "Moose Road 5", 200, "Male", 32, 2);
	}
	
	@Test
	public void testGetCustomerIdExpectOne() {
		Assert.assertEquals("1", this.demographicsRow.getCustomerId());
	}
	
	@Test
	public void testGetFirstNameExpectBilly() {
		Assert.assertEquals("Billy", this.demographicsRow.getFirstName());
	}

	@Test
	public void testGetLastNameExpectMarsvinsen() {
		Assert.assertEquals("Marsvinsen", this.demographicsRow.getLastName());
	}
	
	@Test
	public void testGetAddressExpectMooseRoad5() {
		Assert.assertEquals("Moose Road 5", this.demographicsRow.getAddress());
	}
	
	@Test
	public void testGetZipExpect200() {
		Assert.assertEquals("200", this.demographicsRow.getZip());
	}
	
	@Test
	public void testGetGenderExpectMale() {
		Assert.assertEquals("Male", this.demographicsRow.getGender());
	}
	
	@Test
	public void testGetAgeExpect32() {
		Assert.assertEquals("32", this.demographicsRow.getAge());
	}
	
	@Test
	public void testGetNumOFPersonInHouseExpect2() {
		Assert.assertEquals("2", this.demographicsRow.getNumOfPersonInHouse());
	}

}

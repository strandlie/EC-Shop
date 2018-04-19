package tdt4140.gr1864.app.core.dataMocker;

import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.fasterxml.jackson.core.JsonProcessingException;

public class DataMockerTest {
	DataMocker mocker;
	
	@Before
	public void setup() {
		mocker = new DataMocker();
	}
	
	@Test
	public void testValidJSON() {
		try {
			String data = mocker.getRandomPathJSON();
			JSONParser parser = new JSONParser();
			parser.parse(data);
		} catch (ParseException | JsonProcessingException e) {
			Assert.fail();
		}
	}
}

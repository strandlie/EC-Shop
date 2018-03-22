package org.web.server;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.simple.JSONObject;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;

public class WebServerIT {
	
	@BeforeClass
	public static void setup() {
		DataLoader.main(null);
	}
	
	@Test
	public void testPostDemographics() {
		JSONObject json = new JSONObject();
		json.put("customerID", new Integer(7));
		json.put("address", "Kings Road 6");
		json.put("zip", new Integer(10));
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost("http://localhost:8080/api/v1/user");
		HttpResponse response2 = null;
		httpPost.addHeader("content-type", "application/json");
		try {
			httpPost.setEntity(new StringEntity(json.toString()));
			response2 = httpclient.execute(httpPost);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		try {
		    System.out.println(response2.getStatusLine());
		    HttpEntity entity2 = response2.getEntity();
		    EntityUtils.consume(entity2);
		} catch (Exception e) {
			e.printStackTrace();
		}	
		
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		Customer customer = cdc.retrieve(7);
		
		Assert.assertEquals("Kings Road 6", customer.getAddress());
		Assert.assertEquals(10, customer.getZip());
	}
}

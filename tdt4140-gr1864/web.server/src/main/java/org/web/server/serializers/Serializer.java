package org.web.server.serializers;

import java.io.BufferedReader;
import java.io.IOException;

import org.web.server.persistance.Persister;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import tdt4140.gr1864.app.core.Receipt;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.database.DataLoader;
import tdt4140.gr1864.app.core.interfaces.Model;

/**
 * Generic (de)serializer for web.server
 * @author vegarab
 */
public class Serializer {

	/* global object used for tests */
	private Model object;

	/**
	 * Serializes JSON data from reader into POJO. Does handle non-POJO models 
	 * based on the objects class. Further calls functionality in persistence-layer 
	 * through Persister-class based on method
	 * @param reader 	BufferedReader with JSON-data
	 * @param c			Class of object to serialize to
	 * @param method 	HTTP method
	 */
	@SuppressWarnings({ "rawtypes" })
	public void serialize(BufferedReader reader, Class c, int method) {
		
		/* For classes that needs more advanced serializing */
		switch(c.getName()) {
			case "Receipt": object = serializeReceipt(reader); break;
			case "ShoppingTrip": object = serializeShoppingTrip(reader); break;
			default: object = genericSerializer(reader, c); break;
		}
		Persister.persist(object, c, method);
	}
	
	/**
	 * Generic serializer that can serialize POJOs with Jackson annotations
	 * @param reader 	BufferedReader with JSON-data
	 * @param c			Class of object to serialize to 
	 * @return object
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public Model genericSerializer(BufferedReader reader, Class c) {
		object = null;
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			object = (Model) mapper.readValue(reader, c);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return object;
	}
	
	/**
	 * Deserializes POJO into JSON. Does handle non-POJO models based on
	 * the objects class.
	 * @param o 	object to serialize
	 * @param c    	Class of object
	 * @return JSON String
	 */
	@SuppressWarnings("rawtypes")
	public String deserialize(Model o, Class c) {

		/* For classes that needs more advanced deserializing */
		switch(c.getName()) {
			case Persister.RECEIPT: return deserializeReceipt((Receipt) o);
			case Persister.SHOPPING_TRIP: return deserializeShoppingTrip((ShoppingTrip) o);
		}

		ObjectMapper mapper = new ObjectMapper();
		String json = null;
		try {
			json = mapper.writeValueAsString(o);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return json;
	}

	private String deserializeShoppingTrip(ShoppingTrip trip) {
		// TODO Auto-generated method stub
		return null;
	}

	private String deserializeReceipt(Receipt receipt) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Calls functions to serialize ShoppingTrip with its trips and actions.
	 * app.core's DataLoader has this functionality, so it is used.
	 * @param reader	BufferedReader with JSON-data
	 */
	private Model serializeShoppingTrip(BufferedReader reader) {
		String json = new String();
		try {
			for (String line; (line = reader.readLine()) != null; json += line);
		} catch (IOException e) { e.printStackTrace(); }

		/* Properly persists JSON to Trips/Coordinates/Actions */
		ShoppingTrip trip = DataLoader.loadShoppingTrip(json);
		return trip;
	}

	private Model serializeReceipt(BufferedReader reader) {
		// TODO Auto-generated method stub
		return null;
	}
	
	/* Used by tests */
	public Model getObject() {
		return object;
	}
}

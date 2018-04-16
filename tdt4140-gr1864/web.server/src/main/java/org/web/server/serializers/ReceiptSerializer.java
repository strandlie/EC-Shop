package org.web.server.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.Receipt;

/**
 * A Jackson serializer module. This implements a custom serializer, which serializes
 * Receipt objects in an actually meaningful way. When this module is registered, you
 * can use Jackson as normally but have Receipts serialized nicely.
 * For usage, see the Serializer class, where this plugin is being used.
 */
public class ReceiptSerializer extends StdSerializer<Receipt> {
	
	private static final long serialVersionUID = 1L;


	/**
	 * Boilerplate constructors for Jackson.
	 */
	public ReceiptSerializer() {
		this(null);
	}
	
	public ReceiptSerializer(Class<Receipt> receipt) {
		super(receipt);
	}
 
	/**
	 * This method receives Receipt objects from Jackson and serializes them using a custom serializer.
	 * We manually control where JSON sub-objects start and close.
	 * @param receipt The Receipt being serialized.
	 * @param jsonGenerator The JSON generator, provides an API for generating JSON data.
	 * @param serializer The Serializer which uses this plugin.
	 */
    @Override
    public void serialize(Receipt receipt, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        // The root object.
    	jsonGenerator.writeStartObject();
    	
    	// The shopping trip start time.
        jsonGenerator.writeNumberField("date", receipt.getShoppingTrip().getEnd());
        
        // Whether the invoice has been paid or not.
        jsonGenerator.writeBooleanField("charged", receipt.getShoppingTrip().getCharged());
        
        // The total price of the shopping trip.
        jsonGenerator.writeNumberField("totalPrice", receipt.getTotalPrice());
        
        // Object contaning individual item data.
        jsonGenerator.writeFieldName("items");
        
        // The object for items.
        jsonGenerator.writeStartObject();
        
        // Loop through all the items, add the amount and the total price for all the items of that type.
        for (Product product : receipt.getPrices().keySet()) {
        	jsonGenerator.writeFieldName(product.getName());
	        jsonGenerator.writeStartObject();
	        jsonGenerator.writeNumberField("total", receipt.getPrices().get(product));
	        jsonGenerator.writeNumberField("amount", receipt.getInventory().get(product.getID()));
	        jsonGenerator.writeEndObject();
        }
        
        // Items closing tag.
        jsonGenerator.writeEndObject();
        
        // Close the root object.
        jsonGenerator.writeEndObject();
    }
}
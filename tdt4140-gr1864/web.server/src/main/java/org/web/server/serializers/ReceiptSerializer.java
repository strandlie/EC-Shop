package org.web.server.serializers;

import java.io.IOException;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.Receipt;

public class ReceiptSerializer extends StdSerializer<Receipt> {
	private static final long serialVersionUID = 1L;


	public ReceiptSerializer() {
		this(null);
	}
	
	public ReceiptSerializer(Class<Receipt> receipt) {
		super(receipt);
	}
 

    @Override
    public void serialize(Receipt receipt, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
        // The root object.
    	jsonGenerator.writeStartObject();
    	
    	// The shopping trip start time.
        jsonGenerator.writeNumberField("date", receipt.getShoppingTrip().getStart()); //
        
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
package org.web.server;

import java.io.IOException;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;

import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.Receipt;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class ReceiptServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	private ShoppingTripDatabaseController shoppingTripController = new ShoppingTripDatabaseController();
	private ActionDatabaseController actionController = new ActionDatabaseController();
	
	@Override
	public void init() throws ServletException {
		super.init();
	}

	public class ReceiptSerializer extends StdSerializer<Receipt> {
		public ReceiptSerializer() {
			this(null);
		}
		
		public ReceiptSerializer(Class<Receipt> receipt) {
			super(receipt);
		}
	 

	    @Override
	    public void serialize(Receipt receipt, JsonGenerator jsonGenerator, SerializerProvider serializer) throws IOException {
	        jsonGenerator.writeStartObject();
	        
	        jsonGenerator.writeNumberField("date", receipt.getShoppingTrip().getStart());
	        jsonGenerator.writeNumberField("totalPrice", receipt.getTotalPrice());
	        
	        jsonGenerator.writeFieldName("items");
	        
	        jsonGenerator.writeStartObject();
	        
	        for (Product product : receipt.getPrices().keySet()) {
	        	jsonGenerator.writeFieldName(product.getName());
		        jsonGenerator.writeStartObject();
		        jsonGenerator.writeNumberField("total", receipt.getPrices().get(product));
		        jsonGenerator.writeNumberField("amount", receipt.getInventory().get(product.getID()));
		        jsonGenerator.writeEndObject();
	        }
	        
	        jsonGenerator.writeEndObject();
	        
	        jsonGenerator.writeEndObject();
	    }
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");

		if (!request.getParameterMap().containsKey("trip")) {
	        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
	        return;
		}
		
		int id = Integer.valueOf(request.getParameter("trip"));

		ShoppingTrip trip = shoppingTripController.retrieve(id);
		
		if (trip == null) {
	        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
	        return;
		}
		
		trip.setActions(actionController.retrieveAll(trip.getShoppingTripID()));
		
		ObjectMapper mapper = new ObjectMapper();
	
		SimpleModule module = new SimpleModule("ReceiptSerializer", new Version(1, 0, 0, null, null, null));
		module.addSerializer(Receipt.class, new ReceiptSerializer());
		mapper.registerModule(module);
				
		ObjectWriter writer = mapper.writer().withDefaultPrettyPrinter();
	
		String output = writer.writeValueAsString(new Receipt(trip));
		
        response.setStatus(HttpServletResponse.SC_OK);
	    response.getWriter().println(output);
  	}

}

package tdt4140.gr1864.app.core;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/* 
 * TODO:
 * 	Add ShoppingTrip to a user
 * 	Add actions to products
 */

public class DataLoader {
	
	public static void main(String [] args) {
		DataLoader loader = new DataLoader();
		String path = "../../src/main/resources/test-data.json";
		ShoppingTrip trip = loader.loadShoppingTrips(path);
		
		String pathToProducts = "../../src/main/resources/mock-products.json";
		List<Product> p = loader.loadProducts(pathToProducts);
		
	}
	
	
	public List<Product> loadProducts(String path) {
		String relativePath = getClass().getClassLoader().getResource(".").getPath();
		JSONParser parser = new JSONParser();
		
		List<Product> products = null;

		try {
			Object obj = parser.parse(new FileReader(relativePath + path));
			JSONArray groceries = (JSONArray) obj;
			
			createProducts(groceries);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/*
	 * Creates list of Products
	 * @param productArray JSONArray with generated products 
	 * @return list of Products 
	 */
	public List<Product> createProducts(JSONArray productArray) {
		List<Product> products = new ArrayList<>();
		String name;
		double price;
		
		for (Object o : productArray) {
			JSONObject jsonGroceries = (JSONObject) o;
			name = (String) jsonGroceries.get("grocery");
			price = (double) jsonGroceries.get("price");
			
			Product newProduct = new Product(name, price); 
			products.add(newProduct);
		}
		
		return products;
	}
	
	/*
	 * Loads JSON-data from path, creates ShoppingTrip object 
	 * @param path	relative path to JSON-data (relative to this)
	 */
	public ShoppingTrip loadShoppingTrips(String path) {
		String relativePath = getClass().getClassLoader().getResource(".").getPath();
		JSONParser parser = new JSONParser();
		
		ShoppingTrip shoppingTrip = null;
		
		try {
			Object obj = parser.parse(new FileReader(relativePath + path));
			JSONObject trip = (JSONObject) obj;
			
			// creating Coordinates
			JSONArray coordsArray = (JSONArray) trip.get("path");
			ArrayList<Coordinate> coordinates = (ArrayList<Coordinate>) createCoordinates(coordsArray);
			
			// creating Actions
			JSONArray actionsArray = (JSONArray) trip.get("actions");
			ArrayList<Action> actions = (ArrayList<Action>) createActions(actionsArray);
			
			// create ShoppingTrip
			shoppingTrip = createShoppingTrip(coordinates, actions);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return shoppingTrip;
	}
	
	/*
	 * @param coordinates list of Coordinate objects for ShoppingTrip
	 * @param actions	  list of Action objects for ShoppingTrip
	 * @return ShoppingTrip object created from coordinates and actions
	 */
	public ShoppingTrip createShoppingTrip(List<Coordinate> coordinates, List<Action> actions) {
		return new ShoppingTrip(coordinates, actions);
	}
	
	/*
	 * Creates list of Coordinates for use in ShoppingTrip-creation
	 * @param coordsArray JSONArray with generated coordinates 
	 * @return list of Coordinates for use in ShoppingTrip-creation
	 */
	public List<Coordinate> createCoordinates(JSONArray coordsArray) {
		Coordinate coordinate;
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		double x, y;
		long timeStamp;
		
		for (Object o : coordsArray) {
			JSONObject jsonCoord = (JSONObject) o;
			x = (double) jsonCoord.get("x");
			y = (double) jsonCoord.get("y");
			timeStamp = (long) jsonCoord.get("time");

			coordinate = new Coordinate(x, y, timeStamp);
			coordinates.add(coordinate);
		}
		return coordinates;
	}

	/*
	 * Creates list of Actions for use in ShoppingTrip-creation
	 * @param actionsArray JSONArray with generated actions 
	 * @return list of Actions for use in ShoppingTrip-creation
	 */
	public List<Action> createActions(JSONArray actionsArray) {
		Action action;
		List<Action> actions = new ArrayList<Action>();
		long actionType;
		long productID;
		long timeStamp;
		
		for (Object o : actionsArray) {
			JSONObject jsonAction = (JSONObject) o;
			timeStamp = (long) jsonAction.get("timestamp");
			actionType = (long) jsonAction.get("action");
			productID = (long) jsonAction.get("productID");
			
			action = new Action(timeStamp, actionType, productID);
			actions.add(action);
		}
		return actions;
	}
}

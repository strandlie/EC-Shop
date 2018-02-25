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
		loader.loadData(path);
	}
	
	/*
	 * Loads JSON-data from path, creates ShoppingTrip object 
	 * @param path	relative path to JSON-data (relative to this)
	 */
	public void loadData(String path) {
		String relativePath = getClass().getClassLoader().getResource(".").getPath();
		JSONParser parser = new JSONParser();
		
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
			ShoppingTrip shoppingTrip = createShoppingTrip(coordinates, actions);

		}
		catch (Exception e) {
			e.printStackTrace();
		}
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

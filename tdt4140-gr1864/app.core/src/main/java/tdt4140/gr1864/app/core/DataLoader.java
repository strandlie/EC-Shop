package tdt4140.gr1864.app.core;

import java.io.FileReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.toIntExact;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import tdt4140.gr1864.app.core.storage.OnShelfDatabaseController;
import tdt4140.gr1864.app.core.storage.Shop;
import tdt4140.gr1864.app.core.storage.ShopDatabaseController;

public class DataLoader {
	
	/* trip, customer and shop created by this loader */
	ShoppingTrip trip;
	Customer customer;
	Shop shop;
	
	/* lists of multiples */
	List<Product> products;
	List<Action> actions;
	List<Coordinate> coordinates;

	
	public DataLoader() {
		// Creates database
		Path dbPath = Paths.get("database.db");
		if (! Files.exists(dbPath)) {
			CreateDatabase.main(null);
		}
		
		ProductDatabaseController pdc = new ProductDatabaseController();
	
		// loads products
		String pathToProducts = "../../src/main/resources/mock-products.json";

		products = this.loadProducts(pathToProducts, pdc);
	
		// loads trips
		String pathToTrip = "../../src/main/resources/test-data.json";
		trip = this.loadShoppingTrips(pathToTrip);
		
		// Adds amounts of products to shelfs and storage of DB and updates DB
		addProductsInShelfsInDB();
	}
	
	/**
	 * Loads mock-products file and creates Product objects with corresponding name and price
	 * @param path Path to .json file
	 * @return A list of products which are generated from .json file.
	 */
	public List<Product> loadProducts(String path, ProductDatabaseController pdc) {
		String relativePath = getClass().getClassLoader().getResource(".").getPath();
		JSONParser parser = new JSONParser();
		
		List<Product> products = null;

		try {
			Object obj = parser.parse(new FileReader(relativePath + path));
			JSONArray groceries = (JSONArray) obj;
			
			// Creates a list with products with groceries JSONArray
			products = createProducts(groceries, pdc);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		this.products = products;
		return products;
	}
	
	/**
	 * Creates list of Products
	 * @param productArray JSONArray with generated products 
	 * @return list of products  
	 */
	public List<Product> createProducts(JSONArray productArray, ProductDatabaseController pdc) {
		List<Product> products = new ArrayList<>();
		String name;
		double price;
		
		for (Object o : productArray) {

			// {"grocery": String, "price": double}
			JSONObject jsonGrocery = (JSONObject) o;
			
			// Gets grocery name and price for each product
			name = (String) jsonGrocery.get("grocery");
			price = Double.parseDouble((String) jsonGrocery.get("price"));
			
			// Creates a new products and adds it to the list
			Product newProduct = new Product(name, price);
			
			// Adds the newProduct to database
			pdc.create(newProduct);
			
			products.add(newProduct);
		}
		return products;
	}
	
	/**
	 * creates customer and writes it to db
	 * @return customer
	 */
	public Customer createCustomer() {
		Customer c1 = new Customer("Kari", "Normann");
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		c1 = new Customer(c1.getFirstName(), c1.getLastName(), cdc.create(c1));
		this.customer = c1;
		return c1;
	}
	
	/**
	 * creates shop and writes it to db
	 * @return shop
	 */
	public Shop createShop() {
		Shop s1 = new Shop("Kings Road 2", 1020);
		ShopDatabaseController sdc = new ShopDatabaseController();
		s1 = new Shop(s1.getAddress(), s1.getZip(), sdc.create(s1));
		this.shop = s1;
		return s1;
	}
	
	
	/**
	 * Loads JSON-data from path, creates ShoppingTrip object 
	 * @param path	relative path to JSON-data (relative to this)
	 * @return shoppingTrip
	 */
	public ShoppingTrip loadShoppingTrips(String path) {
		String relativePath = getClass().getClassLoader().getResource(".").getPath();
		JSONParser parser = new JSONParser();
		
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		
		Shop s1 = createShop();
		Customer c1 = createCustomer();

		// We set the charged flag to true to prevent spamming the Stripe API.		
		trip = new ShoppingTrip(c1, s1, true);
		trip = new ShoppingTrip(stdc.create(trip), trip.getCustomer(), trip.getShop(), true);
		
		try {
			Object obj = parser.parse(new FileReader(relativePath + path));
			JSONObject tripObject = (JSONObject) obj;
			
			// creating Coordinates
			JSONArray coordsArray = (JSONArray) tripObject.get("path");
			coordinates = (ArrayList<Coordinate>) createCoordinates(coordsArray, trip);
			
			// creating Actions
			JSONArray actionsArray = (JSONArray) tripObject.get("actions");
			actions = (ArrayList<Action>) createActions(actionsArray, trip);
			
			// adds Coordinate and Action to ShoppingTrip
			trip = createShoppingTrip(trip, coordinates, actions);
			
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return trip;
	}
	
	/**
	 * @param coordinates 		list of Coordinate objects for ShoppingTrip
	 * @param actions	  		list of Action objects for ShoppingTrip
	 * @return ShoppingTrip 	object created from coordinates and actions
	 */
	public ShoppingTrip createShoppingTrip(ShoppingTrip trip, List<Coordinate> coordinates, List<Action> actions) {
		// We set the Charged flag to true to prevent spamming the Stripe API with charges.
		ShoppingTrip newTrip = new ShoppingTrip(coordinates, actions, trip.getShoppingTripID(), true);
		this.trip = newTrip;
		return newTrip;
	}
	
	/**
	 * Creates list of Coordinates for use in ShoppingTrip-creation
	 * @param coordsArray 	JSONArray with generated coordinates 
	 * @return list of Coordinates for use in ShoppingTrip-creation
	 */
	public List<Coordinate> createCoordinates(JSONArray coordsArray, ShoppingTrip trip) {
		Coordinate coordinate;
		CoordinateDatabaseController cdc = new CoordinateDatabaseController();
		List<Coordinate> coordinates = new ArrayList<Coordinate>();
		double x, y;
		String timeStamp;
		
		for (Object o : coordsArray) {
			JSONObject jsonCoord = (JSONObject) o;
			x = (double) jsonCoord.get("x");
			y = (double) jsonCoord.get("y");
			timeStamp = Long.toString((long) jsonCoord.get("time"));

			coordinate = new Coordinate(x, y, timeStamp, trip);
			coordinates.add(coordinate);
			
			// writes coordinate to database
			cdc.create(coordinate);
			
		}
		this.coordinates = coordinates;
		return coordinates;
	}

	/**
	 * Creates list of Actions for use in ShoppingTrip-creation
	 * @param actionsArray JSONArray with generated actions 
	 * @return list of Actions for use in ShoppingTrip-creation
	 */
	public List<Action> createActions(JSONArray actionsArray, ShoppingTrip trip) {
		Action action;
		ActionDatabaseController adc = new ActionDatabaseController();
		ProductDatabaseController pdc = new ProductDatabaseController();
		List<Action> actions = new ArrayList<Action>();
		int actionType;
		int productID;
		String timeStamp;
		
		for (Object o : actionsArray) {
			JSONObject jsonAction = (JSONObject) o;
			timeStamp = Long.toString((long) jsonAction.get("timestamp"));
			actionType = toIntExact((long) jsonAction.get("action"));
			productID = toIntExact((long) jsonAction.get("productID"));
			// producted needed for action-creation
			Product product = pdc.retrieve(productID);

			action = new Action(timeStamp, actionType, product, trip);
			actions.add(action);

			// write action to database
			adc.create(action);
		}
		this.actions = actions;
		return actions;
	}
	
	/**
	 * A function that adds products to the shelfs and storage of the shop, also updates the DB
	 */
	public void addProductsInShelfsInDB() {
		int amountInStorage = 90;
		int amountOnShelfs = 20;
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		for(int i = 0; i < products.size();i++) {
			int productID = products.get(i).getID();
			shop.setAmountInShelfs(productID, amountOnShelfs);
			shop.setAmountInStorage(productID, amountInStorage);
			osdc.create(shop, productID);
		}
	}
	
	public ShoppingTrip getTrip() {
		return trip;
	}
	public Customer getCustomer() {
		return customer;
	}
	public Shop getShop() {
		return shop;
	}
	public List<Product> getProducts() {
		return products;
	}
	public List<Action> getActions() {
		return actions;
	}
	public List<Coordinate> getCoordinates() {
		return coordinates;
	}
}

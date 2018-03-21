package tdt4140.gr1864.app.core.database;

import static java.lang.Math.toIntExact;

import java.io.FileReader;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.Coordinate;
import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.databasecontrollers.ActionDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CoordinateDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.CustomerDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.OnShelfDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ProductDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShopDatabaseController;
import tdt4140.gr1864.app.core.databasecontrollers.ShoppingTripDatabaseController;

public class DataLoader {
	
	/* trip, customer and shop created by this loader */
	private static ShoppingTrip trip;
	private static Shop shop;
	
	/* lists of multiples */
	private static List<Customer> customers;
	private static List<Product> products;
	private static List<Action> actions;
	private static List<Coordinate> coordinates;
	
	private static final String[] NAMES = {	"Reina Novoa",
												"Dyan Linkous",
												"Kenyatta Horning",
												"August Eastwood",
												"Kasi Fredricks",
												"Sherilyn Salamanca",
												"Jean Oslund",
												"Maxima Hargreaves",
												"Isabell Jarrell",
												"Debbi Fuquay" };

	public static void main(String [] args) {
		customers = new ArrayList<>();
		products = new ArrayList<>();
		actions = new ArrayList<>();
		coordinates = new ArrayList<>();

		cleanDatabase();
		loadCustomers();
		loadProducts();
		createShop();
		loadShoppingTrips();
		addProductsInShelfsInDB(products);
	}
	
	/**
	 * Creates database if doesn't exist and wipes if does
	 */
	private static void cleanDatabase() {
		String path = "../../../app.core/src/main/resources/database.db";
		String relativePath;
		//Finds path by getting URL and converting to URI and then to path 
		try {
			URI rerelativeURI = TestDataLoader.class.getClassLoader().getResource(".").toURI();
			relativePath = Paths.get(rerelativeURI).toFile().toString() + "/";
			
		} catch (URISyntaxException e1) {
			//If fail to convert to URI use URL path instead
			relativePath = TestDataLoader.class.getClass().getClassLoader().getResource(".").getPath();
		} 
		path = relativePath + path;
		Path dbPath = Paths.get(path);

		if (! Files.exists(dbPath)) {
			CreateDatabase.main(null);
		} else {
			DatabaseWiper wiper = new DatabaseWiper();
			wiper.wipe();
		}
	}
	
	/**
	 * Loads mock-products file and creates Product objects with corresponding name and price
	 * @return A list of products which are generated from .json file.
	 */
	private static List<Product> loadProducts() {
		ProductDatabaseController pdc = new ProductDatabaseController();

		String path = "../../../app.core/src/main/resources/mock-products.json";
		String relativePath;
		//Finds path by getting URL and converting to URI and then to path 
		try {
			URI rerelativeURI = DataLoader.class.getClassLoader().getResource(".").toURI();
			relativePath = Paths.get(rerelativeURI).toFile().toString() + "/";
			
		} catch (URISyntaxException e1) {
			//If fail to convert to URI use URL path instead
			relativePath = DataLoader.class.getClassLoader().getResource(".").getPath();
		} 
		
		JSONParser parser = new JSONParser();
		
		List<Product> localProducts = null;

		try {
			Object obj = parser.parse(new FileReader(relativePath + path));
			JSONArray groceries = (JSONArray) obj;
			
			// Creates a list with products with groceries JSONArray
			localProducts = createProducts(groceries, pdc);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		products = localProducts;
		return products;
	}
	
	/**
	 * Creates list of Products
	 * @param productArray JSONArray with generated products 
	 * @return list of products  
	 */
	private static List<Product> createProducts(JSONArray productArray, ProductDatabaseController pdc) {
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
			int productID = pdc.create(newProduct);
			Product tempProduct = new Product(productID, name, price);
			
			products.add(tempProduct);
		}
		return products;
	}
	
	/**
	 * Generates 10 customers from NAMES and persists to database
	 */
	private static void loadCustomers() {
		for (int i = 0; i < NAMES.length; i++) {
			customers.add(createCustomer(i));
		}
	}
	
	/**
	 * Gets customer from database based on ID
	 * @param id of customer to get
	 * @return customer
	 */
	private static Customer getCustomer(int id) {
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		return cdc.retrieve(id);
	}

	/**
	 * Creates customer with name NAMES[i] and persists to database.
	 * Used as sub-routine of loadCustomers()
	 * @param index of names
	 * @return customer
	 */
	private static Customer createCustomer(int index) {
		String fullName = NAMES[index];
		String [] names = fullName.split(" ");
		
		Customer c1 = new Customer(names[0], names[1]);
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		c1 = new Customer(c1.getFirstName(), c1.getLastName(), cdc.create(c1));
		return c1;
	}
	
	/**
	 * creates shop and writes it to db
	 * @return shop
	 */
	private static Shop createShop() {
		Shop s1 = new Shop("Kings Road 2", 1020);
		ShopDatabaseController sdc = new ShopDatabaseController();
		s1 = new Shop(s1.getAddress(), s1.getZip(), sdc.create(s1));
		shop = s1;
		return s1;
	}
	
	/**
	 * Loads JSON-data from string, creates ShoppingTrip object
	 * This function is for the API
	 * @param json	String with json-data of a ShoppingTrip
	 */
	public static void loadShoppingTrip(String json) {

		JSONParser parser = new JSONParser();
		
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		ShoppingTrip trip = null;
		Customer customer;
		Shop shop = createShop();

		try {
			JSONObject tripObject = (JSONObject) parser.parse(json);
			
			// get Customer
			customer = (Customer) getCustomer(toIntExact((long) tripObject.get("customerID")));
			
			// create ShoppingTrip
			trip = new ShoppingTrip(customer, shop, true);
			trip = new ShoppingTrip(stdc.create(trip), trip.getCustomer(), trip.getShop(), true);

			// creating Coordinates
			JSONArray coordsArray = (JSONArray) tripObject.get("path");
			coordinates = (ArrayList<Coordinate>) createCoordinates(coordsArray, trip);
			
			// creating Actions
			JSONArray actionsArray = (JSONArray) tripObject.get("actions");
			actions = (ArrayList<Action>) createActions(actionsArray, trip);
			
			// adds Coordinate and Action to ShoppingTrip
			trip = createShoppingTrip(trip, coordinates, actions);

		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Loads JSON-data from file, creates ShoppingTrip object 
	 * @return shoppingTrip
	 */
	private static ShoppingTrip loadShoppingTrips() {
		
		String path = "../../../app.core/src/main/resources/test-data.json";
		String relativePath;
		//Finds path by getting URL and converting to URI and then to path 
		try {
			URI rerelativeURI = DataLoader.class.getClassLoader().getResource(".").toURI();
			relativePath = Paths.get(rerelativeURI).toFile().toString() + "/";
			
		} catch (URISyntaxException e1) {
			//If fail to convert to URI use URL path instead
			relativePath = DataLoader.class.getClassLoader().getResource(".").getPath();
		}
		
		JSONParser parser = new JSONParser();
		
		ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
		
		Shop s1 = createShop();
		Customer c1 = getCustomer(1);

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
	 * Adds Coordinates and Actions to global ShoppingTrip.
	 * Sub-routine of loadShoppingTrip()
	 * @param coordinates 		list of Coordinate objects for ShoppingTrip
	 * @param actions	  		list of Action objects for ShoppingTrip
	 * @return ShoppingTrip 	object created from coordinates and actions
	 */
	private static ShoppingTrip createShoppingTrip(ShoppingTrip trip, List<Coordinate> coordinates, List<Action> actions) {
		// We set the Charged flag to true to prevent spamming the Stripe API with charges.
		ShoppingTrip newTrip = new ShoppingTrip(coordinates, actions, trip.getShoppingTripID(), true);
		trip = newTrip;
		return newTrip;
	}
	
	/**
	 * Creates list of Coordinates for use in ShoppingTrip-creation
	 * @param coordsArray 	JSONArray with generated coordinates 
	 * @return list of Coordinates for use in ShoppingTrip-creation
	 */
	private static List<Coordinate> createCoordinates(JSONArray coordsArray, ShoppingTrip trip) {
		Coordinate coordinate;
		CoordinateDatabaseController cdc = new CoordinateDatabaseController();
		List<Coordinate> localCoordinates = new ArrayList<Coordinate>();
		double x, y;
		String timeStamp;
		
		for (Object o : coordsArray) {
			JSONObject jsonCoord = (JSONObject) o;
			x = (double) jsonCoord.get("x");
			y = (double) jsonCoord.get("y");
			timeStamp = Long.toString((long) jsonCoord.get("time"));

			coordinate = new Coordinate(x, y, timeStamp, trip);
			localCoordinates.add(coordinate);
			
			// writes coordinate to database
			cdc.create(coordinate);
			
		}
		coordinates = localCoordinates;
		return coordinates;
	}

	/**
	 * Creates list of Actions for use in ShoppingTrip-creation
	 * @param actionsArray JSONArray with generated actions 
	 * @return list of Actions for use in ShoppingTrip-creation
	 */
	private static List<Action> createActions(JSONArray actionsArray, ShoppingTrip trip) {
		Action action;
		ActionDatabaseController adc = new ActionDatabaseController();
		ProductDatabaseController pdc = new ProductDatabaseController();
		List<Action> localActions = new ArrayList<Action>();
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
			localActions.add(action);

			// write action to database
			adc.create(action);
		}
		actions = localActions;
		return actions;
	}
	
	
	/**
	 * A function that adds products to the shelfs and storage of the shop, also updates the DB
	 */
	public static void addProductsInShelfsInDB(List<Product> products) {
		ShopDatabaseController sdc = new ShopDatabaseController();
		Shop shop = sdc.retrieve(1);
		
		int amountInStorage = 90;
		int amountOnShelfs = 20;
		OnShelfDatabaseController osdc = new OnShelfDatabaseController();
		for(Product p : products) {
			int productID = p.getID();
			shop.setAmountInShelfs(productID, amountOnShelfs);
			shop.setAmountInStorage(productID, amountInStorage);
			osdc.create(shop, productID);
		}
	}
	
}

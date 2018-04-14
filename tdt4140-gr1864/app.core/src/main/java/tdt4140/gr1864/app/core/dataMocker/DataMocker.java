package tdt4140.gr1864.app.core.dataMocker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import java.util.concurrent.ThreadLocalRandom;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class DataMocker implements Runnable {
	/**
	 * The API URL test data will be sent to.
	 */
	private static String API_URL = "http://localhost:8080/api/v1/shoppingtrip/";
	
	/**
	 * A Rectangle containing the start and end of all paths. Typically the cashier desks.
	 */
	private Rectangle home;
	
	/**
	 * Zones where a customer may stop to pick up goods. We use a List instead of a collection
	 * so that we can easily shuffle the zones in order to pick a random subset. 
	 */
	private List<Rack> zones;
	
	/**
	 * Creates a new DataMocker reflecting a freshly stocked shop.
	 */
	public DataMocker() {
		initializeShop();
	}
	
	/**
	 * Initializes the shop with products.
	 */
	private void initializeShop() {
		home = new Rectangle(new Coordinate(0, 0), new Coordinate(10, 10));

		zones = new ArrayList<>();
		
		int rowAmount = 4;
		
		int productsPerRack = 4;
		
		int rackLength = 20;
		
		int rackHeight = 10;
		
		for (int y = 10; y < rowAmount * 20; y += 20) {
			for (int x = 10; x < rowAmount * 40; x += 40) {
				Collection<Product> products = new ArrayList<>();

				for (int i = 0; i < productsPerRack; i++) {
					products.add(new Product(180));
				}

				Rack productRack = new Rack(new Coordinate(x, y), new Coordinate((x + rackLength), (y + rackHeight)), products);

				zones.add(productRack);
			}
		}
	}
	
	/**
	 * Generates a random coordinate inside a given rectangle.
	 * @param rectangle
	 * @return A random coordinate contained within the given rectangle.
	 */
	private Coordinate generateRandomCoordinateInsideRectangle(Rectangle rectangle) {
		return new Coordinate(
					ThreadLocalRandom.current().nextDouble(rectangle.getLower().getX(), rectangle.getUpper().getX()),
					ThreadLocalRandom.current().nextDouble(rectangle.getLower().getY(), rectangle.getUpper().getY())
				);
	}
	
	/**
	 * Returns a coordinate on the line connecting the start and end coordinates.
	 * @param start The start coordinate.
	 * @param end The end coordinate.
	 * @param displacement The position on the line, given as a percentage between 0 and 1.
	 * @return A coordinate on the line connecting the start and end coordinates.
	 */
	private Coordinate getPointAtSlopeBetweenCoordinates(Coordinate start, Coordinate end, double displacement) {
		double position = start.getX() + (end.getX() - start.getX()) * displacement;
		double slope = (end.getY() - start.getY()) / (end.getX() - start.getX());
		return new Coordinate(position, start.getY() + slope * (position - start.getX()));
	}
	
	/**
	 * Returns a list containing evenly points on the line connecting the start and end coordinates.
	 * @param start The start coordinate.
	 * @param end The end coordinate.
	 * @param intensity The distance between samples.
	 * @param fuzz The amount of randomness added to samples.
	 * @return A list of distinct coordinate on the line connecting the start and end coordinates.
	 */
	private List<Coordinate> getAllPointsAtSlopeBetweenCoordinates(Coordinate start, Coordinate end, double intensity, double fuzz) {
		List<Coordinate> coordinates = new ArrayList<>();
		
		// Calculate the distance between the start and end coordinates.
		double distance = Math.sqrt(Math.pow(end.getX() - start.getX(), 2) + Math.pow(end.getY() - start.getY(), 2));
		
		for (double i = 0; i <= distance; i += intensity) {
			Coordinate position = getPointAtSlopeBetweenCoordinates(start, end, i / distance);
			
			// Add some noise to the coordinates. Clamp the values so that they are not negative.
			// This slightly changes the probability distribution for coordinates close to the edges, which
			// should not really be a problem in practice.
			position.setX(Math.max(position.getX() + ThreadLocalRandom.current().nextDouble(-fuzz / 2, fuzz / 2), 0));
			position.setY(Math.max(position.getY() + ThreadLocalRandom.current().nextDouble(-fuzz / 2, fuzz / 2), 0));
			
			coordinates.add(position);
		}
		
		return coordinates;
	}
	
	/**
	 * Generate a sequence of coordinates describing an users path through the shop.
	 * Five units is one meter, and the maximum speed 60 units per second.
	 * @param intensity The distance between samples.
	 * @param fuzz The amount of randomness added to samples.
	 * @param speed The number of units the person walks each second.
	 * @return A list of coordinates between a random set of zones, starting and ending in the home zone, and
	 * a list of actions performed by the user.
	 */
	public Trip generateRandomPath(double intensity, double fuzz, double speed) {
		Inventory inventory = new Inventory();
		
		List<Position> coordinates = new ArrayList<>();
		
		List<Action> actions = new ArrayList<>();
		
		// New date element used for timestamps.
		Date time = new Date();
		
		Coordinate previous = new Position(generateRandomCoordinateInsideRectangle(home), time);
		
		coordinates.add((Position) previous);
		
		// Shuffle the racks. This is used to create a random subset of zones to visit.
		List<Rack> shuffledRacks = new ArrayList<>(zones);
		
		Collections.shuffle(shuffledRacks);
	
		// Select a random amount of zones from the shuffled list of zones.
		for (Rack box : shuffledRacks.subList(0, ThreadLocalRandom.current().nextInt(0, shuffledRacks.size() + 1))) {
			// The target position.
			Coordinate target = new Position(generateRandomCoordinateInsideRectangle(box), new Date());
			
			for (Coordinate coordinate : getAllPointsAtSlopeBetweenCoordinates(previous, target, intensity, fuzz)) {
				time = addTime(time, Double.valueOf(intensity * speed).intValue());
				coordinates.add(new Position(coordinate, time));
			}
			
			previous = target;
			
			// Choose a random product.
			Product product = box.getRandomItem();

			int action = ThreadLocalRandom.current().nextInt(Action.DROP, Action.PICK_UP + 1);

			boolean validAction = false;

			if (action == Action.PICK_UP && product.canPickUp()) {
				inventory.add(product);
				validAction = true;
			} else if (action == Action.DROP && inventory.contains(product)) {
				inventory.remove(product);
				validAction = true;
			}

			if (validAction) {
				actions.add(new Action(product, time, action));
			}
		}
		
		Coordinate goal = generateRandomCoordinateInsideRectangle(home);
		
		for (Coordinate coordinate : getAllPointsAtSlopeBetweenCoordinates(previous, goal, intensity, fuzz)) {
			time = addTime(time, Double.valueOf(intensity * speed).intValue());
			coordinates.add(new Position(coordinate, time));
		}
		
		return new Trip(coordinates, actions);
	}
	
	/**
	 * @return A random path with sensible default parameters.
	 */
	public Trip generateRandomPath() {
		return generateRandomPath(5, 2, 15);
	}
	
	/**
	 * A basic function to add time to a given Date time object.
	 * The function adds time in milliseconds, as waiting for a long time when testing is boring..
	 * @param time The date to add time to.
	 * @param seconds Milliseconds to add to the time.
	 * @return A Date object where the the given amount of time has been added to the time.
	 */
	private Date addTime(Date time, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.MILLISECOND, seconds);
		time = cal.getTime();
		return time;
	}
	
	/**
	 * Returns JSON describing a shopping trip.
	 * @return 
	 * @throws JsonProcessingException
	 */
	public String getRandomPathJSON() throws JsonProcessingException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		return ow.writeValueAsString(generateRandomPath());
	}
	
	/**
	 * Sends test data to the server. The data represents one shopping trip.
	 * @throws IOException 
	 * @throws ClientProtocolException 
	 */
	public void sendShoppingTripData(Trip trip) throws ClientProtocolException, IOException {
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(trip);
		
		DefaultHttpClient httpclient = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(API_URL + "?customer-id=" + trip.getCustomerID());
		httpPost.addHeader("content-type", "application/json");
		httpPost.setEntity(new StringEntity(json));
		HttpResponse response2 = httpclient.execute(httpPost);

		try {
		    HttpEntity entity2 = response2.getEntity();
		    EntityUtils.consume(entity2);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {		
		PriorityQueue<ThreadAction> heap = new PriorityQueue<>();
	
		long currentTime = 0;
				
		while (true) {
			if (Thread.currentThread().isInterrupted()) {
				break;
			}

			// The delay until the next customer enters the store. We use a random exponential variable
			// with 10 seconds as the expected value.
			heap.add(new ThreadAction((int) (currentTime + Math.log(1 - ThreadLocalRandom.current().nextDouble()) / -0.0001)));
			
			ThreadAction action = heap.poll();
			
			try {
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				// We simply exit early if the sleeping is interrupted.
				break;
			}
			
			currentTime = action.getTime();
			
			if (action.getAction() == 0) {
				Trip trip = generateRandomPath();
				heap.add(new ThreadAction(currentTime + trip.getDuration(), trip));
				// TODO: Send data to customer entering shop endpoint
			} else {				
				try {
					sendShoppingTripData(action.getTrip());
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
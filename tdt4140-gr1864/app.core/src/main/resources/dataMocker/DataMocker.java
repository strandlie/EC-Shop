package dataMocker;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;

public class DataMocker {
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
	 * @param home A rectangle containing the cashier desks.
	 * @param zones A collection of zones where the customer may stop to pick up goods.
	 */
	public DataMocker(Rectangle home, List<Rack> zones) {
		this.home = home;
		this.zones = zones;
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
	 * @return A list of coordinates between a random set of zones, starting and ending in the home zone, and'
	 * a list of actions performed by the user.
	 */
	private Trip generateRandomPath(double intensity, double fuzz, double speed) {
		List<Position> coordinates = new ArrayList<>();
		
		List<Action> actions = new ArrayList<>();
		
		// New date element for timestamp
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
			
			actions.add(generateAction(time, box));
		}
		
		Coordinate goal = generateRandomCoordinateInsideRectangle(home);
		
		for (Coordinate coordinate : getAllPointsAtSlopeBetweenCoordinates(previous, goal, intensity, fuzz)) {
			time = addTime(time, Double.valueOf(intensity * speed).intValue());
			coordinates.add(new Position(coordinate, time));
		}
		
		return new Trip(coordinates, actions);
	}
	
	/**
	 * Generates an action at time where a product from the box is either picked up or
	 * dropped. The product cannot be dropped if it has never been picked up.
	 * @param time Date object for when the action happened
	 * @param box A Rectangle object where a random product is picked from
	 * @return Returns an action object with the product and time for whether the product was picked up or dropped.
	 */
	private Action generateAction(Date time, Rack box) {
		Product product = box.getRandomItem();
		int action = product.canBeDropped() ? ThreadLocalRandom.current().nextInt(Action.DROP, Action.PICK_UP + 1) : 1;
		return new Action(product, time, action);
	}
	
	/**
	 * A basic function to add time in seconds to a given Date time object.
	 * @param time The date to add time to
	 * @param seconds Amount of seconds to add
	 * @return A Date object where the the given amount of seconds has been added to the time.
	 */
	private Date addTime(Date time, int seconds) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(time);
		cal.add(Calendar.SECOND, seconds);
		time = cal.getTime();
		return time;
	}
	
	public static void main(String[] args) throws JsonProcessingException {
		Rectangle home = new Rectangle(new Coordinate(0, 0), new Coordinate(10, 10));
	
		List<Rack> zones = new ArrayList<>();
		Collection<Product> products = new ArrayList<>();

		// Define amount of rows, height and width of racks (in the coordinate system)		
		int rowAmount = 4;
		int productsPerRack = 4;
		int rackLength = 20;
		int rackHeight = 10;
		
		
		// Y coordinate in store grid 
		for (int y = 10; y < rowAmount*20; y+=20) {

			// X coordinate in store grid
			for (int x = 10; x < rowAmount*40; x+=40) {
				
				// Generates productsPerRack products per Rack in products
				for (int i = 0; i < productsPerRack; i++) {
					products.add(new Product());
				}

				// Creates a rack with following x and y coordinates and corresponding products
				Rack productRack = new Rack(
						new Coordinate(x, y), 
						new Coordinate((x + rackLength), (y + rackHeight)), 
						products);

				zones.add(productRack);
			}
		}
		
		DataMocker mocker = new DataMocker(home, zones);

		Trip trip = mocker.generateRandomPath(5, 2, ThreadLocalRandom.current().nextInt(0, 60));		
		ObjectWriter ow = new ObjectMapper().writer().withDefaultPrettyPrinter();
		String json = ow.writeValueAsString(trip);
		
        try {
            FileWriter fw = new FileWriter("./src/main/resources/data.json");
            BufferedWriter bw = new BufferedWriter(fw);
            bw.write(json);
            bw.close();
        } catch (IOException error) {
        	error.printStackTrace();
        }
	}
}
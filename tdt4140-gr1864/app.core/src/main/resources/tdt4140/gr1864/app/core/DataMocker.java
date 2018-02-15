package tdt4140.gr1864.app.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class DataMocker {
	/**
	 * A Rectangle containing the start and end of all paths. Typically the cashier desks.
	 */
	private Rectangle home;
	
	/**
	 * Zones where a customer may stop to pick up goods. We use a List instead of a collection
	 * so that we can easily shuffle the zones in order to pick a random subset. 
	 */
	private List<Rectangle> zones;
	
	/**
	 * @param home A rectangle containing the cashier desks.
	 * @param zones A collection of zones where the customer may stop to pick up goods.
	 */
	public DataMocker(Rectangle home, List<Rectangle> zones) {
		this.home = home;
		this.zones = zones;
	}
	
	/**
	 * Genenrates a random coordinate inside a given rectangle.
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
			
			// Add some noise to the coordinates.
			position.setX(position.getX() + ThreadLocalRandom.current().nextDouble(-fuzz / 2, fuzz / 2));
			position.setY(position.getY() + ThreadLocalRandom.current().nextDouble(-fuzz / 2, fuzz / 2));
			
			coordinates.add(position);
		}
		
		return coordinates;
	}
	
	/**
	 * Generate a sequence of coordinates describing an users path through the shop.
	 * @param intensity The distance between samples.
	 * @param fuzz The amount of randomness added to samples.
	 * @return A list of coordinates between a random set of zones, starting and ending in the home zone.
	 */
	public List<Coordinate> generateRandomPath(double intensity, double fuzz) {
		List<Coordinate> coordinates = new ArrayList<>();
		
		Coordinate previous = generateRandomCoordinateInsideRectangle(home);
		
		// Shuffle the zones. This is used to create a random subset of zones to visit.
		List<Rectangle> shuffledZones = new ArrayList<>(zones);
		
		Collections.shuffle(shuffledZones);
		
		// Select a random amount of zones from the shuffled list of zones.
		for (Rectangle box : shuffledZones.subList(0, ThreadLocalRandom.current().nextInt(0, shuffledZones.size()))) {
			Coordinate position = generateRandomCoordinateInsideRectangle(box);
			coordinates.addAll(getAllPointsAtSlopeBetweenCoordinates(previous, position, intensity, fuzz));
			previous = position;
		}
		
		Coordinate goal = generateRandomCoordinateInsideRectangle(home);
		
		coordinates.addAll(getAllPointsAtSlopeBetweenCoordinates(previous, goal, intensity, fuzz));
		
		return coordinates;
	}
	
	public static void main(String[] args) {
		Rectangle home = new Rectangle(new Coordinate(0, 0), new Coordinate(10, 10));
		List<Rectangle> zones = new ArrayList<>();
		zones.add(new Rectangle(new Coordinate(50, 100), new Coordinate(60, 150)));
		zones.add(new Rectangle(new Coordinate(200, 200), new Coordinate(300, 300)));
		zones.add(new Rectangle(new Coordinate(400, 10), new Coordinate(500, 100)));
		DataMocker mocker = new DataMocker(home, zones);	
		List<Coordinate> path = mocker.generateRandomPath(10, 5);
		for (Coordinate point : path) {
			System.out.println("("+point.getX() + ", " + point.getY()+"),");
		}
	}
}
package tdt4140.gr1864.app.core.databasecontrollers;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tdt4140.gr1864.app.core.Action;
import tdt4140.gr1864.app.core.ShoppingTrip;
import tdt4140.gr1864.app.core.interfaces.DatabaseCRUD;

public class ShoppingTripDatabaseController implements DatabaseCRUD {

	PreparedStatement statement;
	String dbPath;
	
	public ShoppingTripDatabaseController() {
		String path = "../../../app.core/src/main/resources/database.db";
		String relativePath;
		//Finds path by getting URL and converting to URI and then to path 
		try {
			URI rerelativeURI = this.getClass().getClassLoader().getResource(".").toURI();
			relativePath = Paths.get(rerelativeURI).toFile().toString() + "/";
			
		} catch (URISyntaxException e1) {
			//If fail to convert to URI use URL path instead
			relativePath = this.getClass().getClassLoader().getResource(".").getPath();
		} 
		dbPath = relativePath + path;
	}
	
	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#create(java.lang.Object)
	 */
	@Override
	public int create(Object object) {
		ShoppingTrip trip = this.objectIsShoppingTrip(object);
		String sql = "INSERT INTO shopping_trip "
					+ "(customer_id, shop_id, charged, anonymous) "
					+ "VALUES (?, ?, ?, ?)";
					
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setInt(1, trip.getCustomer().getUserId());
			statement.setInt(2, trip.getShop().getShopID());
			statement.setBoolean(3, trip.getCharged());
			statement.setBoolean(4, trip.getAnonymous());
			statement.executeUpdate();
		
			try {
				// Retrieves all generated keys and returns the ID obtained by java object
				// which is inserted into the database
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int i = Math.toIntExact(generatedKeys.getLong(1));
					connection.close();
					return i;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection.close();
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}

	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#update(java.lang.Object)
	 */
	@Override
	public void update(Object object) {
		ShoppingTrip trip = this.objectIsShoppingTrip(object);		
		String sql = "UPDATE shopping_trip "
					+ "SET customer_id=?, shop_id=?, charged=?, anonymous=? "
					+ "WHERE shopping_trip_id=?";
		try {
			
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, trip.getCustomer().getUserId());
			statement.setInt(2, trip.getShop().getShopID());
			statement.setBoolean(3, trip.getCharged());
			statement.setBoolean(4, trip.getAnonymous());
			statement.setInt(5, trip.getShoppingTripID());
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#retrieve(int)
	 */
	@Override
	public ShoppingTrip retrieve(int id) {
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		ShopDatabaseController sdc = new ShopDatabaseController();
		
		String sql = "SELECT * "
					+ "FROM shopping_trip "
					+ "WHERE shopping_trip_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				connection.close();
				return null;
			}
			
			int tripID = rs.getInt("shopping_trip_id");
			
			ShoppingTrip trip = new ShoppingTrip(
					tripID, 
					cdc.retrieve(rs.getInt("customer_id")),
					sdc.retrieve(rs.getInt("shop_id")),
					rs.getBoolean(4),
					rs.getBoolean("anonymous"));
			connection.close();
			return trip;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retrieves and creates a list of all shopping trips based on what's received by the database
	 * @return A list of shoppingTrip objects
	 */
	public List<ShoppingTrip> retrieveAllShoppingTrips() {
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		ShopDatabaseController sdc = new ShopDatabaseController();
		ActionDatabaseController adc = new ActionDatabaseController();
		ShoppingTrip trip;
		List<ShoppingTrip> trips = new ArrayList<>();
		
		String sql = "SELECT * FROM shopping_trip WHERE anonymous=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setBoolean(1, false);
			ResultSet rs = statement.executeQuery();
			
			while(rs.next()) {
				int tripID = rs.getInt(1);
				trip = new ShoppingTrip(
									rs.getInt(1), // ShopID
									cdc.retrieve(rs.getInt(2)), // Customer object 
									sdc.retrieve(rs.getInt(3)), // Shop object
									rs.getBoolean(4), // charged boolean
									rs.getBoolean(5) // anonymous flag
								);
				trip.setActions(adc.retrieveAll(tripID));
				trips.add(trip);
			}
			
			connection.close();
			return trips;
			
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Retrieves and creates a list of shopping trip objects from the database
	 * for the customer with ID = customerID and are not anonymized.
	 * @param customerID The ID of the customer for the shopping trips
	 * @return A list of ShoppingTrip objects corresponding to database instances
	 */
	public List<ShoppingTrip> retrieveAllShoppingTripsForCustomer(int customerID) {
		CustomerDatabaseController cdc = new CustomerDatabaseController();
		ShopDatabaseController sdc = new ShopDatabaseController();
		ActionDatabaseController adc = new ActionDatabaseController();
		ShoppingTrip trip;
		List<ShoppingTrip> trips = new ArrayList<>();
		
		String sql = "SELECT * "
				   + "FROM shopping_trip "
				   + "WHERE customer_id=? AND anonymous=?";
		
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, customerID);
			statement.setBoolean(2, false);
			ResultSet rs = statement.executeQuery();
			
			while (rs.next()) {
				int tripID = rs.getInt("shopping_trip_id");
				trip = new ShoppingTrip(
						rs.getInt(1), // ShopID
						cdc.retrieve(rs.getInt(2)), // Customer object 
						sdc.retrieve(rs.getInt(3)), // Shop object
						rs.getBoolean(4), // charged boolean
						rs.getBoolean(5) // anonymous flag
					);	
				trip.setActions(adc.retrieveAll(tripID));
				trips.add(trip);
			}
			connection.close();
			return trips;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#delete(int)
	 */
	@Override
	public void delete(int id) {
		String sql = "DELETE FROM shopping_trip "
					+ "WHERE shopping_trip_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Checks if incoming object is ShoppingTrip
	 * @param object	suspected ShoppingTrip
	 * @return shoppingTrip
	 */
	public ShoppingTrip objectIsShoppingTrip(Object object) {
		ShoppingTrip trip = (ShoppingTrip) object;
		if (!(object instanceof ShoppingTrip)) {
			throw new IllegalArgumentException("Object is not instance of ShoppingTrip");
		} else {
			return trip;
		}
	}
}

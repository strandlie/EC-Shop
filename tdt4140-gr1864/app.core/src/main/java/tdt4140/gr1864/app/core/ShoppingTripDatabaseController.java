package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import interfaces.DatabaseCRUD;
import tdt4140.gr1864.app.core.storage.ShopDatabaseController;

public class ShoppingTripDatabaseController implements DatabaseCRUD {

	Connection connection;
	PreparedStatement statement;
	
	public ShoppingTripDatabaseController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see interfaces.DatabaseCRUD#create(java.lang.Object)
	 */
	@Override
	public int create(Object object) {
		ShoppingTrip trip = this.objectIsShoppingTrip(object);
		String sql = "INSERT INTO shopping_trip "
					+ "(customer_id, shop_id) "
					+ "VALUES (?, ?)";
					
		try {
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setInt(1, trip.getCustomer().getUserId());
			statement.setInt(2, trip.getShop().getShopID());
			statement.executeUpdate();
		
			try {
				// Retrieves all generated keys and returns the ID obtained by java object
				// which is inserted into the database
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					return Math.toIntExact(generatedKeys.getLong(1));
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
	 * @see interfaces.DatabaseCRUD#update(java.lang.Object)
	 */
	@Override
	public void update(Object object) {
		ShoppingTrip trip = this.objectIsShoppingTrip(object);
		String sql = "UPDATE shopping_trip "
					+ "SET customer_id=?, shop_id=? "
					+ "WHERE shopping_trip_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, trip.getCustomer().getUserId());
			statement.setInt(2, trip.getShop().getShopID());
			statement.setInt(3, trip.getShoppingTripID());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Retrieves all ShoppingTrips in database as a List<> 
	 * @return List<ShoppingTrip> trips of all ShoppingTrips in database
	 */
	public List<ShoppingTrip> retrieveAll() {
		String sql = "SELECT * "
					+ "FROM shopping_trip";
		try {
			statement = connection.prepareStatement(sql);
			ResultSet rs = statement.executeQuery();
			
			CustomerDatabaseController cdc = new CustomerDatabaseController();
			ShopDatabaseController sdc = new ShopDatabaseController();
			ShoppingTrip trip;
			List<ShoppingTrip> trips = new ArrayList<>();

			while (rs.next()) {
				trip = new ShoppingTrip(
						rs.getInt("shopping_trip_id"), 
						cdc.retrieve(rs.getInt("customer_id")),
						sdc.retrieve(rs.getInt("shop_id")));
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
	 * @see interfaces.DatabaseCRUD#retrieve(int)
	 */
	@Override
	public ShoppingTrip retrieve(int id) {
		String sql = "SELECT * "
					+ "FROM shopping_trip "
					+ "WHERE shopping_trip_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			CustomerDatabaseController cdc = new CustomerDatabaseController();
			ShopDatabaseController sdc = new ShopDatabaseController();
			ShoppingTrip trip = new ShoppingTrip(
					rs.getInt("shopping_trip_id"), 
					cdc.retrieve(rs.getInt("customer_id")),
					sdc.retrieve(rs.getInt("shop_id")));
			statement.close();
			return trip;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @see interfaces.DatabaseCRUD#delete(int)
	 */
	@Override
	public void delete(int id) {
		String sql = "DELETE FROM shopping_trip "
					+ "WHERE shopping_trip_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			
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
package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;
import tdt4140.gr1864.app.core.storage.Shop;
import tdt4140.gr1864.app.core.storage.ShopDatabaseController;

public class ShoppingTripDatabaseController implements DatabaseCRUD {

	/* connection to SQLite database */
	Connection connection;
	/* SQL statement executed on database */
	PreparedStatement statement;
	
	public ShoppingTripDatabaseController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/* Cannot be implemented until SHOP and CUSTOMER is implemented */
	@Override
	public int create(Object object) {
		ShoppingTrip trip = this.objectIsShoppingTrip(object);
		try {
			String sql = "INSERT INTO shopping_trip (customer_id, shop_id) VALUES (?, ?)";
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

	/* Cannot be implemented until SHOP and CUSTOMER is implemented */
	@Override
	public void update(Object object) {
		ShoppingTrip trip = this.objectIsShoppingTrip(object);
		String sql = "UPDATE shopping_trip SET customer_id=?, shop_id=? WHERE shopping_trip_id=?";
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

	@Override
	public ShoppingTrip retrieve(int id) {
		try {
			statement = connection
					.prepareStatement("SELECT * FROM shopping_trip "
										+ "WHERE shopping_trip_id=?");
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
			connection.close();
			return trip;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(int id) {
		try {
			statement = connection
					.prepareStatement("DELETE FROM shopping_trip WHERE shopping_trip_id=?");
			statement.setInt(1, id);
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public ShoppingTrip objectIsShoppingTrip(Object object) {
		ShoppingTrip trip = (ShoppingTrip) object;
		if (!(object instanceof ShoppingTrip)) {
			throw new IllegalArgumentException("Object is not instance of ShoppingTrip");
		} else {
			return trip;
		}
	}
}

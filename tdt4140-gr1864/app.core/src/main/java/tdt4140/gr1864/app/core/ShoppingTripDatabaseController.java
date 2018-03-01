package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;

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
		
		/*ShoppingTrip trip = this.objectIsShoppingTrip(object);
		
		try {
			String sql = "INSERT INTO shopping_trip (customer_id, shop_id) values (?, ?, ?, ?)";
			connection.prepareStatement(sql);
			statement.executeQuery();
			connection.close();
		
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
					
					
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		*/
		return -1;
	}

	/* Cannot be implemented until SHOP and CUSTOMER is implemented */
	@Override
	public void update(Object object) {
		
	}

	@Override
	public Object retrieve(int id) {
		try {
			statement = connection
					.prepareStatement("SELECT shopping_trip_id, customer_id, shop_id"
										+ "WHERE shopping_trip_id=?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}

			ShoppingTrip trip = new ShoppingTrip(rs.getInt(1), rs.getInt(2), rs.getInt(3));
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
					.prepareStatement("DELETE FROM shopping_trip WHERE shopping_trip_id=?)");
			statement.setInt(1, id);
			statement.executeQuery();
			connection.close();
			
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

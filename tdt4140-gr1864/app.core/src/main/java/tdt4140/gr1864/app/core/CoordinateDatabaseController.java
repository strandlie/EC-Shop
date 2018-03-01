package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;

public class CoordinateDatabaseController implements DatabaseCRUD{
	
	/* connection to SQLite database */
	Connection connection;
	/* SQL statement executed on database */
	PreparedStatement statement;
	
	public CoordinateDatabaseController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int create(Object object) {
		Coordinate coord = this.objectIsCoordinate(object);
		try {
			String sql = "INSERT INTO coordinate (shopping_trip_id, timestamp, x, y) "
							+ "VALUES (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setInt(1, coord.getShoppingTrip().getShoppingTripID());
			statement.setString(2,  Long.toString(coord.getTimeStamp()));
			statement.setDouble(3, coord.getX());
			statement.setDouble(4, coord.getY());
			statement.executeUpdate();
			
			return coord.getShoppingTrip().getShoppingTripID();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public void update(Object object) {
		Coordinate coord = this.objectIsCoordinate(object);
		try {
			String sql = "UPDATE coordinate SET x=?, y=? "
					+ "WHERE shopping_trip_id=? and timestamp=?";	
			statement = connection.prepareStatement(sql);
			statement.setDouble(1,  coord.getX());
			statement.setDouble(2, coord.getY());
			statement.setInt(3,  coord.getShoppingTrip().getShoppingTripID());
			statement.setString(4, Long.toString(coord.getTimeStamp()));
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public Coordinate retrieve(int shopping_trip_id, long timestamp) {
		try {
			statement = connection
					.prepareStatement("SELECT * FROM coordinate "
										+ "WHERE shopping_trip_id=? AND timestamp=?");
			statement.setInt(1, shopping_trip_id);
			statement.setString(2, Long.toString(timestamp));
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}

			ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
			Coordinate coord = new Coordinate(
					rs.getDouble("x"), 
					rs.getDouble("y"), 
					rs.getString("timestamp"), 
					stdc.retrieve(rs.getInt("shopping_trip_id")));
			connection.close();
			return coord;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * TODO: Should return a LIST of Coordinates...
	 */
	@Deprecated
	@Override
	public Object retrieve(int id) {
		try {
			statement = connection
					.prepareStatement("SELECT shopping_trip_id, timestamp, x, y"
										+ "WHERE shopping_trip_id=?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}

			ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
			Coordinate coord = new Coordinate(
					rs.getDouble("x"), 
					rs.getDouble("y"), 
					rs.getString("timestamp"), 
					stdc.retrieve(rs.getInt("shopping_trip_id")));
			connection.close();
			/* not yet implemented */
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	@Deprecated
	@Override
	public void delete(int id) {
		System.err.println("not in use, see delete(int shopping_trip_id, long timestamp)");
	}

	public void delete(int shopping_trip_id, long timestamp) {
		try {
			statement = connection
					.prepareStatement("DELETE FROM coordinate "
							+ "WHERE shopping_trip_id=? AND timestamp=?");
			statement.setInt(1, shopping_trip_id);
			statement.setString(2,  Long.toString(timestamp));
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	public Coordinate objectIsCoordinate(Object object) {
		Coordinate coord = (Coordinate) object;
		if (!(object instanceof Coordinate)) {
			throw new IllegalArgumentException("Object is not instance of Coordinate");
		} else {
			return coord;
		}
	}


}

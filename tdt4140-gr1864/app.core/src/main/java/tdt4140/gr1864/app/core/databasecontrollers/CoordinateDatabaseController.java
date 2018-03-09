package tdt4140.gr1864.app.core.databasecontrollers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tdt4140.gr1864.app.core.Coordinate;
import tdt4140.gr1864.app.core.interfaces.DatabaseCRUD;

public class CoordinateDatabaseController implements DatabaseCRUD{
	
	PreparedStatement statement;

	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#create(java.lang.Object)
	 */
	@Override
	public int create(Object object) {
		Coordinate coord = this.objectIsCoordinate(object);
		String sql = "INSERT INTO coordinate "
					+ "(shopping_trip_id, timestamp, x, y) "
					+ "VALUES (?, ?, ?, ?)";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			statement = connection.prepareStatement(sql);
			statement.setInt(1, coord.getShoppingTrip().getShoppingTripID());
			statement.setString(2,  Long.toString(coord.getTimeStamp()));
			statement.setDouble(3, coord.getX());
			statement.setDouble(4, coord.getY());
			statement.executeUpdate();
			connection.close();
			
			return coord.getShoppingTrip().getShoppingTripID();
			
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
		Coordinate coord = this.objectIsCoordinate(object);
		String sql = "UPDATE coordinate "
					+ "SET x=?, y=? "
					+ "WHERE shopping_trip_id=? and timestamp=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			statement = connection.prepareStatement(sql);
			statement.setDouble(1,  coord.getX());
			statement.setDouble(2, coord.getY());
			statement.setInt(3,  coord.getShoppingTrip().getShoppingTripID());
			statement.setString(4, Long.toString(coord.getTimeStamp()));
			statement.executeUpdate();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Returns coordinate based on shopping_trip_id and timeStamp
	 * @param shopping_trip_id	id of trip this coordinate is part of
	 * @param timestamp			time of read
	 * @return coordinate
	 */
	public Coordinate retrieve(int shopping_trip_id, long timestamp) {
		String sql = "SELECT * "
					+ "FROM coordinate "
					+ "WHERE shopping_trip_id=? AND timestamp=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shopping_trip_id);
			statement.setString(2, Long.toString(timestamp));
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				connection.close();
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

	/* Deprecated since Coordinate has joint primary-key
	 * Might need functionality of function later
	 */
	@Deprecated
	@Override
	public Object retrieve(int id) {
		String sql = "SELECT shopping_trip_id, timestamp, x, y "
					+ "WHERE shopping_trip_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				connection.close();
				return null;
			}

			connection.close();
			/* not yet implemented */
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/* Deprecated since Coordinate has joint primary-key */
	@Deprecated
	@Override
	public void delete(int id) {
		System.err.println("not in use, see delete(int shopping_trip_id, long timestamp)");
	}

	/**
	 * deletes coordinate from database based on shopping_trip_id and timeStamp
	 * @param shopping_trip_id	id of trip coordinate is a part of 
	 * @param timestamp			time of read
	 */
	public void delete(int shopping_trip_id, long timestamp) {
		String sql = "DELETE FROM coordinate "
					+ "WHERE shopping_trip_id=? AND timestamp=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shopping_trip_id);
			statement.setString(2,  Long.toString(timestamp));
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}	
	}

	/**
	 * checks if incoming object is a Coordinate
	 * @param object		suspected coordinate
	 * @return coordinate
	 */
	public Coordinate objectIsCoordinate(Object object) {
		Coordinate coord = (Coordinate) object;
		if (!(object instanceof Coordinate)) {
			throw new IllegalArgumentException("Object is not instance of Coordinate");
		} else {
			return coord;
		}
	}
}
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
			String sql = "INSERT INTO coordinate (shopping_trip_id, timestamp, x, y) values (?, ?, ?, ?)";
			connection.prepareStatement(sql);
			statement.setInt(1, coord.getShoppingTripID());
			statement.setString(2,  Long.toString(coord.getTimeStamp()));
			statement.setDouble(3, coord.getX());
			statement.setDouble(4, coord.getY());
			statement.executeQuery();
			connection.close();
			
			return coord.getShoppingTripID();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public void update(Object object) {
		Coordinate coord = this.objectIsCoordinate(object);
		try {
			String sql = "UPDATE coordinate SET timestamp=?, x=?, y=?"
					+ "WHERE shopping_trip_id=? and timestamp=?";	
			connection.prepareStatement(sql);
			statement.setString(2, Long.toString(coord.getTimeStamp()));
			statement.setDouble(3,  coord.getX());
			statement.setDouble(4, coord.getY());
			statement.setInt(1,  coord.getShoppingTripID());
			statement.setString(2, Long.toString(coord.getTimeStamp()));
			statement.executeQuery();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

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
			return coord;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(int id) {
		try {
			statement = connection
					.prepareStatement("DELETE FROM coordinate WHERE shopping_trip_id=?)");
			statement.setInt(1, id);
			statement.executeQuery();
			connection.close();
			
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

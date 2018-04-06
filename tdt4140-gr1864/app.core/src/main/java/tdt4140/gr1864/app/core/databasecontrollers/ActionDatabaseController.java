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
import tdt4140.gr1864.app.core.interfaces.DatabaseCRUD;

public class ActionDatabaseController implements DatabaseCRUD {
	
	PreparedStatement statement;
	String dbPath;
	
	public ActionDatabaseController() {
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
		Action action = objectIsAction(object);
		String sql = "INSERT INTO action "
					+ "(timestamp, action_type, product_id, shopping_trip_id) "
					+ "values (?, ?, ?, ?)";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setString(1,  Long.toString(action.getTimeStamp()));
			statement.setInt(2, action.getActionType());
			statement.setInt(3, action.getProduct().getID());
			statement.setInt(4, action.getShoppingTrip().getID());
			statement.executeUpdate();
			connection.close();
			
			return action.getShoppingTrip().getID();

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
		Action action = this.objectIsAction(object);
		String sql = "UPDATE action "
					+ "SET action_type=?, product_id=? "
					+ "WHERE shopping_trip_id=? and timestamp=?";		
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, action.getActionType());
			statement.setInt(2, action.getProduct().getID());
			statement.setInt(3, action.getShoppingTrip().getID());
			statement.setString(4, Long.toString(action.getTimeStamp()));
			statement.executeUpdate();
			connection.close();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Returns action based on shopping_trip_id and timeStamp
	 * @param shopping_trip_id	id of trip this action is part of
	 * @param timestamp			time of action
	 * @return action
	 */
	public Action retrieve(int shopping_trip_id, long timestamp) {
		String sql = "SELECT * "
					+ "FROM action "
					+ "WHERE shopping_trip_id=? AND timestamp=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shopping_trip_id);
			statement.setString(2, Long.toString(timestamp));
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				connection.close();
				return null;
			}

			ProductDatabaseController pdc = new ProductDatabaseController();
			ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
			Action action = new Action(
					rs.getString("timestamp"), 
					rs.getInt("action_type"), 
					pdc.retrieve(rs.getInt("product_id")),
					stdc.retrieve(rs.getInt("shopping_trip_id")));
			connection.close();
			return action;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * Gets all the actions for a shopping trip with a given ID
	 * @param shopping_trip_id int the shopping trip ID
	 * @return List<Action> The list of all the actions for this shoppingTrip, or null if none
	 */
	public List<Action> retrieveAll(int shopping_trip_id) {
		String sql = "SELECT * "
					+ "FROM action "
					+ "WHERE shopping_trip_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shopping_trip_id);
			ResultSet rs = statement.executeQuery();
			
			ProductDatabaseController pdc = new ProductDatabaseController();
			ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
			List<Action> actions = new ArrayList<>();
			while (rs.next()) {
				Action action = new Action(
						rs.getString("timestamp"), 
						rs.getInt("action_type"), 
						pdc.retrieve(rs.getInt("product_id")),
						stdc.retrieve(rs.getInt("shopping_trip_id")));
				actions.add(action);
			}
			connection.close();
			return actions;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* Deprecated since Action has joint primary-key
	 * Might need functionality of function later
	 */
	@Deprecated
	@Override
	public Object retrieve(int shopping_trip_id) {
		String sql = "SELECT * "
					+ "FROM action "
					+ "WHERE shopping_trip_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shopping_trip_id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				connection.close();
				return null;
			}
			connection.close();
			return null;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/* Deprecated since Action has joint primary-key */
	@Deprecated
	@Override
	public void delete(int id) {
		System.err.println("not in use, see delete(int shopping_trip_id, long timestamp)");
	}

	/**
	 * deletes action from database based on shopping_trip_id and timeStamp
	 * @param shopping_trip_id	id of trip action is a part of
	 * @param timestamp			time of action
	 */
	public void delete(int shopping_trip_id, long timestamp) {
		String sql = "DELETE FROM action "
					+ "WHERE shopping_trip_id=? "
					+ "AND timestamp=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
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
	 * checks if incoming object is an Action
	 * @param object 	suspected action
	 * @return action
	 */
	public Action objectIsAction(Object object) {
		Action action = (Action) object;
		if (!(object instanceof Action)) {
			throw new IllegalArgumentException("Object is not instance of Action");
		} else {
			return action;
		}
	}
}

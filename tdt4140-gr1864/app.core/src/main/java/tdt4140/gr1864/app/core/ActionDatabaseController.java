package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;

public class ActionDatabaseController implements DatabaseCRUD {
	
	Connection connection;
	PreparedStatement statement;
	
	public ActionDatabaseController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * @see interfaces.DatabaseCRUD#create(java.lang.Object)
	 */
	@Override
	public int create(Object object) {
		Action action = objectIsAction(object);
		String sql = "INSERT INTO action "
					+ "(timestamp, action_type, product_id, shopping_trip_id) "
					+ "values (?, ?, ?, ?)";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1,  Long.toString(action.getTimeStamp()));
			statement.setInt(2, action.getActionType());
			statement.setInt(3, action.getProduct().getID());
			statement.setInt(4, action.getShoppingTrip().getShoppingTripID());
			statement.executeUpdate();
			statement.close();
			
			return action.getShoppingTrip().getShoppingTripID();

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
		Action action = this.objectIsAction(object);
		String sql = "UPDATE action "
					+ "SET action_type=?, product_id=? "
					+ "WHERE shopping_trip_id=? and timestamp=?";		
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, action.getActionType());
			statement.setInt(2, action.getProduct().getID());
			statement.setInt(3, action.getShoppingTrip().getShoppingTripID());
			statement.setString(4, Long.toString(action.getTimeStamp()));
			statement.executeUpdate();
			statement.close();

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
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shopping_trip_id);
			statement.setString(2, Long.toString(timestamp));
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}

			ProductDatabaseController pdc = new ProductDatabaseController();
			ShoppingTripDatabaseController stdc = new ShoppingTripDatabaseController();
			Action action = new Action(
					rs.getString("timestamp"), 
					rs.getInt("action_type"), 
					pdc.retrieve(rs.getInt("product_id")),
					stdc.retrieve(rs.getInt("shopping_trip_id")));
			statement.close();
			return action;

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
					+ "WHERE shopping_trip_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shopping_trip_id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
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
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shopping_trip_id);
			statement.setString(2,  Long.toString(timestamp));
			statement.executeUpdate();
			statement.close();
			
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
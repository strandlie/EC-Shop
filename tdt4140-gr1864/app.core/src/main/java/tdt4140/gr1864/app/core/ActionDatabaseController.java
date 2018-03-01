package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;

public class ActionDatabaseController implements DatabaseCRUD {
	
	/* connection to SQLite database */
	Connection connection;
	/* SQL statement executed on database */
	PreparedStatement statement;
	
	public ActionDatabaseController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public int create(Object object) {
		Action action = objectIsAction(object);
		try {
			String sql = "INSERT INTO action (timestamp, action_type, product_id, shopping_trip_id) "
										+ "values (?, ?, ?, ?)";
			statement = connection.prepareStatement(sql);
			statement.setString(1,  Long.toString(action.getTimeStamp()));
			statement.setInt(2, action.getActionType());
			statement.setInt(3, action.getProduct().getID());
			statement.setInt(4, action.getShoppingTrip().getShoppingTripID());
			statement.executeUpdate();
			
			return action.getShoppingTrip().getShoppingTripID();

		} catch (SQLException e) {
			System.out.println("action fail");
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public void update(Object object) {
		Action action = this.objectIsAction(object);
		try {
			String sql = "UPDATE action SET action_type=?, product_id=? "
						+ "WHERE shopping_trip_id=? and timestamp=?";		
			statement=connection.prepareStatement(sql);
			statement.setInt(1, action.getActionType());
			statement.setInt(2, action.getProduct().getID());
			statement.setInt(3, action.getShoppingTrip().getShoppingTripID());
			statement.setString(4, Long.toString(action.getTimeStamp()));
			statement.executeUpdate();

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Action retrieve(int shopping_trip_id, long timestamp) {
		try {
			statement = connection
					.prepareStatement("SELECT * FROM action "
										+ "WHERE shopping_trip_id=? AND timestamp=?");
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
			connection.close();
			return action;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * TODO: Should return a LIST of Actions...
	 */
	@Deprecated
	@Override
	public Object retrieve(int shopping_trip_id) {
		try {
			statement = connection
					.prepareStatement("SELECT shopping_trip_id, timestamp, action_type, product_id, action_id "
										+ "WHERE shopping_trip_id=?");
			statement.setInt(1, shopping_trip_id);
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
			connection.close();
			/* not implemented yet */
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
					.prepareStatement("DELETE FROM action WHERE shopping_trip_id=? "
							+ "AND timestamp=?");
			statement.setInt(1, shopping_trip_id);
			statement.setString(2,  Long.toString(timestamp));
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	public Action objectIsAction(Object object) {
		Action action = (Action) object;
		if (!(object instanceof Action)) {
			throw new IllegalArgumentException("Object is not instance of Action");
		} else {
			return action;
		}
	}

}

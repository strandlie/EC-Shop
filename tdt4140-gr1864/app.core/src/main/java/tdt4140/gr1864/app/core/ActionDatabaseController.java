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
			String sql = "INSERT INTO action (timestamp, actionType, product_id, shopping_trip_id=?) "
										+ "values (?, ?, ?, ?)";
			statement=connection.prepareStatement(sql);
			statement.setString(1,  Long.toString(action.getTimeStamp()));
			statement.setInt(2, action.getActionType());
			statement.setInt(3, action.getProductID());
			statement.setInt(4, action.getShoppingTripID());
			statement.executeQuery();
			connection.close();
			
			return action.getShoppingTripID();

		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	@Override
	public void update(Object object) {
		Action action = this.objectIsAction(object);
		try {
			String sql = "UPDATE action SET timestamp=?, actionType=?, product_id=?"
						+ "WHERE shopping_trip_id=? and timestamp=?";		
			statement=connection.prepareStatement(sql);
			statement.setString(2, Long.toString(action.getTimeStamp()));
			statement.setInt(3, action.getActionType());
			statement.setInt(4, action.getProductID());
			statement.setInt(1, action.getShoppingTripID());
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
					.prepareStatement("SELECT shopping_trip_id, timestamp, actionType, product_id, action_id"
										+ "WHERE shopping_trip_id=?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}

			Action action = new Action(rs.getString(2), rs.getInt(3), rs.getInt(4), rs.getInt(1));
			connection.close();
			return action;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(int id) {
		try {
			statement = connection
					.prepareStatement("DELETE FROM action WHERE shopping_trip_id=?)");
			statement.setInt(1, id);
			statement.executeQuery();
			connection.close();
			
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

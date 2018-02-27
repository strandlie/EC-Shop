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
	
	public void write(Action action, String sql) {
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, action.getShoppingTripID());
			statement.setInt(2, action.getTimeStamp());
			statement.setInt(3, action.getActionType());
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create(Object object) {
		Action action = objectIsAction(object);
		String sql = "INSERT INTO action (shopping_trip_id, timestamp, type, product_id) "
										+ "values (?, ?, ?, ?)";
		this.write(action, sql);
	}

	@Override
	public void update(Object object) {
		Action action = this.objectIsAction(object);
		String sql = "UPDATE action SET timestamp=?, type=?";
		this.write(action, sql);
	}

	@Override
	public Object retrieve(int id) {
		try {
			statement = connection
					.prepareStatement("SELECT shopping_trip_id, timestamp, type, product_id"
										+ "where shopping_trip_id=?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}

			Action action = new Action(rs.getInt(2), rs.getInt(3), rs.getInt(4), rs.getInt(1));
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
			throw new IllegalArgumentException();
		} else {
			return action;
		}
	}

}

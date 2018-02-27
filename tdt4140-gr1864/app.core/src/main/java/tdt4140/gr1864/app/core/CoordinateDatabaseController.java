package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
	
	public void write(Coordinate coord, String sql) {
		try {
			statement = connection.prepareStatement(sql);
			statement.setInt(1, coord.getShoppingTripID());
			statement.setInt(2, coord.getTimeStamp());

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void create(Object object) {
		
	}

	@Override
	public void update(Object object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object retrieve(int id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void delete(int id) {
		// TODO Auto-generated method stub
		
	}

}

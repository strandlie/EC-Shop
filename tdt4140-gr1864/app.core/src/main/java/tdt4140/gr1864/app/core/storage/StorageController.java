package tdt4140.gr1864.app.core.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;

public class StorageController implements DatabaseCRUD{
	
	/* connection to SQLite database */
	Connection connection;
	/* SQL statement executed on database */
	PreparedStatement statement;

	
	public StorageController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public int write(Storage storage, String sql) {
		try {
			statement = connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
			
			// Object has been given an ID:
			if (storage.getID() instanceof Integer) {
				statement.setInt(1, storage.getShopID());
			} else {
				statement.setInt(1, storage.getID());
				statement.setInt(2, storage.getShopID());
			}
			
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
			
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	
	
	@Override
	public int create(Object object) {
		Storage storage = (Storage) object;
		
	}
	
	
	/* Check if object is right object */
	public boolean isRightObject(Object object) {
		if (!(object instanceof Storage)) {
			System.out.println("Object isn't storage");
			return false;
		}
		else {return true;}
	}
}

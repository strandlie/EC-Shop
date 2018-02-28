package tdt4140.gr1864.app.core.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;

import interfaces.DatabaseCRUD;


public class ShopDatabaseController implements DatabaseCRUD{
	
	/* connection to SQLite database */
	Connection connection;
	/* SQL statement executed on database */
	PreparedStatement statement;
	
	
	public ShopDatabaseController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public int write(Shop shop, String sql) {
		try {
			statement = connection.prepareStatement(sql, statement.RETURN_GENERATED_KEYS);
			
			// Object has been given an ID:
			statement.setString(1, shop.getAddress());
			statement.setInt(2, shop.getZip());
			statement.executeUpdate();
			
			try {
				// Retrieves all generated keys and returns the ID obtained by java object
				// which is inserted into the database
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					connection.close();
					return Math.toIntExact(generatedKeys.getLong(1));
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		connection.close();
		return -1;
	}


	@Override
	public int create(Object object) {
		Shop shop = (Shop) object;
		String sql = "INSERT INTO Shop (address, zip) "
				+ "VALUES (?, ?)";
		return this.write(shop, sql);
	}
	
	
	@Override
	public void update(Object object) {
		Shop shop = (Shop) object;
		String sql = "Update Shop (address, zip) "
				+ "VALUES (?, ?)";
		this.write(shop, sql);
	}
	
	@Override
	public Shop retrieve(int shopID) {
		try {
			statement = connection
					.prepareStatement("SELECT shop_id, address, zip"
										+ "WHERE shop_id=?");
			statement.setInt(1, shopID);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			Shop shop = new Shop(rs.getInt(1), rs.getString(2), rs.getInt(3));
			connection.close();
			return shop;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
		@Override
		public void delete(int id) {
			try {
				statement = connection.prepareStatement("DELETE FROM shop WHERE shop_id=?)");
				statement.setInt(1, id);
				statement.executeQuery();
				connection.close();
				
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		
	}

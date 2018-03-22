package tdt4140.gr1864.app.core.databasecontrollers;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tdt4140.gr1864.app.core.Shop;
import tdt4140.gr1864.app.core.interfaces.DatabaseCRUD;

public class ShopDatabaseController implements DatabaseCRUD{
	
	/* SQL statement executed on database */
	PreparedStatement statement;
	String dbPath;
	
	public ShopDatabaseController() {
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
	
	@Override
	public int create(Object object) {
		Shop shop = this.objectIsShop(object);
		String sql = "INSERT INTO shop(address, zip)"
				+ " VALUES (?, ?)";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			// Object has been given an ID:
			statement.setString(1, shop.getAddress());
			statement.setInt(2, shop.getZip());
			statement.executeUpdate();
			
			try {
				// Retrieves all generated keys and returns the ID obtained by java object
				// which is inserted into the database
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int i = Math.toIntExact(generatedKeys.getLong(1));
					connection.close();
					return i;
				}
				connection.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	@Override
	public void update(Object object) {
		Shop shop = this.objectIsShop(object);
		String sql = "UPDATE shop SET address=?, zip=? WHERE shop_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			
			// Object has been given an ID:
			statement.setString(1, shop.getAddress());
			statement.setInt(2, shop.getZip());
			statement.setInt(3, shop.getShopID());
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public Shop retrieve(int shopID) {
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection
					.prepareStatement("SELECT * FROM shop"
										+ " WHERE shop_id=?");
			statement.setInt(1, shopID);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			Shop shop = new Shop(rs.getString("address"), rs.getInt("zip"), rs.getInt("shop_id"));
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
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement("DELETE FROM shop WHERE shop_id=?");
			statement.setInt(1, id);
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
		
	public Shop objectIsShop(Object object) {
		Shop shop = (Shop) object;
		if (!(object instanceof Shop)) {
			throw new IllegalArgumentException("Object is not instance of Shop!");
		} else {
			return shop;
		}
	}
}
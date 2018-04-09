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


/**
 * The database controller that links the amounts of products in storage or on shelfs, to the DB
 * 
 * @author stian
 */
public class OnShelfDatabaseController extends DatabaseController implements DatabaseCRUD {
	
	PreparedStatement statement;
	
	/**
	 * The DatabaseCRUD interface was not compatible with table for on_shelf, so its four methods have been deprecated and replaced
	 */
	@Deprecated
	@Override
	public int create(Object object) {
		throw new UnsupportedOperationException("create(Object object) method from DatabaseCRUD is no longer in use for OnShelf DBctrl");
	}
	
	/**
	 * Creates a new row in DB - updates the DB with the amounts of a product that are in storage and on the shelfs
	 * 
	 * Inputs are objects to secure the right id is used in the right place and to simplify the code
	 * 
	 * @param shop				Shop object
	 * @param productID			ID of product
	 */
	public void create(Shop shop, int productID) {
		String sql = "INSERT INTO on_shelf "
					+ "(shop_id, product_id, amount_on_shelfs, amount_in_storage) "
					+ "VALUES (?, ?, ?, ?)";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shop.getShopID());
			statement.setInt(2, productID);
			statement.setInt(3, shop.getAmountInShelfs(productID));
			statement.setInt(4, shop.getAmountInStorage(productID));
			statement.executeUpdate();
			connection.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Deprecated
	@Override
	public void update(Object object) {
		throw new UnsupportedOperationException("update(Object object) method from DatabaseCRUD is no longer in use for OnShelf DBctrl");
	}
	
	/**
	 * Updates the amounts of a product stored in DB
	 * 
	 * @param shop		The shop where the products are stored
	 * @param productID	ID of the product that's stored
	 */
	public void update(Shop shop, int productID) {
		String sql = "UPDATE on_shelf "
					+ "SET amount_on_shelfs=?, amount_in_storage=? "
					+ "WHERE shop_id=? AND product_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shop.getAmountInShelfs(productID));
			statement.setInt(2, shop.getAmountInStorage(productID));
			statement.setInt(3, shop.getShopID());
			statement.setInt(4, productID);
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	@Deprecated
	@Override
	public Object retrieve(int id){
		throw new UnsupportedOperationException("retrieve(int id) method from DatabaseCRUD is no longer in use for OnShelf DBctrl");
	}

	/**
	 * Retrieves the saved amounts from the DB and updates the shop object
	 * 
	 * @param shop		The shop where the products are stored
	 * @param productID	ID of the products that are stored
	 * 
	 * @return shop 	The updated shop object
	 */
	public Shop retrieve(Shop shop, int productID) {
		String sql = "SELECT amount_on_shelfs, amount_in_storage "
					+ "FROM on_shelf "
					+ "WHERE shop_id=? AND product_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shop.getShopID());
			statement.setInt(2, productID);
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				connection.close();
				return null;
			}
			
			//Update the shop object and return it
			shop.setAmountInShelfs(productID, rs.getInt("amount_on_shelfs"));
			shop.setAmountInStorage(productID, rs.getInt("amount_in_storage"));
			
			connection.close();
			return shop;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
		
	
	@Deprecated
	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("delete(int id) method from DatabaseCRUD is no longer in use for OnShelf DBctrl");
	}
	
	/**
	 * Deletes the DB entry where amounts are stored
	 * 
	 * @param shopID		The shop where the products are stored
	 * @param productID		The product that's stored
	 */
	public void delete(int shopID, int productID) {
		String sql = "DELETE FROM on_shelf "
					+ "WHERE shop_id=? AND product_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, shopID);
			statement.setInt(2, productID);
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
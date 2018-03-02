package tdt4140.gr1864.app.core.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;


/**
 * The database controller that links the amounts of products in storage or on shelfs, to the DB
 * 
 * @author stian
 */
public class OnShelfDatabaseController implements DatabaseCRUD{
	
	/* connection to SQLite database */
	Connection connection;
	/* SQL statement executed on database */
	PreparedStatement statement;
	
	
	public OnShelfDatabaseController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * The DatabaseCRUD interface was not compatible with table for on_shelf, so its four methods have been deprecated and replaced
	 */
	@Deprecated
	@Override
	public int create(Object object) {
		throw new UnsupportedOperationException("create(Object object) method from DatabaseCRUD is no longer in use");
	}
	
	/**
	 * Creates a new row in DB - updates the DB with the amounts of a product that are in storage and on the shelfs
	 * 
	 * Inputs are objects to secure the right id is used in the right place and to simplify the code
	 * 
	 * @param shop				Shop object
	 * @param product			Product object
	 */
	public void create(Shop shop, Product product) {
		String sql = "INSERT INTO on_shelf(shop_id, product_id, amount_on_shelfs, amount_in_storage)"
				+ " VALUES (?, ?, ?, ?)";
		
		try {
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, shop.getShopID());
			statement.setInt(2, product.getID());
			statement.setInt(3, shop.getAmountInShelfs(product.getID()));
			statement.setInt(4, shop.getAmountInStorage(product.getID()));
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
		throw new UnsupportedOperationException("update(Object object) method from DatabaseCRUD is no longer in use");
	}
	
	/**
	 * Updates the amounts of a product stored in DB
	 * 
	 * @param shop		The shop where the products are stored
	 * @param product	The product that's stored
	 */
	public void update(Shop shop, Product product) {
		String sql = "UPDATE on_shelf SET amount_on_shelfs=?, amount_in_storage=? WHERE shop_id=? AND product_id=?";
		try {
			statement = connection.prepareStatement(sql);
			
			statement.setInt(1, shop.getAmountInShelfs(product.getID()));
			statement.setInt(2, shop.getAmountInStorage(product.getID()));
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Deprecated
	@Override
	public Object retrieve(int id){
		throw new UnsupportedOperationException("retrieve(int id) method from DatabaseCRUD is no longer in use");
	}

	/**
	 * Retrieves the saved amounts from the DB and updates the shop object
	 * 
	 * @param shop		The shop where the products are stored
	 * @param product	The products that are stored
	 * 
	 * @return shop 	The updated shop object
	 */
	public Shop retrieve(Shop shop, Product product) {
		try {
			statement = connection.
					prepareStatement("SELECT amount_on_shelfs, amount_in_storage FROM on_shelf"
			+ " WHERE shop_id=? AND product_id=?");
			
			statement.setInt(1, shop.getShopID());
			statement.setInt(2, product.getID());
			
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}
			
			//Update and return the shop object
			shop.setAmountInShelfs(product.getID(), rs.getInt("amount_on_shelfs"));
			shop.setAmountInStorage(product.getID(), rs.getInt("amount_in_storage"));
			return shop;
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
		}
		
	
	@Deprecated
	@Override
	public void delete(int id) {
		throw new UnsupportedOperationException("delete(int id) method from DatabaseCRUD is no longer in use");
	}
	
	/**
	 * Deletes the DB entry where amounts are stored
	 * 
	 * @param shopID		The shop where the products are stored
	 * @param productID		The product that's stored
	 */
	public void delte(int shopID, int productID) {
		try {
			statement = connection.prepareStatement("DELETE FROM on_shelf WHERE shop_id=? AND product_id=?");
			statement.setInt(1, shopID);
			statement.setInt(2, productID);
			
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}

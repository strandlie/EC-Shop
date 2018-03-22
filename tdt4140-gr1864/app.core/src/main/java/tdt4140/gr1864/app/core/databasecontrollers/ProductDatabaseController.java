package tdt4140.gr1864.app.core.databasecontrollers;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tdt4140.gr1864.app.core.Product;
import tdt4140.gr1864.app.core.interfaces.DatabaseCRUD;

public class ProductDatabaseController implements DatabaseCRUD {

	PreparedStatement statement;
	String dbPath;
	
	public ProductDatabaseController() {
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
	
	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#create(java.lang.Object)
	 */
	@Override
	public int create(Object object) {
		Product product = objectIsProduct(object);
		String sql = "INSERT INTO product "
					+ "(name, price) "
					+ "VALUES (?, ?)";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, product.getName());
			statement.setDouble(2, product.getPrice());
			statement.executeUpdate();

			// Object has been given an ID first time creating and inserting into database
			try {
				// Retrieves all generated keys and returns the ID obtained by java object
				// which is inserted into the database
				ResultSet generatedKeys = statement.getGeneratedKeys();
				if (generatedKeys.next()) {
					int i = Math.toIntExact(generatedKeys.getLong(1));
					connection.close();
					return i;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return -1;
	}

	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#update(java.lang.Object)
	 */
	@Override
	public void update(Object object) {
		Product product = this.objectIsProduct(object);
		String sql = "UPDATE product "
					+ "SET name=?, price=? "
					+ "WHERE product_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setString(1, product.getName());
			statement.setDouble(2, product.getPrice());
			statement.setInt(3,  product.getID());
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#retrieve(int)
	 */
	@Override
	public Product retrieve(int id) {
		String sql = "SELECT product_id, name, price "
					+ "FROM product "
					+ "WHERE product_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			// Checks if product with id exists
			if (!rs.next()) {
				connection.close();
				return null;
			}
			
			// Creates product with (productID generated from table in sql, name and price)
			Product product = new Product(
					rs.getInt("product_id"), 
					rs.getString("name"), 
					rs.getDouble("price"));
			connection.close();
			return product;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#delete(int)
	 */
	@Override
	public void delete(int id) {
		String sql = "DELETE FROM product "
					+ "WHERE product_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setInt(1, id);
			statement.executeUpdate();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Checks if Object object is an instance of Product.
	 * @param object Usually a Product object
	 * @return The product object if the object is an instance of product
	 */
	public Product objectIsProduct(Object object) {
		Product product = (Product) object;
		if (!(object instanceof Product)) {
			throw new IllegalArgumentException("Object is not instance of Product");
		} else {
			return product;
		}
	}
}
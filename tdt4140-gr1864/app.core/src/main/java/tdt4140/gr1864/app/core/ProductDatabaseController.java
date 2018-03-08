package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;

public class ProductDatabaseController implements DatabaseCRUD {

	PreparedStatement statement;
	
	/**
	 * @see interfaces.DatabaseCRUD#create(java.lang.Object)
	 */
	@Override
	public int create(Object object) {
		Product product = objectIsProduct(object);
		String sql = "INSERT INTO product "
					+ "(name, price) "
					+ "VALUES (?, ?)";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
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
	 * @see interfaces.DatabaseCRUD#update(java.lang.Object)
	 */
	@Override
	public void update(Object object) {
		Product product = this.objectIsProduct(object);
		String sql = "UPDATE product "
					+ "SET name=?, price=? "
					+ "WHERE product_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
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
	 * @see interfaces.DatabaseCRUD#retrieve(int)
	 */
	@Override
	public Product retrieve(int id) {
		String sql = "SELECT product_id, name, price "
					+ "FROM product "
					+ "WHERE product_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
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
	 * @see interfaces.DatabaseCRUD#delete(int)
	 */
	@Override
	public void delete(int id) {
		String sql = "DELETE FROM product "
					+ "WHERE product_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
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
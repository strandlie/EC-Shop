package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;

public class ProductDatabaseController implements DatabaseCRUD {

	Connection connection;
	PreparedStatement statement;
	
	public ProductDatabaseController() {
		try {	
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * (non-Javadoc)
	 * @see interfaces.DatabaseCRUD#create(java.lang.Object)
	 */
	@Override
	public int create(Object object) {
		Product product = objectIsProduct(object);
		String sql = "INSERT INTO product (name, price)"
										+ " VALUES (?, ?)";
		
		try {
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			
			// When creating first time
			statement.setString(1, product.getName());
			statement.setDouble(2, product.getPrice());
				
			// Very important to execute before try-catch
			statement.executeUpdate();

			// Object has been given an ID first time creating and inserting into database
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

			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return -1;
	}

	/*
	 * (non-Javadoc)
	 * @see interfaces.DatabaseCRUD#update(java.lang.Object)
	 */
	@Override
	public void update(Object object) {
		Product product = this.objectIsProduct(object);
		String sql = "UPDATE product SET name=?, price=? WHERE product_id=?";
		
		try {
			statement = connection.prepareStatement(sql);
			
			// When creating first time
			// Object has been given an ID:
			statement.setString(1, product.getName());
			statement.setDouble(2, product.getPrice());
			statement.setInt(3,  product.getID());
			statement.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * @see interfaces.DatabaseCRUD#retrieve(int)
	 */
	@Override
	public Product retrieve(int id) {
		try {
			statement = connection
					.prepareStatement("SELECT product_id, name, price FROM product"
										+ " WHERE product_id=?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			// Checks if product with id exists
			if (!rs.next()) {
				return null;
			}
			
			// Creates product with (productID generated from table in sql, name and price)
			Product product = new Product(
					rs.getInt("product_id"), 
					rs.getString("name"), 
					rs.getDouble("price"));
			statement.close();
			return product;

		} catch (SQLException e) {
			System.out.println("product fail");
			e.printStackTrace();
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * @see interfaces.DatabaseCRUD#delete(int)
	 */
	@Override
	public void delete(int id) {
		try {
			statement = connection
					.prepareStatement("DELETE FROM product WHERE product_id=?");
			statement.setInt(1, id);
			statement.executeUpdate();
			
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

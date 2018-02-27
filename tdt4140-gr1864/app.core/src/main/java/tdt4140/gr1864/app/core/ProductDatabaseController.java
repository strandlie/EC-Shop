package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.Calendar;

import interfaces.DatabaseCRUD;

public class ProductDatabaseController implements DatabaseCRUD {

	/* connection to SQLite database */
	Connection connection;
	/* SQL statement executed on database */
	PreparedStatement statement;
	
	public ProductDatabaseController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Writes a Product object to the database with productID >= 0 if it has been retrieved 
	 * from the database before. If the object has not been retrieved at all, the productID will be -1.
	 * The productID will only be -1 the first time inserting. 
	 * @param product The product object being inserted into the table
	 * @param sql SQL code for sql operation
	 * @param productID The product objects given ID from sql.
	 */
	public void write(Product product, String sql, int productID) {
		try {
			statement = connection.prepareStatement(sql);
			
			// Object has been given an ID:
			if (productID != -1) {
				statement.setString(1, product.getName());
				statement.setDouble(2, product.getPrice());
			} else {
				statement.setInt(1, product.getID());
				statement.setString(2, product.getName());
				statement.setDouble(3, product.getPrice());
			}
			
			statement.executeUpdate();
			connection.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	@Override
	public void create(Object object) {
		Product product = objectIsProduct(object);
		String sql = "INSERT INTO product (name, price) "
										+ "values (?, ?)";
		this.write(product, sql, -1);
	}

	@Override
	public void update(Object object) {
		Product product = this.objectIsProduct(object);
		String sql = "UPDATE product SET name=?, price=? WHERE product_id=?";
		this.write(product, sql, product.getID());
	}

	public Object retrieve(int id) {
		try {
			statement = connection
					.prepareStatement("SELECT product_id, name, price"
										+ "WHERE product_id=?");
			statement.setInt(1, id);
			ResultSet rs = statement.executeQuery();
			
			if (!rs.next()) {
				return null;
			}

			Product product = new Product(rs.getInt(1), rs.getString(2), rs.getDouble(3));
			connection.close();
			return product;

		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public void delete(int id) {
		try {
			statement = connection
					.prepareStatement("DELETE FROM product WHERE product_id=?)");
			statement.setInt(1, id);
			statement.executeQuery();
			connection.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
	}
	
	public Product objectIsProduct(Object object) {
		Product product = (Product) object;
		if (!(object instanceof Product)) {
			throw new IllegalArgumentException("Object is not instance of Product");
		} else {
			return product;
		}
	}
}

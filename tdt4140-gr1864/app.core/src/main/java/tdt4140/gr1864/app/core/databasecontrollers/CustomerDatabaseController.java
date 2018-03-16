package tdt4140.gr1864.app.core.databasecontrollers;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.interfaces.DatabaseCRUD;

public class CustomerDatabaseController implements DatabaseCRUD {

	PreparedStatement statement;
	String dbPath;
	
	public CustomerDatabaseController() {
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
    	Customer customer = objectIsCustomer(object);
		String sql = "INSERT INTO customer (first_name, last_name) values (?, ?)";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
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
			} catch (SQLException e) {
				e.printStackTrace();
			}
			connection.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		return -1;
	}
    
    /**
     * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#retrieve(int)
     */
    @Override
    public Customer retrieve(int userId) {
		String sql = "SELECT * "
					+ "FROM customer "
					+ "WHERE customer_id = " + userId;
        try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            // Returned nothing
            if (!rs.next()) {
            	connection.close();
                return null;
            }
            Customer user = new Customer(
            		rs.getString("first_name"), 
            		rs.getString("last_name"), 
            		rs.getInt("customer_id"));
            statement.close();
            return user;
      
        } 
        catch (SQLException e) {
        	e.printStackTrace();
        }
		return null;
    }
    
    /**
     * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#update(java.lang.Object)
     */
    @Override
    public void update(Object object) {
    	Customer customer = objectIsCustomer(object);
    	String sql = "UPDATE customer "
    				+ "SET first_name=?, last_name=? "
    				+ "WHERE customer_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setInt(3, customer.getUserId());
			statement.executeUpdate();
			connection.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#delete(int)
     */
    @Override
    public void delete(int customerId) {
    	String sql = "DELETE FROM customer "
    				+ "WHERE customer_id=?";
        try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.executeUpdate();
            connection.close();
        } 
        catch (SQLException e) {
        	e.printStackTrace();
        }
    }
    
    /**
	 * Checks if Object object is an instance of Customer
	 * @param object 	suspected Customer object
	 * @return The customer object if the object is an instance of customer
	 */
	public Customer objectIsCustomer(Object object) {
		Customer customer = (Customer) object;
		if (!(object instanceof Customer)) {
			throw new IllegalArgumentException("Object is not instance of Customer");
		} else {
			return customer;
		}
	}
}
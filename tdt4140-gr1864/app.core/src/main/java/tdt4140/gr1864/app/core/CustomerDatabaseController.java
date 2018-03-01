package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import interfaces.DatabaseCRUD;

public class CustomerDatabaseController implements DatabaseCRUD {

	Connection connection;
	PreparedStatement statement;
	
	public CustomerDatabaseController() {
		try {	
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	} 

	/**
	 * (non-Javadoc)
	 * @see interfaces.DatabaseCRUD#create(java.lang.Object)
	 */
	@Override
    public int create(Object object) {
    	Customer customer = objectIsCustomer(object);
		String sql = "INSERT INTO customer (first_name, last_name) values (?, ?)";
		try {
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.executeUpdate();
				
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
		} 
		catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		return -1;
	}
    
    /*
     * (non-Javadoc)
     * @see interfaces.DatabaseCRUD#retrieve(int)
     */
    @Override
    public Customer retrieve(int userId) {
		String sql = "SELECT * "
					+ "FROM customer "
					+ "WHERE customer_id = " + userId;
        try {
            statement = connection.prepareStatement(sql);
            ResultSet rs = statement.executeQuery();

            // Returned nothing
            if (!rs.next()) {
                return null;
            }
            Customer user = new Customer(
            		rs.getString("first_name"), 
            		rs.getString("last_name"), 
            		rs.getInt("customer_id"));
            connection.close();
            return user;
      
        } 
        catch (SQLException e) {
        		System.err.println(e.getMessage());
        }
        
		return null;
    }
    
    /**
     * (non-Javadoc)
     * @see interfaces.DatabaseCRUD#update(java.lang.Object)
     */
    @Override
    public void update(Object object) {
    	Customer customer = objectIsCustomer(object);
    	String sql = "UPDATE customer "
    				+ "SET first_name=?, last_name=? "
    				+ "WHERE customer_id=?";
		try {
			statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setInt(3, customer.getUserId());
			statement.executeUpdate();
		} 
		catch (SQLException e) {
				System.err.println(e.getMessage());
		}
	}
    
    /**
     * (non-Javadoc)
     * @see interfaces.DatabaseCRUD#delete(int)
     */
    @Override
    public void delete(int customerId) {
    	String sql = "DELETE FROM customer "
    				+ "WHERE customer_id=?";
        try {
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerId);
            statement.executeUpdate();
        } 
        catch (SQLException e) {
        		System.err.println(e.getMessage());
        }
    }
    
    /**
	 * Checks if Object object is an instance of Customer
	 * @param object 	suspected Customer object
	 * @return The customer object if the object is an instance of customer
	 */
	public Customer objectIsCustomer(Object object) {
		Customer product = (Customer) object;
		if (!(object instanceof Customer)) {
			throw new IllegalArgumentException("Object is not instance of Customer");
		} else {
			return product;
		}
	}
}


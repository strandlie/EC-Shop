package tdt4140.gr1864.app.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import interfaces.DatabaseCRUD;

public class CustomerDatabaseCRUD implements DatabaseCRUD {
    
    public void create(Object object) {
    		if (object != null) {
	        try {
	        		Customer customer = (Customer) object;
	            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            
	        		PreparedStatement statement = connection
	                    .prepareStatement("INSERT INTO customer (id, first_name, last_name) values (?, ?, ?)");
	            statement.setInt(1, customer.getUserId());
	            statement.setString(2, customer.getFirstName());
	            statement.setString(3, customer.getLastName());
	            statement.executeUpdate();
	            connection.close();
            		

	        } 
	        catch (SQLException e) {
	  	      System.err.println(e.getMessage());
	        }
    		}
    }
    
    public Customer retrieve(int userId) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT id, first_name, last_name FROM customer WHERE id = ?",
                    		ResultSet.TYPE_FORWARD_ONLY, 
                            ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            
            // Returned nothing
            if (!rs.next()) {
                return null;
            }
            
            Customer user = new Customer(rs.getInt(1), rs.getString(2), rs.getString(3));
            connection.close();
            return user;
      
        } 
        catch (SQLException e) {
        		System.err.println(e.getMessage());
        }
        
		return null;
    }
    
    @Override
    public void update(Object object) {
        if (object != null) {
	        try {
	        		// casting to customer object
	        		Customer customer = (Customer) object;
	        	
	            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	            PreparedStatement statement = connection
	                    .prepareStatement("UPDATE customer SET first_name=?, last_name=? WHERE id=?");
	            statement.setString(1, customer.getFirstName());
	            statement.setString(2, customer.getLastName());
	            statement.setInt(3, customer.getUserId());
	            statement.executeUpdate();
	            connection.close();
	        } 
	        catch (SQLException e) {
	        		System.err.println(e.getMessage());
	        }
        }
    }
    
    public void delete(int customerId) {
        try {
        		Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement stmt = connection
                    .prepareStatement("DELETE FROM customer WHERE id=?");
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
            connection.close();
        } 
        catch (SQLException e) {
        		System.err.println(e.getMessage());
        }
    }
}


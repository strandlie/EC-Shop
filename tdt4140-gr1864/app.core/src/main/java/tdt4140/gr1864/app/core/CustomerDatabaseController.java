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
    
    public int create(Object object) {
    		if (object != null) {
				try {
					Customer customer = (Customer) object;
					Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
					
					String sql = "INSERT INTO customer (first_name, last_name) values (?, ?)";
					PreparedStatement statement = connection
						.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
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
    		}
			return -1;
    }
    
    public Customer retrieve(int userId) {
        try {
            Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
            PreparedStatement statement = connection
                    .prepareStatement("SELECT * FROM customer WHERE customer_id= ?",
                    		ResultSet.TYPE_FORWARD_ONLY, 
                            ResultSet.CONCUR_READ_ONLY);
            statement.setInt(1, userId);
            ResultSet rs = statement.executeQuery();
            
            // Returned nothing
            if (rs == null) {
                return null;
            }
            
            Customer user = new Customer(rs.getString("first_name"), rs.getString("last_name"), rs.getInt("customer_id"));
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
	                    .prepareStatement("UPDATE customer SET first_name=?, last_name=? WHERE customer_id=?");
	            statement.setString(1, customer.getFirstName());
	            statement.setString(2, customer.getLastName());
	            statement.setInt(3, customer.getUserId());
	            statement.executeUpdate();
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
                    .prepareStatement("DELETE FROM customer WHERE customer_id=?");
            stmt.setInt(1, customerId);
            stmt.executeUpdate();
        } 
        catch (SQLException e) {
        		System.err.println(e.getMessage());
        }
    }
}


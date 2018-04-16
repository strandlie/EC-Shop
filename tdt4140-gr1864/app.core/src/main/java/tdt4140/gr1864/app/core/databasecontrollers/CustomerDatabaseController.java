package tdt4140.gr1864.app.core.databasecontrollers;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import tdt4140.gr1864.app.core.Coordinate;
import tdt4140.gr1864.app.core.Customer;
import tdt4140.gr1864.app.core.interfaces.DatabaseCRUD;

public class CustomerDatabaseController extends DatabaseController implements DatabaseCRUD {

	PreparedStatement statement;
	
	/**
	 * @see tdt4140.gr1864.app.core.interfaces.DatabaseCRUD#create(java.lang.Object)
	 */
	@Override
    public int create(Object object) {
    	Customer customer = objectIsCustomer(object);
		String sql = "INSERT INTO customer (first_name, last_name, address, zip, gender, age, num_persons_in_household, anonymous, deleted) "
					+ "values (?, ?, ?, ?, ?, ?, ?, ?, ?)";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql, PreparedStatement.RETURN_GENERATED_KEYS);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getAddress());
			statement.setInt(4, customer.getZip());
			statement.setString(5, customer.getGender());
			statement.setInt(6, customer.getAge());
			statement.setInt(7, customer.getNumberOfPersonsInHousehold());
			statement.setBoolean(8, customer.getAnonymous());
			statement.setBoolean(9, customer.isDeleted());
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
    public Customer retrieve(int customerID) {
		String sql = "SELECT * "
					+ "FROM customer "
					+ "WHERE customer_id=?";
        try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
            statement = connection.prepareStatement(sql);
            statement.setInt(1, customerID);
            ResultSet rs = statement.executeQuery();

            // Returned nothing
            if (!rs.next()) {
            	connection.close();
                return null;
            }
            
            Customer user = new Customer(
            		rs.getString("first_name"), 
            		rs.getString("last_name"), 
            		rs.getString("address"),
            		rs.getInt("zip"),
					rs.getString("gender"),
					rs.getInt("age"),
					rs.getInt("num_persons_in_household"),
            		rs.getBoolean("anonymous"),
					rs.getInt("customer_id"),
					rs.getBoolean("deleted")
            		);
            statement.close();
            return user;
      
        } 
        catch (SQLException e) {
        	e.printStackTrace();
        }
		return null;
    }
    
    /**
     * Returns all Customers in the database
     * @return ArrayList of Customers
     */
    public List<Customer> retrieveAll() {
    	String sql = "SELECT * FROM customer";
    	try {
    		Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
    		statement = connection.prepareStatement(sql);
    		ResultSet rs = statement.executeQuery();

    		List<Customer> customers = new ArrayList<>();
    		while (rs.next()) {
				Customer customer = new Customer(
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("address"),
						rs.getInt("zip"),
						rs.getString("gender"),
						rs.getInt("age"),
						rs.getInt("num_persons_in_household"),
						rs.getBoolean("anonymous"),
						rs.getInt("customer_id"),
						rs.getBoolean("deleted"));
    			customers.add(customer);
    		}
    		connection.close();
    		return customers;
			
		} catch (SQLException e) {
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
    				+ "SET first_name=?, last_name=?, "
    				+ "address=?, zip=?, gender=?, "
    				+ "age=?, num_persons_in_household=?, "
    				+ "anonymous=?, deleted=? "
    				+ "WHERE customer_id=?";
		try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
			statement = connection.prepareStatement(sql);
			statement.setString(1, customer.getFirstName());
			statement.setString(2, customer.getLastName());
			statement.setString(3, customer.getAddress());
			statement.setInt(4, customer.getZip());
			statement.setString(5, customer.getGender());
			statement.setInt(6, customer.getAge());
			statement.setInt(7, customer.getNumberOfPersonsInHousehold());
			statement.setBoolean(8, customer.getAnonymous());
			statement.setBoolean(9, customer.isDeleted());
			statement.setInt(10, customer.getID());
			statement.executeUpdate();
			connection.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}


	}

    /**
     * Deletes all values from a Customer
     * @param customerID	ID of Customer to "delete"
     */
    @Override
    public void delete(int customerID) {
    	Customer customer = this.retrieve(customerID);
    	customer.setDeleted(true);
    	this.update(customer);
    }
    
    /**
	 * Checks if Object object is an instance of Customer
	 * @param object 	suspected Customer object
	 * @return The customer object if the object is an instance of customer
	 */
	public Customer objectIsCustomer(Object object) {
		try {
			Customer c = (Customer) object;
			return c;
		} catch (ClassCastException e) {
			throw new IllegalArgumentException("Object is not Customer");
		}
	}
	
	/**
	 * Counts amount of customers and returning the amount
	 * @return int	The amount of customers registered in the database
	 */
	public int countCustomers() {
		String sql = "SELECT COUNT(*) FROM customer";
	    try {
			Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	        statement = connection.prepareStatement(sql);
	        ResultSet rs = statement.executeQuery();
	        // Returned nothing
	        if (!rs.next()) {
	        	System.out.println("closed");
	        	connection.close();
	            return 0;
	        }
	        int countCustomer = rs.getInt(1);

	        statement.close();
	        return countCustomer;
	  
	    } catch (SQLException e) {
	    	e.printStackTrace();
	    }
		return 0;
	}
}
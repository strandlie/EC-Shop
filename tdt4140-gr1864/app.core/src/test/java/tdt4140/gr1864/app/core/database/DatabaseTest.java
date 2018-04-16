package tdt4140.gr1864.app.core.database;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.runners.MethodSorters;

import tdt4140.gr1864.app.core.database.DatabaseWiper;

import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {
	
	/*
	 * Setting up database before running tests
	 * Checks for occurence of database before creating one
	 */
	
	static String dbPath;
	
	@BeforeClass
	public static void setup() throws IOException {
		DatabaseWiper viper = new DatabaseWiper();
		viper.wipe();
		String path = "../../../app.core/src/main/resources/database.db";
		String relativePath;
		//Finds path by getting URL and converting to URI and then to path 
		try {
			URI rerelativeURI = DatabaseTest.class.getClassLoader().getResource(".").toURI();
			relativePath = Paths.get(rerelativeURI).toFile().toString() + "/";
			
		} catch (URISyntaxException e1) {
			//If fail to convert to URI use URL path instead
			relativePath = DatabaseTest.class.getClassLoader().getResource(".").getPath();
		} 
		dbPath = relativePath + path;
	}
	
	@Test
	public void testDatabaseFunctionality() throws SQLException {
		// Test if connection throws exception
		connection();
		
		// Test if writing throws exception
		write();
		
		// Test if select throws exception
		select();

		// Test if delete throws exception
		delete();
	}
	
	// Method for test purpose.
	public void connection() {
		
		Connection connection = null;
      
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		assertNotEquals(connection, null);
	}

	// Method for test purpose.
	public void write() {
		Connection connection = null;
		java.sql.Statement statement = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	         
	         statement = connection.createStatement();
	         String sql = "INSERT INTO customer (customer_id, first_name, last_name, anonymous) " +
	                        "VALUES (-1, 'Ben', 'Ten', 'false');"; 
	         statement.executeUpdate(sql);
	         statement.close();
	         connection.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	}

	// Method for test purpose.
	public void select() throws SQLException {
		Connection connection = null;
		java.sql.Statement statement = null;
	    ResultSet resultSet = null; 
		
	     try {
	         Class.forName("org.sqlite.JDBC");
	         connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	         
	         statement = connection.createStatement();
	         String sql = "SELECT * FROM customer WHERE customer_id = -1;";
	         
	         resultSet = statement.executeQuery(sql);
	         assertEquals(resultSet.next(), true);
	         statement.close();
	         connection.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	}

	// Method for test purpose.
	public void delete() throws SQLException {
		Connection connection = null;
		java.sql.Statement statement = null;
	    ResultSet resultSet = null;   
		
	    try {
	         Class.forName("org.sqlite.JDBC");
	         connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
	         
	         statement = connection.createStatement();
	         String sql = "DELETE FROM customer WHERE customer_id = -1;"; 
	         statement.executeUpdate(sql);
	         
	         sql = "SELECT * FROM customer WHERE customer_id = -1;"; 
	         resultSet = statement.executeQuery(sql);
	         assertEquals(resultSet.next(), false);
	         statement.close();
	         connection.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	}
}

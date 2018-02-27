package tdt4140.gr1864.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.beans.Statement;
import java.io.IOException;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.runners.MethodSorters;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {
	
	/*
	 * Setting up database before running tests
	 * Checks for occurence of database before creating one
	 */
	@BeforeClass
	public static void setup() throws IOException {
		Path path = Paths.get("database.db");
		
		if (! Files.exists(path)) {
			CreateDatabase.main(null);
		}
	}
	
	@Test
	public void AconnectionTest() {
		
		Connection connection = null;
      
		try {
			Class.forName("org.sqlite.JDBC");
			connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		} catch (Exception e) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}
		assertNotEquals(connection, null);
	}
	
	@Test
	public void BwriteTest() {
		Connection connection = null;
		java.sql.Statement statement = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	         
	         statement = connection.createStatement();
	         String sql = "INSERT INTO customer (customer_id, first_name, last_name, age, sex) " +
	                        "VALUES (-1, 'Ben', 'Ten', 15, 'M');"; 
	         statement.executeUpdate(sql);
	         statement.close();
	         connection.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	}
	
	@Test
	public void CselectTest() throws SQLException {
		Connection connection = null;
		java.sql.Statement statement = null;
	    ResultSet resultSet = null; 
		
	     try {
	         Class.forName("org.sqlite.JDBC");
	         connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	         
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
	
	@Test
	public void DdeleteTest() throws SQLException {
		Connection connection = null;
		java.sql.Statement statement = null;
	    ResultSet resultSet = null;   
		
	    try {
	         Class.forName("org.sqlite.JDBC");
	         connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	         
	         statement = connection.createStatement();
	         String sql = "DELETE FROM customer WHERE customer_id = -1;"; 
	         statement.executeUpdate(sql);
	         
	         sql = "SELECT * FROM customer WHERE customer_id = -1;"; 
	         resultSet = statement.executeQuery(sql);
	         assertEquals(resultSet.next(), false);
	         statement.close();
	         statement.close();
	         connection.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	}
	
	/*
	 * Deleting database after running test
	 */
	@AfterClass
	public static void finish() {
		Path path = Paths.get("database.db");
		try {
		    Files.delete(path);
		} catch (NoSuchFileException x) {
		    System.err.format("%s: no such" + " file or directory%n", path);
		} catch (DirectoryNotEmptyException x) {
		    System.err.format("%s not empty%n", path);
		} catch (IOException x) {
		    // File permission problems are caught here.
		    System.err.println(x);
		}
	}
	
}

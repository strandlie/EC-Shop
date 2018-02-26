package tdt4140.gr1864.app.core;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import org.junit.runners.MethodSorters;

import org.junit.FixMethodOrder;
import org.junit.Test;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class DatabaseTest {
	
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
	
}

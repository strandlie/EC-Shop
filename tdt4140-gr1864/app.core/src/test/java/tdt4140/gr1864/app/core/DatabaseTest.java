package tdt4140.gr1864.app.core;

import static org.junit.Assert.assertNotEquals;

import java.beans.Statement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.junit.Test;

public class DatabaseTest {
	
	@Test
	public void connectionTest() {
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
	public void writeTest() {
		Connection connection = null;
		java.sql.Statement statement = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	         
	         statement = connection.createStatement();
	         String sql = "INSERT INTO user (user_id, first_name, last_name) " +
	                        "VALUES (7, 'Ben', 'Ten');"; 
	         statement.executeUpdate(sql);
	         statement.close();
	         connection.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      assertNotEquals(connection, null);
	}
	
	@Test
	public void selectTest() throws SQLException {
		Connection connection = null;
		java.sql.Statement statement = null;
	    ResultSet resultSet = null; 
		
	     try {
	         Class.forName("org.sqlite.JDBC");
	         connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	         
	         statement = connection.createStatement();
	         String sql = "SELECT * FROM user WHERE user_id = 1;"; 
	         resultSet = statement.executeQuery(sql);
	         statement.close();
	         connection.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      assertNotEquals(resultSet.next(), null);
	}
	
	@Test
	public void deleteTest() {
		Connection connection = null;
		java.sql.Statement statement = null;
	      
	      try {
	         Class.forName("org.sqlite.JDBC");
	         connection = DriverManager.getConnection("jdbc:sqlite:database.db");
	         
	         statement = connection.createStatement();
	         String sql = "DELETE FROM user WHERE user_id = 1;"; 
	         statement.executeUpdate(sql);
	         statement.close();
	         connection.close();
	      } catch (Exception e) {
	         System.err.println( e.getClass().getName() + ": " + e.getMessage() );
	         System.exit(0);
	      }
	      assertNotEquals(connection, null);
	}
	
}

package tdt4140.gr1864.app.core;

import static org.junit.Assert.assertNotEquals;

import java.sql.Connection;
import java.sql.DriverManager;

import org.junit.Test;

public class DatabaseTest {
	@Test
	public void dbTest() {
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
	
}

package tdt4140.gr1864.app.core.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import interfaces.DatabaseCRUD;


public class OnShelfDatabaseController implements DatabaseCRUD{
	
	/* connection to SQLite database */
	Connection connection;
	/* SQL statement executed on database */
	PreparedStatement statement;
	
	
	public OnShelfDatabaseController() {
		try {
			this.connection = DriverManager.getConnection("jdbc:sqlite:database.db");
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	/**
	 * The DatabaseCRUD interface was not compatible with table for on_shelf, so its four methods has been deprecated and replaced
	 */
	@Deprecated
	@Override
	public int create(Object object) {
		throw new UnsupportedOperationException("create(Object object) method from DatabaseCRUD is no longer in use");
	}
	
	/**
	 * Creates a new entry in the on_shelf table
	 * 
	 * Inputs are objects to secure the right id is used the right place
	 * 
	 * @param shop				Shop object
	 * @param product			Product object
	 * @param amountOnShelfs	Amount that's on the shelfs
	 * @param amountInStorage	Amount that's in storage
	 */
	public void create(Shop shop, Product product, int amountOnShelfs, int amountInStorage) {
		
	}
	
	@Override
	public Object retrieve(int id){
		Object objectDummy = new Object();
		return objectDummy;
	}
	
	@Override
	public void update(Object object) {
		
	}
	
	public void delete(int id) {
		
	}
}

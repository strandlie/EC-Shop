package tdt4140.gr1864.app.core.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * DatabaseWiper deletes all tables in databse if they exist
 * 
 * @author stian
 *
 */
public class DatabaseWiper {

	private List<String> tables;
	String dbPath;
	
	public DatabaseWiper() {
		String path = "../../../app.core/src/main/resources/database.db";
		String relativePath;
		//Finds path by getting URL and converting to URI and then to path 
		try {
			URI rerelativeURI = this.getClass().getClassLoader().getResource(".").toURI();
			relativePath = Paths.get(rerelativeURI).toFile().toString() + "/";
			
		} catch (URISyntaxException e1) {
			//If fail to convert to URI use URL path instead
			relativePath = this.getClass().getClassLoader().getResource(".").getPath();
		} 
		dbPath = relativePath + path;
		
		this.tables = new ArrayList<>();
		
		this.tables.add("customer");
		this.tables.add("bought");
		this.tables.add("coordinate");
		this.tables.add("action");
		this.tables.add("on_shelf");
		this.tables.add("shop");
		this.tables.add("shopping_trip");
		this.tables.add("product");
	}
	
	public void wipe() {
		Path path = Paths.get(dbPath);
		
		if (Files.exists(path)) {
			for (int i = 0; i<tables.size();i++) {
				String sql = "DROP TABLE IF EXISTS " + tables.get(i);
				try {
					Connection connection = DriverManager.getConnection("jdbc:sqlite:" + dbPath);
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.execute();
					statement.close();
					connection.close();
				} catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		CreateDatabase.main(null);
	}
}

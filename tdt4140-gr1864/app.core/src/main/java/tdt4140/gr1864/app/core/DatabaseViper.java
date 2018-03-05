package tdt4140.gr1864.app.core;

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
 * DatabaseViper sletter alle tabeller i databasen (hvis den finnes) og kaller så CreateDatabase
 * 
 * @author stian
 *
 */
public class DatabaseViper {
	private List<String> tables;
	
	public DatabaseViper() {
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
	
	public void vipe() {
		Path path = Paths.get("database.db");
		
		if (Files.exists(path)) {
			for(int i = 0; i<tables.size();i++) {
				String sql = "DROP TABLE IF EXISTS " + tables.get(i);
				try {
					Connection connection = DriverManager.getConnection("jdbc:sqlite:database.db");
					PreparedStatement statement = connection.prepareStatement(sql);
					statement.execute();
					statement.close();
					connection.close();
				}catch(SQLException e) {
					e.printStackTrace();
				}
			}
		}
		CreateDatabase.main(null);
	}
}

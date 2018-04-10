package tdt4140.gr1864.app.core.database;

import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;

/**
 * Streamlines database-paths, removing it from the constructor
 * of database-controllers.
 * @author vegarab
 */
public class DBPath {
	
	/* Path to the database file */
	String dbPath;
	
	/**
	 * Sets the path string to the database
	 */
	public DBPath() {
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
	}

	/**
	 * Retrieves the path needed to locate the DB
	 * @return dbPath
	 */
	public String getPath() {
		return dbPath;
	}
}

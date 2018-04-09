package tdt4140.gr1864.app.core.databasecontrollers;

import tdt4140.gr1864.app.core.database.DBPath;

/**
 * Super-class for DatabaseControllers to streamline path to DB
 * @author vegarab
 */
public abstract class DatabaseController {
	
	/* String path to database file */
	public String dbPath;
	
	/**
	 * Sets the path to the database file
	 */
	public DatabaseController() {
		dbPath = new DBPath().getPath();
	}
}

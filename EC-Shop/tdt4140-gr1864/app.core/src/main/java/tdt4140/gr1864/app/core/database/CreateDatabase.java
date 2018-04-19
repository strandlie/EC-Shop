package tdt4140.gr1864.app.core.database;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {

    public static void createNewDatabase() throws IOException {

		String path = "../../../app.core/src/main/resources/database.db";
		String relativePath;
		//Finds path by getting URL and converting to URI and then to path 
		try {
			URI rerelativeURI = CreateDatabase.class.getClassLoader().getResource(".").toURI();
			relativePath = Paths.get(rerelativeURI).toFile().toString() + "/";
			
		} catch (URISyntaxException e1) {
			//If fail to convert to URI use URL path instead
			relativePath = CreateDatabase.class.getClassLoader().getResource(".").getPath();
		} 
		String dbPath = relativePath + path;
    		
        String url = "jdbc:sqlite:" + dbPath;
        BufferedReader in = new BufferedReader(new FileReader("../app.core/src/main/resources/database.sql"));
        String str;
        StringBuffer sql = new StringBuffer();
        while ((str = in.readLine()) != null) {
        		sql.append(str + "\n ");
        }
        in.close();
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                
                String[] sqlArr = sql.toString().split("(;\\n)");
                Statement stmt = conn.createStatement();
                for (String s : sqlArr) {
                		s += ";";
                		stmt.execute(s);
                }
            }
 
        } catch (SQLException e) {
        	System.out.println(e.getMessage());
        }
    }
 
    /**
     * @param args the command line arguments
     * @throws IOException 
     */
    public static void main(String[] args) {
    	try {
    		createNewDatabase();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}

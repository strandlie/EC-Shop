package tdt4140.gr1864.app.core;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class CreateDatabase {
    public static void createNewDatabase() throws IOException {
    		
    		String url = "jdbc:sqlite:test.db";
    	
        BufferedReader in = new BufferedReader(new FileReader("database.sql"));
        String str;
        StringBuffer sql = new StringBuffer();
        while ((str = in.readLine()) != null) {
        		sql.append(str + "\n ");
        }
        in.close();
 
        try (Connection conn = DriverManager.getConnection(url)) {
            if (conn != null) {
                //DatabaseMetaData meta = conn.getMetaData();
                
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
    public static void main(String[] args) throws IOException {
        createNewDatabase();
    }
}

package tdt4140.gr1864.app.core;

import java.io.FileReader;

import org.json.simple.parser.JSONParser;

public class DataLoader {
	
	public static void main(String [] args) {
		DataLoader loader = new DataLoader();
		loader.loadData();
	}
	
	public void loadData() {
		String relativePath = getClass().getClassLoader().getResource(".").getPath();
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader(relativePath + "../../src/main/resources/data.json"));
			System.out.println(obj.toString());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
}

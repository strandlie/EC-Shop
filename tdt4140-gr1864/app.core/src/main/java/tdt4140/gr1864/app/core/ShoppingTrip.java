package tdt4140.gr1864.app.core;

import java.util.List;

/*
 * TODO:
 * 		- Finalize structure
 * 		- Import and transform JSON-data
 * 		- Implement DatabaseCRUD-framework
 */

public class ShoppingTrip {

	private int id;
	private int start;
	private int stop;
	private List<Double> trip;
	
	public ShoppingTrip() {
	/*
	 * This needs to take in JSON-data from the data-generator and transform
	 * into into this object-structure.. 
	 */
	}
	
	public int getId() {
		return id;
	}
	
	public int getStart() {
		return start;
	}
	
	public int getStop() {
		return stop;
	}
}

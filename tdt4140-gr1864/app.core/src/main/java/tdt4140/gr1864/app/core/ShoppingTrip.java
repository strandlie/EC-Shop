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
	
	public ShoppingTrip(int id, int start, int stop) {
	/*
	 * This needs to take in JSON-data from the data-generator and transform
	 * into into this object-structure.. 
	 * 
	 * It is currently ONLY supposed to used for testing Customer-object. IDs
	 * are not guaranteed to be unique - don't expect this to actually work, it
	 * is just a template for Customer-object to work.
	 */
		this.id = id;
		this.start = start;
		this.stop = stop;
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

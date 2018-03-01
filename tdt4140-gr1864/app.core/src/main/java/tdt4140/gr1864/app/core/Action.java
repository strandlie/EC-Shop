package tdt4140.gr1864.app.core;

public class Action {
	
	private long timeStamp;
	private int actionType;
	private int productID;
	private ShoppingTrip trip;
	
	/**
	 * The code for PICK_UP actions.
	 */
	public static int PICK_UP = 1;

	/**
	 * The code for DROP actions.
	 */
	public static int DROP = 0;

	/*
	 * Constructor for creating Actions in ActionDatabaseController.
	 * Needs to handle getting correct ShoppingTrip based on it's ID
	 */
	public Action(String timeStamp, int actionType, int productID, int shoppingTripID) {
		this.timeStamp = Long.parseLong(timeStamp);
		this.actionType = actionType;
		this.productID = productID;
		
		// Set trip based on shoppingTripID
		
	}
	
	public Action(String timeStamp, int actionType, int productID) {
		this.timeStamp = Long.parseLong(timeStamp);
		this.actionType = actionType;
		this.productID = productID;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
	public int getActionType() {
		return actionType;
	}
	
	public int getProductID() {
		return productID;
	}
	
	public int getShoppingTripID() {
		return 0;
	}

}

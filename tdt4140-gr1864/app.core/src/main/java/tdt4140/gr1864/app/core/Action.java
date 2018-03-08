package tdt4140.gr1864.app.core;

public class Action {
	
	private long timeStamp;
	/* type of action 1/0 */
	private int actionType;
	
	/* product action was made upon */
	private Product product;
	/* trip the action is a part of */
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
	 * Constructor used by DatabaseController
	 * @param timeStamp 	time of action, parsed to long
	 * @param actionType	type of action, 1/0
	 * @param product		product action was made upon
	 * @param trip			trip the action is a part of
	 */
	public Action(String timeStamp, int actionType, Product product, ShoppingTrip trip) {
		this.timeStamp = Long.parseLong(timeStamp);
		this.actionType = actionType;
		this.trip = trip;
		this.product = product;
	}
	
	/**
	 * @param timeStamp		time of action, parsed to long
	 * @param actionType	type of action, 1/0
	 * @param product		product action was made upon
	 */
	public Action(String timeStamp, int actionType, Product product) {
		this.timeStamp = Long.parseLong(timeStamp);
		this.actionType = actionType;
		this.product = product;
		
	}
	
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
	public int getActionType() {
		return actionType;
	}
	
	public Product getProduct() {
		return product;
	}
	
	public ShoppingTrip getShoppingTrip() {
		return trip;
	}

}

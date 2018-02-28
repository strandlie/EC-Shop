package tdt4140.gr1864.app.core;

public class Action {
	
	private long timeStamp;
	private int actionType;
	private Product product;
	private ShoppingTrip trip;
	
	/*
	 * Constructor for creating Actions in ActionDatabaseController.
	 * Needs to handle getting correct ShoppingTrip based on it's ID
	 */
	public Action(String timeStamp, int actionType, Product product, ShoppingTrip trip) {
		this.timeStamp = Long.parseLong(timeStamp);
		this.actionType = actionType;
		this.trip = trip;
		this.product = product;
	}
	
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
	
	public int getShoppingTripID() {
		return 0;
	}

}

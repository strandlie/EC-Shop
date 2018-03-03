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

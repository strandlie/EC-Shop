package tdt4140.gr1864.app.core;

public class Action {
	
	private int timeStamp;
	private int actionType;
	private int productID;
	private ShoppingTrip trip;
	
	public Action(int timeStamp, int actionType, int productID) {
		this.timeStamp = timeStamp;
		this.actionType = actionType;
		this.productID = productID;
	}
	
	public int getTimeStamp() {
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

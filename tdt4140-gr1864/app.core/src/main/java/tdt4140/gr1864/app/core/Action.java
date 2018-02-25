package tdt4140.gr1864.app.core;

public class Action {
	
	private long timeStamp;
	private long actionType;
	private long productID;
	
	public Action(long timeStamp, long actionType, long productID) {
		this.timeStamp = timeStamp;
		this.actionType = actionType;
		this.productID = productID;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}
	
	public long getActionType() {
		return actionType;
	}
	
	public long getProductID() {
		return productID;
	}

}

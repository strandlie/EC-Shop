package dataMockerGenerator;

import java.util.Date;

public class Action {
	/**
	 * The Product affected by this action.
	 */
	private Product product;
	
	/**
	 * The time at which the action happened.
	 */
	private Date time;
	
	/**
	 * The type of the action, which is either a PICK_UP or a DROP action.
	 */
	private int type;
	
	/**
	 * The code for PICK_UP actions.
	 */
	public static int PICK_UP = 1;
	
	/**
	 * The code for DROP actions.
	 */
	public static int DROP = 0;
	
	/**
	 * @param product The affected product.
	 * @param time The time at which the action happened.
	 * @param type Whether the item was picked up or dropped down.
	 */
	public Action(Product product, Date time, int type) {
		if (type == DROP && product.canBeDropped()) {
			product.drop();
		} else if (type == PICK_UP) {
			product.pickUp();
		} else {
			throw new IllegalStateException("Cannot DROP up product which is not being carried.");
		}

		this.product = product;
		this.time = time;
		this.type = type;
		
	}
	
	/**
	 * @return The type of the action.
	 */
	public int getAction() {
		return type;
	}
	
	/**
	 * @return The ID of the affected product.
	 */
	public int getProductID() {
		return product.getCode();
	}
	
	/**
	 * @return The time at which the action happened.
	 */
	public Date getTimestamp() {
		return time;
	}
}

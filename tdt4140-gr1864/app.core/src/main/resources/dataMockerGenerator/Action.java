package dataMockerGenerator;

import java.util.Date;

public class Action {
	private Product product;
	private Date time;
	private int type;
	
	public static int PICK_UP = 1;
	public static int DROP = 0;
	
	public Action(Product product, Date time, int type) {
		this.product = product;
		this.time = time;
		this.type = type;
		
	}
	
	public int getAction() {
		return type;
	}
	
	public int getProductID() {
		return product.getCode();
	}
	
	public Date getTimestamp() {
		return time;
	}
}

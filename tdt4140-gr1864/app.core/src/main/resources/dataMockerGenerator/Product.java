package dataMockerGenerator;

public class Product {
	private int code;
	private int amount_of_picked = 0;
	private static int productNum = 0;
	
	public Product() {
		this.code = productNum;
		productNum++;
	}

	public int getCode() {
		return code;
	}
	
	public void pickUp() {
		this.amount_of_picked++;
	}
	
	public void drop() {
		this.amount_of_picked--;
	}
	
	public boolean canBeDropped() {
		return amount_of_picked > 0;
	}
	
}

package dataMockerGenerator;

public class Item {
	private int code;
	private static int productNum = 0;
	
	public Item() {
		this.code = productNum;
		productNum++;
	}

	public int getCode() {
		return code;
	}
}

package dataMockerGenerator;

public class Item {
	private String name;
	private int code;
	
	public Item(String name, int code) {
		this.name = name;
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public int getCode() {
		return code;
	}
}

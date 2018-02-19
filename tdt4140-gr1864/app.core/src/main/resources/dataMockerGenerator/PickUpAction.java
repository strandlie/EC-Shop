package dataMockerGenerator;

public class PickUpAction {
	private int code;
	private double time;
	
	public PickUpAction(int code, double time) {
		this.code = code;
		this.time = time;
	}
	
	public int getCode() {
		return code;
	}
	
	public double getTime() {
		return time;
	}
}

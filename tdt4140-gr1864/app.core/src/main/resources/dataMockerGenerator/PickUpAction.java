package dataMockerGenerator;

import java.util.Date;

public class PickUpAction {
	private int code;
	private Date time;
	
	public PickUpAction(int code, Date time) {
		this.code = code;
		this.time = time;
	}
	
	public int getCode() {
		return code;
	}
	
	public Date getTime() {
		return time;
	}
}

package dataMockerGenerator;

import java.util.Date;

public class Position extends Coordinate {
	private Date time;
	
	public Position(double x, double y, Date time) {
		super(x, y);
		
		this.time = time;
	}

	public Position(Coordinate coordinate, Date time) {
		super(coordinate.getX(), coordinate.getY());
		this.time = time;
	}
	
	public Date getTime() {
		return time;
	}
}

package dataMockerGenerator;

public class Position extends Coordinate {
	private double time;
	
	public Position(double x, double y, double time) {
		super(x, y);
		
		this.time = time;
	}

	public Position(Coordinate coordinate, double time) {
		super(coordinate.getX(), coordinate.getY());
		
		this.time = time;
	}
	
	public double getTime() {
		return time;
	}
}

package tdt4140.gr1864.app.core;

public class Coordinate {
	
	private double x;
	private double y;
	private long timeStamp;
	
	public Coordinate(double x, double y, long timeStamp) {
		this.x = x;
		this.y = y;
		this.timeStamp = timeStamp;
	}
	
	public double getX() {
		return x;
	}
	
	public double getY() {
		return y;
	}
	
	public long getTimeStamp() {
		return timeStamp;
	}

}

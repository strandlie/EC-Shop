package tdt4140.gr1864.app.core.dataMocker;

public class ThreadAction implements Comparable {
	public static int ACTION_ENTER = 0;
	public static int ACTION_LEAVE = 1;
	
	private int action;
	private long time;
	private Trip trip;
	
	/**
	 * Enter action.
	 * @param time
	 */
	public ThreadAction(int time) {
		this.action = 0;
		this.time = time;
	}
	
	/**
	 * Exit action.
	 * @param time
	 * @param trip
	 */
	public ThreadAction(long time, Trip trip) {
		this.action = 1;
		this.time = time;
		this.trip = trip;
	}
	
	public int getAction() {
		return action;
	}
	
	public long getTime() {
		return time;
	}
	
	public Trip getTrip() {
		return trip;
	}

	@Override
	public int compareTo(Object action) {
		return (int) (this.getTime() - ((ThreadAction) action).getTime());
	}
}

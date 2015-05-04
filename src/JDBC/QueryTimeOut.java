package JDBC;

public class QueryTimeOut {

	private int startTime = 0;
	private int endTime = 0;
	private boolean timeOut = false;
	private int queryTimeOut = 0;
	
    public void setQueryTimeOut (int seconds){
    	queryTimeOut = seconds;
    }
	public void setStartTime(int seconds) {
		startTime = seconds;
	}

	public void setEndTime(int seconds) {
		endTime = seconds;
		compareTimes();
	}

	public boolean getTimeOut() {
		return timeOut;
	}

	private void compareTimes() {
		if (queryTimeOut == 0) {
			timeOut = false;
		} else {
			if (endTime - startTime >= queryTimeOut) {
				timeOut = true;
			} else {
				timeOut = false;
			}
		}
	}
}

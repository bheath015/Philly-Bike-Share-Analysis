/**
 * This class represents a station in the Indego network
 * We store all necessary characteristics of a station here
 * We use data from station files and manipulate it to provide
 * callers of this class with robust and usable data
 * @author Brian
 *
 */
public class Station {

	private int stationID;
	private String stationName;
	private String goLiveDate;
	private String status;
	private int goLiveYear;
	private int goLiveMonth;
	private int goLiveDay;
	
	/**
	 * New stations are constructed when station files are read
	 * A new station is on each line and takes in the following inputs
	 * @param stationID is the station ID number
	 * @param stationName is the name of the Station
	 * @param goLiveDate is the day the station went live on the network
	 * @param status is whether or not the station is active
	 * We also partition the string representing the go live date to 
	 * allow user interaction with the actual year, month, and date
	 */
	public Station(int stationID, String stationName, String goLiveDate, String status) {
		super();
		this.stationID = stationID;
		this.stationName = stationName;
		this.goLiveDate = goLiveDate;
		this.status = status;
		partitionGoLiveDate();
	}

	/**
	 * We splice the go-live date for each station
	 * This allows callers of station objects to more
	 * easily interact with the date
	 */
	private void partitionGoLiveDate() {
		String[] partitionedDate = goLiveDate.split("/");
		goLiveYear = Integer.parseInt(partitionedDate[2]);
		goLiveMonth = Integer.parseInt(partitionedDate[1]);
		goLiveDay = Integer.parseInt(partitionedDate[0]);
	}

	/**
	 * @return the stationID
	 */
	public int getStationID() {
		return stationID;
	}

	/**
	 * @return the stationName
	 */
	public String getStationName() {
		return stationName;
	}

	/**
	 * @return the goLiveDate
	 */
	public String getGoLiveDate() {
		return goLiveDate;
	}

	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}

	/**
	 * @return the goLiveYear
	 */
	public int getGoLiveYear() {
		return goLiveYear;
	}

	/**
	 * @return the goLiveMonth
	 */
	public int getGoLiveMonth() {
		return goLiveMonth;
	}

	/**
	 * @return the goLiveDay
	 */
	public int getGoLiveDay() {
		return goLiveDay;
	}
	
	
}

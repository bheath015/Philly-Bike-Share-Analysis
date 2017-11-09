/**
 * This class represents a trip taken on the Indego network
 * It has characteristics of each trip, most of which 
 * are brought in from the Indego trip file
 * Some characteristics are also created within this class
 * based on Indego file data
 * @author Brian
 *
 */
public class Trip {

	private int tripID;
	private int duration;
	private String startTime;
	private String endTime;
	private int startStation;
	private double startLat;
	private double startLong;
	private int endStation;
	private double endLat;
	private double endLong;
	private int bikeID;
	private int planDuration;
	private String tripRouteCategory;
	private String passholderType;
	
	private int startYear;
	private int startMonth;
	private int startDay;
	private int startHour;
	private int startMinute;
	private int startSecond;
	private int endYear;
	private int endMonth;
	private int endDay;
	private int endHour;
	private int endMinute;
	private int endSecond;
	
	
	/**
	 * A new trip object is constructed for each line in the 
	 * input Indego file
	 * The file passes in parameters representing trip info
	 * @param tripID is the unique trip ID
	 * @param duration is the duration of the trip 
	 * @param startTime is the trip's start time and date
	 * @param endTime is the trip's end time and date
	 * @param startStation is the numeric ID of the start station
	 * @param startLat is the start Latitude
	 * @param startLong is the start Longitude
	 * @param endStation is the numeric ID of the end station
	 * @param endLat is the end Latitude
	 * @param endLong is the end Longitude
	 * @param bikeID is the ID of the bike used on the trip
	 * @param planDuration is the trip's planned duration
	 * @param tripRouteCategory indicates whether the trip was one-way or RT
	 * @param passholderType is the type of account the rider had
	 * 
	 * We also call on two methods to provide more robust date and time
	 * information to those who call on trip objects
	 */
	public Trip(int tripID, int duration, String startTime, String endTime, int startStation, double startLat,
			double startLong, int endStation, double endLat, double endLong, int bikeID, int planDuration,
			String tripRouteCategory, String passholderType) {
		this.tripID = tripID;
		this.duration = duration;
		this.startTime = startTime;
		this.endTime = endTime;
		this.startStation = startStation;
		this.startLat = startLat;
		this.startLong = startLong;
		this.endStation = endStation;
		this.endLat = endLat;
		this.endLong = endLong;
		this.bikeID = bikeID;
		this.planDuration = planDuration;
		this.tripRouteCategory = tripRouteCategory;
		this.passholderType = passholderType;
		partitionStartDateTime();
		partitionEndDateTime();
	}

	/**
	 * This method partitions the start time data
	 * It first splits the date and time
	 * then calls on other methods to store components
	 * of the date and the time
	 */
	private void partitionStartDateTime() {
		String[] partitionST = startTime.split(" ");
		String startCompleteDate = partitionST[0];
		String startCompleteTime = partitionST[1];
		startYear = getYear(startCompleteDate);
		startMonth = getMonth(startCompleteDate);
		startDay = getDate(startCompleteDate);
		startHour = getHour(startCompleteTime);
		startMinute = getMinute(startCompleteTime);
		startSecond = getSecond(startCompleteTime);
	}

	/**
	 * This method partitions the end time data
	 * It first splits the date and time
	 * then calls on other methods to store components
	 * of the date and the time
	 */
	private void partitionEndDateTime() {
		String[] partitionET = endTime.split(" ");
		String endCompleteDate = partitionET[0];
		String endCompleteTime = partitionET[1];
		endYear = getYear(endCompleteDate);
		endMonth = getMonth(endCompleteDate);
		endDay = getDate(endCompleteDate);
		endHour = getHour(endCompleteTime);
		endMinute = getMinute(endCompleteTime);
		endSecond = getSecond(endCompleteTime);
	}

	/**
	 * This method takes in date data and
	 * outputs the year
	 * @param completeDate is the full start or end date
	 * @return only the year
	 */
	private int getYear(String completeDate) {
		// TODO Auto-generated method stub
		String[] partitionedDate = completeDate.split("-");
		String partitionedDateInt = partitionedDate[0].substring(1, 5);
		int year = Integer.parseInt(partitionedDateInt);
		return year;
	}
	
	/**
	 * This method takes in date data and
	 * outputs the month
	 * @param completeDate is the full start or end date
	 * @return only the month
	 */
	private int getMonth(String completeDate) {
		// TODO Auto-generated method stub
		String[] partitionedDate = completeDate.split("-");
		int month = Integer.parseInt(partitionedDate[1]);
		return month;
	}
	
	/**
	 * This method takes in date data and
	 * outputs the date
	 * @param completeDate is the full start or end date
	 * @return only the date
	 */
	private int getDate(String completeDate) {
		// TODO Auto-generated method stub
		String[] partitionedDate = completeDate.split("-");
		int date = Integer.parseInt(partitionedDate[2]);
		return date;
	}
	
	/**
	 * This method takes in time data and
	 * outputs the hour
	 * @param completeDate is the full start or end time
	 * @return only the hour
	 */
	private int getHour(String completeTime) {
		String[] partitionedTime = completeTime.split(":");
		int hour = Integer.parseInt(partitionedTime[0]);
		return hour;
	}
	
	/**
	 * This method takes in time data and
	 * outputs the minute
	 * @param completeDate is the full start or end time
	 * @return only the minute
	 */
	private int getMinute(String completeTime) {
		String[] partitionedTime = completeTime.split(":");
		int minute = Integer.parseInt(partitionedTime[1]);
		return minute;
	}
	
	/**
	 * This method takes in time data and
	 * outputs the second
	 * @param completeDate is the full start or end time
	 * @return only the second
	 */
	private int getSecond(String completeTime) {
		String[] partitionedTime = completeTime.split(":");
		String partitionedTimeInt = partitionedTime[2].substring(0, 1);
		int second = Integer.parseInt(partitionedTimeInt);
		return second;
	}

	/**
	 * @return the tripID
	 */
	public int getTripID() {
		return tripID;
	}
	/**
	 * @return the duration
	 */
	public int getDuration() {
		return duration;
	}

	/**
	 * @return the startTime
	 */
	public String getStartTime() {
		return startTime;
	}

	/**
	 * @return the endTime
	 */
	public String getEndTime() {
		return endTime;
	}

	/**
	 * @return the startStation
	 */
	public int getStartStation() {
		return startStation;
	}

	/**
	 * @return the startLat
	 */
	public double getStartLat() {
		return startLat;
	}

	/**
	 * @return the startLong
	 */
	public double getStartLong() {
		return startLong;
	}

	/**
	 * @return the endStation
	 */
	public int getEndStation() {
		return endStation;
	}

	/**
	 * @return the endLat
	 */
	public double getEndLat() {
		return endLat;
	}

	/**
	 * @return the endLong
	 */
	public double getEndLong() {
		return endLong;
	}

	/**
	 * @return the bikeID
	 */
	public int getBikeID() {
		return bikeID;
	}

	/**
	 * @return the planDuration
	 */
	public int getPlanDuration() {
		return planDuration;
	}

	/**
	 * @return the tripRouteCategory
	 */
	public String getTripRouteCategory() {
		return tripRouteCategory;
	}

	/**
	 * @return the passholderType
	 */
	public String getPassholderType() {
		return passholderType;
	}

	/**
	 * @return the startYear
	 */
	public int getStartYear() {
		return startYear;
	}

	/**
	 * @return the startMonth
	 */
	public int getStartMonth() {
		return startMonth;
	}

	/**
	 * @return the startDay
	 */
	public int getStartDay() {
		return startDay;
	}

	/**
	 * @return the startHour
	 */
	public int getStartHour() {
		return startHour;
	}

	/**
	 * @return the startMinute
	 */
	public int getStartMinute() {
		return startMinute;
	}

	/**
	 * @return the startSecond
	 */
	public int getStartSecond() {
		return startSecond;
	}

	/**
	 * @return the endYear
	 */
	public int getEndYear() {
		return endYear;
	}

	/**
	 * @return the endMonth
	 */
	public int getEndMonth() {
		return endMonth;
	}

	/**
	 * @return the endDay
	 */
	public int getEndDay() {
		return endDay;
	}

	/**
	 * @return the endHour
	 */
	public int getEndHour() {
		return endHour;
	}

	/**
	 * @return the endMinute
	 */
	public int getEndMinute() {
		return endMinute;
	}

	/**
	 * @return the endSecond
	 */
	public int getEndSecond() {
		return endSecond;
	}
	
	/**
	 * This method allows users to access all trip data 
	 * for a specific trip, and it outputs in the same format
	 * as the Indego trip file
	 * @return a string of all trip info
	 */
	public String getAllTripData() {
		String allTripData = Integer.toString(tripID) + "," +
		Integer.toString(duration) + "," + startTime + "," + endTime +
		 "," + startStation + "," + startLat + startLong + "," + endStation +
		 "," + endLat + "," + endLong + "," + bikeID + "," + planDuration + 
		 "," + tripRouteCategory + "," + passholderType;
		return allTripData;
	}
	
	
}

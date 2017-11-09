/**
 * This method performs various analyses on trip and 
 * station data
 * It creates arrays of trips and stations
 * along with a few helpful HashMaps that are used on
 * multiple analyses
 */

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class DataAnalysis {
	private ArrayList<Station> stations;
	private ArrayList<Trip> trips;
	private HashMap<Integer, Integer> startTimeMap;
	private HashMap<Integer, Integer> endTimeMap;
	HashMap<Integer, Double> stationLongitudeHashMap;
	HashMap<Integer, Double> stationLatitudeHashMap;
	
	/**
	 * Users construct data analyses objects
	 * by inputting the file extensions they want to use
	 * We construct instance variables here to include
	 * station/trip arrays and useful hash maps
	 * @param fileNameStation is the file path for the station file
	 * @param fileNameTrip is the file path for the trip file
	 */
	public DataAnalysis(String fileNameStation, String fileNameTrip) {
		StationReader sr;
		try {
			sr = new StationReader(fileNameStation);
			stations = sr.getEachStation();
		} catch (FileNotFoundException fnfe) {
			System.out.println("Please enter a valid station"
					+ " file to analyze this quarter's data!");
			System.exit(0);
		}
		TripReader tr;
		try {
			tr = new TripReader(fileNameTrip);
			trips = tr.getEachTrip();
			createTimeMaps();
			stationLongitudeHashMap = stationLatitudeMap(stations);
			stationLatitudeHashMap = stationLongitudeMap(stations);
		} catch (FileNotFoundException fnfe) {
			System.out.println("Please enter a valid trip"
					+ " file to analyze this quarter's data!");
			System.exit(0);
		} catch (NullPointerException npe) {
			
		}
	}
	
	/**
	 * The construct calls this method to make two hash maps
	 * These maps represent time in terms of seconds
	 * for easy relative comparisons
	 * One maps trip IDs to start time proxies
	 * One maps trip IDs to end time proxies
	 */
	private void createTimeMaps() {
		startTimeMap = new HashMap<Integer, Integer>();
		for (Trip t : trips) {
			Integer startValue = ((t.getStartYear() - 2016) * 977616000) + 
					(t.getStartMonth() * 2678400) + 
					(t.getStartDay() * 86400) +
					(t.getStartHour() * 3600) +
					(t.getStartMinute() * 60) + 
					(t.getStartSecond());
			startTimeMap.put(t.getTripID(), startValue);
		}
		endTimeMap = new HashMap<Integer, Integer>();
		for (Trip t : trips) {
			Integer endValue = ((t.getEndYear() - 2016) * 977616000) + 
					(t.getEndMonth() * 2678400) + 
					(t.getEndDay() * 86400) +
					(t.getEndHour() * 3600) +
					(t.getEndMinute() * 60) + 
					(t.getEndSecond());
			endTimeMap.put(t.getTripID(), endValue);
		}
	}
	
	/**
	 * This method prints an array of
	 * station start years
	 * @return an array of start years
	 */
	public ArrayList<Station> getStationStartYear() {
		ArrayList<Station> returnArray = new ArrayList<>();
		for(Station s : stations) {
			if(s.getGoLiveYear() != 0) {
				returnArray.add(s);
			}
		} 
		return returnArray;
	}
	
	/**
	 * @return an array of all stations
	 */
	public ArrayList<Station> getAllStations() {
		return stations;
	}
	
	/**
	 * @return an array of all trips
	 */
	public ArrayList<Trip> getAllTrips() {
		return trips;
	}
	
	/**
	 * This method caluculates the number of trips by type
	 * in a given year
	 * @param type is one-way or round-trip
	 * @param year is the year to measure
	 * @return the number of trips 
	 */
	public int tripsByTripType(String type, int year) throws NullPointerException {
		int count = 0;
		for (Trip t : trips) {
			if (t.getTripRouteCategory().equalsIgnoreCase("\"" + type + "\"") && t.getStartYear() == year) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Calculates the number of stations with a given status
	 * and a given activation year
	 * @param status whether a station is active or inactive
	 * @param year the year the station went live
	 * @return the number of stations
	 */
	public int stationsByStatus(String status, int year) throws NullPointerException {
		int count = 0;
		for (Station s : stations) {
			if (s.getStatus().equals(status) && s.getGoLiveYear() == year) {
				count++;
			}
		}
		return count;
	}
	
	/**
	 * Calculates the number of trips given a destination
	 * @param destination is the station destination name
	 */
	public String tripsByDestination(String destination) throws NullPointerException {
		int stationID = stationNameToStationID(destination);
		if (stationID == 0000) {
			return "Not a valid station name.";
		}
		int countToDest = 0;
		double totalCount = 0.0;
		for (Trip t : trips) {
			totalCount++;
			if (t.getEndStation() == stationID) {
				countToDest++;
			}
		}
		return String.format("%.4f%%", countToDest/totalCount*100);
		
	}
	
	/**
	 * Converts station name to stationID
	 * @param stationName is the actual string name
	 * @return is the station's ID
	 * 0000 is an undefined marker that 
	 * will alert one of an incorrect name input
	 */
	private int stationNameToStationID(String stationName) throws NullPointerException {
		for (Station s : stations) {
			if (s.getStationName().equals(stationName)) {
				return s.getStationID();
			}
		}
		return 0000;
	}
	
	/**
	 * Calculates the month with the most trips 
	 * given a certain type of Indego pass
	 * @param passholderType is the type of pass
	 * @return the month with the most trips 
	 */
	public int tripsByPassholderTypeMonth(String passholderType) throws NullPointerException {
		int[] months = new int[12];
		for (Trip t : trips) {
			if (t.getPassholderType().contains(passholderType)){
				months[t.getStartMonth()] = months[t.getStartMonth()] + 1;		
			}
		}
		
		int largestMonth = 0;
		for (int i = 1; i < months.length; i++) {	
			if (months[largestMonth] < months[i]) {
				largestMonth = i;
			}
		}
		return largestMonth;
	}
	
	/**
	 * This method uses a hash map to map bikeID to trip duration
	 * for all trips the bike was involved in
	 * It then finds the bike with the greatest value in the
	 * hash map
	 * The hash map measures trips by seconds
	 * @return the bike ID that was traveled the most
	 */
	public int mostTraveledBikeByTime()  throws NullPointerException{
		int busyBikeID = 0;
		double busyBikeHours = 0;
		HashMap<Integer, Double> bikes = new HashMap<Integer, Double>();
		for (Trip t : trips) {
			double tripDuration = 0.0;
			if (t.getStartDay() == t.getEndDay()) {
				double startSecond = (t.getStartHour() * 3600) + (t.getStartMinute() * 60) +
						t.getStartSecond();
				double endSecond = t.getEndHour() * 3600 + t.getEndMinute() * 60 + 
						t.getEndSecond();
				double tripDurationSeconds = (endSecond - startSecond);
				tripDuration = tripDurationSeconds / 3600;
				bikes.putIfAbsent(t.getBikeID(), tripDuration);
				bikes.put(t.getBikeID(), bikes.get(t.getBikeID()) + tripDuration);
			} else {
				double startSecond = (t.getStartHour() * 3600) + (t.getStartMinute() * 60) +
						t.getStartSecond();
				double endSecond = t.getEndHour() * 3600 + t.getEndMinute() * 60 + 
						t.getEndSecond() + 86400;
				double tripDurationSeconds = (endSecond - startSecond);
				tripDuration = tripDurationSeconds / 3600;
				bikes.putIfAbsent(t.getBikeID(), tripDuration);
				bikes.replace(t.getBikeID(), bikes.get(t.getBikeID()) + tripDuration);
			}
		}
		for (HashMap.Entry<Integer, Double> entry : bikes.entrySet()) {
			if (entry.getValue() > busyBikeHours) {
				busyBikeHours = entry.getValue();
				busyBikeID = entry.getKey();
			}
		}
		return busyBikeID;
	}
	
	/**
	 * This method calculates the number of bikes in use
	 * given two periods of time regardless of the day
	 * @param startTime is a given hour that rides must start on or after
	 * @param endTime is a given hour that rides must end on or before
	 * @return the number of trips in that interval
	 */
	public String tripsWithinInterval(String startTime, String endTime)  throws NullPointerException {
		String[] startTimeInput = startTime.split(":");
		String[] endTimeInput = endTime.split(":");
		int startHourInput = Integer.parseInt(startTimeInput[0]);
		int startMinuteInput = Integer.parseInt(startTimeInput[1]);
		int endHourInput = Integer.parseInt(endTimeInput[0]);
		int endMinuteInput = Integer.parseInt(endTimeInput[1]);
		int countInFrame = 0;
		double totalCount = 0.0;
		for (Trip t : trips) {
			totalCount++;
			if (t.getStartDay() == t.getEndDay() && t.getStartMonth() == t.getEndMonth()) {
				if (t.getStartHour() > startHourInput && t.getEndHour() < endHourInput) {
					countInFrame++;
				} else if (t.getStartHour() == startHourInput && 
						t.getStartMinute() >= startMinuteInput &&
						t.getEndHour() < endHourInput) {
					countInFrame++;
				} else if (t.getStartHour() == startHourInput && 
						t.getStartMinute() >= startMinuteInput &&
						t.getEndHour() == endHourInput &&
						t.getEndMinute() <= endMinuteInput) {
					countInFrame++;
				} else if (t.getStartHour() > startHourInput && 
						t.getEndHour() == endHourInput &&
						t.getEndMinute() <= endMinuteInput) {
					countInFrame++;
				}
			}
		}
		return String.format("%.4f%%", countInFrame/totalCount*100);
	}
	
	/**
	 * This method uses the start and end hash maps
	 * and compares rides to a time value input by the user
	 * It gives the number of bikes in use at that input
	 * @param date is the date the user would like to check
	 * @param time is the time the user would like to check
	 * @return the number of bikes in use at that time
	 */
	public int bikesInUseByDateTime(String date, String time) { 
		int bikesInUse = 0;
		String[] dateInput = date.split("/");
		String[] timeInput = time.split(":");
		int yearInput = Integer.parseInt(dateInput[2]);
		int monthInput = Integer.parseInt(dateInput[0]);
		int dayInput = Integer.parseInt(dateInput[1]);
		int hourInput = Integer.parseInt(timeInput[0]);
		int minuteInput = Integer.parseInt(timeInput[1]);
		int inputValue = ((yearInput - 2016) * 977616000) +
				(monthInput * 2678400) +
				(dayInput * 86400) +
				(hourInput * 3600) +
				(minuteInput * 60);
		for (HashMap.Entry<Integer, Integer> trip : startTimeMap.entrySet()) {
			if (trip.getValue() <= inputValue) {
				if (endTimeMap.get(trip.getKey()) >= inputValue) {
					bikesInUse++;	
				}
			}
		}
		return bikesInUse;
	}
	
	/**
	 * This method search through trips
	 * to find the one with the longest distance
	 * @return all of the trip info for the longest trip
	 */
	public String longestTripByDistance() throws NullPointerException {
		String tripInfo = null;
		double longestTripDistance = 0;
		int longestTrip = 0;
		for (Trip t : trips) {
			if (t.getStartLat() != -1.0 && t.getStartLong() != -1.0 &&
					t.getEndLat() != -1.0 && t.getEndLong() != -1.0) {
				double tripDistance = Math.sqrt(Math.pow((t.getStartLat()-t.getEndLat()), 2) +
						Math.pow((t.getStartLong()-t.getEndLong()), 2));
				if (tripDistance > longestTripDistance) {
					longestTripDistance = tripDistance;
					longestTrip = trips.indexOf(t);
				}
			}
		}
		tripInfo = trips.get(longestTrip).getAllTripData();
		return tripInfo;
	}
	
	/**
	 * Outputs the number of trips that started or ended
	 * at a station and controls for round-trips
	 * @param stationList is the list of stations to check
	 * @return the number of trips
	 */
	public int tripsByStations(ArrayList<Station> stationList) throws NullPointerException {
		int totalTrips = 0;
		for (Trip t : trips) {
			for (Station s : stationList) {
				if (t.getStartStation() == s.getStationID()) {
					totalTrips++;
				}
			}
			for (Station s : stationList) {
				if (t.getEndStation() == s.getStationID()) {
					totalTrips++;
				}
			}
			for (Station s: stationList) {
				if (t.getEndStation() == s.getStationID() && 
						t.getStartStation() == s.getStationID()) {
					totalTrips--;
				}
			}
		}
		return totalTrips;
	}
	
	
	/**
	 * Creates an array list of stations that
	 * had unique go-live dates
	 * by checking for those with the same start date
	 * @return an array list of these stations
	 */
	public ArrayList<Station> stationsUniqueStartDate() throws NullPointerException {
		ArrayList<Station> stationList = new ArrayList<Station>();
		for (Station s : stations) {
			Integer goLiveTimeValue = (s.getGoLiveYear()-2016) * 977616000 +
					s.getGoLiveMonth() * 2678400 +
					s.getGoLiveDay() * 86400;
			int sisterStations = -1;
			for (Station s2 : stations) {
				Integer goLiveTimeValue2 = (s2.getGoLiveYear()-2016) * 977616000 +
						s2.getGoLiveMonth() * 2678400 +
						s2.getGoLiveDay() * 86400;
				if ((int) goLiveTimeValue == (int) goLiveTimeValue2) {
					sisterStations++;
				}
			}
			if (sisterStations < 1) {
				stationList.add(s);
			}
		}
		return stationList;
	}
	
	/**
	 * Creates a hash map for mapping stations
	 * to their lattitude
	 * @param stationList are the stations to map
	 * @return a hash map of stations and their latitude
	 */
	private HashMap<Integer, Double> stationLatitudeMap(ArrayList<Station> stationList) {
		stationLatitudeHashMap = new HashMap<Integer, Double>();
		for (Station s : stations) {
			outerloop:
			for (Trip t : trips) {
				stationLatitudeHashMap.put(s.getStationID(), -1.0);
				if (t.getStartStation() == s.getStationID()) {
					stationLatitudeHashMap.put(s.getStationID(), t.getStartLat());
					break outerloop;
				}
			}
		}
		return stationLatitudeHashMap;
	}
	
	/**
	 * Creates a hash map for mapping stations
	 * to their longitude
	 * @param stationList are the stations to map
	 * @return a hash map of stations and their longitude
	 */
	private HashMap<Integer, Double> stationLongitudeMap(ArrayList<Station> stationList) {
		stationLongitudeHashMap = new HashMap<Integer, Double>();
		for (Station s : stationList) {
			outerloop:
			for (Trip t : trips) {
				stationLongitudeHashMap.put(s.getStationID(), -1.0);
				if (t.getStartStation() == s.getStationID()) {
					stationLongitudeHashMap.put(s.getStationID(), t.getStartLong());
					break outerloop;
				} else {
					stationLatitudeHashMap.put(s.getStationID(), -1.0);
				}
			}
		}
		return stationLongitudeHashMap;
	}
	
	/**
	 * Looks for stations that are near to each other
	 * Ensures that only stations with valid 
	 * long/lat data are included
	 * @return the number of stations with an average 
	 * long/lat separation of less than .02
	 */
	public int closeStations() {
		int closeStationCount = 0;
		for (Station s : stations) {
			for (Station s2 : stations) {
				if (stationLatitudeHashMap.get(s.getStationID()) != -1.0 && 
						stationLatitudeHashMap.get(s2.getStationID()) != -1.0 && 
						s.getStationID() != s2.getStationID()) {
					double latDifference = Math.abs(stationLatitudeHashMap.get(s.getStationID()) - 
							stationLatitudeHashMap.get(s2.getStationID()));
					double longDifference = Math.abs(stationLongitudeHashMap.get(s.getStationID()) - 
							stationLongitudeHashMap.get(s2.getStationID()));
					double distance = (latDifference + longDifference) / 2;
					if (distance <= .02) {
						closeStationCount++;
					}
				}
			}
		}
		return closeStationCount / 2;
	}
	
	/**
	 * Calculates the most/least popular/unpopular
	 * station within a given data set
	 * It calculates all and returns what the user requested
	 * @param mostOrLeast is whether the user wants most or least
	 * @param startOrDestination is whether the user 
	 * wants popular or unpopular
	 * @return the name of the station
	 */
	public String topOrBottomStation(String mostOrLeast, String startOrDestination) throws NullPointerException {
		String unpopularStart = null;
		String unpopularDest = null;
		String popularStart = null;
		String popularDest = null;
		int unpopularStartCount = 100000;
		int unpopularDestCount = 100000;
		int popularStartCount = 0;
		int popularDestCount = 0;
		for (Station s : stations) {
			int destinationCount = 0;
			int startCount = 0;
			for (Trip t : trips) {
				if (t.getEndStation() == s.getStationID()) {
					destinationCount++;
				}
				if (t.getStartStation() == s.getStationID()) {
					startCount++;
				}
			}
			if (startCount < unpopularStartCount) {
				unpopularStartCount = startCount;
				unpopularStart = s.getStationName();
			}
			if (destinationCount < unpopularDestCount) {
				unpopularDestCount = destinationCount;
				unpopularDest = s.getStationName();
			}
			if (startCount > popularStartCount) {
				popularStartCount = startCount;
				popularStart = s.getStationName();
			}
			if (destinationCount > popularDestCount) {
				popularDestCount = destinationCount;
				popularDest = s.getStationName();
			}
		}
		if (mostOrLeast.equalsIgnoreCase("least") && 
				startOrDestination.equalsIgnoreCase("start")) {
			return unpopularStart;
		}
		if (mostOrLeast.equalsIgnoreCase("least") && 
				startOrDestination.equalsIgnoreCase("destination")) {
			return unpopularDest;
		}
		if (mostOrLeast.equalsIgnoreCase("most") && 
				startOrDestination.equalsIgnoreCase("start")) {
			return popularStart;
		}
		if (mostOrLeast.equalsIgnoreCase("most") && 
				startOrDestination.equalsIgnoreCase("destination")) {
			return popularDest;
		} else {
			String error = "Not a valid input";
			return error;
		}
	}
	
	/**
	 * Calculates the most popular riding day in a given month
	 * @param monthInput is the month to check
	 * @return is the date that was most popular
	 */
	public int popularDayInMonth(int monthInput) throws NullPointerException {
		int[] daysInMonth = new int[31];
		for (Trip t : trips) {
			if (t.getStartMonth() == monthInput) {
				daysInMonth[t.getStartDay() - 1] = daysInMonth[t.getStartDay() - 1] + 1;
			}
		}
		int busyDay = 0;
		for (int i = 1; i <=30; i++) {
			if (daysInMonth[busyDay] < daysInMonth[i]) {
				busyDay = i;
			}
		}
		return busyDay + 1;
	}
	
	/**
	 * Calculates the bikes that require maintenance 
	 * given an input number of rides that ended
	 * at that station in a given month
	 * @param departureThreshold the number to compare
	 * arrivals at a station to in order to determine
	 * whether or not they need maintenance
	 * @return the list of station IDs that need maintenance
	 */
	public String stationMaintenanceCategories(int departureThreshold) throws NullPointerException {
		String stationMaintenanceList = "";
		HashMap<Integer, Integer> departures = new HashMap<Integer, Integer>();
		for (Trip t : trips) {
			Integer count = departures.get(t.getEndStation());
			if (count == null) {
			    departures.put(t.getEndStation(), 1);
			}
			else {
			    departures.put(t.getEndStation(), count + 1);
			}
		}
		for (HashMap.Entry<Integer, Integer> entry : departures.entrySet()) {
			if (entry.getValue() > departureThreshold) {
				stationMaintenanceList += entry.getKey() + " ";
			}
		}
		if (stationMaintenanceList != "") {
			return stationMaintenanceList;
		} else {
			return stationMaintenanceList += "No checks necessary this month";
		}
	}
}
	
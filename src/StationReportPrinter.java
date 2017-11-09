/**
 * This station creates reports regarding all stations
 * in the Indego network
 * It uses station and trip information to  present
 * interesting facts about each station
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class StationReportPrinter {

	private ArrayList<Station> stations;
	private ArrayList<Trip> trips;
	
	/**
	 * An SRP object requires two files, station
	 * and trip, to analyze and will create array lists
	 * of both the trips and the stations from which it
	 * will base computations
	 * @param fileNameStation is file path for station data file
	 * @param fileNameTrip is file path for trip data file
	 */
	public StationReportPrinter(String fileNameStation, String fileNameTrip) {
		StationReader sr;
		try {
			sr = new StationReader(fileNameStation);
			stations = sr.getEachStation();
		} catch (FileNotFoundException e) {
			System.out.println("Please enter a valid station"
					+ " file to print a station report!");
			System.exit(0);
//			e.printStackTrace();
		}
		TripReader tr;
		try {
			tr = new TripReader(fileNameTrip);
			trips = tr.getEachTrip();
		} catch (FileNotFoundException e) {
			System.out.println("Please enter a valid trip"
					+ " file to print a station report!");
			System.exit(0);
//			e.printStackTrace();
		}

	}
	
	/**
	 * This method prints fun news about when each station
	 * went live. I used this as a tester class for printing
	 * @throws FileNotFoundException
	 */
//	public void printStationNews() {
//		File newFile = new File("station_news.txt");
//		try {
//			PrintWriter out = new PrintWriter(newFile);
//			for (Station s : stations) {
//				out.write("Station " + s.getStationID() + " went live in " + s.getGoLiveYear() + "! \n");
//			}
//			out.close();
//		} catch (FileNotFoundException fnfe) {
//			System.out.println("It appears that you"
//					+ " do not have access to write to this"
//					+ " location or use this file name!");
//		}
//	}
//	
	/**
	 * This method prints the report
	 * It calls another method to compile the
	 * station data after it makes a header row
	 * @throws FileNotFoundException
	 */
	public void printStationReport(String filePath) {
		File newFile = new File(filePath);
//		boolean properPermissions = false;
//		while (properPermissions == false)
			try {
				PrintWriter out = new PrintWriter(newFile);
				out.write("station id,station name,total number of trips, "
						+ "average trip duration (time), average distance, "
						+ "max trip duration (time), max distance, "
						+ "percent one-way trips, difference between "
						+ "departing trips and destinations\n");
				for (Station s : stations) {
					out.write(stationReportOutput(s) + "\n");
				}
				out.close();
				System.out.println("Complete!");
//				properPermissions = true;
			} catch (FileNotFoundException fnfe) {
				System.out.println("It appears that you"
						+ " do not have access to write to this"
						+ " location or use this file name!");
			}
	}


	/**.
	 * This method takes a specific station and
	 * uses various methods to compute and compile data
	 * @param stationInput is the list of stations
	 * @return the row of computed data for each station
	 */
	public String stationReportOutput(Station stationInput) {
		String fullOutput = Integer.toString(
				stationInput.getStationID());
		fullOutput += "," + (stationInput.getStationName());
		fullOutput += "," + totalTrips(stationInput);
		fullOutput += "," + averageTripDurationLookup(stationInput);
		fullOutput += "," + averageTripDistanceLookup(stationInput);
		fullOutput += "," + maxTripDurationLookup(stationInput);
		fullOutput += "," + maxTripDistanceLookup(stationInput);
		fullOutput += "," + percentageOneWayTrips(stationInput);
		fullOutput += "," + differenceStartEnd(stationInput);
		return fullOutput;
	}

	/**.
	 * This method calculates the difference between
	 * the rides that started at a station and
	 * the rides that ended at that station
	 * @param stationInput is the station to check
	 * @return the difference between start and end trips
	 */
	private int differenceStartEnd(Station stationInput) {
		int tripsOnlyStart = 0;
		int tripsOnlyEnd = 0;
		for (Trip t : trips) {
			if (t.getStartStation() == stationInput.getStationID() && 
					t.getEndStation() != stationInput.getStationID()) {
				tripsOnlyStart++;
			}
			if (t.getStartStation() != stationInput.getStationID() && 
					t.getEndStation() == stationInput.getStationID()) {
				tripsOnlyEnd++;
			}
		}
		return tripsOnlyStart - tripsOnlyEnd;
	}

	/**
	 * Calculates the number of one-way trips
	 * from a station
	 * @param stationInput is the station to calculate
	 * @return the percent of trips
	 */
	private String percentageOneWayTrips(Station stationInput) {
		double percentOneWay = 0.0;
		double totalOneWay = 0.0;
		int totalTrips = 0;
		for (Trip t : trips) {
			if (t.getStartStation() == stationInput.getStationID()) {
				totalTrips++;
				if (t.getTripRouteCategory().equals("\"One Way\"")) {
					totalOneWay++;
				}
			}
		}
		percentOneWay = 100 * totalOneWay / totalTrips;
		if (Double.isNaN(percentOneWay)) {
			String noTrips = "No location data available";
			return noTrips;
		} else {
			return String.format("%.3f%%", percentOneWay);
		}
	}

	/**.
	 * Cycles through all trips that departed from
	 * a station and finds the length of the longest trip
	 * It excludes any station data with absent
	 * lat or long information
	 * @param stationInput is the station to check
	 * @return the length of the longest trip
	 */
	private String maxTripDistanceLookup(Station stationInput) {
		double maxDistance = 0.0;
		for (Trip t : trips) {
			if (t.getStartLat() != -1.0 && t.getStartLong() != -1.0
					&& t.getEndLat() != -1.0
					&& t.getEndLong() != -1.0
					&& t.getStartStation()
					== stationInput.getStationID()) {
				double tripDistance = Math.sqrt(Math.pow((t.getStartLat()-t.getEndLat()), 2) +
						Math.pow((t.getStartLong()-t.getEndLong()), 2));
				if (tripDistance > maxDistance) {
					maxDistance = tripDistance;
				}
			}
		}
		if (Double.isNaN(maxDistance)) {
			String noTrips = "No location data available";
			return noTrips;
		} else {
			return String.format("%.03f", maxDistance);
		}
	}

	/**.
	 * Cycles through all the trips that left a station
	 * to calculate the one that lasted the longest
	 * @param stationInput is the station to check
	 * @return the longest duration by time
	 */
	private int maxTripDurationLookup(Station stationInput) {
		int maxDuration = 0;
		for (Trip t : trips)  {
			if (t.getStartStation() == stationInput.getStationID()) {
				if (t.getDuration() > maxDuration) {
					maxDuration = t.getDuration();
				}
			}
		}
		return maxDuration;
	}

	/**
	 * Cycles through the trips that left a station
	 * to find the average distance of those trips
	 * @param stationInput the station to check
	 * @return the average distance of trips
	 */
	private String averageTripDistanceLookup(Station stationInput) {
		double averageTripDistance = 0.0;
		double totalTripDistance = 0.0;
		int totalTrips = 0;
		for (Trip t : trips) {
			if (t.getStartLat() != -1.0 && t.getStartLong() != -1.0 &&
					t.getEndLat() != -1.0 && t.getEndLong() != -1.0 &&
					t.getStartStation() == stationInput.getStationID()) {
				double tripDistance = Math.sqrt(Math.pow((t.getStartLat()-t.getEndLat()), 2) +
						Math.pow((t.getStartLong()-t.getEndLong()), 2));
				totalTripDistance = totalTripDistance + tripDistance;
				totalTrips++;
			}
		}
		averageTripDistance = totalTripDistance / totalTrips;
		if (Double.isNaN(averageTripDistance)) {
			String noTrips = "No location data available";
			return noTrips;
		} else {
			return String.format("%.03f", averageTripDistance);		
		}
	}

	/**
	 * Cycles through all the trips that left a station 
	 * to calculate the average duration
	 * @param stationInput is the station to check
	 * @return the average duration by time
	 */
	private String averageTripDurationLookup(Station stationInput) {

		double averageTripDuration = 0.0;
		double totalTripDuration = 0.0;
		int totalTrips = 0;
		for (Trip t : trips) {
			if (t.getStartStation() == stationInput.getStationID()) {
				totalTrips++;
				totalTripDuration = totalTripDuration + t.getDuration();
			}
		averageTripDuration = totalTripDuration / totalTrips;
		}
		return String.format("%.03f", averageTripDuration);
	}

	/**
	 * Calculates the total number of trips
	 * that started or ended at a station
	 * and accounts for round-trips
	 * @param stationInput the station to check
	 * @return the total trip count
	 */
	private int totalTrips(Station stationInput) {
		int totalTrips = 0;
		for (Trip t : trips) {
			if (t.getStartStation() == stationInput.getStationID()) {
				totalTrips++;
			}
			if (t.getEndStation() == stationInput.getStationID()) {
				totalTrips++;
			}
			if (t.getEndStation() == stationInput.getStationID() && 
					t.getStartStation() == stationInput.getStationID()) {
				totalTrips--;
			}
		}
		return totalTrips;
	}
}
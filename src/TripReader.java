import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class TripReader {

	private ArrayList<Trip> eachTrip;
	
	public TripReader(String inputFileName) throws FileNotFoundException {
		eachTrip = new ArrayList<Trip>();
//		try {
			File inputFile=new File(inputFileName);
			Scanner in = new Scanner(inputFile);	
			in.nextLine();
			while (in.hasNextLine()) {
				String tripCompleteData = in.nextLine();
				String[] completeList=tripCompleteData.split(",");
				int tripID = Integer.parseInt(completeList[0]);
				int duration = Integer.parseInt(completeList[1]);
				String startTime = completeList[2];
				String endTime = completeList[3];
				int startStation = Integer.parseInt(completeList[4]);
				double startLat;
//				String startLatInput = completeList[5];
				try {
					startLat = Double.parseDouble(completeList[5]);
				} catch (IllegalArgumentException iae) {
					startLat = -1.0;
				}
//				if (startLatInput.equals("\"\"")) {
//					startLat = -1.0;
//				} else {
//					startLat = Double.parseDouble(completeList[5]);
//				}
				double startLong;
//				String startLongInput = completeList[6];
				try {
					startLong = Double.parseDouble(completeList[6]);
				} catch (IllegalArgumentException iae) {
					startLong = -1.0;
				}
//				if (startLongInput.equals("\"\"")) {
//					startLong = -1.0;
//				} else {
//					startLong = Double.parseDouble(completeList[6]);
//				}
				int endStation = Integer.parseInt(completeList[7]);
				double endLat;
//				String endLatInput = completeList[8];
				try { 
					endLat = Double.parseDouble(completeList[8]);
				} catch (IllegalArgumentException iae) {
					endLat = -1.0;
				}
//				if (endLatInput.equals("\"\"")) {
//					endLat = -1.0;
//				} else {
//					endLat = Double.parseDouble(completeList[8]);
//				}
				double endLong;
//				String endLongInput = completeList[9];
				try {
					endLong = Double.parseDouble(completeList[9]);
				} catch (IllegalArgumentException iae) {
					endLong = -1.0;
				}
//				if (endLongInput.equals("\"\"")) {
//					endLong = -1.0;
//				} else {
//					endLong = Double.parseDouble(completeList[9]);
//				}
				String bikeIDInput = completeList[10];
				bikeIDInput = bikeIDInput.replaceAll("\"", "");
				int bikeID = Integer.parseInt(bikeIDInput);
				int planDuration = Integer.parseInt(completeList[11]);
				String tripRouteCategory = completeList[12];
				String passholderType = completeList[13];
			
				Trip newTrip = new Trip(tripID, duration, 
						startTime, endTime, startStation, startLat,
						startLong, endStation, endLat, endLong, bikeID,
						planDuration, tripRouteCategory, passholderType);
				
				eachTrip.add(newTrip);
			}
			in.close();
//		} catch (FileNotFoundException fnfe) {
//			System.out.println("Trip file not found, "
//					+ "please check the file path and try again!"
//					+ " Any computations that rely on trip data will"
//					+ " be incorrect :(.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		} 
	}
	
	
	public ArrayList<Trip> getEachTrip() {
		return eachTrip;
	}
}

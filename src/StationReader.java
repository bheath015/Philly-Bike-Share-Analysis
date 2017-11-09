/**
 * 
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class StationReader {

	private ArrayList<Station> eachStation;
	private int stationID;
	private String stationName;
	private String goLiveDate;
	private String status;
	
	public StationReader(String inputFileName) throws FileNotFoundException {
		eachStation = new ArrayList<Station>();
//		try {
			File inputFile=new File(inputFileName);
			Scanner in = new Scanner(inputFile);	
			in.nextLine();
			while (in.hasNextLine()) {
				String tripCompleteData = in.nextLine();
				if (tripCompleteData.contains("\"")) {
					String[] completeList=tripCompleteData.split(",");
					stationID = Integer.parseInt(completeList[0]);
					stationName = completeList[1].concat(completeList[2]);
					goLiveDate = completeList[3];
					status = completeList[4];
				} else {
					String[] completeList=tripCompleteData.split(",");
					stationID = Integer.parseInt(completeList[0]);
					stationName = completeList[1];
					goLiveDate = completeList[2];
					status = completeList[3];
				}
			
				Station newStation = new Station(stationID, stationName,
						goLiveDate, status);
				
				eachStation.add(newStation);
			}
			in.close();
//		} catch (FileNotFoundException fnfe) {
//			System.out.println("Station file not found, "
//					+ "please check the file path and try again!"
//					+ " Any computations that rely on station data will"
//					+ " be incorrect :(.");
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
	}
	
	public ArrayList<Station> getEachStation() {
		return eachStation;
	}
}
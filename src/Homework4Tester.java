import java.io.FileNotFoundException;
import java.util.Scanner;

public class Homework4Tester {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
//		System.out.println("Which station file would you like to use?");
//		String fileNameStation = in.nextLine();
		String fileNameStation = "indego-stations-2017-10-20.csv";
//		System.out.println("Which trip file would you like to use?");
//		String fileNameTrips = in.nextLine();
		String fileNameTrips = "indego-trips-2017-q3.csv";
		
		StationReportPrinter srp = new StationReportPrinter(fileNameStation, fileNameTrips);
		DataAnalysis da = new DataAnalysis(fileNameStation, fileNameTrips);
		System.out.println("Question 1: " + da.tripsByTripType("One Way", 2017));
		System.out.println("Question 2: " + da.stationsByStatus("Active", 2016));
		System.out.println("Question 3: " + da.tripsByDestination("Philadelphia Zoo"));
		System.out.println("Question 4: " + da.tripsByPassholderTypeMonth("Indego30"));
		System.out.println("Question 5: " + da.mostTraveledBikeByTime());
		System.out.println("Question 6: " + da.tripsWithinInterval("0:00", "5:00"));
		System.out.println("Question 7: " + da.bikesInUseByDateTime("9/15/2017", "7:00"));
		System.out.println("Question 8: " + da.longestTripByDistance());
		System.out.println("Question 9: " + da.tripsByStations(da.stationsUniqueStartDate()));
		System.out.println("Question 10: " + da.popularDayInMonth(8));
//		srp.printStationNews();
		System.out.println("EC Question 1: " + da.closeStations());
		System.out.println("EC Question 2: " + da.topOrBottomStation("least", "destination"));
		System.out.println("EC Question 3: " + da.stationMaintenanceCategories(5000));
		System.out.println("Please enter the file path (if necessary) and "
				+ "name of the file that you would like to write.");
		srp.printStationReport(in.nextLine());
		
		in.close();
	}

}

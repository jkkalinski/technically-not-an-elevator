package com.techelevator.view;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Scanner;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.park.Park;
import com.techelevator.model.park.ParkDAO;
import com.techelevator.model.reservation.Reservation;
import com.techelevator.model.site.Site;

public class CampgroundUI {

	private static final String MAIN_MENU_OPTION_CAMPGROUNDS = "View Campgrounds";
	private static final String SEARCH_FOR_RESERVATION = "Search for Reservation By Confirmation ID";
	private static final String RETURN_TO_PREVIOUS_SCREEN = "Return to previous Screen";
	private static final String[] SELECT_A_COMMAND = new String[] { MAIN_MENU_OPTION_CAMPGROUNDS,
			SEARCH_FOR_RESERVATION, RETURN_TO_PREVIOUS_SCREEN };

	private static final String[] MENU_TWO = new String[] { "Search for Available Reservation",
			"Return to Previous Screen" };

	Scanner scan = new Scanner(System.in);
	SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");

	/*******************************************************************************************************
	 * This is the CampgroundUI class
	 * 
	 * All user interactions should be coded here
	 * 
	 * The application program will instantiate an object of this class and use the
	 * object to interact with the user.
	 * 
	 * And data required from the user for the application will be acquired by
	 * methods of this class and sent back to the user either as the return value
	 * from the method or in an object returned from the method.
	 * 
	 * Any messages the application programmer wishes to display to the user may be
	 * sent to methods of this class as variables or objects. Any messaging method
	 * may also have "canned" messages the user may request by a message id.
	 * 
	 *******************************************************************************************************/

	/*******************************************************************************************************
	 * Menu class object
	 * 
	 * Use this Menu object for ALL Menu choices presented to the user
	 * 
	 * This is the same Menu class discussed in module 1 and made available in the
	 * module 1 capstone
	 * 
	 * 
	 ******************************************************************************************************/

	Menu menu = new Menu(System.in, System.out); // Define menu for keyboard input and screen output

	/*******************************************************************************************************
	 * Class member variables, objects, constants and methods should be coded here.
	 ******************************************************************************************************/

	// private static final String[] MAIN_MENU_PARK_OPTIONS = {"YELLOW STONE ",
	// "ACADIA"};
	List<String> firstMenuOptions;
	List<String> secondMenuOptions;
	Park parkChoice;

	public List<String> sendNamesToUI(List<String> names) {
		firstMenuOptions = names;
		firstMenuOptions.add("Exit");
		return firstMenuOptions;
	}

	public List<String> sendCampNamesToUI(List<String> names) {
		secondMenuOptions = names;
		return secondMenuOptions;
	}

	public Park sendParkToUI(Park aPark) {
		parkChoice = aPark;
		return parkChoice;
	}

	public String startScreen() {
		String choice = (String) menu.getChoiceFromOptions(firstMenuOptions.toArray());

		return choice;
	}

	public String secondMenuScreen() {
		String choice = (String) menu.getChoiceFromOptions(MENU_TWO);

		return choice;
	}

	public void parkInformationScreen(Park aPark) {
		System.out.println("\nPARK INFORMATION SCREEN");
		System.out.printf("\n%s National Park: \n", aPark.getName());
		System.out.println("\n    Location:        " + aPark.getLocation());
		System.out.println("    Established:     " + aPark.getEstDate());
		System.out.println("    Area:            " + aPark.getArea());
		System.out.printf("    Annual Visitors: " + aPark.getVisitorCount());
		System.out.printf("\n    Description:     %s", aPark.getDescription() + "\n");
	}

	public String selectCommand() {

		return (String) menu.getChoiceFromOptions(SELECT_A_COMMAND);		
	}

	public void displayGreeting() {
		System.out.println("Welcome to the Campground Reservation!\nPlease select a Park: \n  ^ ^\n" + " (°v°)\n"
				+ " [ \\ \\\n" + "  >=´");
	}

	public void campInformationScreen(Campground aCamp) {
		System.out.println(aCamp.toString());
	}

	public void showCampground(List<Campground> campgrounds) {
		System.out.println("\t\tPARK CAMPGROUNDS");
		for (Campground camp : campgrounds) {
			System.out.println(
					"#" + camp.getCampgroundId() + " Name: " + camp.getName() + " Open from: " + camp.getOpenFromMonth()
							+ " Closed by: " + camp.getOpenToMonth() + " Daily Fee: $" + camp.getDailyFee() + "\n");
		}
	}

	public int getUserIdForReservationSearch() {
		String name = scan.nextLine();
		int userIdToSearch = Integer.parseInt(name);

		return userIdToSearch;
	}

	public int getResoCampgroundIdFromUser() {
		System.out.println("Which campground? (enter 0 to cancel)");
		String choice = scan.nextLine();
		int campId = Integer.parseInt(choice);

		return campId;
	}

	public Date getResoStartDateFromUser() throws ParseException {
		System.out.print("What is the arrival date?   (YYYY-MM-DD) ");
		String choice = scan.nextLine();
		Date startDate = (Date) simpleDateFormat.parse(choice);
		return startDate;
	}

	public Date getResoEndDateFromUser() throws ParseException {
		System.out.print("What is the departure date? (YYYY-MM-DD) ");
		String choice = scan.nextLine();
		Date endDate = (Date) simpleDateFormat.parse(choice);
		return endDate;
	}

	public void displayFiveSiteOptions(List<Site> siteOptions) {
			
		   String format = "%-20s%s";
		      String format2 = "%20s";
		      System.out.printf(format,"Site No:","Max Occupation");
		      System.out.printf(format2,"Accessible?");
		      System.out.printf(format2,"Max RV Length");
		      System.out.printf(format2,"Utilities");
		      for(Site s : siteOptions) {
		    	  String format3 = "%-20d%d";
			      String format4 = "%26s";
			      String format5 = "%16d";
			      String format6 = "%26s";
			      System.out.println();
			      System.out.printf(format3,s.getSiteId(),s.getMaxOccupancy());
			      System.out.printf(format4,s.isAccessible());
			      System.out.printf(format5,s.getMaxRVLength());
			      System.out.printf(format6,s.isUtilities());
			     
		    	 
		      }
		System.out.println();
		
		
		
		
	}

	public int getSiteChoiceFromUser() {
		System.out.println("Which site should be reserved? (Enter 0 to cancel)");
		String siteChoice = scan.nextLine();
		return Integer.parseInt(siteChoice);

	}

	public String getReservationNameFromUser() {
		System.out.println("What name should the reservation be made under?");
		String reservationNameGivenByUser = scan.nextLine();

		return reservationNameGivenByUser;
	}

	public void farewell() {
		System.out.println("Thanks for visiting!");

		System.out.println("  ^ ^\n" + " (°v°)\n" + " [ \\ \\\n" + "  >=´");

		System.out.println("  ^ ^\n" + " (°v°)\n" + " / / ]\n" + "  >=´");

	}

}

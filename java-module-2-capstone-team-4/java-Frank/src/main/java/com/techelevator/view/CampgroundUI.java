package com.techelevator.view;

import java.io.FileNotFoundException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import com.techelevator.CampgroundApp;
import com.techelevator.model.campground.Campground;
import com.techelevator.model.park.Park;
import com.techelevator.model.reservation.Reservation;
import com.techelevator.model.site.Site;

public class CampgroundUI {
	
	/*******************************************************************************************************
	 * This is the CampgroundUI class
	 * 
	 * All user interactions should be coded here
	 * 
	 * The application program will instantiate an object of this class and use the object to interact
	 * with the user.
	 * 
	 * And data required from the user for the application will be acquired by methods of this class
	 * and sent back to the user either as the return value from the method or in an object returned
	 * from the method.
	 * 
	 * Any messages the application programmer wishes to display to the user may be sent to methods of
	 * this class as variables or objects.  Any messaging method may also have "canned" messages the
	 * user may request by a message id.
	 * 
	 *******************************************************************************************************/
	
	
	/*******************************************************************************************************
	 * 
	 * Menu class object
	 * 
	 * Use this Menu object for ALL Menu choices presented to the user
	 * 
	 * This is the same Menu class discussed in module 1 and made available in the module 1 capstone
	 * 
	 * 
	 ******************************************************************************************************/
	// Main menu options
	private static final String MAIN_MENU_OPTION_PARKS = "Parks";
	private static final String MAIN_MENU_OPTION_QUIT = "Quit";
	private static final String[] MAIN_MENU_OPTION = {MAIN_MENU_OPTION_PARKS, MAIN_MENU_OPTION_QUIT};
	// Choose park
	private static final String PARK_CHOICE_1 = "";
	private static final String PARK_CHOICE_2 = " ";
	private static final String PARK_CHOICE_3 = "  ";
	private static final String[] PARK_CHOICES = {PARK_CHOICE_1, PARK_CHOICE_2, PARK_CHOICE_3};

	// Park Campground menu options - Frank Changes names to fit purpose

	private static final String CAMP_MENU_OPTION_VIEW_CAMP = "View Campgrounds";
	private static final String CAMP_MENU_OPTION_CHECK_OPEN_DATES = "Show me your top 5 available sites for my visit";
	private static final String CAMP_MENU_OPTION_RETURN_TO_MM = "Return to Main Menu";
	private static final String[] CAMP_MENU_OPTIONS = {CAMP_MENU_OPTION_VIEW_CAMP, CAMP_MENU_OPTION_CHECK_OPEN_DATES, CAMP_MENU_OPTION_RETURN_TO_MM};
	
	
	Menu CampMenu = new Menu(System.in, System.out);  // Define menu for keyboard input and screen output
	CampgroundApp campApp = new CampgroundApp();
	/*******************************************************************************************************
	 * Class member variables, objects, constants and methods should be coded here. 
	 ******************************************************************************************************/
	
	Park aPark = new Park();
	Campground aCamp = new Campground();
	Site aSite = new Site();
	Reservation aRes = new Reservation();
	

	public boolean displayMainMenu() {        // Display Main Menu
		boolean shouldProcess = true;         // Loop control variable
		boolean nextMenu = false;

		System.out.println("\n---------------------------------------------------------------------------------------");
		System.out.println("W            W     EEEEEE     LL         CCCCCC     OOOOOO     M    M     EEEEEE");
		System.out.println(" W          W      EE         LL         CC         OO  OO     MM  MM     EE");
		System.out.println("  W   WW   W       EEEE       LL         CC         OO  OO     M MM M     EEEE");
		System.out.println("   W W  W W        EE         LL         CC         OO  OO     M    M     EE");
		System.out.println("    W    W         EEEEEE     LLLLLL     CCCCCC     OOOOOO     M    M     EEEEEE");
		System.out.println("---------------------------------------------------------------------------------------\n");			
			
		String choice = (String)CampMenu.getChoiceFromOptions(MAIN_MENU_OPTION);  // Display menu and get choice
			
		switch(choice) {                  	// Process based on user menu choice
			
			case MAIN_MENU_OPTION_PARKS:
					
				nextMenu = true;         	// If they want to see parks - Send true back to App
				break;						// Exit switch statement
			
				case MAIN_MENU_OPTION_QUIT:
					nextMenu = false;         // If they want to exit the app - Send false back to App              
					break;                    // Exit switch statement					
			}	
		
		return nextMenu;  // End method and return to caller with whether user want to continue or not                  
	}
	
	public String parkChoice(List<Park> parks) {  // Returns Park name chosen by user

		String[] allParks = new String[parks.size()]; // Array of Strings to hold Park names for menu
		
		for(int i = 0; i < parks.size(); i++) {       // Loop through List of parks and extract names
			allParks[i] = parks.get(i).getParkName(); // Get park name and assign to String array
		}
		
		String choice = (String) CampMenu.getChoiceFromOptions(allParks);  // Display Park name array as menu
		
		return choice;  // Return Park name to user
	}
	
// Method to display Campground menu
	
	public int campgroundChoice(List<Campground> campsForPark) { // Display campgrounds for Park and let user pick one - return ID for Campground chosen
		
		String[] campMenuChoices = new String[campsForPark.size()+1]; // Array of Strings to hold Campground choices for menu plus exit choice
		
		
		for(int i = 0; i < campsForPark.size(); i++) {     // Loop through List of Campgrounds and construct Menu line
			String campMenuLine   =  campsForPark.get(i).getCampId().toString(); // Convert campId to String and add to Menu line
			campMenuLine += " - " + campsForPark.get(i).getCampName();        // Add campName to Menu line
			campMenuLine += " - " + campsForPark.get(i).getDailyRate();       // Add dailyRate to Menu line
			
			campMenuChoices[i] =  campMenuLine;
		}
		campMenuChoices[campMenuChoices.length-1] = "0 - Exit to Main Menu";

		String choice = (String) CampMenu.getChoiceFromOptions(campMenuChoices);  // Display Park name array as menu
			
		String[] choiceParts = choice.split("-"); // Separate campgroundId in user choice from rest of the String
		
		int idForCampgroundChosen = Integer.parseInt(choiceParts[0].trim());  // Convert Camground Id from choice to int
		
		return idForCampgroundChosen;
		
	}

// Method to display Park innformation
	
	public void displayParkInfo(Park aPark) { // Display information for a Park
		
		System.out.println("\nPark Information Screen");
		System.out.printf("\n%s National Park \n", aPark.getParkName());
		System.out.println("\n       Location: " + aPark.getParkLocation());
		System.out.println("    Established: " + aPark.getParkEstDate());
		System.out.println("           Area: " + aPark.getParkArea());
		System.out.printf(" Annual Vistors: " + aPark.getParkVisitors());
		System.out.printf("\n\n    Description: %s", aPark.getParkInfo());
		
	}
// 
	
// Method to Ask user what do they want to do next
	
	public String campgroundChoiceMenu() {  // Ask user for campground option they want next

		String menuChoice = "";
		System.out.println("\n\nChoose an option to explore your chosen Park");
		String choice = (String)CampMenu.getChoiceFromOptions(CAMP_MENU_OPTIONS);
		
		switch(choice) {
		
		case CAMP_MENU_OPTION_VIEW_CAMP:
			menuChoice = "View";
			break;
			
		case CAMP_MENU_OPTION_CHECK_OPEN_DATES:
			menuChoice = "OpenDates";
			break;
			
		case CAMP_MENU_OPTION_RETURN_TO_MM:
			menuChoice = "Return";
			break;
		}
		
		return menuChoice;
	} 
//	
	
	public void goodbye() {
		System.out.println("\nGoodbye! - Thanks for visiting!\n");
	}
	
	public int doNext(int next) {
		return next;
	}



}

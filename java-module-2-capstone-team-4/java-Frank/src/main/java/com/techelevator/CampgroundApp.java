package com.techelevator;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;
import com.techelevator.model.reservation.*;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.view.*;

public class CampgroundApp {
	
	/****************************************************************************************************
	 * This is the Campground Reservation system application program
	 * 
	 * Any and all user interactions should be placed in the CampgroundUI class 
	 *     which is in the com.techelevator.view package.
	 *     
	 * This application program should instantiate a CampgroundUI object 
	 *      and use its methods to interact with the user.
	 *      
	 * This application program should contain no user interactions.
	 * 
	 * Any and all database accesses should be placed in the appropriate
	 *     com.techelevator.model.tablename package component
	 *     
	 * This application program should instantiate DAO objects and use the methods
	 *     in the DAO to interact with the database tables.   
	 *     
	 * There should be no SQL in this application program
	 *   
	 ***************************************************************************************************/
	
	
	
	public static void main(String[] args) {
		
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundUI userInterface = new CampgroundUI();  // Object to manage user interactions;
		                                                  // Feel free to change the name
		
		/****************************************************************************************************
		 * Instantiate any DAOs you will be using here
		 ***************************************************************************************************/

		final JdbcParkDAO daoPark       = new JdbcParkDAO(dataSource);        // final not necessary, just safe
		final JdbcCampgroundDAO daoCamp = new JdbcCampgroundDAO(dataSource);  // final not necessary, just safe
		final JdbcSiteDAO daoSite       = new JdbcSiteDAO(dataSource);        // final not necessary, just safe
		final JdbcReservationDAO daoRes = new JdbcReservationDAO(dataSource); // final not necessary, just safe
		
		/****************************************************************************************************
		 * Your application programming logic goes here
		 ***************************************************************************************************/
		
		String     parkName = "";     // This will hold Park name chosen by user
		Park       parkChoice = null; // This will hold the Park chosen by the user
		Campground campChoice = null; // This will hold the Campground chosen by the user
		
		boolean userChoice = true;    // Loop control variable - Assume you want to process
		
		while (userChoice) {          // Loop while user wants to process    
			userChoice = userInterface.displayMainMenu(); // ask user what they want to do
			if (userChoice == false) {        // If user wants to end
				break;                        //    exit main processing loop
			}
		// We get here only if does not want to end
		// Processing summary
		
			parkName = userInterface.parkChoice(daoPark.getAllParks());  // Display list of parks and ask user to choose a park by name
			 
			parkChoice = daoPark.getParkByName(parkName); // Get Park from database using the name chosen by the user
			 
			userInterface.displayParkInfo(parkChoice);    // Display information about Park chosen by user

			boolean shouldLoopCampgroundMenu = true;      // Loop control variable - Assume you want to process 
		 
			while (shouldLoopCampgroundMenu) {  // Loop through Campground choice menu until user wants to exit
				
				String campgroundMenuChoice = userInterface.campgroundChoiceMenu(); // Display Campground choices and get user's choice
			 
				switch (campgroundMenuChoice) {    // Check user's campground choice
			 
			 		case "View":  // User wants to view campgrounds at thier chosen Park
				 
			 			int campIdChoice = userInterface.campgroundChoice(daoCamp.getAllCampsByPark(parkChoice));  // Get all the Campgrounds for the park the user chose from database
					                                                                                               // and display them for them to pick one or decide to exit
			 			// We now have the id for the campground they want or 0 if they wanted to exit
			 			
			 			if (campIdChoice == 0) {               // If user wanted to exit
			 				shouldLoopCampgroundMenu = false;  // Set to end  Campground Menu Loop
			 				break;                             // Break out of switch
			 			}
			 		// We get here if user did not want to exit
			 			campChoice = daoCamp.getCampgroundById(campIdChoice);  // Get information on the campground the user picked and display it
	
			 		// We now have a Campground object with the campground the user chose
			 			
			 		// For now, just display the campground they picked 
			 		// Replace these displays with whatever you are supposed to do with the campground they pick
			 			System.out.println();
			 			System.out.println(campChoice);
			 			System.out.println();
			 			break;    // exit switch
			 
			 		case "OpenDates":   // User wants to see open dates for a campground
			 
			 			// Code processing for viewing open dates
			 
			 			break;    // exit switch
			 
			 		case "Return":  // User wants to return to previous menu
			 			shouldLoopCampgroundMenu = false;
			 			break;    // exit switch
				} // end of switch	
			}  // End of Campground Menu Loops
			 
		 } // End of primary loop
		 
		// We get here when we break out of primary while loop (User wants to end)
		userInterface.goodbye();      // Say goodbye
		return;                       // End program
}
}


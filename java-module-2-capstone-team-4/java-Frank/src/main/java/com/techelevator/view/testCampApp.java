package com.techelevator.view;

import java.io.FileNotFoundException;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.CampgroundApp;
import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;
import com.techelevator.model.reservation.JdbcReservationDAO;
import com.techelevator.model.site.JdbcSiteDAO;

public class testCampApp {
	
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
	
	private static final String MAIN_MENU_OPTION_PARKS = "View Parks";
	private static final String MAIN_MENU_OPTION_QUIT = "Quit";
	private static final String[] MAIN_MENU_OPTIONS = {MAIN_MENU_OPTION_PARKS, MAIN_MENU_OPTION_QUIT};
	
	private static final String PARK_MENU_OPTION_CAMPGROUNDS = "View Campgrounds";
	private static final String PARK_MENU_OPTION_SEARCHRES = "Search for Reservation";
	private static final String PARK_MENU_OPTION_RETURN = "Return to Previous Screen";
	private static final String[] PARK_MENU_OPTIONS = {PARK_MENU_OPTION_CAMPGROUNDS, PARK_MENU_OPTION_SEARCHRES, PARK_MENU_OPTION_RETURN};
	
	private Menu campMenu;
	
	private JdbcParkDAO daoPark;
	private JdbcCampgroundDAO daoCamp;
	private JdbcSiteDAO daoSite;
	private JdbcReservationDAO daoRes;
	
	
	public static void main(String[] args) {
		
		testCampApp mainApp = new testCampApp();
		mainApp.run();
	}
	
		
		public testCampApp() {
		
		this.campMenu = new Menu(System.in, System.out);
			
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		CampgroundUI userInterface = new CampgroundUI();  // Object to manage user interactions;
		                                                  // Feel free to change the name
		
		
		/****************************************************************************************************
		 * Instantiate any DAOs you will be using here
		 ***************************************************************************************************/


		
		daoPark = new JdbcParkDAO(dataSource);
		daoCamp = new JdbcCampgroundDAO(dataSource);
		daoSite = new JdbcSiteDAO(dataSource);
		daoRes = new JdbcReservationDAO(dataSource);
		}
	
		
		/****************************************************************************************************
		 * Your application programming logic goes here
		 ***************************************************************************************************/

		public void run(){
			int userParkChoice = 0;
			boolean shouldProcess = true;         // Loop control variable
			
//			System.out.print(daoPark.getAllParks());
			while(shouldProcess) {                // Loop until user indicates they want to exit
				System.out.println("\nWelcome to the National Park Campsite Reservation App!");
				System.out.println("\nSelect a Park for further details");
				System.out.println(daoPark.getAllParks());
				String choice = (String)campMenu.getChoiceFromOptions(MAIN_MENU_OPTIONS);  // Display menu and get choice
				
				switch(choice) {                  // Process based on user menu choice
				
					case MAIN_MENU_OPTION_PARKS:
						handleParks();           // invoke method to display items in Vending Machine
						break;                    // Exit switch statement
				
					case MAIN_MENU_OPTION_QUIT:
						goodbye();          // invoke method to purchase items from Vending Machine
						break;                    // Exit switch statement

						
				}	
			}

			return;                               // End method and return to caller
		}
		
		
		public void handleParks() {
			List<Park> allParks = daoPark.getAllParks();
			System.out.println(allParks);
			String choice = (String)campMenu.getChoiceFromOptions(PARK_MENU_OPTIONS);
			
			switch(choice) {
			case PARK_MENU_OPTION_CAMPGROUNDS:
				System.out.println("\ngetAllCampgrounds() should go here");
				handleCamps();
				break;
				
			case PARK_MENU_OPTION_SEARCHRES:
				System.out.println("\nmethod to search reservations should go here");
//				handleSearchRes();
				break;
				
			case PARK_MENU_OPTION_RETURN:
				System.out.println("\nreturn to last screen");
				break;
			}
		}
		
		public void handleCamps() {
	//		List<Campground> allCamps = daoCamp.getAllCampsByPark(1);
	//		System.out.println(allCamps);
			
		}
		
		public void goodbye() {
			System.out.println("\nGoodbye!");
		}

	

}

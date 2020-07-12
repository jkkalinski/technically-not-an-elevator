package com.techelevator;

import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbcp2.BasicDataSource;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.CampgroundDAO;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;
import com.techelevator.model.park.ParkDAO;
import com.techelevator.model.reservation.*;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.model.site.Site;
import com.techelevator.model.site.SiteDAO;
import com.techelevator.view.*;

public class CampgroundApp {

	private static ParkDAO parkDao;
	private static SiteDAO siteDao;
	private static ReservationDAO reservationDao;
	private static CampgroundDAO campgroundDao;
	private Park parkSelected;
	private Campground campSelected;
	private Reservation reservationSelected;

	static List<String> parkNames;
	static List<String> campNames;
	List<Campground> campObj;
	CampgroundUI userInterface = new CampgroundUI();
	int toSearch;
	int chosenCampgroundId;
	Date startDate;
	Date endDate;
	int startMonth;
	int endMonth;

	/****************************************************************************************************
	 * This is the Campground Reservation system application program
	 * 
	 * Any and all user interactions should be placed in the CampgroundUI class
	 * which is in the com.techelevator.view package.
	 * 
	 * This application program should instantiate a CampgroundUI object and use its
	 * methods to interact with the user.
	 * 
	 * This application program should contain no user interactions.
	 * 
	 * Any and all database accesses should be placed in the appropriate
	 * com.techelevator.model.tablename package component
	 * 
	 * This application program should instantiate DAO objects and use the methods
	 * in the DAO to interact with the database tables.
	 * 
	 * There should be no SQL in this application program
	 * 
	 * @throws ParseException
	 * 
	 ***************************************************************************************************/

	public static void main(String[] args) throws ParseException {

		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");

		// Object to manage user interactions;
		// Feel free to change the name

		/****************************************************************************************************
		 * Instantiate any DAOs you will be using here
		 ***************************************************************************************************/

		parkDao = new JdbcParkDAO(dataSource);
		siteDao = new JdbcSiteDAO(dataSource);
		reservationDao = new JdbcReservationDAO(dataSource);
		campgroundDao = new JdbcCampgroundDAO(dataSource);

		/****************************************************************************************************
		 * Your application programming logic goes here
		 ***************************************************************************************************/

		CampgroundApp cgApp = new CampgroundApp();
		cgApp.run();

	}

	public void run() throws ParseException {
		userInterface.displayGreeting();
		initiateMenuAction();
	}

	public void initiateMenuAction() throws ParseException {

		boolean shouldLoop = true;

		while (shouldLoop) {
			firstMenu();
			String selection = userInterface.selectCommand();

			switch (selection) {
			case ("View Campgrounds"): {
				viewCamp();

				String secondSelection = userInterface.secondMenuScreen();

				// Inside of view camp menu option, start a loop that continues as long as user
				// is interested in the campground
				switch (secondSelection) {
				case ("Search for Available Reservation"): {
					List<Site> siteOptions = userCampsiteSearch();

					userInterface.displayFiveSiteOptions(siteOptions);
					int siteChosen = userInterface.getSiteChoiceFromUser();
					String nameForReservation = userInterface.getReservationNameFromUser();

					reservationSelected = reservationDao.addReservation(siteChosen, startDate, endDate,
							nameForReservation);
					String totalCost = reservationDao.getCostOfReservation(reservationSelected.getReservationId(),
							reservationSelected.getReservationId());
					System.out.println(totalCost);
					// return a confirmation
					System.out.println("Reservation has been made and your confirmation code is: "
							+ reservationSelected.getReservationId());
					break;
				}
				case ("Return to Previous Screen"): {

					break;
				}

				}
				break;

			}
			case ("Search for Reservation By Confirmation ID"): {
				findExistingReservationByReservationId();
				break;
			}
			case ("Return to previous Screen"): {
				break;
			}
			}
		}
	}

	public void firstMenu() throws ParseException {
		// storing LIST of park names
		parkNames = parkDao.getParkNames();
		// sends List of Park Names to UI to make list of MENU options
		userInterface.sendNamesToUI(parkNames);
		// DISPLAYS MENU OPTIONS FROM UI, stores choice in a String
		String parkChoice = userInterface.startScreen();
		if (parkChoice.equalsIgnoreCase("exit")) {
			userInterface.farewell();
			System.exit(0);
		}
		// STORES PARK (BASED ON parkChoice) into a Park Object
		parkSelected = parkDao.getParkByName(parkChoice);
		// UI PRINTS ALL PARK INFO FROM PARK OBJECT USING DEFAULT toString()
		userInterface.parkInformationScreen(parkSelected);

	}

	public List<Site> userCampsiteSearch() {
		chosenCampgroundId = userInterface.getResoCampgroundIdFromUser();
		try {
			startDate = userInterface.getResoStartDateFromUser();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			endDate = userInterface.getResoEndDateFromUser();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return siteDao.getSitesThatAreAvailableOnDateRange(chosenCampgroundId, startDate, endDate, startMonth,
				endMonth);

	}

	public void displayCampgrounds(Park aPark) {
		// ACCEPT PARK FROM APP
		System.out.println(aPark.toString());
	}

	public void viewCamp() {
		System.out.println(parkSelected.getName());
		campObj = campgroundDao.getCampgroundsByParkName(parkSelected.getName());
		userInterface.showCampground(campObj);

		userInterface.sendCampNamesToUI(campNames);
	}

	public void findExistingReservationByReservationId() {
		System.out.print("Enter an existing confirmation ID ");
		toSearch = userInterface.getUserIdForReservationSearch();
		reservationSelected = reservationDao.searchExistingReservationsById(toSearch);
		System.out.println("\nName:      " + reservationSelected.getName() + "\nSite ID:   "
				+ reservationSelected.getSiteId() + "\nFrom date: " + reservationSelected.getFromDate()
				+ "\nTo date:   " + reservationSelected.getToDate());
	}

}

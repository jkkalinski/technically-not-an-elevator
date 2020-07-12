package com.techelevator.model.park;

import java.util.List;

public interface ParkDAO {
	
	/*
	 * Get a list of all available parks in the system, sorted alphabetically 
	 * by name
	 * 
	 */
	
	public List<Park> getAllParks();
	
	
	/*
	 *  Is Park Open?  Bonus question.
	 */
	
	public boolean isParkOpen();
	
	
	/*
	 * Select a Park for Further Details
	 */
	
	public String getParkInfo();

}	

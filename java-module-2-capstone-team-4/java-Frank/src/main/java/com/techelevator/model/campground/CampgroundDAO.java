package com.techelevator.model.campground;

import java.util.List;

import com.techelevator.model.park.Park;

public interface CampgroundDAO {
	
	/*
	 * Select a park that my customer is visiting and see a list of all 
	 * campgrounds for that available park
	 * 
	 */
	
	public List<Campground> getAllCampsByPark(Park campParkId);  // Frank - modified return type
	

}

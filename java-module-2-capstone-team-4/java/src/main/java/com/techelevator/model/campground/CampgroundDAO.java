package com.techelevator.model.campground;

import java.time.LocalDate;
import java.util.List;

public interface CampgroundDAO {
	
	// Use this list to show user what campgrounds they can pick from in the park
	public List<Campground> getCampgroundsByParkName(String name);

	// Use this list internally to limit reservation search to only those in season
	public List<Campground> getCampgroundsInSeasonAndAvailable(Integer ParkId, LocalDate startDate, LocalDate endDate);

	public List<String> getCampNames();

	
}

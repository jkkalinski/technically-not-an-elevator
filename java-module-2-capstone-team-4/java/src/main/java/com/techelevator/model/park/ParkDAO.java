package com.techelevator.model.park;

import java.util.List;

public interface ParkDAO {
	
	public List<Park> getAvailableParks();
	
	public List<String> getParkNames();

	public Park getParkByName(String name);

}

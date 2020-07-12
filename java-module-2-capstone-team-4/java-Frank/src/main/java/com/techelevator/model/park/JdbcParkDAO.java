package com.techelevator.model.park;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.campground.Campground;

public class JdbcParkDAO implements ParkDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcParkDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	// Frank - New method to get a Park when you have the name
	
	public Park getParkByName(String parkNameWanted) {
		
		Park aPark = null;     // Park to be returned
		
		List<Park> allParks = new ArrayList<Park>();  // Hold Parks returned from database (in case there is more than 1)
		
		String getParksSQL = "SELECT park_id "
	 					   + "	     ,name "
						   + "       ,location "
						   + "       ,establish_date "
						   + "       ,area"
						   + "       ,visitors "
						   + "       ,description  "
						   + " FROM park "
						   + " Where name = ?";
		
		SqlRowSet sqlResults = jdbcTemplate.queryForRowSet(getParksSQL, parkNameWanted);
				
		while(sqlResults.next()) {
			allParks.add(mapRowToPark(sqlResults));
		}
		
		if (allParks.size() != 0)  { // If at least one Park returned from data base
			aPark = allParks.get(0);   //     Assign first Park returned from SQL to Park to be returned
		}
			return aPark;              //     Return Park from database or null if no park found
		
	}
// Frank - End of method added by Frank
	
	
	@Override 
	public List<Park> getAllParks() {
		List<Park> allParks = new ArrayList<Park>();
		
		String getParksSQL = "SELECT park_id "
						   + "	     ,name"
						   + "       ,location"
						   + "       ,establish_date"
						   + "       ,area"
						   + "       ,visitors"
						   + "       ,description"
						   + " FROM park"
						   + " ORDER BY name";
		
		SqlRowSet sqlResults = jdbcTemplate.queryForRowSet(getParksSQL);
				
		while(sqlResults.next()) {
			allParks.add(mapRowToPark(sqlResults));
		}
		return allParks;
		
	}

	
	
	@Override
	public boolean isParkOpen() {
		boolean isOpen = true;
		
		String getIsOpenSQL = "SELECT park_id"
							+ " AND open_from_mm "
							+ " AND open_to_mm"
							+ " FROM campground"
							+ " WHERE park_id = (SELECT park_id FROM park WHERE name = ?)";
		
		SqlRowSet sqlResults = jdbcTemplate.queryForRowSet(getIsOpenSQL);
		
		while(sqlResults.next()) {
			Campground camp = mapRowToCamp(sqlResults);
			
			int open = Integer.parseInt(camp.getOpenSeason());
			int closed = Integer.parseInt(camp.getEndSeason());
			int parkId = camp.getCampParkId();
			int resOpen = 4;
			int resClose = 5;
			
			if(parkId == 3 && resOpen < 5 || resOpen > 11 && resClose < 5 || resOpen > 11) {
				isOpen = false;
			}
		
		}
		return isOpen;
		
	}
	
	
	@Override
	public String getParkInfo() {
		
		Park aPark = new Park();
		
		String getAPark = "SELECT * FROM park WHERE park_id = ?";
		
		SqlRowSet sqlResults = jdbcTemplate.queryForRowSet(getAPark);
		
		while(sqlResults.next()) {
			aPark = mapRowToPark(sqlResults);
		}
		
		return aPark.toString();
		
			
	}
	
	
	private Park mapRowToPark(SqlRowSet resultsFromSQL) {
		
		Park aPark = new Park();
		
		aPark.setParkId(resultsFromSQL.getLong("park_id"));
		aPark.setParkName(resultsFromSQL.getString("name"));
		aPark.setParkLocation(resultsFromSQL.getString("location"));
		aPark.setParkEstDate(resultsFromSQL.getDate("establish_date"));
		aPark.setParkArea(resultsFromSQL.getInt("area"));
		aPark.setParkVisitors(resultsFromSQL.getInt("visitors"));
		aPark.setParkInfo(resultsFromSQL.getString("description"));
		
		return aPark;
		
	}
	
	private Campground mapRowToCamp(SqlRowSet resultsFromSQL) {
		Campground aCamp = new Campground();
		
		aCamp.setCampParkId(resultsFromSQL.getInt("park_id"));
		aCamp.setOpenSeason(resultsFromSQL.getString("open_from_mm"));
		aCamp.setEndSeason(resultsFromSQL.getString("open_to_mm"));
		
		return aCamp;
	}


	
}

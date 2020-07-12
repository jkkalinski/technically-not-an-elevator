package com.techelevator.model.campground;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.park.Park;

public class JdbcCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcCampgroundDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Campground> getAllCampsByPark(Park parkChosen) { // Frank - This should receive a Park
		List<Campground> allCamps = new ArrayList<Campground>();
		
		String getCampsSQL = "SELECT campground_id"
						   + "       , park_id"	
				           + "       , name"
						   + "       , open_from_mm" 
						   + "       , open_to_mm" 
						   + "       , daily_fee"
						   + " FROM campground"
						   + " WHERE park_id = ?";
		
		SqlRowSet sqlResults = jdbcTemplate.queryForRowSet(getCampsSQL, parkChosen.getParkId());
		
		while(sqlResults.next()) {
			allCamps.add(mapRowToCamp(sqlResults));
		}
		return allCamps;
	}
	
// Frank - new method to get campGroundById
	
	public Campground getCampgroundById(int campGroundIdWanted) {
		
		Campground aCampground = null;  // If campground not found in data base null object returned
		
		String getACampgroundSQL = "SELECT campground_id"
						        + "       , park_id"	
				                + "       , name"
						        + "       , open_from_mm" 
						        + "       , open_to_mm" 
						        + "       , daily_fee"
						        + " FROM campground"
						        + " WHERE campground_id = ?";
		
		SqlRowSet sqlResults = jdbcTemplate.queryForRowSet(getACampgroundSQL, campGroundIdWanted);

		// We know only one Campground can be returned from database as campgroundId is the primary key		
				
		if (sqlResults.next()) {                     // If Campground was found in the database
			aCampground = mapRowToCamp(sqlResults);  // Copy data from SQL to Campground object
		}
		return aCampground;

	}
	
	
	
	
	private Campground mapRowToCamp(SqlRowSet resultsFromSQL) {
		Campground aCamp = new Campground();
		
		aCamp.setCampId(resultsFromSQL.getLong("campground_id"));
		aCamp.setCampParkId(resultsFromSQL.getInt("park_id"));
		aCamp.setCampName(resultsFromSQL.getString("name"));
		aCamp.setOpenSeason(resultsFromSQL.getString("open_from_mm"));
		aCamp.setEndSeason(resultsFromSQL.getString("open_to_mm"));
		aCamp.setDailyRate(resultsFromSQL.getDouble("daily_fee"));
		
		return aCamp;
	}
	
	

}

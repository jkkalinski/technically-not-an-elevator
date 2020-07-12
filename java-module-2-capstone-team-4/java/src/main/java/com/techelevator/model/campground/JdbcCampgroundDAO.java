package com.techelevator.model.campground;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcCampgroundDAO implements CampgroundDAO {
	
	private JdbcTemplate jdbctemplate;
	public JdbcCampgroundDAO(DataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}
	
	
	@Override
	public List<Campground> getCampgroundsByParkName(String name) {
		List<Campground> campList = new ArrayList<>();
		String sqlSelectCampgrounds = "SELECT campground_id,campground.park_id, campground.name, open_from_mm as Open, open_to_mm as Close, daily_fee" + 
				"  FROM campground" + 
				"       JOIN park" + 
				"         ON park.park_id = campground.park_id" + 
				" WHERE park.name = ?";
		SqlRowSet result = jdbctemplate.queryForRowSet(sqlSelectCampgrounds, name);
		
		while(result.next()) {
			Campground newCamp = mapRowToCampground(result);
			campList.add(newCamp);
		}
		return campList;
	}
	@Override
	public List<Campground> getCampgroundsInSeasonAndAvailable(Integer ParkId, LocalDate startDate, LocalDate endDate) {
		// TODO Auto-generated method stub
		return null;
	}
	public Campground mapRowToCampground(SqlRowSet result) {
		Campground aCampground = new Campground();
		aCampground.setParkId(result.getInt("park_id"));
		aCampground.setCampgroundId(result.getLong("campground_id"));
		aCampground.setDailyFee(result.getDouble("daily_fee"));
		aCampground.setName(result.getString("name"));
		aCampground.setOpenFromMonth(result.getString(4));
		aCampground.setOpenToMonth(result.getString(5));
		return aCampground;
	}
	
	public List<String> getCampNames(){
		List<String> campNames = new ArrayList<>();
		String sqlGetCampNames = "SELECT name FROM campground";
		SqlRowSet results = jdbctemplate.queryForRowSet(sqlGetCampNames);
		while(results.next()) {
			campNames.add(results.getString("name"));
		}
		return campNames;
		
	}
	

}

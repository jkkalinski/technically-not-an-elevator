package com.techelevator.model.park;

import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class JdbcParkDAO implements ParkDAO {

	private JdbcTemplate jdbctemplate;
	public JdbcParkDAO(DataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}
	
	@Override
	public List<Park> getAvailableParks() {
		List<Park> parks = new ArrayList<>();
		String sqlAvailableParks = "SELECT * FROM park";
		SqlRowSet results = jdbctemplate.queryForRowSet(sqlAvailableParks);
	
		while(results.next()) {
			Park aPark = mapRowToPark(results);
			parks.add(aPark);
		}
		return parks;
	}
	
	@Override
	public List<String> getParkNames() {
		List<String> names = new ArrayList<>();
		String sqlGetParkNames = "SELECT name FROM park";
		SqlRowSet results = jdbctemplate.queryForRowSet(sqlGetParkNames);
		while(results.next()) {
			names.add(results.getString("name"));
		}
		return names;
	}
	
	@Override
	public Park getParkByName(String name) {
		String sqlGetParkByName = "SELECT * FROM park WHERE name LIKE ?";
		SqlRowSet results = jdbctemplate.queryForRowSet(sqlGetParkByName, name);
		results.next();
		Park aPark = mapRowToPark(results);
		return aPark;
	}

	
	
	private Park mapRowToPark(SqlRowSet results) {
		Park newPark = new Park();
		newPark.setName(results.getString("name"));
		newPark.setArea(results.getInt("area"));
		newPark.setDescription(results.getString("description"));
		newPark.setEstDate(results.getDate("establish_date").toLocalDate());
		newPark.setLocation(results.getString("location"));
		newPark.setParkId(results.getLong("park_id"));
		newPark.setVisitorCount(results.getInt("visitors"));
		return newPark;
	}
	
	
	


}

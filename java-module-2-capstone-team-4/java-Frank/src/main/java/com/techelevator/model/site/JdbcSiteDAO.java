package com.techelevator.model.site;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcSiteDAO implements SiteDAO {
	
	private JdbcTemplate jdbcTemplate;
	
	public JdbcSiteDAO(DataSource dataSource) {
		this.jdbcTemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Site> getAvailableSiteInfo(Long campgroundId, Date startDate, Date endDate) {
		
		List<Site> allAvailSites = new ArrayList<Site>();
		
		String getAvailSitesSQL = "SELECT *"
				  				+ " FROM site"
				  				+ " WHERE site_id IN (SELECT site.site_id FROM site WHERE site.site_id NOT IN"
				  				+ " (SELECT reservation.site_id FROM reservation WHERE ? < to_date AND ? > from_date))"
				  				+ " AND campground_id IN (SELECT site.campground_id FROM sire WHERE site.campground_id IN"
				  				+ " (SELECT campground_id FROM campground WHERE ? >= cast(open_from_mm as Integer)"
				  				+ " AND ? <= cast(open_to_mm as Integer)))"
				  				+ " AND campground_id = ?"
				  				+ " LIMIT 5";
		
		SqlRowSet sqlResults = jdbcTemplate.queryForRowSet(getAvailSitesSQL, campgroundId, startDate, endDate);
		
		while(sqlResults.next()) {
			allAvailSites.add(mapRowToSite(sqlResults));
		}

		return allAvailSites;
	}

	
	private Site mapRowToSite(SqlRowSet resultsFromSql) {
		Site aSite = new Site();
		
		aSite.setSiteId(resultsFromSql.getLong("site_id"));
		aSite.setCampId(resultsFromSql.getInt("campground_id"));
		aSite.setSiteNumber(resultsFromSql.getInt("site_number"));
		aSite.setMaxOccupancy(resultsFromSql.getInt("max_occupancy"));
		aSite.setAccessible(resultsFromSql.getBoolean("accessible"));
		aSite.setMaxRvLength(resultsFromSql.getInt("max_rv_length"));
		aSite.setUtilities(resultsFromSql.getBoolean("utilities"));
		
		return aSite;
	}

}

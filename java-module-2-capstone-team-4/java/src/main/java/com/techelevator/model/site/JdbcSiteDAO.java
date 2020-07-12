package com.techelevator.model.site;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class JdbcSiteDAO implements SiteDAO {

	private JdbcTemplate jdbctemplate;
	public JdbcSiteDAO(DataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public List<Integer> getSitesThatAreTakenOnDateRange(int campId, Date startDate, Date endDate) {
		List<Integer> sitesTaken = new ArrayList<>();
		String sqlGetTakenSites = "SELECT site.site_id" + 
				"  FROM site" + 
				"       JOIN reservation" + 
				"         ON site.site_id = reservation.site_id" + 
				"            JOIN campground" + 
				"              ON campground.campground_id = site.campground_id" + 
				" WHERE campground.campground_id = ? AND (from_date <= ? AND to_date >= ?)";
		SqlRowSet results = jdbctemplate.queryForRowSet(sqlGetTakenSites, campId, endDate, startDate);
		while(results.next()) {
			sitesTaken.add(results.getInt("site_id"));
		}
			
		return sitesTaken;
	}
	
	@Override
	public List<Site> getSitesThatAreAvailableOnDateRange(int campId, Date startDate, Date endDate, int startMonth, int endMonth) {
		List<Site> sitesAvailable = new ArrayList<>();
		String sqlgetAvailableSites = 
				"  SELECT *   " 					 + 
				"   FROM site   "										 + 
				"   WHERE site_id IN (SELECT site.site_id              " +// Site is in list of sites available during dates wanted 
				"                       FROM site " 					 + 
				"                      WHERE site.site_id NOT IN   "     + 
				"                           (SELECT reservation.site_id " + // List sites with existing Reservations during the period wanted\n
				"                              FROM reservation   " + 
				"                              WHERE ? < to_date " + 
				"                                AND ? > from_date))   " + 
				"   AND campground_id IN (SELECT site.campground_id          " + // Campground is in the list of Campgrounds open during the dates wanted
				"                           FROM site" + 
				"                          WHERE site.campground_id IN (SELECT campground_id " +  // list of campgrounds open (Is Campground we want open during requested time)  
				"                                                         FROM campground   " + 
				"                                                        WHERE 06 >= cast(open_from_mm AS Integer)  " + // during the months 
				"                                                          AND 06 <= cast(open_to_mm AS Integer)))  " + // of the requested reservation  
				"                                                          AND campground_id =   ?                  " + // but only check Campground we want
				"    order by site.site_number desc limit 5";
		SqlRowSet results = jdbctemplate.queryForRowSet(sqlgetAvailableSites, startDate, endDate, campId);
		while(results.next()) {
			Site aSite = mapRowToSite(results);
			sitesAvailable.add(aSite);
		}
		return sitesAvailable;
	}
	
	
	private Site mapRowToSite(SqlRowSet row) {
		Site aSite = new Site();
		aSite.setSiteId(row.getLong("site_id"));
		aSite.setSiteNumber(row.getInt("site_number"));
		aSite.setAccessible(row.getBoolean("accessible"));
		aSite.setCampgroundId(row.getInt("campground_id"));
		aSite.setMaxOccupancy(row.getInt("max_occupancy"));
		aSite.setMaxRVLength(row.getInt("max_rv_length"));
		aSite.setUtilities(row.getBoolean("utilities"));
		return aSite;
	}
	
	
}

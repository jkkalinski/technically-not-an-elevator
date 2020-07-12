package com.techelevator.model.site;

import java.util.Date;
import java.util.List;

public interface SiteDAO {
	
	/*
	 * Search for available sites
	 */
	
	public List<Site> getAvailableSiteInfo(Long campgroundId, Date startDate, Date endDate);

}

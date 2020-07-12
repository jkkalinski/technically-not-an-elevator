package com.techelevator.model.site;

import java.util.Date;
import java.time.LocalDate;
import java.util.List;

public interface SiteDAO {
	
	
	public List<Site> getSitesThatAreAvailableOnDateRange(int campId, Date startDate, Date endDate, int startMonth, int endMonth);

	public List<Integer> getSitesThatAreTakenOnDateRange(int campId, Date startDate, Date endDate);
	
}

package com.techelevator.model.site;

public class Site {
	
	private Long siteId;
	private Integer siteNumber;


	private Integer campgroundId;
	private Integer maxOccupancy;
	private boolean accessible;
	private Integer maxRVLength;
	private boolean utilities;
	
	
	public Long getSiteId() {
		return siteId;
	}
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	public Integer getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Integer campgroundId) {
		this.campgroundId = campgroundId;
	}
	
	public Integer getMaxOccupancy() {
		return maxOccupancy;
	}
	public void setMaxOccupancy(Integer maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	public boolean isAccessible() {
		return accessible;
	}
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	public Integer getMaxRVLength() {
		return maxRVLength;
	}
	public void setMaxRVLength(Integer maxRVLength) {
		this.maxRVLength = maxRVLength;
	}
	public boolean isUtilities() {
		return utilities;
	}
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	public Integer getSiteNumber() {
		return siteNumber;
	}
	public void setSiteNumber(Integer siteNumber) {
		this.siteNumber = siteNumber;
	}
	
	
	@Override
	public String toString() {
		return "Site [siteId=" + siteId + ", campgroundId=" + campgroundId
				+ ", maxOccupancy=" + maxOccupancy + ", accessible=" + accessible + ", maxRVLength=" + maxRVLength
				+ ", utilities=" + utilities + "]";
	}
	
	

}

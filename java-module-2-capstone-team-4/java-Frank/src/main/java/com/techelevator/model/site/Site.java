package com.techelevator.model.site;

public class Site {
	
	private int         campId;
	private int     siteNumber;
	private int   maxOccupancy;
	private boolean accessible;
	private int    maxRvLength;
	private boolean  utilities;
		
	private Long        siteId;
	/**
	 * @return the siteId
	 */
	public Long getSiteId() {
		return siteId;
	}
	/**
	 * @param siteId the siteId to set
	 */
	public void setSiteId(Long siteId) {
		this.siteId = siteId;
	}
	/**
	 * @return the campId
	 */
	public int getCampId() {
		return campId;
	}
	/**
	 * @param campId the campId to set
	 */
	public void setCampId(int campId) {
		this.campId = campId;
	}
	/**
	 * @return the siteNumber
	 */
	public int getSiteNumber() {
		return siteNumber;
	}
	/**
	 * @param siteNumber the siteNumber to set
	 */
	public void setSiteNumber(int siteNumber) {
		this.siteNumber = siteNumber;
	}
	/**
	 * @return the maxOccupancy
	 */
	public int getMaxOccupancy() {
		return maxOccupancy;
	}
	/**
	 * @param maxOccupancy the maxOccupancy to set
	 */
	public void setMaxOccupancy(int maxOccupancy) {
		this.maxOccupancy = maxOccupancy;
	}
	/**
	 * @return the accessible
	 */
	public boolean isAccessible() {
		return accessible;
	}
	/**
	 * @param accessible the accessible to set
	 */
	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}
	/**
	 * @return the maxRvLength
	 */
	public int getMaxRvLength() {
		return maxRvLength;
	}
	/**
	 * @param maxRvLength the maxRvLength to set
	 */
	public void setMaxRvLength(int maxRvLength) {
		this.maxRvLength = maxRvLength;
	}
	/**
	 * @return the utilities
	 */
	public boolean isUtilities() {
		return utilities;
	}
	/**
	 * @param utilities the utilities to set
	 */
	public void setUtilities(boolean utilities) {
		this.utilities = utilities;
	}
	
	@Override
	public String toString() {
		return "Site [siteNumber=" + siteNumber + ", maxOccupancy=" + maxOccupancy + ", accessible=" + accessible
				+ ", maxRvLength=" + maxRvLength + ", utilities=" + utilities + "]";
	}

}

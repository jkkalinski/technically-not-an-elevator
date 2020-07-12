package com.techelevator.model.campground;

public class Campground {
	
	private Long campId;
	private int  campParkId;
	private String campName;
	private String openSeason;
	private String endSeason;
	private double dailyRate;
	
	/**
	 * @return the campId
	 */
	public Long getCampId() {
		return campId;
	}
	/**
	 * @param campId the campId to set
	 */
	public void setCampId(Long campId) {
		this.campId = campId;
	}
	/**
	 * @return the campParkId
	 */
	public int getCampParkId() {
		return campParkId;
	}
	/**
	 * @param campParkId the campParkId to set
	 */
	public void setCampParkId(int campParkId) {
		this.campParkId = campParkId;
	}
	/**
	 * @return the campName
	 */
	public String getCampName() {
		return campName;
	}
	/**
	 * @param campName the campName to set
	 */
	public void setCampName(String campName) {
		this.campName = campName;
	}
	/**
	 * @return the openSeason
	 */
	public String getOpenSeason() {
		return openSeason;
	}
	/**
	 * @param openSeason the openSeason to set
	 */
	public void setOpenSeason(String openSeason) {
		this.openSeason = openSeason;
	}
	/**
	 * @return the endSeason
	 */
	public String getEndSeason() {
		return endSeason;
	}
	/**
	 * @param endSeason the endSeason to set
	 */
	public void setEndSeason(String endSeason) {
		this.endSeason = endSeason;
	}
	/**
	 * @return the dailyRate
	 */
	public double getDailyRate() {
		return dailyRate;
	}
	/**
	 * @param dailyRate the dailyRate to set
	 */
	public void setDailyRate(double dailyRate) {
		this.dailyRate = dailyRate;
	}
	@Override
	public String toString() {
		return "Campground [campName= " + campName + ", dailyRate= " + dailyRate + "]";
	}
	
	

}

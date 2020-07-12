package com.techelevator.model.campground;

public class Campground {
	
	private Long campgroundId;
	private int parkId;
	private String name;
	private String openFromMonth;
	private String openToMonth;
	private double dailyFee;
	public Long getCampgroundId() {
		return campgroundId;
	}
	public void setCampgroundId(Long campgroundId) {
		this.campgroundId = campgroundId;
	}
	public int getParkId() {
		return parkId;
	}
	public void setParkId(int parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getOpenFromMonth() {
		return openFromMonth;
	}
	public void setOpenFromMonth(String openFromMonth) {
		this.openFromMonth = openFromMonth;
	}
	public String getOpenToMonth() {
		return openToMonth;
	}
	public void setOpenToMonth(String openToMonth) {
		this.openToMonth = openToMonth;
	}
	public double getDailyFee() {
		return dailyFee;
	}
	public void setDailyFee(double dailyFee) {
		this.dailyFee = dailyFee;
	}
	
	@Override
	public String toString() {
		return "Campground [campgroundId=" + campgroundId + ", parkId=" + parkId + ", name=" + name + ", openFromMonth="
				+ openFromMonth + ", openToMonth=" + openToMonth + ", dailyFee=" + dailyFee + "]";
	}
	
	

}

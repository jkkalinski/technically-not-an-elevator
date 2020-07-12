package com.techelevator.model.park;

import java.util.Date;

public class Park {

	private Long parkId;
	private String parkName;
	private String parkLocation;
	private Date parkEstDate;
	private int parkArea;
	private int parkVisitors;
	private String parkInfo;
	
	
	public Long getParkId() {
		return parkId;
	}
	
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
	
	public String getParkName() {
		return parkName;
	}
	
	public void setParkName(String parkName) {
		this.parkName = parkName;
	}
	
	public String getParkLocation() {
		return parkLocation;
	}
	
	public void setParkLocation(String parkLocation) {
		this.parkLocation = parkLocation;
	}
	
	public Date getParkEstDate() {
		return parkEstDate;
	}
	
	public void setParkEstDate(Date parkEstDate) {
		this.parkEstDate = parkEstDate;
	}
	
	public int getParkArea() {
		return parkArea;
	}
	
	public void setParkArea(int parkArea) {
		this.parkArea = parkArea;
	}
	
	public int getParkVisitors() {
		return parkVisitors;
	}
	
	public void setParkVisitors(int parkVisitors) {
		this.parkVisitors = parkVisitors;
	}
	
	public String getParkInfo() {
		return parkInfo;
	}
	
	public void setParkInfo(String parkInfo) {
		this.parkInfo = parkInfo;
	}
	
	@Override
	public String toString() {
		return parkName + "\n" + "Location:               " + parkLocation
		     + "\nEstablished:                " + parkEstDate
		     + "\nArea:                   " + parkArea 
		     + "\nAnnual Visitors:           " + parkVisitors
		     + "\n" + parkInfo;
	}
	
	
	
	
}

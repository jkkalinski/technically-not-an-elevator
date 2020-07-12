package com.techelevator.model.park;

import java.time.LocalDate;

public class Park {
	
	private Long parkId;
	private String name;
	private String location;
	private LocalDate estDate;
	private Integer area;
	private Integer visitorCount;
	private String description;
	
	public Long getParkId() {
		return parkId;
	}
	public void setParkId(Long parkId) {
		this.parkId = parkId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public LocalDate getEstDate() {
		return estDate;
	}
	public void setEstDate(LocalDate estDate) {
		this.estDate = estDate;
	}
	public Integer getArea() {
		return area;
	}
	public void setArea(Integer area) {
		this.area = area;
	}
	public Integer getVisitorCount() {
		return visitorCount;
	}
	public void setVisitorCount(Integer visitorCount) {
		this.visitorCount = visitorCount;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	@Override
	public String toString() {
		return "Id: " + parkId + " Name: " + name + " Location: " + location + " Established Date: " + estDate + " Area: " + area + " Visitor Count: " + visitorCount + " Description: " + description;
	}

}

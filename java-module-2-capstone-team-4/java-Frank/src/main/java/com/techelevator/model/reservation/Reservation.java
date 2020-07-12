package com.techelevator.model.reservation;

import java.time.LocalDate;
import java.util.Date;

public class Reservation {
	
	private Long reservationId;
	private int reservationSiteId;
	private String reservationName;
	private LocalDate resStartDate;
	private LocalDate resEndDate;
	private LocalDate resBookDate;
	
	
	public Long getReservationId() {
		return reservationId;
	}
	
	public void setReservationId(Long reservationId) {
		this.reservationId = reservationId;
	}
	
	public int getReservationSiteId() {
		return reservationSiteId;
	}
	
	public void setReservationSiteId(int reservationSiteId) {
		this.reservationSiteId = reservationSiteId;
	}
	
	public String getReservationName() {
		return reservationName;
	}
	
	public void setReservationName(String reservationName) {
		this.reservationName = reservationName;
	}
	
	public LocalDate getResStartDate() {
		return resStartDate;
	}

	public void setResStartDate(LocalDate resStartDate) {
		this.resStartDate = resStartDate;
	}
	
	public LocalDate getResEndDate() {
		return resEndDate;
	}
	
	public void setResEndDate(LocalDate resEndDate) {
		this.resEndDate = resEndDate;
	}
	
	public LocalDate getResBookDate() {
		return resBookDate;
	}
	
	public void setResBookDate(LocalDate resBookDate) {
		this.resBookDate = resBookDate;
	}
	
	@Override
	public String toString() {
		return "Your reservation ID: " + reservationId + "\nFor: " + reservationName + "\nSite #: "+ reservationSiteId + "\nBooked on: "
				+ resBookDate + "\nFrom: " + resStartDate + " Until: " + resEndDate;
	}
	

}

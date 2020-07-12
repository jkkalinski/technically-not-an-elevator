package com.techelevator.model.reservation;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface ReservationDAO {
	
	/*
	 * Select a campground and search for date availability to make
	 * a reservation
	 */
	
	public List<Reservation> searchReservationByAvailableSites(Long campgroundId, LocalDate fromDate, LocalDate toDate);
	
	
	/*
	 * User needs the ability to book a reservation at a selected campsite
	 */
	
	public void createReservation(int siteId, String name, LocalDate startDate, LocalDate endDate, Date createDate);
	
	
	public Reservation resConfirmed(String name, LocalDate startDate, LocalDate endDate);
	
	public String costOfStay(Long reservationId, Long reservationId2);

}

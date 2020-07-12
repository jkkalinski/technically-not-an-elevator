package com.techelevator.model.reservation;

import java.time.LocalDate;
import java.util.Date;

import com.techelevator.model.site.Site;

public interface ReservationDAO {
	
	public Reservation addReservation(int siteId, Date arrivalDate, Date departureDate, String reservationName);

	public Reservation searchExistingReservationsById(int id);
	
	public String getCostOfReservation(Long reservationId, Long reservationId2);

}

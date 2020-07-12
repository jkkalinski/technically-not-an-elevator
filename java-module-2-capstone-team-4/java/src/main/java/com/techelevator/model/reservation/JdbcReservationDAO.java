package com.techelevator.model.reservation;

import java.time.LocalDate;
import java.util.Date;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.site.Site;

public class JdbcReservationDAO implements ReservationDAO {

	private JdbcTemplate jdbctemplate;

	public JdbcReservationDAO(DataSource dataSource) {
		this.jdbctemplate = new JdbcTemplate(dataSource);
	}

	@Override
	public Reservation addReservation(int siteId, Date arrivalDate, Date departureDate, String reservationName) {
		Reservation newReso = new Reservation();
		String sqlGetNextId = "SELECT nextval('reservation_reservation_id_seq')";
		SqlRowSet results = jdbctemplate.queryForRowSet(sqlGetNextId);
		results.next();
		int id = results.getInt(1);
		String sqlAddReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date) VALUES (?, ?, ?, ?, ?)";// RETURNING
																																		// *";
		jdbctemplate.update(sqlAddReservation, id, siteId, reservationName, arrivalDate, departureDate);

		String sqlSelectReservationCode = "SELECT * from reservation where name like ?";
		SqlRowSet idResult = jdbctemplate.queryForRowSet(sqlSelectReservationCode, reservationName);
		// while(idResult.next())
		idResult.next();
		newReso = mapRowToReservation(idResult);

		return newReso;
	}

	@Override
	public Reservation searchExistingReservationsById(int id) {
		Reservation newReserve = new Reservation();
		String sqlSearchById = "SELECT * FROM reservation WHERE reservation_id=?";
		SqlRowSet results = jdbctemplate.queryForRowSet(sqlSearchById, id);
		if (results.next()) {
			return newReserve = mapRowToReservation(results);
		}
		return newReserve;
	}

	public Reservation mapRowToReservation(SqlRowSet results) {
		Reservation newReserve = new Reservation();
		newReserve.setCreateDate(results.getDate("create_date").toLocalDate());
		newReserve.setFromDate(results.getDate("from_date").toLocalDate());
		newReserve.setToDate(results.getDate("to_date").toLocalDate());
		newReserve.setName(results.getString("name"));
		newReserve.setReservationId(results.getLong("reservation_id"));
		newReserve.setSiteId(results.getInt("site_id"));
		return newReserve;
	}

	@Override
	public String getCostOfReservation(Long reservationId, Long reservationId2) {
		String yourTotal = "";
		double daysAtCamp = 0;
		double totalOwed = 0;
		Campground campFee = new Campground();
		String getFeeSQL = "SELECT campground.daily_fee " + "    FROM campground" + "    INNER JOIN site"
				+ "    ON site.campground_id =" + "    campground.campground_id" + "    INNER JOIN reservation"
				+ "    ON reservation.site_id =" + "    site.site_id" + "    WHERE reservation.site_id = "
				+ "    (SELECT site_id FROM reservation WHERE reservation_id = ?)";
		SqlRowSet sqlResults = jdbctemplate.queryForRowSet(getFeeSQL, reservationId);

		while (sqlResults.next()) {
			campFee = mapRowToCamp(sqlResults);
		}
		String getDays = "SELECT DATE_PART('day',"
				+ " (SELECT to_date FROM reservation WHERE reservation_id = ?)::timestamp"
				+ "  - (SELECT from_date FROM reservation WHERE reservation_id = ?)::timestamp)";

		SqlRowSet otherResults = jdbctemplate.queryForRowSet(getDays, reservationId2, reservationId2);

		while (otherResults.next()) {
			daysAtCamp = mapRowToDouble(otherResults);
		}

		double dailyFee = campFee.getDailyFee();
		totalOwed = dailyFee * daysAtCamp;

		yourTotal = "Your total fee for your stay is: $" + totalOwed;

		return yourTotal;

	}

	private Campground mapRowToCamp(SqlRowSet sqlResults) {
		Campground aCamp = new Campground();
		aCamp.setDailyFee(sqlResults.getDouble("daily_fee"));
		return aCamp;
	}

	private double mapRowToDouble(SqlRowSet sqlResults) {
		double daysStayed = 0;
		daysStayed = sqlResults.getDouble("DATE_PART");
		return daysStayed;
	}

}

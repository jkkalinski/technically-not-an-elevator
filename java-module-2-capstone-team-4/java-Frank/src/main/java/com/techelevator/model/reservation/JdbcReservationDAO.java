package com.techelevator.model.reservation;

import java.util.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import com.techelevator.model.campground.Campground;

public class JdbcReservationDAO implements ReservationDAO {
	
	private JdbcTemplate jdbcTemplate;
	
		public JdbcReservationDAO(DataSource dataSource) {
			this.jdbcTemplate = new JdbcTemplate(dataSource);
		}
	
		@Override
		public List<Reservation> searchReservationByAvailableSites(Long campgroundId, LocalDate fromDate, LocalDate toDate) {
			
			List<Reservation> availableSites = new ArrayList<Reservation>();
			
			String findAvailSites = "";
			
			SqlRowSet sqlResults = jdbcTemplate.queryForRowSet(findAvailSites);
			
			while(sqlResults.next()) {
				availableSites.add(mapRowToRes(sqlResults));
			}
			
			
			
			return availableSites;
		}
		
		@Override
		public void createReservation(int siteId, String name, LocalDate startDate, LocalDate endDate, Date createDate) {
			
			Reservation newRes = new Reservation();

//			newRes.setReservationId((Long) getNextResId());
			
			String addReservationSQL = "INSERT INTO reservation ("
									// + " reservation_id"
									 + " , site_id"
									 + " , name"
									 + " , from_date"
									 + " , to_date"
									 + " , create_date"
									 + "VALUES (?,?,?,?,?)";
			
			jdbcTemplate.update(addReservationSQL, siteId, name, startDate, endDate, createDate);

		}
		
		
		public Reservation resConfirmed(String name, LocalDate startDate, LocalDate endDate) {
			Reservation resConf = new Reservation();
			
			String getNewResSQL = "SELECT reservation_id"
							    + "       , site_id"
							    + "       , name"
							    + "       , from_date"
							    + "       , to_date"
							    + "       , create_date"
							    + " FROM reservation"
							    + " WHERE name = ? AND start_date = ? AND end_date = ?";
			
			SqlRowSet results = jdbcTemplate.queryForRowSet(getNewResSQL, name, startDate, endDate);
			
			while(results.next()) {
				resConf = mapRowToRes(results);
			}
			return resConf;
		}
		
		public String costOfStay(Long reservationId, Long reservationId2) {
			String yourTotal = "";
			double daysAtCamp = 0;
			double totalOwed = 0;
			Campground campFee = new Campground();
			String getFeeSQL = "SELECT daily_fee "
							   + "    FROM campground.daily_fee"
							   + "    INNER JOIN site"
							   + "    ON site.campground_id ="
							   + "    campground.campground_id"
							   + "    INNER JOIN reservation"
							   + "    ON reservation.site_id ="
							   + "    site.site_id"
							   + "    WHERE reservation.site_id = "
							   + "    (SELECT site_id FROM reservation WHERE reservation_id = ?";
			
			SqlRowSet sqlResults = jdbcTemplate.queryForRowSet(getFeeSQL, reservationId);
			
			while(sqlResults.next()) {
				campFee = mapRowToCamp(sqlResults);
			}
			String getDays = "SELECT DATE_PART('day',"
					       + " (SELECT from_date FROM reservation WHERE reservation_id = ?)::timestamp"
					       + "  - (SELECT to_date FROM reservation WHERE reservation_id = ?)::timestamp)";
			
			SqlRowSet otherResults = jdbcTemplate.queryForRowSet(getDays, reservationId2);
			
			while(otherResults.next()) {
				daysAtCamp = mapRowToDouble(otherResults);
			}
			
			double dailyFee = campFee.getDailyRate();
			totalOwed = dailyFee * daysAtCamp;
			
			yourTotal = "Your total fee for your stay is: $" + totalOwed;
			
			return yourTotal;
		}
		
		
		private Reservation mapRowToRes(SqlRowSet resultsFromSQL) {
			Reservation aRes = new Reservation();
			
			aRes.setReservationId(resultsFromSQL.getLong("reservation_id"));
			aRes.setReservationSiteId(resultsFromSQL.getInt("site_id"));
			aRes.setReservationName(resultsFromSQL.getString("name"));
			aRes.setResStartDate(resultsFromSQL.getDate("from_date").toLocalDate());
			aRes.setResEndDate(resultsFromSQL.getDate("to_date").toLocalDate());
			aRes.setResBookDate(resultsFromSQL.getDate("create_date").toLocalDate());

			return aRes;
		}
		
		private Campground mapRowToCamp(SqlRowSet resultsFromSQL) {
			Campground aCamp = new Campground();
			
			aCamp.setDailyRate(resultsFromSQL.getDouble("daily_fee"));
			
			return aCamp;
		}
		
		private double mapRowToDouble(SqlRowSet resultsFromSQL) {
			double daysStayed = 0;
			daysStayed = resultsFromSQL.getDouble("date_part");
			return daysStayed;
		}
		
		
//		private long getNextResId() {
//			SqlRowSet nextIdResult = jdbcTemplate.queryForRowSet("SELECT nextval('reservation_reservation_id_seq')");
//			if(nextIdResult.next()) {
//				return nextIdResult.getLong(1);
//			} else {
//				throw new RuntimeException("Something went wrong while getting an id for the new Rental");
//			}
//		}
		
			

	}

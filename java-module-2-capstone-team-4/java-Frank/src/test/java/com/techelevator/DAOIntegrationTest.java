package com.techelevator;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import javax.sql.DataSource;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;

import com.techelevator.model.campground.Campground;
import com.techelevator.model.campground.JdbcCampgroundDAO;
import com.techelevator.model.park.JdbcParkDAO;
import com.techelevator.model.park.Park;
import com.techelevator.model.reservation.JdbcReservationDAO;
import com.techelevator.model.reservation.Reservation;
import com.techelevator.model.site.JdbcSiteDAO;
import com.techelevator.model.site.Site;

public class DAOIntegrationTest {
	
	private static final Long TEST_PARK_ID = 100L;
	private static final String TEST_PARK_NAME = "Metro";
	private static final String TEST_PARK_LOCATION = "Cleveland";
	private static final LocalDate TEST_PARK_ESTDATE = LocalDate.of(1950,6,17);
	private static final int TEST_PARK_AREA = 1000;
	private static final int TEST_PARK_VISITORS = 1000000;
	private static final String TEST_PARK_INFO = "It's really great";
	
	
	private static final Long TEST_CAMP_ID = 69L;
	private static final int TEST_CAMP_PARK_ID = 69;
	private static final String TEST_CAMP_NAME = "Camp Anawanna";
	private static final String TEST_OPEN_FROM_MONTH = "01";
	private static final String TEST_OPEN_UNTIL_MONTH = "10";
	private static final BigDecimal TEST_CAMP_DAILY_FEE = BigDecimal.valueOf(69);
	
	
	private static final Long TEST_SITE_ID = 96L;
	private static final int TEST_SITE_CAMP_ID = 19;
	private static final int TEST_SITE_NUMBER = 73;
	private static final int TEST_SITE_MAX_OCCUPANCY = 54;
	private static final boolean TEST_SITE_ACCESSIBLE = true;
	private static final int TEST_SITE_MAX_RV_LENGTH = 40;
	private static final boolean TEST_SITE_UTILITIES = true;
	
	
	private static final Long TEST_RESERVATION_ID = 999L;
	private static final int TEST_RESERVATION_SITE_ID = 1999;
	private static final String TEST_RESERVATION_NAME = "Buehler";
	private static final LocalDate TEST_RESERVATION_FROM_DATE = LocalDate.of(2020,6,4);
	private static final LocalDate TEST_RESERVATION_TO_DATE = LocalDate.of(2020,7,4);
	private static final LocalDate TEST_RESERVATION_CREATE_DATE = LocalDate.of(2020,6,9);
	
	
	
	
	
	/* Using this particular implementation of DataSource so that
	 * every database interaction is part of the same database
	 * session and hence the same database transaction */
	private static SingleConnectionDataSource dataSource;
	
	private JdbcParkDAO daoPark;
	private JdbcCampgroundDAO daoCamp;
	private JdbcSiteDAO daoSite;
	private JdbcReservationDAO daoRes;
	

	/* Before any tests are run, this method initializes the datasource for testing. */
	@BeforeClass
	public static void setupDataSource() {
		dataSource = new SingleConnectionDataSource();
		dataSource.setUrl("jdbc:postgresql://localhost:5432/campground");
		dataSource.setUsername("postgres");
		dataSource.setPassword("postgres1");
		/* The following line disables autocommit for connections
		 * returned by this DataSource. This allows us to rollback
		 * any changes after each test */
		dataSource.setAutoCommit(false);
	}

	/* After all tests have finished running, this method will close the DataSource */
	@AfterClass
	public static void closeDataSource() throws SQLException {
		dataSource.destroy();
	}

	/* After each test, we rollback any changes that were made to the database so that
	 * everything is clean for the next test */
	@After
	public void rollback() throws SQLException {
		dataSource.getConnection().rollback();
	}

	/* This method provides access to the DataSource for subclasses so that
	 * they can instantiate a DAO for testing */
	protected DataSource getDataSource() {
		return dataSource;
	}
	
	@Before
	public void setup() {
		daoPark = new JdbcParkDAO(dataSource);
		daoCamp = new JdbcCampgroundDAO(dataSource);
		daoSite = new JdbcSiteDAO(dataSource);
		daoRes  = new JdbcReservationDAO(dataSource);
		
		
		
		String sqlInsertPark = "INSERT INTO park (park_id, name, location, establish_date, area, visitors, description)"
								 + "   VALUES (?,?,?,?,?,?,?)";
		
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
		jdbcTemplate.update(sqlInsertPark, TEST_PARK_ID, TEST_PARK_NAME, TEST_PARK_LOCATION, TEST_PARK_ESTDATE, TEST_PARK_AREA, TEST_PARK_VISITORS, TEST_PARK_INFO);
		
		
		String sqlInsertCamp = "INSERT INTO campground (campground_id, park_id, name, open_from_mm, open_to_mm, daily_fee)"
							 + "        VALUES (?,?,?,?,?,?)";
		
		JdbcTemplate jdbcTemplate2 = new JdbcTemplate(dataSource);
		jdbcTemplate2.update(sqlInsertCamp, TEST_CAMP_ID, TEST_CAMP_PARK_ID, TEST_CAMP_NAME, TEST_OPEN_FROM_MONTH, TEST_OPEN_UNTIL_MONTH, TEST_CAMP_DAILY_FEE);
		
		
		String sqlInsertSite = "INSERT INTO site (site_id, campground_id, site_number, max_occupancy, accessible, max_rv_length, utilities)"
							+ "       VALUES (?,?,?,?,?,?,?)";
		
		JdbcTemplate jdbcTemplate3 = new JdbcTemplate(dataSource);
		jdbcTemplate3.update(sqlInsertSite, TEST_SITE_ID, TEST_SITE_CAMP_ID, TEST_SITE_NUMBER, TEST_SITE_MAX_OCCUPANCY, TEST_SITE_ACCESSIBLE, TEST_SITE_MAX_RV_LENGTH, TEST_SITE_UTILITIES);
		
		
		String sqlInsertReservation = "INSERT INTO reservation (reservation_id, site_id, name, from_date, to_date, create_date)"
									+ "       VALUES (?,?,?,?,?,?)";
		
		JdbcTemplate jdbcTemplate4 = new JdbcTemplate(dataSource);
		jdbcTemplate4.update(sqlInsertReservation, TEST_RESERVATION_ID, TEST_RESERVATION_SITE_ID, TEST_RESERVATION_NAME, TEST_RESERVATION_FROM_DATE, TEST_RESERVATION_TO_DATE, TEST_RESERVATION_CREATE_DATE);
	}
	
	@Test
	public void test_get_all_parks() {
		
		List<Park> testresults = daoPark.getAllParks();
		
		assertNotNull(testresults);
		assertEquals(4, testresults.size());
	}
	
	@Test
	public void test_get_all_camps() {
		
//		List<Campground> testresults = daoCamp.getAllCampsByPark(1);
//		
//		assertNotNull(testresults);
//		assertEquals(3, testresults.size());
	}
	
//	@Test
//	public void test_all_sites_in_campground_by_dates() {
//		
//		List<Reservation> testresults = daoRes.searchReservationByAvailableSites( TEST_RESERVATION_FROM_DATE, TEST_RESERVATION_TO_DATE);
//		
//		assertNotNull(testresults);
//	}
	
	@Test
	public void test_create_reservation() {
//   Frank - I commented this out to run the app
//		Reservation testRes = daoRes.createReservation(TEST_RESERVATION_ID, TEST_RESERVATION_NAME, TEST_RESERVATION_FROM_DATE, TEST_RESERVATION_TO_DATE, TEST_RESERVATION_CREATE_DATE);
		
	}
	
	
	
	/*******************   HELPER METHODS   *************************/
	
	
	public Reservation getReservation(Long reservationId, int reservationSiteId, String reservationName, LocalDate resStartDate, LocalDate resEndDate, Date resBookDate) {
		Reservation fakeRes = new Reservation();
		fakeRes.setReservationId(TEST_RESERVATION_ID);
		fakeRes.setReservationSiteId(TEST_RESERVATION_SITE_ID);
		fakeRes.setReservationName(TEST_RESERVATION_NAME);
		fakeRes.setResStartDate(TEST_RESERVATION_FROM_DATE);
		fakeRes.setResEndDate(TEST_RESERVATION_TO_DATE);
		fakeRes.setResBookDate(TEST_RESERVATION_CREATE_DATE);
		
		return fakeRes;
		
	}
}


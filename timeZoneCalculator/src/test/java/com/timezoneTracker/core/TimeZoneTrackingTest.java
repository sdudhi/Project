package com.timezoneTracker.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.TimeZone;

import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.timezoneTracker.exceptions.IllegalTimeZoneEntryException;

/**
 * @author SDUDHI
 * 
 */
public class TimeZoneTrackingTest {

	private TimeZoneTracking timeZoneTracking;
	private final Logger logger = Logger.getLogger(TimeZoneTrackingTest.class);

	@Before
	public void setUp() throws Exception {
		timeZoneTracking = new TimeZoneTracking();
	}

	@After
	public void tearDown() throws Exception {
		timeZoneTracking = null;
	}

	/**
	 * Test case to get the time zones in between two time zones
	 * 
	 */
	@Test
	public void getTimeZonesInBetween() throws IllegalTimeZoneEntryException {
		logger.info("TimeZoneTrackingTest::getTimeZonesInBetween()::Start");
		String startTimeZone = "PST";
		String endTimeZone = "EST";
		Assert.assertNotNull(timeZoneTracking);
		LinkedHashMap<String, ArrayList<TimeZone>> inBetweenTimeZones = timeZoneTracking
				.getTimeZonesInBetween(startTimeZone, endTimeZone);
		// LinkedHashMap<String, ArrayList<TimeZone>> inBetweenTimeZones =
		// timeZoneTracking.getTimeZonesInBetween(startTimeZone, endTimeZone);
		int count = inBetweenTimeZones.size();
		Assert.assertEquals(2, count);
		logger.info("Total TimeZones Between " + startTimeZone + " and "
				+ endTimeZone + ": " + count);
		logger.info("TimeZones Between " + startTimeZone + " and "
				+ endTimeZone );
		timeZoneTracking.displayTimeZones(inBetweenTimeZones);
		logger.info("TimeZoneTrackingTest::getTimeZonesInBetween()::End");
	}

	/**
	 * Test case to get the timezones if provided in reverse order
	 * 
	 */
	@Test
	public void getTimeZonesInBetweenInReverse()
			throws IllegalTimeZoneEntryException {
		logger.info("TimeZoneTrackingTest::getTimeZonesInBetweenInReverse()::Start");
		String startTimeZone = "EST";
		String endTimeZone = "PST";
		Assert.assertNotNull(timeZoneTracking);
		LinkedHashMap<String, ArrayList<TimeZone>> inBetweenTimeZones = timeZoneTracking
				.getTimeZonesInBetween(startTimeZone, endTimeZone);
		int count = inBetweenTimeZones.size();
		Assert.assertEquals(2, count);
		logger.info("Total TimeZones Between " + startTimeZone + " and "
				+ endTimeZone + ": " + count);
		timeZoneTracking.displayTimeZones(inBetweenTimeZones);
		logger.info("TimeZoneTrackingTest::getTimeZonesInBetweenInReverse()::End");

	}

	/**
	 * Test case to check the Exception
	 * 
	 */
	@Test(expected = IllegalTimeZoneEntryException.class)
	public void getTimeZonesIncorrectData()
			throws IllegalTimeZoneEntryException {
		logger.info("TimeZoneTrackingTest::getTimeZonesIncorrectData()::Start");
		String startTimeZone = "IST1";
		String endTimeZone = "PST2";
		Assert.assertNotNull(timeZoneTracking);
		LinkedHashMap<String, ArrayList<TimeZone>> inBetweenTimeZones = timeZoneTracking
				.getTimeZonesInBetween(startTimeZone, endTimeZone);
		int count = inBetweenTimeZones.size();
		Assert.assertEquals(0, count);
		logger.info("Total TimeZones Between " + startTimeZone + " and "
				+ endTimeZone + ": " + count);
		timeZoneTracking.displayTimeZones(inBetweenTimeZones);
		logger.info("TimeZoneTrackingTest::getTimeZonesIncorrectData()::End");
	}

}

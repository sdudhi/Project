/**
 * 
 */
package com.timezoneTracker.core;

import java.util.Set;

import com.assignment.IllegalTimeZoneException;
import com.timezoneTracker.core.TimeZoneTracking;

import junit.framework.Assert;
import junit.framework.TestCase;

/**
 * @author SDUDHI
 *
 */
public class TimeZoneTrackingTest extends TestCase {

	TimeZoneTracking timeZoneTracking;
	/* (non-Javadoc)
	 * @see junit.framework.TestCase#setUp()
	 */
	protected void setUp() throws Exception {
		super.setUp();
		timeZoneTracking = new TimeZoneTracking();
	}

	/* (non-Javadoc)
	 * @see junit.framework.TestCase#tearDown()
	 */
	protected void tearDown() throws Exception {
		super.tearDown();
		timeZoneTracking= null;
	}

	/**
	 * @throws IllegalTimeZoneException 
	 * 
	 */
	public void testGetTimeZonesInBetween() throws IllegalTimeZoneException {
		String startTimeZone ="PST";
		String endTimeZone = "EST";
		assertNotNull(timeZoneTracking);
		Set<String> inBetweenTimeZones = timeZoneTracking.getTimeZonesInBetween(startTimeZone, endTimeZone);
		Assert.assertEquals(2, inBetweenTimeZones.size());
		timeZoneTracking.displayTimeZones(inBetweenTimeZones);
	}
	
	/**
	 * @throws IllegalTimeZoneException 
	 * 
	 */
	public void testGetTimeZonesInBetweenInReverse() throws IllegalTimeZoneException {
		String startTimeZone ="EST";
		String endTimeZone = "PST";
		assertNotNull(timeZoneTracking);
		Set<String> inBetweenTimeZones = timeZoneTracking.getTimeZonesInBetween(startTimeZone, endTimeZone);
		Assert.assertEquals(2, inBetweenTimeZones.size());
		timeZoneTracking.displayTimeZones(inBetweenTimeZones);
	}
	/**
	 * @throws IllegalTimeZoneException 
	 * 
	 */
	public void testGetTimeZonesIncorrectData() throws IllegalTimeZoneException {
        /*try {
        	timeZoneTracking.getTimeZonesInBetween(startTimeZone, endTimeZone);            fail("Should throw an exception if one or more of given numbers are negative");
        } catch (Exception e) {
            Assert.assertTrue(e)
                    .isInstanceOf(IllegalTimeZoneException.class)
                    .hasMessage("negatives not allowed: [-1, -2]");
        }
*/
		String startTimeZone ="123";
		String endTimeZone = "test";
		assertNotNull(timeZoneTracking);
		Set<String> inBetweenTimeZones = timeZoneTracking.getTimeZonesInBetween(startTimeZone, endTimeZone);
		Assert.assertEquals(0, inBetweenTimeZones.size());
		timeZoneTracking.displayTimeZones(inBetweenTimeZones);
	}
	
}

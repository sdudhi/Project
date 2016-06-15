package com.timezoneTracker.core;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Optional;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import com.timezoneTracker.exceptions.IllegalTimeZoneEntryException;
/**
 * 
 * @author SDUDHI
 * @link TimeZoneTracking class is used for fetching the time zones that are between two time zones.
 * 
 */
public class TimeZoneTracking {
	final static Logger logger = Logger.getLogger(TimeZoneTracking.class);

	 /**
	   * Method to get all the time zones between two time zones
	   *
	   * @param firstTimeZone String first time zone to start
	   * @param secondTimeZone , end time zone 
	   *
	   * @exception @link IllegalTimeZoneEntryException
	   * 
	   * @return Set<String>.
	   */ 
	public LinkedHashMap<String, ArrayList<TimeZone>> getTimeZonesInBetween(String firstTimeZone, String secondTimeZone) throws IllegalTimeZoneEntryException {
		logger.debug("TimeZoneTracking::getTimeZonesInBetween()::Start");
		LinkedHashMap<String, ArrayList<TimeZone>> inBetweenTimeZones =
										calculateInbetweenTimeZones(firstTimeZone, secondTimeZone);
		Optional<LinkedHashMap<String, ArrayList<TimeZone>>> optionalDataSet = Optional.ofNullable(inBetweenTimeZones);
		if (!optionalDataSet.isPresent() || inBetweenTimeZones.size() <= 0) {
			logger.debug("Interchanging the order entered to follow the sequence of timeZones");
			inBetweenTimeZones = calculateInbetweenTimeZones(secondTimeZone, firstTimeZone);
			optionalDataSet = Optional.ofNullable(inBetweenTimeZones);
			if (!optionalDataSet.isPresent() || inBetweenTimeZones.size()<=0) {
				logger.error("Invalid TimeZone entry : " + firstTimeZone + " or "  + secondTimeZone);
				throw new IllegalTimeZoneEntryException("Invalid TimeZone entry");
			}
		}
		logger.debug("TimeZoneTracking::getTimeZonesInBetween()::End");
		return inBetweenTimeZones;

	}

	 /**
	   * Method to fetch all the timeZone that are available between two time zones
	   * 
	   * @param first String first time zone to start
	   * @param second , end time zone 
	   *
	   * @return LinkedHashMap<String, ArrayList<TimeZone>>.
	   */ 
	private LinkedHashMap<String, ArrayList<TimeZone>> calculateInbetweenTimeZones(
			String first, String second) {
		logger.debug("TimeZoneTracking::calculateInbetweenTimeZones()::Start");

		boolean isFirst = false;
		boolean isLast = false;

		LinkedHashMap<String, ArrayList<TimeZone>> allTimeZones = getAllTimeZones();
		LinkedHashMap<String, ArrayList<TimeZone>> uniqueTimeZones = new LinkedHashMap<String, ArrayList<TimeZone>>();

		for (String id : allTimeZones.keySet()) {
			if (id.equalsIgnoreCase(getTimeZone(TimeZone.getTimeZone(second)))) {
				isLast = true;
				break;
			}
			if (isFirst)
				uniqueTimeZones.put(id, allTimeZones.get(id));
			if (id.equalsIgnoreCase(getTimeZone(TimeZone.getTimeZone(first))))
				isFirst = true;
		}
		logger.debug("TimeZoneTracking::calculateInbetweenTimeZones()::End");

		if (isFirst && isLast) {
			return uniqueTimeZones;
		} else {
			return null;
		}
		
	}

	
 /**
   * Method to get all the timeZones 
   * 
   * @return LinkedHashMap<String, ArrayList<TimeZone>>.
   */ 
	private LinkedHashMap<String, ArrayList<TimeZone>> getAllTimeZones() {
		logger.debug("TimeZoneTracking::getAllTimeZones()::Start");

		LinkedHashMap<String, ArrayList<TimeZone>> uniqueTimeZones = new LinkedHashMap<String, ArrayList<TimeZone>>();
		String[] ids = TimeZone.getAvailableIDs();
        List<TimeZone> sortedTimeZones = new ArrayList<TimeZone>();
		String timeZone = "";
		ArrayList<TimeZone> timeZones = null;
		
		//Sort the existing Time zones of @link TimeZone that are available
        for (String id : ids) {
            sortedTimeZones.add(TimeZone.getTimeZone(id));
        }
        
        Collections.sort(sortedTimeZones,
                        new Comparator<TimeZone>() {
            public int compare(TimeZone s1, TimeZone s2) {
                return s1.getRawOffset() - s2.getRawOffset();
            }
        });
       
        //Arrange the TimeZone Ids 
		for (TimeZone id : sortedTimeZones) {
			timeZone = getTimeZone(TimeZone.getTimeZone(id.getID()));
			if (uniqueTimeZones.containsKey(timeZone)) {
				timeZones = uniqueTimeZones.get(timeZone);
				timeZones.add(TimeZone.getTimeZone(id.getID()));
				uniqueTimeZones.put(timeZone, timeZones);
			} else {
				timeZones = new ArrayList<TimeZone>();
				timeZones.add(TimeZone.getTimeZone(id.getID()));
				uniqueTimeZones.put(timeZone, timeZones);
			}

		}	
		logger.debug("TimeZoneTracking::getAllTimeZones()::End");

		return uniqueTimeZones;

	}

	 /**
	   * Format the time zone 
	   * 
	   * @param @link TimeZone
	   * @return String
	   */ 
	private String getTimeZone(TimeZone tz) {
		logger.debug("TimeZoneTracking::getTimeZone()::Start");

		long hours = TimeUnit.MILLISECONDS.toHours(tz.getRawOffset());
		long minutes = TimeUnit.MILLISECONDS.toMinutes(tz.getRawOffset())
				- TimeUnit.HOURS.toMinutes(hours);
		minutes = Math.abs(minutes);
		String result = "";
		if (hours > 0) {
			result = String.format("(GMT+%d:%02d)", hours, minutes);
		} else if (hours < 0) {
			result = String.format("(GMT%d:%02d)", hours, minutes);
		} else {
			result = String.format("(GMT)");
		}
		logger.debug("TimeZoneTracking::getTimeZone()::End");
		return result;
	}
	
	public void displayTimeZones(LinkedHashMap<String, ArrayList<TimeZone>> inBetweenTimeZones) {
		if (!inBetweenTimeZones.isEmpty()) {
			for (String key : inBetweenTimeZones.keySet()) {
				System.out.println("        "+key + " , " + inBetweenTimeZones.get(key).get(0).getDisplayName());				
			}
		}
	}
}
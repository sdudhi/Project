package com.timezoneTracker.core;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

import com.assignment.IllegalTimeZoneException;

public class TimeZoneTracking {

	public static void main(String args[]) {
		TimeZoneTracking gt = new TimeZoneTracking();
		try {
			gt.getTimeZonesInBetween("123", "222");
		} catch (IllegalTimeZoneException e) {
			e.printStackTrace();
		}
	}

	public Set<String> getTimeZonesInBetween(String first, String second) throws IllegalTimeZoneException {
		List<TimeZone> timeZones = new ArrayList<TimeZone>();
		LinkedHashMap<String, ArrayList<TimeZone>> inBetweenTimeZones = getInBetweenTimeZones(
				first, second);

		if (inBetweenTimeZones == null) {
			inBetweenTimeZones = getInBetweenTimeZones(second, first);
			if (inBetweenTimeZones == null) {
				throw new IllegalTimeZoneException("Invalid TimeZone entry");
			}
		}
		
		/*if(!inBetweenTimeZones.isEmpty()){
			for (String key : inBetweenTimeZones.keySet()) {
				System.out.println(key);
				timeZones = inBetweenTimeZones.get(key);
			}
		}
		*/
			return inBetweenTimeZones.keySet();

	}

	/*
	 * Method to fetch all the timeZone that are available between two time
	 * zones
	 */
	private LinkedHashMap<String, ArrayList<TimeZone>> getInBetweenTimeZones(
			String first, String second) {
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

		if (isFirst && isLast) {
			return uniqueTimeZones;
		} else {
			return null;
		}
	}

	/*
	 * Method to fetch all the timeZones
	 */
	private LinkedHashMap<String, ArrayList<TimeZone>> getAllTimeZones() {
		LinkedHashMap<String, ArrayList<TimeZone>> uniqueTimeZones = new LinkedHashMap<String, ArrayList<TimeZone>>();
		String[] ids = TimeZone.getAvailableIDs();
		String timeZone = "";
		ArrayList<TimeZone> timeZones = null;
		for (String id : ids) {
			timeZone = getTimeZone(TimeZone.getTimeZone(id));
			if (uniqueTimeZones.containsKey(timeZone)) {
				timeZones = uniqueTimeZones.get(timeZone);
				timeZones.add(TimeZone.getTimeZone(id));
				uniqueTimeZones.put(timeZone, timeZones);
			} else {
				timeZones = new ArrayList<TimeZone>();
				timeZones.add(TimeZone.getTimeZone(id));
				uniqueTimeZones.put(timeZone, timeZones);
			}

		}
		return uniqueTimeZones;

	}

	/*
	 * Format the Timezone
	 */
	private static String getTimeZone(TimeZone tz) {
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
		return result;
	}
	
	public void displayTimeZones(Set<String> inBetweenTimeZones){
		if(!inBetweenTimeZones.isEmpty()){
			for (String key : inBetweenTimeZones) {
				System.out.println(key);
				//timeZones = inBetweenTimeZones.get(key);
			}
		}
	}

}
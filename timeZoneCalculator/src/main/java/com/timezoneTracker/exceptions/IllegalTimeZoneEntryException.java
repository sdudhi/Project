package com.timezoneTracker.exceptions;
/**
 * 
 * @author SDUDHI
 * 
 * IllegalTimeZoneException is a class to capture the improper timezones.
 * 
 */
public class IllegalTimeZoneEntryException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String message = null;
	 
    public IllegalTimeZoneEntryException() {
        super();
    }
 
    public IllegalTimeZoneEntryException(String message) {
        super(message);
        this.message = message;
    }
 
    public IllegalTimeZoneEntryException(Throwable cause) {
        super(cause);
    }
 
    @Override
    public String toString() {
        return message;
    }
 
    @Override
    public String getMessage() {
        return message;
    }

}

package com.assignment;
/**
 * 
 * @author SDUDHI
 * 
 * IllegalTimeZoneException is a class to capture the improper timezones.
 * 
 */
public class IllegalTimeZoneException extends Exception {
	
	private static final long serialVersionUID = 1L;
	private String message = null;
	 
    public IllegalTimeZoneException() {
        super();
    }
 
    public IllegalTimeZoneException(String message) {
        super(message);
        this.message = message;
    }
 
    public IllegalTimeZoneException(Throwable cause) {
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

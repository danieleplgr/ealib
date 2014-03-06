package com.ealib.net;

public class ConnectivityException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -6017231905961276430L;
	
	@Override
	public String getMessage() {
		return "Error: No valid connection was found.";
	}
}

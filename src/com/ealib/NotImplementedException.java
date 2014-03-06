package com.ealib;

public class NotImplementedException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1386502894171873939L;
	private String customMess;

	public NotImplementedException(String customMess) {
		this.customMess = customMess;
	}
	
	public NotImplementedException() {
	}

	@Override
	public String getMessage() {
		if (customMess == null)
			return "Not implemented yet!";
		return customMess;
	}

}

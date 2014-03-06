package com.ealib.io.sd;

import java.io.IOException;

public class ExternalStorageNotAvailableException extends IOException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 555020948968806547L;

	public ExternalStorageNotAvailableException() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public String getMessage() {
		return "External storage not mouthed";
	}
	
}

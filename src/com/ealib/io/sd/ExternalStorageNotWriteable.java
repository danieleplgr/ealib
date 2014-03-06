package com.ealib.io.sd;

import java.io.IOException;

public class ExternalStorageNotWriteable extends IOException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5187863986630150610L;

	public ExternalStorageNotWriteable() {
	}
	
	@Override
	public String getMessage() {
		return "External storage not writable";
	}
	
	
}

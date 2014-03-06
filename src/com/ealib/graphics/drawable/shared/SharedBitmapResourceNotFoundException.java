package com.ealib.graphics.drawable.shared;

public class SharedBitmapResourceNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1312362399239808245L;
	private String customImageId;
	
	
	
	public SharedBitmapResourceNotFoundException(String customId) {
		this.customImageId = customId;
	}



	@Override
	public String getMessage() {
		return "The resource BitmapImage with customId: " +this.customImageId + " was not found!";
	}

}

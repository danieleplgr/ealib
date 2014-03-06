package com.ealib.graphics.drawable.shared;

public class SharedDrawableResourceNotFoundException extends Exception {

	private String customImageId;

	public SharedDrawableResourceNotFoundException(String customId) {
		this.customImageId = customId;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -4031338603475960724L;

	@Override
	public String getMessage() {
		return "The resource DrawableImage with customId: " +this.customImageId + " was not found!";
	}
	
}

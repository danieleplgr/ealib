package com.ealib.localization;

import com.ealib.localization.GeoLocator.LocationListenerProvider;

public class LocationProviderNotPermitterException extends Exception {

	private GeoLocator.LocationListenerProvider provider;

	public LocationProviderNotPermitterException(
			LocationListenerProvider provider) {
		this.provider = provider;
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = -5053615797698123205L;

	@Override
	public String getMessage() {
		return "The provider "+provider.toString()+" is not permitted in the current device.";
	}
	
}

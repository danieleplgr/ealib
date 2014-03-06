package com.ealib.localization;

import android.location.Location;

public class LocationListenerRegistrationResult {

	private boolean gpsProviderEnabled;
	private boolean networkProviderEnabled;
	private Location gpsLastKnownLocation;
	private Location networkLastKnownLocation;

	public boolean isGpsProviderEnabled() {
		return gpsProviderEnabled;
	}

	public void setGpsProviderEnabled(boolean gpsProviderEnabled) {
		this.gpsProviderEnabled = gpsProviderEnabled;
	}

	public boolean isNetworkProviderEnabled() {
		return networkProviderEnabled;
	}

	public void setNetworkProviderEnabled(boolean networkProviderEnabled) {
		this.networkProviderEnabled = networkProviderEnabled;
	}

	public void setGpsLastKnownLocation(Location gpsLastKnownLocation) {
		this.gpsLastKnownLocation = gpsLastKnownLocation;
	}

	public Location getGpsLastKnownLocation() {
		return gpsLastKnownLocation;
	}

	public void setNetworkLastKnownLocation(Location networkLastKnownLocation) {
		this.networkLastKnownLocation = networkLastKnownLocation;
	}
	
	public Location getNetworkLastKnownLocation() {
		return networkLastKnownLocation;
	}
	
}

package com.ealib.localization;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

public class GeoLocator {
	
	public enum LocationListenerProvider {
		GPS_PROVIDER, NETWORK_PROVIDER
	}

	private boolean gpsEnabled;
	private boolean networkEnabled;
	
	private Location gpsLastKnownLocation;
	private Location networkLastKnownLocation;
	
	private static LocationManager locationManager;
	
	LocationResultListener locationResultListener;
	
	public GeoLocator(Context context) throws LocationProviderNotPermitterException {
		instanciateLocationManager(context);
		
		refreshProvidersConnectivityInfo(context);
		refreshLastKnownLocations();
	}


	private void refreshLastKnownLocations() {

		if (gpsEnabled) {
			gpsLastKnownLocation = locationManager.getLastKnownLocation(LocationManager.GPS_PROVIDER);
		}
		
		if (networkEnabled) {
			networkLastKnownLocation = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
		}
	}


	private void refreshProvidersConnectivityInfo(Context context)
			throws LocationProviderNotPermitterException {
		instanciateLocationManager(context);
		gpsEnabled = isGpsEnabled(context);
		networkEnabled = isNetworkEnabled(context);
	}

	public void registerLocationUpgradesListener(
			Context context, LocationResultListener result) throws LocationProviderNotPermitterException {

		refreshProvidersConnectivityInfo(context);
//		refreshLastKnownLocations();
		
		if (gpsEnabled) {
			locationManager.requestLocationUpdates(
					LocationManager.GPS_PROVIDER, 0, 0, locationListenerGps);
		}

		if (networkEnabled) {
			locationManager.requestLocationUpdates(
					LocationManager.NETWORK_PROVIDER, 0, 0,
					locationListenerNetwork);
		}
		
	}

	private static void instanciateLocationManager(Context context) {
		if (locationManager == null)
			locationManager = (LocationManager) context
					.getSystemService(Context.LOCATION_SERVICE);
	}

	LocationListener locationListenerGps = new LocationListener() {
		@Override
		public void onLocationChanged(Location newLocation) {
			
			if(isValid(newLocation)){
				GeoLocator.this.gpsLastKnownLocation = newLocation;
			}
			
			locationResultListener.onProviderLocationChanged(newLocation, LocationListenerProvider.GPS_PROVIDER);
			
			boolean continuosUpgrades = locationResultListener.getContinousUpgrades();
			if(continuosUpgrades == false){
				locationManager.removeUpdates(this);
				locationManager.removeUpdates(locationListenerGps);
			}
		}

		public void onProviderDisabled(String provider) {
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(locationListenerGps);
			locationResultListener.onGpsProviderDisabled();
		}

		public void onProviderEnabled(String provider) {
			locationResultListener.onGpsProviderEnabled();
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
	};

	LocationListener locationListenerNetwork = new LocationListener() {
		public void onLocationChanged(Location newLocation) {

			if(isValid(newLocation)){
				GeoLocator.this.networkLastKnownLocation = newLocation;
			}
			
			locationResultListener.onProviderLocationChanged(newLocation, LocationListenerProvider.NETWORK_PROVIDER);
			
			boolean continuosUpgrades = locationResultListener.getContinousUpgrades();
			if(continuosUpgrades == false){
				locationManager.removeUpdates(this);
				locationManager.removeUpdates(locationListenerNetwork);
			}
			
		}

		public void onProviderDisabled(String provider) {
			locationManager.removeUpdates(this);
			locationManager.removeUpdates(locationListenerNetwork);
			locationResultListener.onNetworkProviderDisabled();
		}

		public void onProviderEnabled(String provider) {
			locationResultListener.onNetworkProviderEnabled();
		}

		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
	};
	
	public static abstract class LocationResultListener {

		public abstract void onProviderLocationChanged(Location newLocation,
				LocationListenerProvider locatizationProvider);

		public boolean getContinousUpgrades() {
			return false;
		}

		public abstract void onGpsProviderDisabled();

		public abstract void onGpsProviderEnabled();

		public abstract void onNetworkProviderDisabled();

		public abstract void onNetworkProviderEnabled();

	}

	public static boolean isGpsEnabled(Context context) throws LocationProviderNotPermitterException {
		try {
			instanciateLocationManager(context);
			if (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
				return true;
			}
		} catch (Exception e) {
			throw new LocationProviderNotPermitterException(LocationListenerProvider.GPS_PROVIDER);
		}
		return false;
	}

	protected static boolean isNetworkEnabled(Context context) throws LocationProviderNotPermitterException {
		try {
			instanciateLocationManager(context);
			if (locationManager
					.isProviderEnabled(LocationManager.NETWORK_PROVIDER)) {
				return true;
			}
		} catch (Exception e) {
			throw new LocationProviderNotPermitterException(LocationListenerProvider.NETWORK_PROVIDER);
		}
		return false;
	}

	public static void buildAlertMessageNoGps(final Context context, String message, String yesMessageString, String noMessageString) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		builder.setMessage(message)
				.setCancelable(false)
				.setPositiveButton(yesMessageString,
						new DialogInterface.OnClickListener() {
							public void onClick(
									  final DialogInterface dialog,
									 final int id) {
								context.startActivity(new Intent(
										android.provider.Settings.ACTION_LOCATION_SOURCE_SETTINGS));
							}
						})
				.setNegativeButton(noMessageString, new DialogInterface.OnClickListener() {
					public void onClick(final DialogInterface dialog,
							 final int id) {
						dialog.cancel();
					}
				});
		final AlertDialog alert = builder.create();
		alert.show();
		
		
	}
	
	private static boolean isValid(Location location) {
		if(location != null){
			if(location.getLatitude() != 0 && location.getLongitude() != 0){
				return true;
			}
		}
		return false;
	}
	
	public Location getGpsLastKnownLocation() {
		return gpsLastKnownLocation;
	}
	
	public Location getNetworkLastKnownLocation() {
		return networkLastKnownLocation;
	}
	
	public Location getNewestKnownLocation(Context context) throws LocationProviderNotPermitterException{
		
		refreshProvidersConnectivityInfo(context);
		refreshLastKnownLocations();
		
		if(isValid(gpsLastKnownLocation) && isValid(networkLastKnownLocation)){
			if(gpsLastKnownLocation.getTime() > networkLastKnownLocation.getTime()){
				return gpsLastKnownLocation;
			}else{
				return networkLastKnownLocation;
			}
		}
		
		else if(!isValid(gpsLastKnownLocation) && isValid(networkLastKnownLocation)){
			return networkLastKnownLocation;
		}
		else if(isValid(gpsLastKnownLocation) && !isValid(networkLastKnownLocation)){
			return gpsLastKnownLocation;
		}
		return null;
	}
}

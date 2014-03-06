package com.ealib.localization;

import android.content.Context;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

public class LoopGeoLocator {

	private static Object needUpgradeLock = new Object();
	
	UpgradeLocationListener upgradeLocationListener;
	static long periodUpgrade = 1500;
	
	static boolean needUpgrades;

	protected static final String RETRAIVE_LOCATION = "_RETRAIVE_LOCATION_LOOP_";

	private Handler handler = new Handler() {
		public void handleMessage(android.os.Message msg) {

			Location location = null;
			Bundle data = msg.getData();

			if (data != null) {
				location = data.getParcelable(LoopGeoLocator.RETRAIVE_LOCATION);
			}

			if (upgradeLocationListener != null)
				upgradeLocationListener.onUpgradeReceived(location);

		};
	};

	private Context context;
	private GeoLocator geoLocatorInstance;
	private Thread upgradestThread;

	public LoopGeoLocator(final Context context, long periodUpgrade)
			throws LocationProviderNotPermitterException {

		synchronized (needUpgradeLock) {
			LoopGeoLocator.needUpgrades = true;
		}
		
		this.context = context;
		LoopGeoLocator.periodUpgrade = periodUpgrade;

		geoLocatorInstance = new GeoLocator(context);
	    // register listeners native
		instanciateNewThread(context);
	}

	private void instanciateNewThread(final Context context) {
		upgradestThread = new Thread(new Runnable() {
			@Override
			public void run() {

				while (needUpgrades) {

					Location newestKnownLocation = null;
					try {
						newestKnownLocation = geoLocatorInstance
								.getNewestKnownLocation(context);
					} catch (LocationProviderNotPermitterException e) {

					}

					Message msg = handler.obtainMessage();
					Bundle b = new Bundle();
					b.putParcelable(LoopGeoLocator.RETRAIVE_LOCATION,
							newestKnownLocation);
					msg.setData(b);
					handler.sendMessage(msg);

					try {
						Thread.sleep(LoopGeoLocator.periodUpgrade);
					} catch (InterruptedException e) {

					}

				}
			}
		});
	}

	public interface UpgradeLocationListener {
		void onUpgradeReceived(Location location);
	}

	public void setOnUpgradeListener(
			UpgradeLocationListener upgradeLocationListener) {
		this.upgradeLocationListener = upgradeLocationListener;
	}

	public synchronized void requestStop() {
		synchronized (needUpgradeLock) {
			needUpgrades = false;
		}
	}

	public synchronized void start() {
		if (!upgradestThread.isAlive())
			upgradestThread.start();
		else {
			instanciateNewThread(context);
			start();
		}
	}
}

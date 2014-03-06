package com.ealib.device;

import android.accounts.Account;
import android.accounts.AccountManager;
import android.content.Context;
import android.content.pm.PackageManager;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.telephony.TelephonyManager;

public class DeviceInfo {

	static class Applications {

		public static boolean isSkypeInstalled(Context context) {
			return isInstalledPackage(context, "com.skype.raider");
		}

		public static boolean isInstalledPackage(Context context,
				String packageName) {
			PackageManager myPackageMgr = context.getPackageManager();
			try {
				myPackageMgr.getPackageInfo(packageName,
						PackageManager.GET_ACTIVITIES);
				return true;
			} catch (PackageManager.NameNotFoundException e) {
				return false;
			}
		}

	}

	public static boolean isConnectionAvailable(Context context) {
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		if (cm != null) {
			NetworkInfo info = cm.getActiveNetworkInfo();
			if (info != null) {
				return info.isConnectedOrConnecting();
			} else {
				return false;
			}
		}
		return false;
	}

	public static String getAndroidVersion() {
		String release = Build.VERSION.RELEASE;
		return release;
	}

	public static String getDeviceName() {
		String manufacturer = Build.MANUFACTURER;
		String model = Build.MODEL;
		if (model.startsWith(manufacturer)) {
			return capitalize(model);
		} else {
			return capitalize(manufacturer) + " " + model;
		}
	}

	private static String capitalize(String s) {
		if (s == null || s.length() == 0) {
			return "";
		}
		char first = s.charAt(0);
		if (Character.isUpperCase(first)) {
			return s;
		} else {
			return Character.toUpperCase(first) + s.substring(1);
		}
	}

	public static boolean existsTelephonySIM(Context context) {
		TelephonyManager tm = (TelephonyManager) context
				.getSystemService(Context.TELEPHONY_SERVICE);
		if (tm != null) {
			int simState = tm.getSimState();
			if (simState == TelephonyManager.SIM_STATE_READY) {
				return true;
			}
		}
		return false;

	}

	public static Account[] getConfiguredEmailAccounts(Context context) {
		try {
			AccountManager am = AccountManager.get(context);
			Account[] accounts = am.getAccounts();
			return accounts;
		} catch (Exception e) {
			return null;
		}
	}

}

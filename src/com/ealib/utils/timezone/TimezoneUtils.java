package com.ealib.utils.timezone;

import java.util.Calendar;
import java.util.TimeZone;

public class TimezoneUtils {

	static final String DATEFORMAT = "yyyy-MM-dd HH:mm:ss";

	public static TimeZone getDeviceTimezone() {
		TimeZone defaultTimezone = TimeZone.getDefault();
		if (defaultTimezone != null)
			return defaultTimezone;
		else {
			Calendar cal = Calendar.getInstance();
			TimeZone tz = cal.getTimeZone();
			return tz;
		}
	}

	public static String getGMTTime() {
		TimeZone GMTtimeZone = TimeZone.getTimeZone("GMT");
		String displayName = GMTtimeZone.getDisplayName();
		return displayName;
	}

}

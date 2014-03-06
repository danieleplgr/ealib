package com.ealib.utils.date;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DateUtils {

	private static final String DATETIME_FORMAT_ONLY_DIGITS = "yyyyMMddHHmmss";
	
	private static final String DATETIME_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
	private static final String DATE_FORMAT_NOW = "yyyy-MM-dd";

	public static String getNowDateString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}
	
	public static String getNowDateTimeString() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_NOW);
		return sdf.format(cal.getTime());
	}

	public static String getNowDateTimeStringDigits() {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATETIME_FORMAT_ONLY_DIGITS);
		return sdf.format(cal.getTime());
	}
	
	public static String getNowDateTimeString(String format) {
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(cal.getTime());
	}
	
}

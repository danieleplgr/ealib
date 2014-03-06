package com.ealib.db.utils.date;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateSQLliteStringFormatter {

	public enum DatetimeFormatType {
		DATETIME("dd-MM-yy HH:mm:SS") , DATETIME_TIMEZONE("dd-MM-yy HH:mm:SSZ");
		
		String formatDateString;
		
		DatetimeFormatType(String formatString){
			this.formatDateString = formatString;
		}
		
		public String getFormatDateString() {
			return formatDateString;
		}
	} 

	private SimpleDateFormat usedFormatter;
	
	private DateSQLliteStringFormatter(SimpleDateFormat formatter) {
		usedFormatter = formatter;
	}
	
	public static DateSQLliteStringFormatter getInstance(DatetimeFormatType formatType){
		switch (formatType) {
		case DATETIME_TIMEZONE:
			SimpleDateFormat formatterTimezone = new SimpleDateFormat(DatetimeFormatType.DATETIME_TIMEZONE.getFormatDateString());
			return new DateSQLliteStringFormatter(formatterTimezone); 
			
		case DATETIME:
			SimpleDateFormat formatterDatetime = new SimpleDateFormat(DatetimeFormatType.DATETIME.getFormatDateString());
			return new DateSQLliteStringFormatter(formatterDatetime); 

		default:
			throw new UnsupportedOperationException("no match found for formatType "+formatType != null ? formatType.toString() : "null");
		}
		
	}
	
	public String convertToString(Date date) {
		return usedFormatter.format(date); 
	}
	
	
	

}

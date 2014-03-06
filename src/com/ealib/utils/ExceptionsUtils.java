package com.ealib.utils;

public class ExceptionsUtils {

	private static final int SYSTEM_COMMON_NUMBER_OF_STACK_ELEMENTS = 13;
	
	public static String getDetailMessage(Exception e, boolean fullMessageStack) {
		String message = "";
		if (e != null) {
			message += e.getClass().getCanonicalName() + ": ";
			message += e.getMessage() + "; Stack Trace: ";
			if (e.getStackTrace() != null) {
				 
				StackTraceElement[] stackTrace = e.getStackTrace();
				
				int numeroDiStackNecessarie = 0;
				int totalStackTraceLength = stackTrace.length;
				
				if(fullMessageStack){
					numeroDiStackNecessarie = totalStackTraceLength;
				}else{
					numeroDiStackNecessarie = totalStackTraceLength - SYSTEM_COMMON_NUMBER_OF_STACK_ELEMENTS;
				}
				
				for(int i = 0; i<numeroDiStackNecessarie; i++){
					StackTraceElement _stackTraceElement = stackTrace[i];
					message += _stackTraceElement.toString() +  " " + System.getProperty("line.separator");
				}

			}
		}
		return message;
	}

}

package com.ealib.application;
 
import android.content.Context; 

public class AppUtils {
	 
	public static String getPackageName(Context context){
		String PACKAGE_NAME = context.getApplicationContext().getPackageName();
		return PACKAGE_NAME;
	}
}

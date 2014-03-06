package com.ealib.utils.string;

public class StringBufferUtils {

	public static void replaceAll(StringBuffer stringBuffer, String searchString, String newString){
		int start = stringBuffer.toString().indexOf(searchString);
		int end = start+searchString.length();
		stringBuffer.replace(start, end, newString);
	} 

	
}

package com.ealib.utils.string;

public class StringUtils {

	public static String extractString(String content, String beginString,
			String finishString, boolean includeBegin) {
		System.out.println("OK");
		int begin = content.indexOf(beginString);
		if (includeBegin)
			begin = begin + beginString.length();

		String newStringWithBeginFirstSearchedSequenceChars = content
				.substring(begin, content.length());
		int finish = newStringWithBeginFirstSearchedSequenceChars
				.indexOf(finishString);
		String extracted = content.substring(begin, begin + finish);
		return extracted;
	}

}

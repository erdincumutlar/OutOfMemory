package com.patientkeeper.tools;

import java.util.List;

public class ToHTML {

	final static String OL_OPEN = "<ol>";
	final static String OL_CLOSE = "</ol>";
	final static String LI_OPEN = "<li>";
	final static String LI_CLOSE = "</li>";
	
	public static String getOrderedList(List<String> items) {		
		StringBuilder sb = new StringBuilder();
		sb.append(OL_OPEN);
		for(String temp : items) {
			sb.append(LI_OPEN);
			sb.append(temp);
			sb.append(LI_CLOSE);
		}
		sb.append(OL_CLOSE);
		return sb.toString();
	}
}

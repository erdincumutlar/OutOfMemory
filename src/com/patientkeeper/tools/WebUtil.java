package com.patientkeeper.tools;

public class WebUtil {

	public static String out (Object object) {

		if(object != null) {
			return object.toString();
		}

		return "";		
	}

}
package com.ef.util;

public class ParserUtil {

	public static int parseInt(String val) {
		try {
			return Integer.parseInt(val);
		} catch (RuntimeException e) {
			e.printStackTrace();
		}
		return 0;
	}
}

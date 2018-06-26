package com.sixt.platform.interview;

public class AnagramTester {

	private AnagramTester() {
		
	}
	public static boolean isAnagram(String lhs, String rhs) {		
		assert lhs != null : "lhs string is null";
		assert rhs != null : "rhs string is null";
		if (rhs.length() != lhs.length()) {
			return false;
		}					
		if (rhs.length() == 0) {
			return true;
		}		
		if (rhs.equals(lhs)) {
			return true;
		}else {
			char rhc = rhs.charAt(0);
			String subIhs = getStringWithoutTheChar(lhs, rhc);		
			return isAnagram(subIhs,rhs.substring(1, rhs.length()));						
		}			
	}

	private static String getStringWithoutTheChar(String lhs, char rhc) {
		return lhs.replaceFirst(""+rhc, "");		
	}

}

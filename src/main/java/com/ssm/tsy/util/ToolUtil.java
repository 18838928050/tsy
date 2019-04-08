package com.ssm.tsy.util;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ToolUtil {
	/**
	 * MD5加密技术
	 * @param str
	 * @return
	 */
	public static String MD5(String str) throws Exception {
		byte[] bt = str.getBytes();
		StringBuffer sbf = null;
		MessageDigest md = null;
		md = MessageDigest.getInstance("MD5");
		byte[] bt1 = md.digest(bt);
		sbf = new StringBuffer();
		for (int i = 0; i < bt1.length; i++) {
			int val = ((int) bt1[i]) & 0xff;
			if (val < 16)
				sbf.append("0");
			sbf.append(Integer.toHexString(val));
		}
		return sbf.toString();
	}
	
	public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);  
            dest = m.replaceAll("");
        }
        return dest;
    }
	
	public static <T> int contains( T[] array, T v ) {
		int k = -1;
	    for ( T e : array ){
	    	k++;
	        if ( e == v || v != null && v.equals( e ) )
	            return k;
	    }
	    return k;
	}
}

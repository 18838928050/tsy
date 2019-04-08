package com.ssm.tsy.util;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.ssm.tsy.bean.wechat.ResponseArticle;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;

public class TransUtil {
  
	private static byte[] base64DecodeChars = new byte[] { -1, -1, -1, -1, -1,  
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
        -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,  
        -1, -1, -1, -1, 62, -1, -1, -1, 63, 52, 53, 54, 55, 56, 57, 58, 59,  
        60, 61, -1, -1, -1, -1, -1, -1, -1, 0, 1, 2, 3, 4, 5, 6, 7, 8, 9,  
        10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22, 23, 24, 25, -1,  
        -1, -1, -1, -1, -1, 26, 27, 28, 29, 30, 31, 32, 33, 34, 35, 36, 37,  
        38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, -1, -1, -1,  
        -1, -1 }; 
	
	/** 
	 * 解密 
	 * @param str 
	 * @return 
	 */  
	public static byte[] decode(String str) {  
	    byte[] data = str.getBytes();  
	    int len = data.length;  
	    ByteArrayOutputStream buf = new ByteArrayOutputStream(len);  
	    int i = 0;  
	    int b1, b2, b3, b4;  
	    while (i < len) {  
	        do {  
	            b1 = base64DecodeChars[data[i++]];  
	        } while (i < len && b1 == -1);  
	        if (b1 == -1) {  
	            break;  
	        }  
	  
	        do {  
	            b2 = base64DecodeChars[data[i++]];  
	        } while (i < len && b2 == -1);  
	        if (b2 == -1) {  
	            break;  
	        }  
	        buf.write((int) ((b1 << 2) | ((b2 & 0x30) >>> 4)));  
	        do {  
	            b3 = data[i++];  
	            if (b3 == 61) {  
	                return buf.toByteArray();  
	            }  
	            b3 = base64DecodeChars[b3];  
	        } while (i < len && b3 == -1);  
	        if (b3 == -1) {  
	            break;  
	        }  
	        buf.write((int) (((b2 & 0x0f) << 4) | ((b3 & 0x3c) >>> 2)));  
	        do {  
	            b4 = data[i++];  
	            if (b4 == 61) {  
	                return buf.toByteArray();  
	            }  
	            b4 = base64DecodeChars[b4];  
	        } while (i < len && b4 == -1);  
	        if (b4 == -1) {  
	            break;  
	        }  
	        buf.write((int) (((b3 & 0x03) << 6) | b4));  
	    }  
	    return buf.toByteArray();  
	}  
	
	/**
	 * 中文转unicode（数据请求成功-->\u6570\u636e\u8bf7\u6c42\u6210\u529f）
	 * @param gbString
	 * @return
	 */
	public static String gbEncoding(String gbString) {
		char[] utfBytes = gbString.toCharArray();
		String unicodeBytes = "";
		for (int byteIndex = 0; byteIndex < utfBytes.length; byteIndex++) {
			String hexB = Integer.toHexString(utfBytes[byteIndex]);
			if (hexB.length() <= 2)
				hexB = "00" + hexB;
			unicodeBytes = unicodeBytes + "\\u" + hexB;
		}
		return unicodeBytes;
	}
	
	/**
	 * 字符串转int类型
	 * @param gbString
	 * @return
	 * @throws Exception 
	 */
	public static int converterToInt(String gbString) throws Exception{
		int returnInt = -1;
		if(gbString.equals("")||gbString==null){
			throw new Exception("转换成[int]类型的内容为空！！！");
		}else if(JudgeUtil.IsNumber(gbString)){
			throw new Exception("转换成[int]类型的内容不是正确的数据类型");
		}else{
			returnInt = Integer.parseInt(gbString);
		}
		return returnInt;
	}
	
	/**
	 * unicode转中文（\u6570\u636e\u8bf7\u6c42\u6210\u529f-->数据请求成功）
	 * @param ascii
	 * @return
	 */
	public static String ascii2native(String theString) {
		char aChar;
		int len = theString.length();
		StringBuffer outBuffer = new StringBuffer(len);
		for (int x = 0; x < len;) {
			aChar = theString.charAt(x++);
			if (aChar == '\\') {
				aChar = theString.charAt(x++);
				if (aChar == 'u') {
					int value = 0;
					for (int i = 0; i < 4; i++) {
						aChar = theString.charAt(x++);
						switch (aChar) {
						case '0':
						case '1':
						case '2':
						case '3':
						case '4':
						case '5':
						case '6':
						case '7':
						case '8':
						case '9':
							value = (value << 4) + aChar - '0';
							break;
						case 'a':
						case 'b':
						case 'c':
						case 'd':
						case 'e':
						case 'f':
							value = (value << 4) + 10 + aChar - 'a';
							break;
						case 'A':
						case 'B':
						case 'C':
						case 'D':
						case 'E':
						case 'F':
							value = (value << 4) + 10 + aChar - 'A';
							break;
						default:
							throw new IllegalArgumentException(
									"Malformed   \\uxxxx   encoding.");
						}
					}
					outBuffer.append((char) value);
				} else {
					if (aChar == 't')
						aChar = '\t';
					else if (aChar == 'r')
						aChar = '\r';
					else if (aChar == 'n')
						aChar = '\n';
					else if (aChar == 'f')
						aChar = '\f';
					outBuffer.append(aChar);
				}
			} else
				outBuffer.append(aChar);
		}
		return outBuffer.toString();
	}
	
	/**
	 * 将微信消息中的CreateTime转换成标准格式的时间（yyyy-MM-dd HH:mm:ss）
	 * @param subtime
	 * @return
	 */
	public static String WeChatData(String subtime) {
		// 获取用户关注时间
		long subscribeTime = Long.parseLong(subtime) * 1000L;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return sdf.format(new Date(subscribeTime));
	}
	
	/**
	 * 汉字转换位汉语拼音首字母，英文字符不变
	 * @param chines 汉字
	 * @return 拼音
	 */
	public static String converterToFirstSpell(String chines) throws Exception {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				pinyinName += PinyinHelper.toHanyuPinyinStringArray( nameChar[i], defaultFormat)[0].charAt(0);
			} else {
				pinyinName += nameChar[i];
			}
		}
		char[] cs= pinyinName.toCharArray();
        cs[0]-=32;
		return String.valueOf(cs);
	}
	
	/**
	 * 汉字转换位汉语拼音，英文字符不变
	 * @param chines 汉字
	 * @return 拼音
	 */
	public static String converterToSpell(String chines) throws Exception {
		String pinyinName = "";
		char[] nameChar = chines.toCharArray();
		HanyuPinyinOutputFormat defaultFormat = new HanyuPinyinOutputFormat();
		defaultFormat.setCaseType(HanyuPinyinCaseType.LOWERCASE);
		defaultFormat.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
		for (int i = 0; i < nameChar.length; i++) {
			if (nameChar[i] > 128) {
				pinyinName += PinyinHelper.toHanyuPinyinStringArray(nameChar[i], defaultFormat)[0];
			} else {
				pinyinName += nameChar[i];
			}
		}
		char[] cs= pinyinName.toCharArray();
        cs[0]-=32;
		return String.valueOf(cs);
	}
	
	/**
	 * 字节码转换  11.98M
	 * @param fileS
	 * @return
	 */
	public static String FormetFileSize(long fileS) {//转换文件大小
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = df.format((double) fileS) + "B";
        } else if (fileS < 1048576) {
            fileSizeString = df.format((double) fileS / 1024) + "K";
        } else if (fileS < 1073741824) {
            fileSizeString = df.format((double) fileS / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) fileS / 1073741824) + "G";
        }
        return fileSizeString;
    }
	
	/**
	 * 字节码单位转换  M
	 * @param fileS
	 * @return
	 */
	public static Object FormetFileUnit(long fileS) {
        String fileSizeString = "";
        if (fileS < 1024) {
            fileSizeString = "B";
        } else if (fileS < 1048576) {
            fileSizeString = "K";
        } else if (fileS < 1073741824) {
            fileSizeString = "M";
        } else {
            fileSizeString = "G";
        }
        return fileSizeString;
	}
	
	/**
	 * 本地图文消息转微信图文消息
	 * @param bean
	 * @return
	 */
	public static List<ResponseArticle> mapToResponseArticleList(List<Map<String,Object>> beans) {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		for (int i = 0; i < beans.size(); i++) {
			ResponseArticle article = new ResponseArticle();
			article.setTitle(beans.get(i).get("graphic_message_title").toString());
			article.setDescription(beans.get(i).get("graphic_message_title").toString());
			article.setPicUrl(Constants.IP_URI+beans.get(i).get("graphic_message_jieshao_pic").toString());
			System.out.println(article.getPicUrl());
			article.setUrl(Constants.GRAPHIC_MESSAGE_IP_URI+beans.get(i).get("id"));
			articleList.add(article);
		}
		return articleList;
	}
}

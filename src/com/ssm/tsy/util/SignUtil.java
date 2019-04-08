package com.ssm.tsy.util;

import java.security.MessageDigest;
import java.util.Arrays;

/**
 * 请求校验工具类
 * 
 */

public class SignUtil {
	// 与接口配置信息中的Token要一致
	private static String token = "fhf";

	/**
	 * 验证签名
	 * 
	 * @param signature
	 * @param timestamp
	 * @param nonce
	 * @return
	 */
	public static boolean checkSignature(String signature, String timestamp, String nonce) throws Exception {
		String[] arr = new String[] { token, timestamp, nonce };
		// 将token、timestamp、nonce三个参数进行字典序排序，字典排序就是按照字母顺序，或者数字小大顺序，由小到大的形成序列
		Arrays.sort(arr);
		StringBuilder content = new StringBuilder();
		for (int i = 0; i < arr.length; i++)
			content.append(arr[i]);
		MessageDigest md = null;
		String tmpStr = null;
		md = MessageDigest.getInstance("SHA-1");
		// 将三个参数字符串拼接成一个字符串进行sha1加密
		byte[] digest = md.digest(content.toString().getBytes());
		tmpStr = byteToStr(digest);
		System.out.println("加密后的字符串："+tmpStr);
		System.out.println("signature:"+signature);
		content = null;
		return tmpStr != null ? tmpStr.equals(signature.toUpperCase()) : false;//// 将sha1加密后的字符串可与signature对比，标识该请求来源于微信
	}

	/**
	 * 将字节数组转换为十六进制字符串
	 * 
	 * @param byteArray
	 * @return
	 */
	private static String byteToStr(byte[] byteArray) {
		String strDigest = "";
		for (int i = 0; i < byteArray.length; i++)
			strDigest += byteToHexStr(byteArray[i]);
		return strDigest;
	}

	/**
	 * 将字节转换为十六进制字符串
	 * 
	 * @param mByte
	 * @return
	 */
	private static String byteToHexStr(byte mByte) {
		char[] Digit = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F' };
		char[] tempArr = new char[2];
		tempArr[0] = Digit[(mByte >>> 4) & 0X0F];
		tempArr[1] = Digit[mByte & 0X0F];
		String s = new String(tempArr);
		return s;
	}
}

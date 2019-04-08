package com.wechat.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ShengriyuceService {
	/**
	 * 生气预测
	 */
	public static String httpUrl = "http://apis.baidu.com/txapi/dob/dob";
	// 配置您申请的KEY
	public static final String APPKEY = "e0e023e4a535563f76697782c9668503";

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param m
	 *            :参数月
	 * @param d
	 *            :参数日
	 * @return 返回结果
	 */
	public static String request(int m, int d) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String httpArg = "m=" + m + "&d=" + d;
		httpUrl = httpUrl + "?" + httpArg;
		try {
			URL url = new URL(httpUrl);
			HttpURLConnection connection = (HttpURLConnection) url
					.openConnection();
			connection.setRequestMethod("GET");
			// 填入apikey到HTTP header
			connection.setRequestProperty("apikey", APPKEY);
			connection.connect();
			InputStream is = connection.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sbf.append(strRead);
				sbf.append("\r\n");
			}
			reader.close();
			result = sbf.toString();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void main(String[] args) {
		String jsonResult = request(10, 1);
		System.out.println(jsonResult);
	}
}

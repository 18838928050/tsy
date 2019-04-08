package com.wechat.service;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import net.sf.json.JSONObject;

public class IDCardService {

	public static String return_data;

	/**
	 * 
	 * 身份证识别
	 * 
	 * @param args
	 */
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "9d4dd45b7a977c4336fbb96b93e4eb15";

	// 1.身份证信息查询
	public static String getRequest1(String idcard) {
		return_data = idcard;
		String result = null;
		String url = "http://apis.juhe.cn/idcard/index";// 请求接口地址
		Map<String, Object> params = new HashMap<>();// 请求参数
		params.put("cardno", idcard);// 身份证号码
		params.put("dtype", "json");// 返回数据格式：json或xml,默认json
		params.put("key", APPKEY);// 你申请的key

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				JSONObject bean = object.getJSONObject("result");
				// System.out.println("地址：" + bean.get("area"));
				// System.out.println("性别：" + bean.get("sex"));
				// System.out.println("生日：" + bean.get("birthday"));
				return_data = "身份证号：" + return_data + "\n地址："
						+ bean.get("area") + "\n性别：" + bean.get("sex")
						+ "\n生日：" + bean.get("birthday");
				getRequest2(idcard);
			} else {
				return_data = "身份证号" + return_data + "\n"
						+ object.get("error_code") + ":" + object.get("reason");
				// System.out.println(object.get("error_code") + ":"
				// + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return return_data;
	}

	// 2.身份证泄漏查询
	public static void getRequest2(String idcard) {
		String result = null;
		String url = "http://apis.juhe.cn/idcard/leak";// 请求接口地址
		Map<String, Object> params = new HashMap<>();// 请求参数
		params.put("cardno", idcard);// 身份证号码
		params.put("dtype", "json");// 返回数据格式：json或xml,默认json
		params.put("key", APPKEY);// 你申请的key

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				JSONObject bean = object.getJSONObject("result");
				// System.out.println("身份证泄漏情况：" + bean.get("tips"));

				return_data = return_data + "\n身份证泄漏情况：" + bean.get("tips");
				getRequest3(idcard);
			} else {
				System.out.println(object.get("error_code") + ":"
						+ object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 3.身份证挂失查询
	public static void getRequest3(String idcard) {
		String result = null;
		String url = "http://apis.juhe.cn/idcard/loss";// 请求接口地址
		Map<String, Object> params = new HashMap<>();// 请求参数
		params.put("cardno", idcard);// 身份证号码
		params.put("dtype", "json");// 返回数据格式：json或xml,默认json
		params.put("key", APPKEY);// 你申请的key

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				JSONObject bean = object.getJSONObject("result");
				// System.out.println("身份证挂失情况：" + bean.get("tips"));

				return_data = return_data + "\n身份证挂失情况：" + bean.get("tips");
			} else {
				System.out.println(object.get("error_code") + ":"
						+ object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param strUrl
	 *            请求地址
	 * @param params
	 *            请求参数
	 * @param method
	 *            请求方法
	 * @return 网络请求字符串
	 * @throws Exception
	 */
	public static String net(String strUrl, Map<String, Object> params, String method)
			throws Exception {
		HttpURLConnection conn = null;
		BufferedReader reader = null;
		String rs = null;
		try {
			StringBuffer sb = new StringBuffer();
			if (method == null || method.equals("GET")) {
				strUrl = strUrl + "?" + urlencode(params);
			}
			URL url = new URL(strUrl);
			conn = (HttpURLConnection) url.openConnection();
			if (method == null || method.equals("GET")) {
				conn.setRequestMethod("GET");
			} else {
				conn.setRequestMethod("POST");
				conn.setDoOutput(true);
			}
			conn.setRequestProperty("User-agent", userAgent);
			conn.setUseCaches(false);
			conn.setConnectTimeout(DEF_CONN_TIMEOUT);
			conn.setReadTimeout(DEF_READ_TIMEOUT);
			conn.setInstanceFollowRedirects(false);
			conn.connect();
			if (params != null && method.equals("POST")) {
				try {
					DataOutputStream out = new DataOutputStream(
							conn.getOutputStream());
					out.writeBytes(urlencode(params));
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
			InputStream is = conn.getInputStream();
			reader = new BufferedReader(new InputStreamReader(is, DEF_CHATSET));
			String strRead = null;
			while ((strRead = reader.readLine()) != null) {
				sb.append(strRead);
			}
			rs = sb.toString();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (reader != null) {
				reader.close();
			}
			if (conn != null) {
				conn.disconnect();
			}
		}
		return rs;
	}

	// 将map型转为请求参数型
	@SuppressWarnings("rawtypes")
	public static String urlencode(Map<String, Object> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=")
						.append(URLEncoder.encode(i.getValue() + "", "UTF-8"))
						.append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	// public static void main(String[] args) {
	// getRequest1("412326199609207220");
	// }

}

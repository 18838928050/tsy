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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class ChangtuqicheService {

	/**
	 * 
	 * 长途汽车
	 * 
	 */

	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "7a4b3fca93620e74e3993efd699e5d83";

	// 1.汽车站信息查询
	public static String getRequest1(String cityname) {
		String return_data = null;

		String result = null;
		String url = "http://op.juhe.cn/onebox/bus/query";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("station", cityname);// 城市名称，如:北京
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				return_data = City(object.get("result").toString());
				// System.out.println(object.get("result"));
			} else {
				return_data = object.get("error_code") + ":"
						+ object.get("reason");
				// System.out.println(object.get("error_code") + ":"
				// + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return return_data;
	}

	// 2.汽车站到站检索
	public static String getRequest2(String cityname_from, String cityname_to) {
		String return_data = null;

		String result = null;
		String url = "http://op.juhe.cn/onebox/bus/query_ab";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("from", cityname_from);// 出发城市，如：上海
		params.put("to", cityname_to);// 到达城市，如:苏州
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				return_data = From_To(object.get("result").toString());
				// System.out.println(object.get("result"));
			} else {
				return_data = object.get("error_code") + ":"
						+ object.get("reason");
				// System.out.println(object.get("error_code") + ":"
				// + object.get("reason"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return return_data;
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
	public static String net(String strUrl, Map<String, Object> params,
			String method) throws Exception {
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

	/**
	 * 两地之间的长途汽车
	 * 
	 * @param result
	 * @return
	 */
	public static String From_To(String result) {
		String return_data = null;

		JSONObject data = JSONObject.fromObject(result);

		return_data = "标题："
				+ data.getString("title")
				+ "\n出发站               终点站               发车时间               票价\n";
		// System.out.println("标题：" + data.getString("title"));

		JSONArray jsonArray = data.getJSONArray("list");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject bean = jsonArray.getJSONObject(i);
			// System.out.println("出发站：" + bean.getString("start"));
			// System.out.println("终点站：" + bean.getString("arrive"));
			// System.out.println("发车时间：" + bean.getString("date"));
			// System.out.println("票价：" + bean.getString("price"));
			return_data = return_data + bean.getString("start")
					+ "             " + bean.getString("arrive")
					+ "               " + bean.getString("date")
					+ "               " + bean.getString("price") + "\n";
		}

		return return_data;
	}

	/**
	 * 某个城市的长途汽车
	 * 
	 * @param result
	 * @return
	 */
	public static String City(String result) {
		String return_data = null;

		JSONObject data = JSONObject.fromObject(result);

		return_data = "标题：" + data.getString("title");
		// System.out.println("标题：" + data.getString("title"));

		JSONArray jsonArray = data.getJSONArray("list");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject bean = jsonArray.getJSONObject(i);
			// System.out.println("出发站：" + bean.getString("name"));
			// System.out.println("联系方式：" + bean.getString("tel"));
			// System.out.println("地址：" + bean.getString("adds"));
			return_data = return_data + "\n出发站：" + bean.getString("name")
					+ "\n联系方式：" + bean.getString("tel") + "\n地址："
					+ bean.getString("adds") + "\n\n";
		}

		return return_data;
	}

	// public static void main(String[] args) {
	// getRequest1("郑州");
	// getRequest2("郑州", "上海");
	// }
}

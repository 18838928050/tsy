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

public class GongjiaoService {
	/**
	 * 
	 * 公交车
	 * 
	 * 
	 */
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "9f0057e66fe13e5d451cf7f2770055fd";

	// 1.公交线路查询
	public static String getRequest1(String city, String xianlu) {

		String return_data = null;

		String result = null;
		String url = "http://op.juhe.cn/189/bus/busline";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json
		params.put("city", city);// 城市名称(如：苏州)或者城市代码（如：0512）
		params.put("bus", xianlu);// 公交线路

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				return_data = Checheng(object.get("result").toString());
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

	// 2.公交站台经往车辆查询
	public static String getRequest2(String city, String zhanpai) {

		String return_data = null;

		String result = null;
		String url = "http://op.juhe.cn/189/bus/station";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json
		params.put("city", city);// 城市名称(如：苏州)或者城市代码（如：0512）
		params.put("station", zhanpai);// 公交站台名称

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				return_data = Tujingdian(object.get("result").toString(),
						zhanpai);
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

	// 3.公交线路换乘方案
	public static String getRequest3(String city, String tujingdian,
			String leixing) {

		String return_data = null;

		String result = null;
		String url = "http://op.juhe.cn/189/bus/transfer";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json
		params.put("city", city);// 城市名称(如：苏州)或者城市代码（如：0512）
		params.put("xys", tujingdian);// 途经点坐标集合
		params.put("type", leixing);// 行驶类型 0表示最快捷模式，尽可能乘坐轨道交通和快速公交线路；
		// 2表示最少换乘模式，尽可能减少换乘次数； 3表示最少步行模式，尽可能减少步行距离；
		// 4表示最舒适模式，；乘坐有空调的车线； 5表示纯地铁模式，只选择地铁换乘

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				System.out.println(object.get("result"));
			} else {
				System.out.println(object.get("error_code") + ":"
						+ object.get("reason"));
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

	/**
	 * 解析车程
	 * 
	 * @param result
	 * @return
	 */
	public static String Checheng(String result) {
		String return_data = null;

		JSONArray dataall = JSONArray.fromObject(result);
		JSONObject data = dataall.getJSONObject(0);
		// System.out.println("线路名称：" + data.getString("name"));
		// System.out.println("首发站：" + data.getString("front_name"));
		// System.out.println("终点站：" + data.getString("terminal_name"));
		// System.out.println("线路编号：" + data.getString("line_id"));
		// System.out.println("基本票价：" + data.getString("basic_price"));
		// System.out.println("总票价：" + data.getString("total_price"));
		// System.out.println("公交公司民初：" + data.getString("company"));
		// System.out.println("线路长度：" + data.getString("length"));
		// System.out.println("早班车时间：" + data.getString("start_time"));
		// System.out.println("末班车时间：" + data.getString("end_time"));

		return_data = "\n线路名称："
				+ data.getString("name")
				+ "\n线路编号："
				+ data.getString("line_id")
				+ "\n首发站："
				+ data.getString("front_name")
				+ "\n终点站："
				+ data.getString("terminal_name")
				+ "\n基本票价："
				+ data.getString("basic_price")
				+ "\n总票价："
				+ data.getString("total_price")
				+ "\n线路长度："
				+ data.getString("length")
				+ "\n早班车时间："
				+ data.getString("start_time")
				+ "\n末班车时间："
				+ data.getString("end_time")
				+ "\n公交公司民初："
				+ data.getString("company")
				+ "\n路线：--------------------------------------------------------\n"
				+ "站台编号       公交线路序号       站台名称\n";

		JSONArray jsonArray = data.getJSONArray("stationdes");
		for (int i = 0; i < jsonArray.size(); i++) {
			JSONObject bean = jsonArray.getJSONObject(i);
			// System.out.println("站台编号" + bean.get("code"));
			// System.out.println("公交线路序号" + bean.get("stationNum"));
			// System.out.println("站台名称" + bean.get("name"));
			// System.out.println("站台坐标" + bean.get("xy"));
			if (i < 9) {
				return_data = return_data + bean.get("code") + "             "
						+ bean.get("stationNum") + "                        "
						+ bean.get("name") + "\n";
			} else {
				return_data = return_data + bean.get("code") + "             "
						+ bean.get("stationNum") + "                      "
						+ bean.get("name") + "\n";
			}

		}

		return return_data;
	}

	/**
	 * 解析途经点
	 * 
	 * @param result
	 * @return
	 */
	public static String Tujingdian(String result, String tujing) {
		String return_data = null;
		return_data = tujing;
		JSONArray dataall = JSONArray.fromObject(result);
		for (int j = 0; j < dataall.size(); j++) {
			JSONObject data = dataall.getJSONObject(j);
			// System.out.println("线路名称：" + data.getString("name"));
			// System.out.println("首发站：" + data.getString("front_name"));
			// System.out.println("终点站：" + data.getString("terminal_name"));
			// System.out.println("线路编号：" + data.getString("line_id"));
			// System.out.println("基本票价：" + data.getString("basic_price"));
			// System.out.println("总票价：" + data.getString("total_price"));
			// System.out.println("公交公司民初：" + data.getString("company"));
			// System.out.println("线路长度：" + data.getString("length"));
			// System.out.println("早班车时间：" + data.getString("start_time"));
			// System.out.println("末班车时间：" + data.getString("end_time"));
			return_data = return_data + "\n线路名称：" + data.getString("name")
					+ "\n线路编号：" + data.getString("line_id") + "\n首发站："
					+ data.getString("front_name") + "\n终点站："
					+ data.getString("terminal_name") + "\n基本票价："
					+ data.getString("basic_price") + "\n总票价："
					+ data.getString("total_price") + "\n线路长度："
					+ data.getString("length") + "\n早班车时间："
					+ data.getString("start_time") + "\n末班车时间："
					+ data.getString("end_time") + "\n公交公司民初："
					+ data.getString("company") + "\n";
		}

		return return_data;
	}

	// public static void main(String[] args) {
	// getRequest1("郑州", "B12");
	// getRequest2("郑州", "碧沙岗");
	// getRequest3("010", "116.4604213,39.9204703;116.2883602,39.8236433",
	// "0");
	// }
}

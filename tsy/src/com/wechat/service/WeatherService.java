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

public class WeatherService {
	/**
	 * 
	 * 天气预报
	 * 
	 * 
	 * @param args
	 */
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "81866a32e7acbe2b5ea92045cddce37b";

	// 1.根据城市查询天气
	public static String getRequest1(String cityname, String data) {
		String return_data = null;
		String result = null;
		String url = "http://op.juhe.cn/onebox/weather/query";// 请求接口地址
		Map<String, String> params = new HashMap<String, String>();// 请求参数
		params.put("cityname", cityname);// 要查询的城市，如：温州、上海、北京
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				if (data == null || data.equals("")) {
					return_data = Week(object.get("result"));
				} else if (data.equals("今天")) {
					return_data = Today(object.get("result"));
				}
			} else {
				System.out.println(object.get("error_code") + ":" + object.get("reason"));
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
	public static String net(String strUrl, Map<String, String> params, String method) throws Exception {
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
					e.printStackTrace();
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
	public static String urlencode(Map<String, String> data) {
		StringBuilder sb = new StringBuilder();
		for (Map.Entry i : data.entrySet()) {
			try {
				sb.append(i.getKey()).append("=").append(URLEncoder.encode(i.getValue() + "", "UTF-8")).append("&");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		return sb.toString();
	}

	/**
	 * 获取今天的天气情况
	 * 
	 * @param result
	 */
	public static String Today(Object result) {
		JSONObject res = JSONObject.fromObject(result);

		// 获取数据
		JSONObject data = res.getJSONObject("data");

		// 获取今天日期的天气情况
		JSONObject realtime = data.getJSONObject("realtime");

		// 获取风的情况和今天的情况
		JSONObject wind = realtime.getJSONObject("wind");
		// System.out.println("风速：" + wind.getString("windspeed"));
		// System.out.println("风向：" + wind.getString("direct"));
		// System.out.println("风级：" + wind.getString("power"));
		// System.out.println("风偏移：" + wind.getString("offset"));
		// System.out.println("数据请求时间：" + realtime.getString("dataUptime"));
		// System.out.println("今天日期：" + realtime.getString("date"));
		// System.out.println("城市编码：" + realtime.getString("city_code"));
		// System.out.println("城市名称：" + realtime.getString("city_name"));
		// System.out.println("星期：" + realtime.getString("week"));
		// System.out.println("农历：" + realtime.getString("moon"));

		// 天气
		JSONObject weather = realtime.getJSONObject("weather");
		// System.out.println("湿度：" + weather.getString("humidity"));
		// System.out.println("图片：" + weather.getString("img"));
		// System.out.println("天气：" + weather.getString("info"));
		// System.out.println("温度：" + weather.getString("temperature"));

		// 日常生活注意事项
		JSONObject life = data.getJSONObject("life");
		JSONObject info = life.getJSONObject("info");
		// System.out.println("空调：" + info.getJSONArray("kongtiao").getString(0)
		// + info.getJSONArray("kongtiao").getString(1));
		// System.out.println("运动：" + info.getJSONArray("yundong").getString(0)
		// + info.getJSONArray("yundong").getString(1));
		// System.out.println("紫外线：" +
		// info.getJSONArray("ziwaixian").getString(0)
		// + info.getJSONArray("ziwaixian").getString(1));
		// System.out.println("感冒：" + info.getJSONArray("ganmao").getString(0)
		// + info.getJSONArray("ganmao").getString(1));
		// System.out.println("洗车：" + info.getJSONArray("xiche").getString(0)
		// + info.getJSONArray("xiche").getString(1));
		// System.out.println("污染：" + info.getJSONArray("wuran").getString(0)
		// + info.getJSONArray("wuran").getString(1));
		// System.out.println("穿衣：" + info.getJSONArray("chuanyi").getString(0)
		// + info.getJSONArray("chuanyi").getString(1));

		// PM2.5
		JSONObject pm25 = data.getJSONObject("pm25");
		// System.out.println("刷新时间：" + pm25.getString("dateTime"));
		JSONObject pm25_bean = pm25.getJSONObject("pm25");
		// System.out.println("单位：" + pm25_bean.getString("curPm"));
		// System.out.println("可入肺颗粒物：" + pm25_bean.getString("pm25"));
		// System.out.println("可吸入颗粒物：" + pm25_bean.getString("pm10"));
		// System.out.println("PM级别：" + pm25_bean.getString("level"));
		// System.out.println("空气质量：" + pm25_bean.getString("quality"));
		// System.out.println("建议：" + pm25_bean.getString("des"));

		String information = "城市名称：" + realtime.getString("city_name")
				+ "\n城市编码：" + realtime.getString("city_code") + "\n星期："
				+ realtime.getString("week") + "\n今天日期："
				+ realtime.getString("date") + "\n农历："
				+ realtime.getString("moon") + "\n风速："
				+ wind.getString("windspeed") + "\n风向："
				+ wind.getString("direct") + "\n风级：" + wind.getString("power")
				+ "\n风偏移：" + wind.getString("offset") + "\n湿度："
				+ weather.getString("humidity") + "\n天气："
				+ weather.getString("info") + "\n温度："
				+ weather.getString("temperature") + "\n空调："
				+ info.getJSONArray("kongtiao").getString(0) + "  "
				+ info.getJSONArray("kongtiao").getString(1) + "\n运动："
				+ info.getJSONArray("yundong").getString(0) + "  "
				+ info.getJSONArray("yundong").getString(1) + "\n紫外线："
				+ info.getJSONArray("ziwaixian").getString(0) + "  "
				+ info.getJSONArray("ziwaixian").getString(1) + "\n感冒："
				+ info.getJSONArray("ganmao").getString(0) + "  "
				+ info.getJSONArray("ganmao").getString(1) + "\n洗车："
				+ info.getJSONArray("xiche").getString(0) + "  "
				+ info.getJSONArray("xiche").getString(1) + "\n穿衣："
				+ info.getJSONArray("chuanyi").getString(0) + "  "
				+ info.getJSONArray("chuanyi").getString(1) + "\nPM单位："
				+ pm25_bean.getString("curPm") + "\nPM可入肺颗粒物："
				+ pm25_bean.getString("pm25") + "\nPM可吸入颗粒物："
				+ pm25_bean.getString("pm10") + "\nPM级别："
				+ pm25_bean.getString("level") + "\n空气质量："
				+ pm25_bean.getString("quality") + "\n建议："
				+ pm25_bean.getString("des");

		return information;
	}

	/**
	 * 一周内的天气
	 * 
	 * @param result
	 * @return
	 */
	public static String Week(Object result) {
		String information = null;

		JSONObject res = JSONObject.fromObject(result);

		// 获取数据
		JSONObject data = res.getJSONObject("data");

		// 获取数据
		JSONArray weather = data.getJSONArray("weather");

		for (int i = 0; i < weather.size(); i++) {
			JSONObject bean = weather.getJSONObject(i);
			// System.out.println("日期：" + bean.getString("date"));
			// System.out.println("星期：" + bean.getString("week"));
			// System.out.println("农历：" + bean.getString("nongli"));

			JSONArray day = bean.getJSONObject("info").getJSONArray("day");
			// System.out.println("白天天气：" + day.getString(1));
			// System.out.println("白天高温：" + day.getString(2));
			// System.out.println("白天风向：" + day.getString(3));
			// System.out.println("白天风级：" + day.getString(4));

			JSONArray night = bean.getJSONObject("info").getJSONArray("night");
			// System.out.println("夜晚天气：" + night.getString(1));
			// System.out.println("夜晚高温：" + night.getString(2));
			// System.out.println("夜晚风向：" + night.getString(3));
			// System.out.println("夜晚风级：" + night.getString(4));
			if (i == 0) {
				information = "日期：" + bean.getString("date") + "\n农历："
						+ bean.getString("nongli") + "\n星期："
						+ bean.getString("week") + "\n白天天气：" + day.getString(1)
						+ "\n白天高温：" + day.getString(2) + "\n白天风向："
						+ day.getString(3) + "\n白天风级：" + day.getString(4)
						+ "\n夜晚天气：" + night.getString(1) + "\n夜晚高温："
						+ night.getString(2) + "\n夜晚风向：" + night.getString(3)
						+ "\n夜晚风级：" + night.getString(4) + "\n\n";
			} else {
				information = information + "日期：" + bean.getString("date")
						+ "\n农历：" + bean.getString("nongli") + "\n星期："
						+ bean.getString("week") + "\n白天天气：" + day.getString(1)
						+ "\n白天高温：" + day.getString(2) + "\n白天风向："
						+ day.getString(3) + "\n白天风级：" + day.getString(4)
						+ "\n夜晚天气：" + night.getString(1) + "\n夜晚高温："
						+ night.getString(2) + "\n夜晚风向：" + night.getString(3)
						+ "\n夜晚风级：" + night.getString(4) + "\n\n";
			}

		}
		return information;

	}

	/**
	 * 天气预报
	 * 
	 * 
	 * @param args
	 */
	// public static void main(String[] args) {
	// // getRequest1("郑州", null);
	// getRequest1("郑州", "今天");
	// }

}

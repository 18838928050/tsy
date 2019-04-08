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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.ssm.tsy.bean.wechat.ResponseArticle;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

public class MoviePiaoService {

	/**
	 * 
	 * 
	 * 电影票房调用
	 * 
	 * 
	 * 
	 **/

	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "cc5deb2ed01ffc2e4d182b27a47bbdb2";

	// 1.最新票房榜
	public static String getRequest1(String leixing) {

		String return_data = null;

		String result = null;
		String url = "http://v.juhe.cn/boxoffice/rank";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("area", leixing);// 票房榜的区域,CN-内地，US-北美，HK-香港
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml/json，默认json

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				return_data = object.get("result").toString();
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

	// 2.网票票房
	public static void getRequest2() {
		String result = null;
		String url = "http://v.juhe.cn/boxoffice/wp";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json

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
	 * 
	 * 电影排行榜
	 * 
	 * @param leixing
	 * @return
	 */
	public static List<ResponseArticle> Movie_Paihangbang(String leixing) {

		String title = null;
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();

		if (leixing.equals("CN")) {
			title = "内地排行榜" + "前五名";
		} else if (leixing.equals("US")) {
			title = "北美排行榜" + "前五名";
		} else if (leixing.equals("HK")) {
			title = "香港排行榜" + "前五名";
		} else {
			title = "系统出错";
			ResponseArticle article = new ResponseArticle();
			article.setTitle(title);
			article.setDescription("");
			articleList.add(article);
			return articleList;
		}
		ResponseArticle article = new ResponseArticle();
		article.setTitle(title);
		article.setDescription("");
		articleList.add(article);

		String result = getRequest1(leixing);
		JSONArray data = JSONArray.fromObject(result);
		for (int i = 0; i < 5; i++) {
			JSONObject bean = data.getJSONObject(i);
			ResponseArticle article1 = new ResponseArticle();
			article1.setTitle(bean.getString("name") + "\n"
					+ bean.getString("wk") + "  周末票房:"
					+ bean.getString("wboxoffice") + "  累计票房:"
					+ bean.getString("tboxoffice"));
			article1.setDescription("");
			articleList.add(article1);
		}

		return articleList;
	}

	/**
	 * 电影票房
	 * 
	 * @param args
	 */
	// public static void main(String[] args) {
	// 电影排行榜
	// getRequest1("CN");
	// 网票票房
	// getRequest2();
	// }
}

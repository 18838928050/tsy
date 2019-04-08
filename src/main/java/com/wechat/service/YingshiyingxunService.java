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

public class YingshiyingxunService {
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "8785b4749876f124d2df38ee7e9a7f0f";

	// 1.影视搜索
	public static void getRequest1(String movie) {
		String result = null;
		String url = "http://op.juhe.cn/onebox/movie/video";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json
		params.put("q", movie);// 影视搜索名称

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

	// 2.最近影讯
	public static String getRequest2(String city) {
		String return_data = null;

		String result = null;
		String url = "http://op.juhe.cn/onebox/movie/pmovie";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("dtype", "json");// 返回数据的格式,xml或json，默认json
		params.put("city", city);// 城市名称

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
	 * 正在上映
	 * 
	 * @return
	 */
	public static List<ResponseArticle> RunningMovie(String content) {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		String result = getRequest2(content);
		if (result.startsWith("209405")) {
			ResponseArticle article = new ResponseArticle();
			article.setTitle("最近上映");
			article.setDescription(result);
			articleList.add(article);
		} else {
			JSONObject data = JSONObject.fromObject(result);
			ResponseArticle article = new ResponseArticle();
			article.setTitle(content+"影讯_最近上映电影");
			article.setDescription(data.getJSONArray("data").getJSONObject(0).getString("name"));
			article.setUrl(data.getString("m_url"));
			articleList.add(article);

			JSONObject data1 = data.getJSONArray("data").getJSONObject(0);
			JSONArray movies = data1.getJSONArray("data");
			for (int i = 0; i < movies.size(); i++) {
				JSONObject bean = movies.getJSONObject(i);
				ResponseArticle article1 = new ResponseArticle();
				article1.setTitle(bean.getString("tvTitle"));
				article1.setDescription(bean.getJSONObject("story").getJSONObject("data").getString("storyBrief"));
				article1.setPicUrl(bean.getString("iconaddress"));
				article1.setUrl(bean.getString("m_iconlinkUrl"));
				articleList.add(article1);
			}
		}
		return articleList;
	}
	
	/**
	 * 即将上映
	 * 
	 * @return
	 */
	public static List<ResponseArticle> JijiangMovie(String content) {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		String result = getRequest2(content);
		if (result.startsWith("209405")) {
			ResponseArticle article = new ResponseArticle();
			article.setTitle("即将上映");
			article.setDescription(result);
			articleList.add(article);
		} else {
			JSONObject data = JSONObject.fromObject(result);
			ResponseArticle article = new ResponseArticle();
			article.setTitle(content+"影讯_即将上映电影");
			article.setDescription(data.getJSONArray("data").getJSONObject(1).getString("name"));
			article.setUrl(data.getString("m_url"));
			articleList.add(article);

			JSONObject data1 = data.getJSONArray("data").getJSONObject(1);
			JSONArray movies = data1.getJSONArray("data");
			for (int i = 0; i < movies.size(); i++) {
				JSONObject bean = movies.getJSONObject(i);
				ResponseArticle article1 = new ResponseArticle();
				article1.setTitle(bean.getString("tvTitle"));
				article1.setDescription(bean.getJSONObject("story").getJSONObject("data").getString("storyBrief"));
				article1.setPicUrl(bean.getString("iconaddress"));
				article1.setUrl(bean.getString("m_iconlinkUrl"));
				articleList.add(article1);
			}
		}

		// System.out.println(result);

		return articleList;
	}

	// public static void main(String[] args) {
	// // getRequest1("变形金刚");
	// getRequest2("郑州");
	// }
}

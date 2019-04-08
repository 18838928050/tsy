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

public class GetOpenIdByCode {
	
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	
	public static Map<String,Object> getRequest1(String appId, String secret, String code) {
		String openid = null;
		String result = null;
		String access_token = null;
		String refresh_token = null;
		Map<String,Object> bean = new HashMap<String, Object>();
		String url = "https://api.weixin.qq.com/sns/oauth2/access_token";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("appid", appId);
		params.put("secret", secret);
		params.put("code", code);
		params.put("grant_type", "authorization_code");
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			openid = object.getString("openid");
			access_token = object.getString("access_token");
			refresh_token = object.getString("refresh_token");
			bean.put("openid", openid);
			bean.put("access_token", access_token);
			bean.put("refresh_token", refresh_token);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
	}
	
	public static Map<String,Object> getRequest2(String access_token, String openid) {
		Map<String,Object> bean = new HashMap<String, Object>();
		String result = null;
		String url = "https://api.weixin.qq.com/sns/userinfo";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("access_token", access_token);// 调用接口凭证
		params.put("openid", openid);// 普通用户的标识，对当前公众号唯一
		params.put("lang", "zh_CN");// 返回国家地区语言版本，zh_CN 简体，zh_TW 繁体，en 英语
		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			bean.put("openId", object.getString("openid"));
			bean.put("headImgUrl", object.getString("headimgurl"));
			bean.put("nickName", object.getString("nickname"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return bean;
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

}

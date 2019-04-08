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

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ssm.tsy.bean.NewsBean;

public class NewService {

	/**
	 * 新闻头条
	 */
	public static final String DEF_CHATSET = "UTF-8";
	public static final int DEF_CONN_TIMEOUT = 30000;
	public static final int DEF_READ_TIMEOUT = 30000;
	public static String userAgent = "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36";

	// 配置您申请的KEY
	public static final String APPKEY = "1f4743a8958b7d557158d1910dacc4bd";

	// 1.微信精选列表
	public static String getRequest1() {

		String return_date = null;

		String result = null;
		String url = "http://v.juhe.cn/toutiao/index";// 请求接口地址
		Map<String, Object> params = new HashMap<String, Object>();// 请求参数
		params.put("key", APPKEY);// 应用APPKEY(应用详细页查询)
		params.put("type", "top");// 类型,,top(头条，默认),shehui(社会),guonei(国内),guoji(国际),yule(娱乐),tiyu(体育)junshi(军事),keji(科技),caijing(财经),shishang(时尚)

		try {
			result = net(url, params, "GET");
			JSONObject object = JSONObject.fromObject(result);
			if (object.getInt("error_code") == 0) {
				return_date = object.get("result").toString();
			} else {
				return_date = object.get("error_code") + ":"
						+ object.get("reason");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return return_date;
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
	 * 新闻列表
	 * @return
	 */
	public static List<NewsBean> Json(){
		List<NewsBean> news_list = new ArrayList<NewsBean>();
		String data = getRequest1();
		JSONObject obj = JSONObject.fromObject(data);
		JSONArray n_data = obj.getJSONArray("data");
		for(int i = 0;i<n_data.size();i++){
			String thumbnail_pic_s02 = null;
			String thumbnail_pic_s03 = null;
			JSONObject bean = n_data.getJSONObject(i);
			String title = bean.getString("title");
			String uniquekey = bean.getString("uniquekey");
			String author_name = bean.getString("author_name");
			String thumbnail_pic_s = bean.getString("thumbnail_pic_s");
			if(bean.containsKey("thumbnail_pic_s02")){
				thumbnail_pic_s02 = bean.getString("thumbnail_pic_s02");
			}
			if(bean.containsKey("thumbnail_pic_s03")){
				thumbnail_pic_s03 = bean.getString("thumbnail_pic_s03");
			}
			String category = bean.getString("category");
			String date = bean.getString("date");
			String url = bean.getString("url");
			
			NewsBean be = new NewsBean();
			be.setAuthor_name(author_name);/* 作者 */
			be.setDate(date);/* 时间 */
			be.setTitle(title);/* 标题 */
			be.setThumbnail_pic_s(thumbnail_pic_s);/* 图片1 */
			be.setThumbnail_pic_s02(thumbnail_pic_s02);/* 图片2 */
			be.setThumbnail_pic_s03(thumbnail_pic_s03);/* 图片3 */
			be.setUniquekey(uniquekey);/* 新闻编号 */
			be.setUrl(url);/* 新闻链接 */
			be.setType(category);/* 类型一 */
			
			news_list.add(be);
		}
		return news_list;
	}

	public static void main(String[] args) {
		System.out.println(Json().size());;
	}
}

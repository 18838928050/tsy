package com.wechat.service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import com.ssm.tsy.bean.NewsBean;
import com.ssm.tsy.util.DateUtil;

public class NewsYuleService {

	/**
	 * 娱乐新闻头条
	 */
	public static String httpUrl = "http://apis.baidu.com/txapi/huabian/newtop";
	// 配置您申请的KEY
	public static final String APPKEY = "e0e023e4a535563f76697782c9668503";

	/**
	 * @param urlAll
	 *            :请求接口
	 * @param num
	 *            :参数返回数量，最大50
	 * @param page
	 *            :参数分页，输出跟随num参数
	 * @return 返回结果
	 */
	public static String request(int num, int page) {
		BufferedReader reader = null;
		String result = null;
		StringBuffer sbf = new StringBuffer();
		String httpArg = "num=" + num + "&page=" + page;
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
	
	/**
	 * 
	 * @return
	 */
	public static List<NewsBean> json() {

		List<NewsBean> newslist = new ArrayList<NewsBean>();

		try {
			String jsonResult = request(50, 1);
			JSONObject res = JSONObject.fromObject(jsonResult);
			JSONArray reslist = res.getJSONArray("newslist");
			for (int i = 0; i < reslist.size(); i++) {
				JSONObject bea = reslist.getJSONObject(i);

				String title = bea.getString("title");
				String date = bea.getString("ctime");
				String realtype = "娱乐";
				String type = bea.getString("description");
				String author_name = bea.getString("description");
				String thumbnail_pic_s = bea.getString("picUrl");
				String url = bea.getString("url");
				String uniquekey = DateUtil.getToString();

				NewsBean be = new NewsBean();
				be.setAuthor_name(author_name);/* 作者 */
				be.setDate(date);/* 时间 */
				be.setRealtype(realtype);/* 类型二 */
				be.setTitle(title);/* 标题 */
				be.setThumbnail_pic_s(thumbnail_pic_s);/* 图片1 */
				be.setThumbnail_pic_s02(null);/* 图片2 */
				be.setThumbnail_pic_s03(null);/* 图片3 */
				be.setUniquekey(uniquekey);/* 新闻编号 */
				be.setUrl(url);/* 新闻链接 */
				be.setType(type);/* 类型一 */

				newslist.add(be);
			}

		} catch (Exception e) {

		} finally {

		}

		return newslist;

	}

	public static void main(String[] args) {
		String jsonResult = request(50, 1);
		System.out.println(jsonResult);
	}
}

package com.wechat.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.ssm.tsy.util.JudgeUtil;

public class BaiduSearchService {
	public static String httpUrl = "http://www.sobaidupan.com/search.asp";
	
	public static String topUrl = "http://www.sobaidupan.com/";
	
	public static String[] request(String num ,int page) {
		String requestType = "get";
		String str = null;
		try {
			Connection conn = Jsoup.connect(httpUrl);
			conn.data("wd",num);
			conn.data("page",String.valueOf(page));
			conn.data("so_md5key", "e16b5564ef4c261fb3a24f9a0f1eb22c");
			// 设置请求类型  
            Document doc = null;  
            switch (requestType)  
            {  
            case "get":  
                doc = conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").timeout(100000).get();  
                break;  
            case "post":  
                doc = conn.header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:33.0) Gecko/20100101 Firefox/33.0").timeout(100000).post();  
                break;  
            }  
            
            Elements linksHref = doc.select("a[href]");
            for(Element link : linksHref){
            	String linkHref = link.attr("href");
                if(linkHref.startsWith("http://www.sobaidupan.com/file")){//java:file-6881557.html
                	String linkText = link.html();
            		String url = requestTop(linkHref);
            		if(str==null){
            			str = linkText+"@@"+url;
            		}else{
            			str = str +"," + linkText+"@@"+url;
            		}
                }else if(linkHref.startsWith("user")){//微风吹过的黎明:user-2452036771-1.html
                	String linkText = link.html();
                	if(str==null){
            			str = linkText+"@@"+topUrl+linkHref;
            		}else{
            			str = str +"," + linkText+"@@"+topUrl+linkHref;
            		}
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		return str.split(",");
	}
	
	public static String requestTop(String Url) {
		String result = null;
		String requestType = "get";
		try {
			Connection conn = Jsoup.connect(Url);
			// 设置请求类型  
			Document doc = null;  
			switch (requestType)  
			{  
			case "get":  
				doc = conn.timeout(100000).get();  
				break;  
			case "post":  
				doc = conn.timeout(100000).post();  
				break;  
			}  
			Elements linksHref = doc.select("a");
			for(Element link : linksHref){
				String title = link.attr("title");
				if(title.endsWith("百度网盘下载")){
					result = link.attr("href");
				}
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static List<Map<String,Object>> getSearch(String num , int page){
		String str[] = request(num,page);
		List<Map<String,Object>> beans = new ArrayList<>();
		for(int i = 0;i<str.length;i=i+2){
			Map<String,Object> bean = new HashMap<>();
			bean.put("title", str[i].split("@@")[0]);
			bean.put("titleURI", str[i].split("@@")[1]);
			bean.put("name", str[i+1].split("@@")[0]);
			bean.put("nameURI", str[i+1].split("@@")[1]);
			beans.add(bean);
		}
		return beans;
	}
	
	public static String getSearchByAI(String num , int page){
		String str[] = request(num,page);
		String returnStr = "";
		for(int i = 0;i<str.length;i=i+2){
			if(JudgeUtil.isNull(returnStr)){
				returnStr = "<a href='"+str[i].split("@@")[1]+"'>"+str[i].split("@@")[0]+"</a>\n";
			}else{
				returnStr = returnStr + "<a href='"+str[i].split("@@")[1]+"'>"+str[i].split("@@")[0]+"</a>\n";
			}
		}
		return returnStr;
	}
	

	public static void main(String[] args) {
		List<Map<String, Object>> jsonResult = getSearch("css",1);
		System.out.println(jsonResult);
	}
}

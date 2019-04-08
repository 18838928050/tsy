package com.ssm.tsy.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.ssm.tsy.bean.wechat.ResponseArticle;
import com.ssm.tsy.bean.wechat.ResponseMusic;
import com.wechat.service.BaiduMusicService;
import com.wechat.service.BaiduSearchService;
import com.wechat.service.BaiduTranslateService;
import com.wechat.service.CaipuService;
import com.wechat.service.ChangtuqicheService;
import com.wechat.service.FaceService;
import com.wechat.service.FastSoSoService;
import com.wechat.service.GongjiaoService;
import com.wechat.service.IDCardService;
import com.wechat.service.IPService;
import com.wechat.service.MoviePiaoService;
import com.wechat.service.PhoneService;
import com.wechat.service.TodayInHistoryService;
import com.wechat.service.WeatherService;
import com.wechat.service.WeixinJingxuanService;
import com.wechat.service.WendaJiqirenService;
import com.wechat.service.YingshiyingxunService;

public class MessageSendUtil {
	
	/**
	 * 签到
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String TsySign(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, content));
	}
	
	/**
	 * 百度云搜索
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String Search(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		// 回复文本消息
		String keyWord = content.replaceAll("^搜索[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "请输入要搜索的文本..."));
		} else {
			String result = FastSoSoService.getSearchByAI(keyWord, 1);
			if (result == null) {
				return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "搜索失败..."));
			} else {
				return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, result));
			}
		}
	}

	/**
	 * 判断用户发送的是否是单个QQ表情
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String QQbiaoqing(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		// 回复文本消息 用户发什么QQ表情，就返回什么QQ表情
		return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, content));
	}

	/**
	 * 翻译文本
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String Fanyi(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		// 回复文本消息
		String keyWord = content.replaceAll("^翻译[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "请输入要翻译的文本..."));
		} else {
			String result = BaiduTranslateService.translateToEn(keyWord, request);
			if (result == null) {
				return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "翻译出错，参考百度错误代码和说明。"));
			} else {
				return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, result));
			}
		}
	}

	/**
	 * 歌曲 --------由于链接禁止，所以该功能禁止显示
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String Gequ(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		String keyWord = content.replaceAll("^歌曲[\\+ ~!@#%^-_=]?", "");// 将歌曲2个字及歌曲后面的+、空格、-等特殊符号去掉
		if ("".equals(keyWord)) {// 如果歌曲名称为空
			return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "请输入歌曲名..."));
		} else {
			String[] kwArr = keyWord.split("@");
			String musicTitle = kwArr[0];// 歌曲名称
			String musicAuthor = "";// 演唱者默认为空
			if (2 == kwArr.length)
				musicAuthor = kwArr[1];
			// 搜索音乐
			ResponseMusic music = null;
			music = BaiduMusicService.json(musicTitle);
			if (null == music) {// 未搜索到音乐
				return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "对不起，没有找到你想听的歌曲<" + musicTitle + ">。或许服务器异常"));
			} else {// 音乐消息
				return MessageUtil.musicMessageToXml(WeChatPublicUtil.getMusicMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_MUSIC, music));
			}
		}
	}

	/**
	 * 图片消息-------人脸识别
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String Photo(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		// 人脸检测
		String detectResult = FaceService.detect(content);
		return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, detectResult));
	}

	/**
	 * 订阅
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Dingyue(String fromUserName, String toUserName) throws Exception {
		// 文本消息对象转换成xml字符串
		return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "欢迎关注小i机器人\n"));
	}

	/**
	 * 菜单栏点击事件
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param eventKey
	 * @return
	 */
	public static String Click(String fromUserName, String toUserName, String eventKey, Map<String,Object> map,List<Map<String,Object>> userBeans) throws Exception {
		String respContent = null;
		// 回复文本消息  事件KEY值，与创建自定义菜单时指定的KEY值对应
		int rebackInt = Integer.parseInt(map.get("rebackint").toString());
		switch (rebackInt) {
			case 1://图文消息
				List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
				for(Map<String,Object> bean:userBeans){
					ResponseArticle article1 = new ResponseArticle();
					article1.setTitle(bean.get("graphicMessageTitle").toString());
					article1.setDescription("");
					article1.setPicUrl(Constants.IP_URI+bean.get("graphicMessageJieshaoPic").toString());
					article1.setUrl(Constants.GRAPHIC_MESSAGE_IP_URI+bean.get("id").toString());
					articleList.add(article1);
				}
				return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
			case 2://图片
						
				return "";
			case 3://语音
				
				return "";
			case 4://视频
				
				return "";
			case 5://文字
				respContent = map.get("rebackcontext").toString();
				return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, respContent));
			case 6://网页
				break;
			default:
				respContent = "该功能正在筹备中,请等待...";
				return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, respContent));
		}
		return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "系统出错"));
	}

	/**
	 * 电影排行榜
	 * 
	 * @param jingweidu
	 * @return
	 */
	public static String Movie_Paihangbang(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		articleList = MoviePiaoService.Movie_Paihangbang(content);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 手机号码归属地查询
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String Shoujihao(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article = new ResponseArticle();
		String keyWord = content.replaceAll("^手机号[\\+ ~!@#%^-_=]?", "");
		article.setTitle(keyWord + "归属地");
		if ("".equals(keyWord)) {
			article.setDescription("请输入手机号...");
		} else if (!JudgeUtil.isPhoneNO(keyWord)) {
			article.setDescription("手机号格式不正确...");
		} else {
			String result;
			result = PhoneService.Json(keyWord);
			if (result == null) {
				article.setDescription("数据有误或者服务器故障...");
			} else {
				article.setDescription(result);
			}
		}
		articleList.add(article);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * IP归属地查询
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @param content
	 * @return
	 */
	public static String DiannaoIP(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article = new ResponseArticle();
		String keyWord = content.replaceAll("^IP[\\+ ~!@#%^-_=]?", "");
		article.setTitle(keyWord + "归属地");
		if ("".equals(keyWord)) {
			article.setDescription("请输入IP地址...");
		} else if (!JudgeUtil.isIP(keyWord)) {
			article.setDescription("IP地址不正确...");
		} else {
			String result;
			result = IPService.Json(keyWord);
			if (result == null) {
				article.setDescription("数据有误或者服务器故障...");
			} else {
				article.setDescription(result);
			}
		}
		articleList.add(article);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 最近上映电影
	 * 
	 * @param jingweidu
	 * @return
	 */
	public static String Zuijinshangying(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception{
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		String keyWord = content.replaceAll("^最近上映[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			ResponseArticle article;
			article = new ResponseArticle();
			article.setTitle("最近上映");
			article.setDescription("请输入城市名称...");
			articleList.add(article);
		} else {
			articleList = YingshiyingxunService.RunningMovie(keyWord);
		}
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 即将上映电影
	 * 
	 * @param jingweidu
	 * @return
	 */
	public static String Jijiangshangying(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		String keyWord = content.replaceAll("^即将上映[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			ResponseArticle article;
			article = new ResponseArticle();
			article.setTitle("即将上映");
			article.setDescription("请输入城市名称...");
			articleList.add(article);
		} else {
			articleList = YingshiyingxunService.JijiangMovie(keyWord);
		}
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 不是回复关键字的回复----------图灵机器人
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Reback(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		// 回复文本消息
		String result = null;
		result = WendaJiqirenService.getRequest1(content);
		if (result == null) {
			return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, "该机器人不支持该文字" + content + "的回复"));
		} else {
			return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, result));
		}
	}

	/**
	 * 今日往事
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Today(String fromUserName, String toUserName) throws Exception {
		// 回复文本消息
		return MessageUtil.textMessageToXml(WeChatPublicUtil.getTextMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_TEXT, TodayInHistoryService.getTodayInHistoryInfo()));
	}

	/**
	 * 天气预报-------今日天气
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Jinritianqi(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article = new ResponseArticle();
		article.setTitle("今日天气");
		String keyWord = content.replaceAll("^今日天气[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			article.setDescription("请输入城市...");
		} else {
			String result;
			result = WeatherService.getRequest1(keyWord, "今天");
			if (result == null) {
				article.setDescription("数据有误或者服务器故障...");
			} else {
				article.setDescription(result);
			}
		}
		articleList.add(article);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 天气预报-------未来一周
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Weilaiyizhou(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article = new ResponseArticle();
		article.setTitle("未来一周");
		String keyWord = content.replaceAll("^未来一周[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			article.setDescription("请输入城市...");
		} else {
			String result;
			result = WeatherService.getRequest1(keyWord, null);
			if (result == null) {
				article.setDescription("数据有误或者服务器故障...");
			} else {
				article.setDescription(result);
			}
		}
		articleList.add(article);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));

	}

	/**
	 * 身份证号码查询
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Shenfenzheng(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article = new ResponseArticle();
		article.setTitle("身份证查询");
		String keyWord = content.replaceAll("^身份证[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			article.setDescription("请输入身份证号...");
		} else {
			String result;
			result = IDCardService.getRequest1(keyWord);
			if (result == null) {
				article.setDescription("数据有误...");
			} else {
				article.setDescription(result);
			}
		}
		articleList.add(article);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 某个城市的长途汽车站查询
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Zhangtuqichezhan(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article = new ResponseArticle();
		article.setTitle("当地长途汽车站");
		String keyWord = content.replaceAll("^长途汽车站[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			article.setDescription("请输入城市...");
		} else {
			String result;
			result = ChangtuqicheService.getRequest1(keyWord);
			if (result == null) {
				article.setDescription("数据有误...");
			} else {
				article.setDescription(result);
			}
		}
		articleList.add(article);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 两个城市之间的长途汽车站查询
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Zhangtuqiche(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article = new ResponseArticle();
		article.setTitle("长途汽车站_时刻表");
		String keyWord = content.replaceAll("^长途汽车[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			article.setDescription("请输入城市...");
		} else {
			if (keyWord.indexOf("@") <= 0) {
				article.setDescription("请按照规则来进行输入查询...");
			} else {
				String from = keyWord.substring(0, keyWord.indexOf("@"));
				String to = keyWord.substring((keyWord.indexOf("@") + 1), keyWord.length());
				String result;
				result = ChangtuqicheService.getRequest2(from, to);
				if (result == null) {
					article.setDescription("数据有误...");
				} else {
					article.setDescription(result);
				}
			}
		}
		articleList.add(article);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 某个城市的公交车牌查询
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Gongjiaoche(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article = new ResponseArticle();
		article.setTitle("当地公交车");
		String keyWord = content.replaceAll("^公交车[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			article.setDescription("请输入内容...");
		} else {
			if (keyWord.indexOf("@") <= 0) {
				article.setDescription("请按照规则来进行输入查询...");
			} else {
				String city = keyWord.substring(0, keyWord.indexOf("@"));
				String xianlu = keyWord.substring((keyWord.indexOf("@") + 1), keyWord.length());
				String result;
				result = GongjiaoService.getRequest1(city, xianlu);
				if (result == null) {
					article.setDescription("数据有误...");
				} else {
					article.setDescription(result);
				}
			}
		}
		articleList.add(article);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 某个城市的公交车途径点查询
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Zhanpaitujingdian(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception {
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		ResponseArticle article = new ResponseArticle();
		article.setTitle("站牌途经点");
		String keyWord = content.replaceAll("^站牌途经点[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			article.setDescription("请输入内容...");
		} else {
			if (keyWord.indexOf("@") <= 0) {
				article.setDescription("请按照规则来进行输入查询...");
			} else {
				String city = keyWord.substring(0, keyWord.indexOf("@"));
				String tujingdian = keyWord.substring((keyWord.indexOf("@") + 1), keyWord.length());
				String result;
				result = GongjiaoService.getRequest2(city, tujingdian);
				if (result == null) {
					article.setDescription("数据有误...");
				} else {
					article.setDescription(result);
				}
			}
		}
		articleList.add(article);
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 菜谱名称-------菜的佳肴做法
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String Chuyi(String fromUserName, String toUserName, String content, HttpServletRequest request) throws Exception{
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		String keyWord = content.replaceAll("^厨艺[\\+ ~!@#%^-_=]?", "");
		if ("".equals(keyWord)) {
			ResponseArticle article;
			article = new ResponseArticle();
			article.setTitle("菜谱名称");
			article.setDescription("请输入内容...");
			articleList.add(article);
		} else {
			articleList = CaipuService.JsonreQuest3(keyWord);
		}
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

	/**
	 * 微信精选
	 * 
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 */
	public static String WeixinJingxuan(String fromUserName, String toUserName) throws Exception{
		List<ResponseArticle> articleList = new ArrayList<ResponseArticle>();
		articleList = WeixinJingxuanService.Json();
		return MessageUtil.newsMessageToXml(WeChatPublicUtil.getNewsMessage(fromUserName, toUserName, Constants.RESP_MESSAGE_TYPE_NEWS, articleList));
	}

}

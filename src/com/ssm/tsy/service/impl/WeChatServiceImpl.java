package com.ssm.tsy.service.impl;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.ssm.tsy.bean.WeChatAPP;
import com.ssm.tsy.bean.WeChatKeys;
import com.ssm.tsy.bean.WeChatUser;
import com.ssm.tsy.bean.wechat.ResponseTextMessage;
import com.ssm.tsy.dao.MenuDao;
import com.ssm.tsy.dao.TsyGraphicMessageDao;
import com.ssm.tsy.dao.TsyInvestigationQuestionDao;
import com.ssm.tsy.dao.WeChatAPPDao;
import com.ssm.tsy.dao.WeChatDao;
import com.ssm.tsy.dao.WeChatKeysDao;
import com.ssm.tsy.dao.WechatSetupMapper;
import com.ssm.tsy.service.WeChatService;
import com.ssm.tsy.service.WechatMessageService;
import com.ssm.tsy.util.Constants;
import com.ssm.tsy.util.DateUtil;
import com.ssm.tsy.util.MessageSendUtil;
import com.ssm.tsy.util.MessageUtil;
import com.ssm.tsy.util.XiaoqUtil;
import com.ssm.util.TokenThread;
import com.wechat.service.GetUserMationService;

@Service
public class WeChatServiceImpl implements WeChatService {

	@Resource
	private MenuDao menuDao;
	
	@Resource
	private WeChatDao weChatDao;

	@Resource
	private WeChatKeysDao weChatKeysDao;

	@Resource
	private WeChatAPPDao weChatAPPDao;
	
	@Resource
	private WechatMessageService wechatMessageService;
	
	@Resource
	private WechatSetupMapper wechatSetupMapper;
	
	@Resource
	private TsyGraphicMessageDao tsyGraphicMessageDao;
	
	@Resource
	private TsyInvestigationQuestionDao tsyInvestigationQuestionDao;
	
	private String messageId = "";
	
	/**
	 * 微信消息处理
	 * 
	 * @param request
	 */
	@Override
	public String WechatReply(HttpServletRequest request) {

		String respMessage = null;
		try {
			String respContent = "请求处理异常，请稍候尝试！";// 默认返回的文本消息内容
			Map<String, String> requestMap = MessageUtil.parseXml(request);// xml请求解析
			String fromUserName = requestMap.get("FromUserName");// 发送方帐号（open_id）
			String toUserName = requestMap.get("ToUserName");// 公众帐号
			String msgType = requestMap.get("MsgType");// 消息类型

			// 回复文本消息
			ResponseTextMessage textMessage = new ResponseTextMessage();
			textMessage.setToUserName(fromUserName);
			textMessage.setFromUserName(toUserName);
			textMessage.setCreateTime(new Date().getTime());
			textMessage.setMsgType(Constants.RESP_MESSAGE_TYPE_TEXT);
			textMessage.setFuncFlag(0);
			
			/*添加消息记录*/
			if (msgType.equals(Constants.REQ_MESSAGE_TYPE_TEXT)) {
				Map<String,Object> map = new HashMap<String, Object>();
				map.put("wechatMessageFromUser", fromUserName);
				map.put("wechatMessage", requestMap.get("Content"));
				messageId = wechatMessageService.insertWechatMessage(map).get("wechat_message_id").toString();
			}
			
			WeChatAPP bean_list1 = weChatAPPDao.SelectAll();
			// 是否绑定公众号
			if (bean_list1!=null) {
				WeChatUser wechatuser = weChatDao.SelectByOpenId(fromUserName);
				// 判断用户是否存在
				if (wechatuser == null) {
					// 获取关注用户信息
					WeChatUser bean = GetUserMationService.getRequest1(fromUserName, TokenThread.accessToken.getToken());
					bean.setAppId(bean_list1.getId());
					weChatDao.AddWeChat(bean);
					wechatuser = weChatDao.SelectByOpenId(fromUserName);
				}
				// 该微信用户没有被冻结
				if (wechatuser.getFrozen() == 1) {
					if (msgType.equals(Constants.REQ_MESSAGE_TYPE_TEXT)) {/*文本消息*/
						// 文本消息内容
						String content = requestMap.get("Content");
						String key = "";
						List<WeChatKeys> weChatKeys_list = weChatKeysDao.SelectAll();
						for (WeChatKeys be : weChatKeys_list) {
							if (content.startsWith(be.getKeyvalue())) {
								key = be.getKeyvalue();
								break;
							}
						}
						if(key.equals("")||key==null){
							Map<String,Object> map = new HashMap<String, Object>();
							map.put("appId", bean_list1.getId());
							map.put("setupType", Constants.WECHAT_SETUP_TYPE_AI);//智能回复
							map = wechatSetupMapper.queryWechatSetupListByAppId(map);
							if(map.get("setup_state").toString().equals(Constants.WECHAT_SETUP_TYPE_AI_STATE_ON)){
								updateWechatReturnMessage();
								if (XiaoqUtil.isQqFace(content)) {// 判断用户发送的是否是单个QQ表情
									return MessageSendUtil.QQbiaoqing(fromUserName, toUserName, content, request);
								}else{//图灵机器人
									return MessageSendUtil.Reback(fromUserName, toUserName, content, request);
								}
							}else{
								respContent = "智能回复已关闭...";
							}
						}else{
							WeChatKeys bean = weChatKeysDao.SelectAllByKey(key);
							StringBuffer buffer = new StringBuffer();
							updateWechatReturnMessage();
							if(Constants.KEYCLASS_WINDOW.equals(String.valueOf(bean.getKeyclass()))){//系统正文关键字
								if (bean.getJudge() == 1) {
									String st = bean.getContext();
									if(content.startsWith("搜索")){
										Map<String,Object> map = new HashMap<String, Object>();
										map.put("appId", bean_list1.getId());
										map.put("setupType", Constants.WECHAT_SETUP_TYPE_AI_SEARCH);//智能搜索
										map = wechatSetupMapper.queryWechatSetupListByAppId(map);
										if(!map.get("setup_state").toString().equals(Constants.WECHAT_SETUP_TYPE_AI_SEARCH_STATE_ON)){
											buffer.append("智能搜索已关闭,请等待开放...").append("\n\n").append("消息来自官方开发团队");
											textMessage.setContent(buffer.toString());
											return MessageUtil.textMessageToXml(textMessage);
										}
									}else if(content.startsWith("签到")){
										content = tsyWechatUserSign(fromUserName);
									}
									Class<?> classType = Class.forName("com.ssm.tsy.util.MessageSendUtil");
									Method method = classType.getMethod(st,new Class[] { String.class,String.class, String.class,HttpServletRequest.class });
									return (String) method.invoke(classType,new Object[] { fromUserName,toUserName, content, request });
								}
							}else{//数字关键字  符号关键字   文字关键字
								if (bean.getJudge() == 1) {
									buffer.append(bean.getContext());
									textMessage.setContent(buffer.toString());
									return MessageUtil.textMessageToXml(textMessage);
								}
							}
							buffer.append("列表功能正在维护").append("\n\n").append("消息来自官方开发团队");
							textMessage.setContent(buffer.toString());
							return MessageUtil.textMessageToXml(textMessage);
						}
					} else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_IMAGE)) {// 图片消息
						// 取得图片地址
						String picUrl = requestMap.get("PicUrl");
						return MessageSendUtil.Photo(fromUserName, toUserName, picUrl, request);
					} else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_LOCATION)) {//地理位置消息
						respContent = "您发送的是地理位置消息！";
						String access_token = TokenThread.accessToken.getToken();// 获取access_token
						GetUserMationService.getRequest1(fromUserName,access_token);// 查看用户信息
					} else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_LINK)) {// 链接消息
						respContent = "您发送的是链接消息！";
					} else if (msgType.equals(Constants.REQ_MESSAGE_TYPE_VOICE)) {// 音频消息
						respContent = "您发送的是音频消息！";
					}
				} else {
					respContent = "该账号被冻结，请联系管理员XXX XXXX XXXX";// 用户被冻结
				}
				if (msgType.equals(Constants.REQ_MESSAGE_TYPE_EVENT)) {// 事件推送
					String eventType = requestMap.get("Event");// 事件类型
					if (eventType.equals(Constants.EVENT_TYPE_SUBSCRIBE)) {// 订阅
						return Subscribe(fromUserName,toUserName);
					} else if (eventType.equals(Constants.EVENT_TYPE_UNSUBSCRIBE)) {// 取消订阅------取消订阅后用户再收不到公众号发送的消息，因此不需要回复消息
						unSubscribe(fromUserName,toUserName);
					} else if (eventType.equals(Constants.EVENT_TYPE_CLICK)) {// 自定义菜单点击事件
						if (wechatuser.getFrozen() == 1) {// 该微信用户没有被冻结
							String eventKey = requestMap.get("EventKey");
							return CustomMenu(eventKey,fromUserName,toUserName);
						} else {// 用户被冻结
							respContent = "该账号被冻结，请联系管理员XXX XXXX XXXX";
						}
					}
				}
			}else{
				respContent = "该公众号暂未开始运营，请等待...";
			}
			textMessage.setContent(respContent);
			respMessage = MessageUtil.textMessageToXml(textMessage);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return respMessage;
	}
	
	/**
	 * 修改消息状态未已回复
	 * @throws Exception
	 */
	public void updateWechatReturnMessage() throws Exception{
		Map<String,Object> mapUpdate = new HashMap<String, Object>();
		mapUpdate.put("wechatMessageId", messageId);
		wechatMessageService.updateWechatMessage(mapUpdate);
	}
	
	/**
	 * 签到
	 * @param toUserName 
	 * @throws Exception
	 */
	public String tsyWechatUserSign(String fromUserName) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("userOpenId", fromUserName);
		Map<String,Object> bean = tsyInvestigationQuestionDao.queryinvestigationQuestionListByContent(map);
		if(bean==null){
			map.put("wechatUserSignTime", DateUtil.getTimeAndToString());
			int size = tsyInvestigationQuestionDao.insertinvestigationQuestionListByContent(map);
			if(size>0){
				return "尊敬的用户,您好:\n\n今天签到已完成\n\n\n签到时间:"+DateUtil.getTimeAndToString()+"\n\n今日签到次数：1次";
			}else{
				return "签到失败,请联系管理员...";
			}
		}else{
			return "您今天已签过到,无需在进行签到...";
		}
	}
	
	/**
	 * 订阅
	 * @return
	 * @throws Exception 
	 */
	public String Subscribe(String fromUserName,String toUserName) throws Exception{
		// 判断是不是新订阅用户
		WeChatUser bean1 = weChatDao.SelectByOpenId(fromUserName);
		if (bean1 == null) {
			// 获取关注用户信息
			WeChatUser bean = GetUserMationService.getRequest1(fromUserName, TokenThread.accessToken.getToken());
			// 判断是否取到了用户信息
			if (bean != null) {
				weChatDao.AddWeChat(bean);
			}
		} else {
			bean1.setSubscribe("1");
			weChatDao.UpdateWeChat(bean1);
		}
		return MessageSendUtil.Dingyue(fromUserName, toUserName);
	}
	
	/**
	 * 取消订阅
	 * @return
	 * @throws Exception 
	 */
	public void unSubscribe(String fromUserName,String toUserName) throws Exception{
		WeChatUser bean = weChatDao.SelectByOpenId(fromUserName);
		// 查看之前数据库是否存有这个用户的信息，如果有则取消订阅，否则则添加至数据库再取消订阅
		if (bean != null) {
			bean.setSubscribe("0");
			bean.setFrozen(0);
			weChatDao.UpdateWeChat(bean);
		} else {
			// 获取关注用户信息
			WeChatUser new_bean = GetUserMationService.getRequest1(fromUserName,TokenThread.accessToken.getToken());
			// 判断是否取到了用户信息
			if (new_bean == null) {
			} else {
				new_bean.setSubscribe("0");
				new_bean.setFrozen(0);
				weChatDao.AddWeChat(bean);
			}
		}
	}
	
	/**
	 * 自定义菜单
	 * @param eventKey
	 * @param fromUserName
	 * @param toUserName
	 * @return
	 * @throws Exception
	 */
	public String CustomMenu(String eventKey,String fromUserName,String toUserName) throws Exception{
		Map<String,Object> map = new HashMap<String, Object>();
		map.put("menuKey", eventKey);
		Map<String,Object> bean = menuDao.SelectItemByMenuKey(map);
		int rebackInt = Integer.parseInt(bean.get("rebackint").toString());
		List<Map<String,Object>> userBeans = new ArrayList<Map<String,Object>>();
		switch (rebackInt) {
			case 1://图文消息
				Map<String,Object> cdBean = new HashMap<String, Object>();
				cdBean.put("rowId", bean.get("rebackcontext").toString());
				userBeans = tsyGraphicMessageDao.queryGraphicMessageByRowId(cdBean);
				userBeans.addAll(tsyGraphicMessageDao.queryGraphicMessageByFatherId(cdBean));
				break;
			case 2://图片
				break;
			case 3://语音
				
				break;
			case 4://视频
				
				break;
			case 5://文字
				break;
			case 6://网页
				break;
			default:
				break;
		}
		return MessageSendUtil.Click(fromUserName, toUserName, eventKey, bean, userBeans);
	}

	/**
	 * 查询所有的关注人
	 * 
	 * @return
	 */
	@Override
	public List<WeChatUser> SelectAll() {
		return weChatDao.SelectAll();
	}

	/**
	 * 修改关注人信息 ->冻结用户 ->解除冻结 ->添加绑定信息 ->解除绑定信息 ->取消关注 ->重新关注
	 * ->更新关注人信息---头像---性别---分组---昵称---城市
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public int UpdateWeChat(WeChatUser bean) {
		return weChatDao.UpdateWeChat(bean);
	}

	/**
	 * 根据用户的标识来查询该用户是否存在
	 * 
	 * @param openid
	 * @return
	 */
	@Override
	public WeChatUser SelectByOpenId(String openid) {
		return weChatDao.SelectByOpenId(openid);
	}

	/**
	 * 获取所有的关注用户
	 * 
	 * @return
	 */
	@Override
	public List<String> getAllWeiXinUser(String accessToken) {
		List<String> openIds = new ArrayList<String>();
		// 上传文件请求路径
		String action = "https://api.weixin.qq.com/cgi-bin/user/get?access_token=" + accessToken;
		try {
			URL urlGet = new URL(action);
			HttpURLConnection http = (HttpURLConnection) urlGet.openConnection();
			http.setRequestMethod("GET"); // 必须是get方式请求
			http.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
			http.setDoOutput(true);
			http.setDoInput(true);
			System.setProperty("sun.net.client.defaultConnectTimeout", "30000");// 连接超时30秒
			System.setProperty("sun.net.client.defaultReadTimeout", "30000"); // 读取超时30秒
			http.connect();

			InputStream is = http.getInputStream();
			int size = is.available();
			byte[] jsonBytes = new byte[size];
			is.read(jsonBytes);
			String result = new String(jsonBytes, "UTF-8");
			JSONObject jsonObj = new JSONObject(result);
			JSONObject json1 = new JSONObject(jsonObj.get("data").toString());
			JSONArray json2 = new JSONArray(json1.get("openid").toString());
			for (int i = 0; i < json2.length(); i++) {
				openIds.add(i, json2.getString(i));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return openIds;
	}

	/**
	 * 添加新的关注人
	 * 
	 * @param bean
	 * @return
	 */
	@Override
	public int AddWeChat(WeChatUser bean) {
		return weChatDao.AddWeChat(bean);
	}

	/**
	 * 根据昵称查询关注人
	 * 
	 * @return
	 */
	@Override
	public List<WeChatUser> SelectAllByNickName(String nickname) {
		return weChatDao.SelectAllByNickName(nickname);
	}

}

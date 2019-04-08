package com.ssm.tsy.util;

/**
 * 常量类
 *
 */
public class Constants {
	
	public static final String IP_URI = "http://z1714z2699.imwork.net/tsy/";
	
	public static final String GRAPHIC_MESSAGE_IP_URI = "http://z1714z2699.imwork.net/tsy/jsp/html/admTsy/graphicMessage.html?rowId=";
	
	//投票的答案表
	public static final String VOTE_TABLE_BEAN = "tsy_investigation_answer_";
	
	//登录日志表
	public static final String LOGIN_LOG_TABLE = "tsy_user_login_log";
	
	public static final String SAVE_YES = "1201";
	public static final String SAVE_NO = "1200";
	
	//微信消息类型
	public static final String RESP_MESSAGE_TYPE_TEXT = "text";//返回消息类型：文本
	public static final String RESP_MESSAGE_TYPE_MUSIC = "music";//返回消息类型：音乐
	public static final String RESP_MESSAGE_TYPE_NEWS = "news";//返回消息类型：图文
	public static final String REQ_MESSAGE_TYPE_TEXT = "text";//请求消息类型：文本
	public static final String REQ_MESSAGE_TYPE_IMAGE = "image";//请求消息类型：图片
	public static final String REQ_MESSAGE_TYPE_LINK = "link";//请求消息类型：链接
	public static final String REQ_MESSAGE_TYPE_LOCATION = "location";//请求消息类型：地理位置
	public static final String REQ_MESSAGE_TYPE_VOICE = "voice";//请求消息类型：音频
	public static final String REQ_MESSAGE_TYPE_EVENT = "event";//请求消息类型：推送
	public static final String EVENT_TYPE_SUBSCRIBE = "subscribe";//事件类型：subscribe(订阅)
	public static final String EVENT_TYPE_UNSUBSCRIBE = "unsubscribe";//事件类型：unsubscribe(取消订阅)
	public static final String EVENT_TYPE_CLICK = "CLICK";//事件类型：CLICK(自定义菜单点击事件)
	
	
	public static final String ZERO = "0";//正常
	public static final String WRONG = "-9999";//出错
	
	public static final String ADD_ERROR = "1095";//添加失败
	public static final String ADD_SUCCESS = "1096";//添加成功
	public static final String ADD_ERROR_RES = "1097";//添加失败--原因，添加的内容存在
	
	public static final String UPDATE_ERROR = "1085";//修改失败
	public static final String UPDATE_SUCCESS = "1086";//修改成功
	public static final String UPDATE_ERROR_RES = "1087";//修改失败--原因，修改的内容存在
	
	public static final String DELETE_ERROR = "1075";//删除失败
	public static final String DELETE_SUCCESS = "1076";//删除成功
	public static final String DELETE_ERROR_RES = "1077";//删除失败--原因，删除的内容不存在
	public static final String DELETE_JUDGE = "1078";//要删除的内容下面还有子内容，是否真正删除
	public static final String DELETE_SUCCESS_CANLIU = "1079";//删除成功，数据有残留
	
	public static final String ERROR = "0404";//请求失败
	public static final String SUCCESS = "0506";//请求成功
	
	public static final String KNOWLEDGED_DOWN_SUCCESS = "1068";//微信内容下线成功
	public static final String KNOWLEDGE_UP_SUCCESS = "1069";//微信内容上线成功
	
	public static final String WECHAT_MENU_ADD_ERROR = "1055";//微信菜单发布失败
	public static final String WECHAT_MENU_ADD_SUCCESS = "1056";//微信菜单发布成功
	
	public static final String NUMBER_IS_NULL = "0100";//账号为空
	public static final String PASSWORD_IS_NULL = "0101";//密码为空
	public static final String NAME_IS_NULL = "0102";//姓名为空
	public static final String OLDPASSWORD_IS_NULL = "0110";//旧密码为空
	public static final String NEWPASSWORD_IS_NULL = "0111";//新密码为空
	public static final String NUMBER_IS_WRONG = "0103";//账号错误
	public static final String PASSWORD_IS_WRONG = "0104";//密码错误
	public static final String OLDPASSWORD_IS_WRONG = "0105";//旧密码错误
	public static final String LOGIN = "0001";//登陆
	
	public static final String KEYVALUE_IS_NULL = "0200";//关键字‘键’为空
	public static final String KEYCLASS_IS_NULL = "0201";//关键字类型为空
	public static final String KEYCLASS_IS_ZERO = "0202";//请选择关键字
	public static final String CONTENT_IS_NULL = "0203";//如果是数字关键字，请输入回复内容，内容为空
	public static final String KEYCLASS_IS_WRONG = "0204";//关键字类型错误
	
	public static final String KEYCLASS_NUMBER = "1";//数字关键字
	public static final String KEYCLASS_WINDOW = "2";//系统正文关键字
	public static final String KEYCLASS_SYMBOL = "3";//符号关键字
	public static final String KEYCLASS_WORDS = "4";//文字关键字
	
	/*用户权限*/
	public static final String USER_JURISDICTION_SENIOR_ADMIN = "3";//高级管理员
	public static final String USER_JURISDICTION_GENERAL_ADMIN = "2";//普通管理员
	public static final String USER_JURISDICTION_SUPER_ADMIN = "1";//超级管理员
	public static final String WECHAT_KNOWLEDGE_ORDINARY_STAFF = "0";//普通员工
	
	/*微知识表状态*/
	public static final String WECHAT_KNOWLEDGE_STATE_XX = "0";//下线
	public static final String WECHAT_KNOWLEDGE_STATE_SX = "1";//上线
	
	/*门户滚动图片表*/
	public static final String TSY_SCOLLOR_PIC_STATE_XX = "0";//下线
	public static final String TSY_SCOLLOR_PIC_STATE_SX = "1";//上线
	public static final String TSY_SCOLLOR_PIC_FB_XX = "0";//未发布
	public static final String TSY_SCOLLOR_PIC_FB_SX = "1";//已发布
	
	/*附件表*/
	public static final String TSYOPTIONS_OPTIONSTATE_FF = "0";//非法文件
	public static final String TSYOPTIONS_OPTIONSTATE_ZC = "1";//正常文件
	public static final String TSYOPTIONS_OPTIONSTATE_SC = "2";//删除文件逻辑删除
	
	/*视频文档表类型*/
	public static final String TSY_USER_CUSTOM_OPTIONS_TYPE_SP = "1";//视频
	public static final String TSY_USER_CUSTOM_OPTIONS_TYPE_WD = "2";//文档
	/*视频文档表文档状态*/
	public static final String TSY_USER_CUSTOM_OPTIONS_STATE_SHZ = "0";//审核中
	public static final String TSY_USER_CUSTOM_OPTIONS_STATE_SHTG = "1";//审核通过
	public static final String TSY_USER_CUSTOM_OPTIONS_STATE_SHBTG = "2";//审核不通过
	public static final String TSY_USER_CUSTOM_OPTIONS_STATE_FFWJ = "3";//非法文件
	
	/*视频文档分类表分类状态*/
	public static final String TSY_USER_CUSTOM_OPTIONS_CLASS_STATE_FF = "0";//非法
	public static final String TSY_USER_CUSTOM_OPTIONS_CLASS_STATE_ZC = "1";//正常
	/*视频文档分类表分类类型*/
	public static final String TSY_USER_CUSTOM_OPTIONS_CLASS_TYPE_SY = "1";//私有
	public static final String TSY_USER_CUSTOM_OPTIONS_CLASS_TYPE_GK = "2";//公开
	
	/*视频文档标签表标签状态*/
	public static final String TSY_USER_CUSTOM_OPTIONS_LABEL_STATE_FF = "0";//非法
	public static final String TSY_USER_CUSTOM_OPTIONS_LABEL_STATE_ZC = "1";//正常
	
	public static final String ADM_TSY_USER_SESSION_LOG_IS_NULL = "1341";//门户用户session信息为空
	public static final String ADM_TSY_USER_SESSION_LOG_NOT_IS_NULL = "1342";//门户用户session信息不为空
	
	/*微信表回复状态*/
	public static final String WECHAT_MESSAGE_STATE_FF = "0";//未回复
	public static final String WECHAT_MESSAGE_STATE_ZC = "1";//已回复
	
	/*积分规格表*/
	public static final String TSY_OPTIONS_INTEGRAL_GG_OPTIONS_INTEGRAL_TYPE_ADD = "1";//新添
	public static final String TSY_OPTIONS_INTEGRAL_GG_OPTIONS_INTEGRAL_TYPE_EVALUATE = "2";//评价
	public static final String TSY_OPTIONS_INTEGRAL_GG_OPTIONS_INTEGRAL_TYPE_LLLEGAL = "3";//违规
	public static final String TSY_OPTIONS_INTEGRAL_GG_OPTIONS_INTEGRAL_TYPE_RETURN = "1100";//该类型已存在在，请选择其他类型或直接对该类型进行修改
	
	//设置表
	public static final String WECHAT_SETUP_TYPE_AI = "1";//人工智能
	public static final String WECHAT_SETUP_TYPE_AI_STATE_ON = "1";//人工智能开启
	public static final String WECHAT_SETUP_TYPE_AI_STATE_OFF = "0";//人工智能关闭
	public static final String WECHAT_SETUP_TYPE_AI_SEARCH = "2";//智能搜索
	public static final String WECHAT_SETUP_TYPE_AI_SEARCH_STATE_ON = "1";//智能搜索开启
	public static final String WECHAT_SETUP_TYPE_AI_SEARCH_STATE_OFF = "0";//智能搜索关闭
	
	//图文消息发送状态
	public static final String WECHAT_MESSAGE_GRAPHIC_MESSAGE_JUDGE_SEND_N = "0";//未发送
	public static final String WECHAT_MESSAGE_GRAPHIC_MESSAGE_JUDGE_SEND_Y = "1";//已发送
	
	//投票内容是否保存
	public static final String WECHAT_TSY_VOTE_NO_SAVE = "0";//未保存
	public static final String WECHAT_TSY_VOTE_SAVE = "1";//已保存
	
	//投票内容当前状态
	public static final String WECHAT_TSY_VOTE_STATE_NO_RUN = "0";//未执行
	public static final String WECHAT_TSY_VOTE_STATE_RUNING = "1";//已执行
	public static final String WECHAT_TSY_VOTE_STATE_RUNOVER = "2";//执行完毕
	public static final String WECHAT_TSY_VOTE_STATE_DELETE = "3";//删除
	
	//投票内容问题类型
	public static final String WECHAT_TSY_QUESTION_TYPE_DANX = "1";//单选
	public static final String WECHAT_TSY_QUESTION_TYPE_DUOX = "2";//多选
	
	//投票内容问题类型
	public static final String WECHAT_TSY_OPTIONS_TYPE_WZ = "1";//文字
	public static final String WECHAT_TSY_OPTIONS_TYPE_TP = "2";//图片
	
	public static final String getLoginThisMonthTable(){
		return LOGIN_LOG_TABLE + DateUtil.getTimeSixAndToString();
	}
	
	public static final String getLoginPrevMonthTable(){
		return LOGIN_LOG_TABLE + DateUtil.getTimeSixAndToString();
	}
	
}

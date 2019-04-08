<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<base href="<%=basePath%>">

<title>My JSP 'zhinan.jsp' starting page</title>

<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->

<style type="text/css">
* {
	margin: 0;
	padding: 0;
}

.center {
	width: 100%;
	margin: 0 auto;
	padding-top: 20px;
}

p {
	padding-left: 30px;
	color: #000;
	margin-bottom: 20px;
	font-size: 13px;
}

h3 {
	font-size: 14px;
	margin-bottom: 20px;
}

h3 strong {
	color: #000;
}

h1 {
	padding-bottom: 10px;
	border-bottom: 2px solid #1CA47D;
	margin-bottom: 20px;
	font-size: 14px;
}

.guide {
	display: block;
	max-width: 530px;
	margin-left: 80px;
	margin-bottom: 20px;
}
</style>


</head>

<body oncontextmenu="return false;" onselectstart="return false;">

	<div class="center">
		<div class="app-inner clearfix">
			<div class="page-header">
				<h1>开通指引</h1>
			</div>
			<h3>
				<strong>一、如何配置URL与Token</strong>
			</h3>
			<p>
				步骤1、点击【<a href="https://mp.weixin.qq.com/" target="_Blank">微信公众平台</a>】，跳转到微信公众平台<a
					href="https://mp.weixin.qq.com/" target="_Blank">https://mp.weixin.qq.com/</a>。
			</p>
			<img class="guide" src="images/u6.jpg">
			<p>步骤2、登录微信公众平台后，在左侧树状菜单栏最下方点击【开发者中心】，在配置项栏里找到URL和Token，将这两项内容分别复制粘贴上去，点击【提交】即可。</p>
			<img class="guide" src="images/u0.png">
			<h3>
				<strong>二、如何配置Appld与App Secret？</strong>
			</h3>
			<p>步骤1、登录微信公众平台后，在左侧树状菜单栏最下方点击【开发者中心】，在配置项栏里找到AppID和AppSecret，将这两项内容分别复制。</p>
			<img class="guide" src="images/u14.png">
			<p>步骤2、回到平台管理后台—微信公众号，将AppID和AppSecret这两项的内容分别粘贴或输入进去，点击【保存】，即会提示“保存微信配置成功”。</p>
			<img class="guide" src="images/u18.png">
		</div>
	</div>

</body>
</html>

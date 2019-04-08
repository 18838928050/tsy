<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt"%>
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
<meta http-equiv="pragma" content="no-cache">
<meta http-equiv="cache-control" content="no-cache">
<meta http-equiv="expires" content="0">
<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
<meta http-equiv="description" content="This is my page">
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="HandheldFriendly" content="true" />
<meta name="MobileOptimized" content="320" />
<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no" />
<link rel="stylesheet" type="text/css" href="css/dandelion.css" media="screen" />
<title>404错误页面</title>
</head>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<div class="rt_content">
		<div id="da-wrapper" class="fluid">
			<!-- Content -->
			<div id="da-content">
				<!-- Container -->
				<div class="da-container clearfix">
					<div id="da-error-wrapper">
						<div id="da-error-pin"></div>
						<div id="da-error-code"> error <span>404</span>
						</div>
						<h1 class="da-error-heading">哎哟喂！页面让狗狗叼走了！</h1>
						<p>大家可以到狗狗没有叼过的地方看看！<br/> <a href="javaScript:;" >${requestScope.message }</a>
						</p>
					</div>
				</div>
			</div>
		</div>
	</div>
	</section>
</body>
</html>

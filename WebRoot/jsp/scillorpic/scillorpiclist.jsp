<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<link type="text/css" rel="stylesheet" href="js/bootstrap/dist/css/bootstrap.min.css" />
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<div class="rt_content">
		<div class="page_title">
			<h2 class="fl">通知列表</h2>
			<font style="font-size: 15px;color: black; margin-left: 850px;font-style:normal">
				当前线上
				<img alt="上线" src="images/up.png" style="margin-left: 10px" />：<font id="up">${up_size }</font>个，
				线下
				<img alt="下线" src="images/down.png" style="margin-left: 10px" />：<font id="down">${size-up_size }</font>个，共${size }个
			</font>
			<a href="scillorpic/ToAddPage" class="fr top_rt_btn add_icon">添加</a>
		</div>
		<form action="" method="post">
			<section class="mtb"> <select id="display" name="display"
				class="select">
				<option value="-1">通知状态</option>
				<option value="1">线上通知</option>
				<option value="0">线下通知</option>
			</select> <input type="text" id="name" name="name" class="textbox textbox_225"
				placeholder="名称" /> <input type="button" id="select" value="查询"
				class="group_btn" /></section>
		</form>
		<table class="table">
			<tr>
				<th style="width: 60px">序号</th>
				<th class="center">名称</th>
				<th class="center">介绍</th>
				<th class="center">添加时间</th>
				<th class="center">通知状态</th>
				<th class="center">发布状态</th>
				<th class="center">操作</th>
			</tr>
			<tbody id="tablelsw">
				<c:if test="${size==0 }">
					<td colspan="6" style="text-align: center;color: red;">暂时没有数据</td>
				</c:if>
				<c:if test="${size!=0 }">
					<c:forEach var="bean" items="${scillorpiclist}" varStatus="status">
						<tr>
							<td class="center"><a>${status.index + 1}</a></td>
							<td class="center">${bean.scollorPicName }</td>
							<td class="center">
								<span style="word-wrap:break-word;word-break:break-all;">${bean.scollorPicIntroduce}</span>
							</td>
							</td>
							<td class="center">${bean.scollorPicData }</td>
							<td class="center">
								<c:if test="${bean.scollorPicDisplay==1 }">
									<span style="color: green;"><img alt="上线" src="images/up.png" style="margin-left: 10px" />[线上]</span>
								</c:if>
								<c:if test="${bean.scollorPicDisplay==0 }">
									<span style="color: red"><img alt="下线" src="images/down.png" style="margin-left: 10px" />[线下]</span>
								</c:if>
							</td>
							<td class="center">
								<c:if test="${bean.scollorPicFb==1 }">
									<span style="color: green;">[已发布]</span>
								</c:if>
								<c:if test="${bean.scollorPicFb==0 }">
									<span style="color: red">[未发布]</span>
								</c:if>
							</td>
							<td class="center">
								<a id="bianji" href="scillorpic/ToUpdatePage?id=${bean.id}" title="编辑" class="link_icon">&#101;</a>
								<a id="shanchu" href="javaScript:;" onclick="deleteItem('${bean.id}','${bean.scollorPicPath}')" title="删除" class="link_icon">&#100;</a>
								<a id="upordown" href="javaScript:;" onclick="upordown('${bean.id}')" title="状态切换"><img src="images/qiehuan.png" style="margin-top: -7px;width: 20px;height: 20px" /> </a>
								<c:if test="${bean.scollorPicFb==0 }">
									<a id="fb" href="javaScript:;" onclick="fb('${bean.id}')" title="发布"><img src="images/fb.png" style="margin-top: -7px;width: 20px;" /> </a>
								</c:if>
							</td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<aside class="paging"> <span id="spanFirst"><a
			style="color:#8B8386;">第一页</a> </span> <span id="spanPre"><a
			style="color:#8B8386;">上一页</a> </span> <span id="spanNext"><a>下一页</a>
		</span> <span id="spanLast"><a>最后一页</a> </span> 第<span id="spanPageNum"></span>页/共<span
			id="spanTotalPage"></span>页 共<span id="alltiao"></span>条</aside>
	</div>
	</section>
	
	<script id="select-temp" type="text/x-handlebars-template"> 
 	{{#each rows}}
		<tr>
			<td class="center"><a>{{#compare1 @index}}{{/compare1}}</a></td>
			<td class="center">{{scollor_pic_name}}</td>
			<td class="center">
				<span style="word-wrap:break-word;word-break:break-all;">{{scollor_pic_introduce}}</span>
			</td>
			</td>
			<td class="center">{{scollor_pic_data }}</td>
			<td class="center">
				{{#compare2 scollor_pic_display}}{{/compare2}}
			</td>
			<td class="center">
				{{#compare3 scollor_pic_fb}}{{/compare3}}
			</td>
			<td class="center">
				<a id="bianji" href="scillorpic/ToUpdatePage?id={{id}}" title="编辑" class="link_icon">&#101;</a> 
				<a id="shanchu" href="javaScript:return void(0);" onclick="deleteItem('{{id}}','{{scollor_pic_path}}')" title="删除" class="link_icon">&#100;</a>
				<a id="upordown" href="javaScript:return void(0);" onclick="upordown('{{id}}')" title="状态切换"><img src="images/qiehuan.png" style="margin-top: -7px;width: 20px;height: 20px" /> </a>
				{{#compare4 scollor_pic_fb id}}{{/compare4}}			
			</td>
		</tr>
	{{/each}}
	</script>
	
	<script type="text/javascript" src="js/fenye.js"></script>
	<script src="js/bootstrap/qiao.js"></script>
	<script type="text/javascript" src="jsp/js/util/main.js"></script>
	<script src="jsp/js/util/handlebars-v4.0.5.js"></script>
	<script type="text/javascript" src="jsp/js/scillorpic/scillorpiclist.js"></script>
</body>
</html>

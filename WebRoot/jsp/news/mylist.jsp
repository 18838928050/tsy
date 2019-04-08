<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<%@ include file="/jsp/public/news.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">我的新闻管理</h2>
			</div>
			<div id="dialog" title="Basic dialog">
				<p>
					<iframe scrolling="auto" style="width: 100%;height: 100%;" id="load" frameborder="0"></iframe>
				</p>
			</div>
			<section class="mtb"> 
				<select id="realtype-select" name="jibie" class="select">
				</select> 
				<input type="text" id="title" name="name" class="textbox textbox_225" placeholder="输入新闻标题..." /> 
				<input type="button" id="select" value="查询" class="group_btn" /> 
			</section>
			<table class="table">
				<tr>
					<th style="width: 60px">序号</th>
					<th>新闻标题</th>
					<th>相关图片</th>
					<th>作者</th>
					<th>新闻分类</th>
					<th>发表日期</th>
					<th>查看</th>
					<th>发送状态</th>
					<th>发送日期</th>
					<th>操作</th>
				</tr>
				<tbody id="tablelsw">
					<c:if test="${size==0 }">
						<td colspan="9" style="text-align: center;color: red;">暂时没有数据</td>
					</c:if>
					<c:if test="${size!=0 }">
						<c:forEach var="bean" items="${newslist}" varStatus="status">
							<tr>
								<td class="center"><a>${status.index + 1}</a></td>
								<td class="center">${bean.title }</td>
								<td class="center"><img src="${bean.thumbnail_pic_s }" width="150px" /></td>
								<td class="center">${bean.author_name }</td>
								<td class="center">${bean.realtype }</td>
								<td class="center">${bean.date }</td>
								<td class="center"><a href="javaScript:return void(0);" onclick="jiazaiUrl('${bean.url }')">查看</a></td>
								<td class="center">
									<c:if test="${bean.shifoufasong==0 }">
										<font color="red">未发送</font>
									</c:if> 
									<c:if test="${bean.shifoufasong==1 }">
										<font color="green">已发送</font>
									</c:if>
								</td>
								<td class="center">${bean.fasongdata }</td>
								<td class="center"></td>
							</tr>
						</c:forEach>
					</c:if>
				</tbody>
			</table>
			<aside class="paging">
				<span id="spanFirst">
					<a style="color:#8B8386;">第一页</a> 
				</span>
				<span id="spanPre">
					<a style="color:#8B8386;">上一页</a> 
				</span> 
				<span id="spanNext">
					<a>下一页</a>
				</span>
				<span id="spanLast">
					<a>最后一页</a>
				</span>
			        第<span id="spanPageNum"></span>
			        页/共<span id="spanTotalPage"></span>页  
			        共<span id="alltiao"></span>条
			 </aside>
		</div>
		<div class="div-H60"></div>
	</section>
	<script id="address-template" type="text/x-handlebars-template">  
 	{{#each rows}}
	<tr>
		<td class="center"><a>{{#compare1 @index}}{{/compare1}}</a></td>
		<td class="center">{{title }}</td>
		<td class="center"><img src="{{thumbnail_pic_s }}" width="150px" /></td>
		<td class="center">{{author_name }}</td>
		<td class="center">{{realtype }}</td>
		<td class="center">{{date }}</td>
		<td class="center"><a href="javaScript:return void(0);" onclick="jiazaiUrl('{{url }}')">查看</a></td>
		<td class="center">
			{{#compare shifoufasong}}{{/compare}}
		</td>
		<td class="center">{{fasongdata }}</td>
		<td class="center"></td>
	</tr> 
	{{/each}}
	</script>
	<script id="select-temp" type="text/x-handlebars-template"> 
	<option value="">全部新闻</option> 
 	{{#each rows}}
		<option value="{{realtype}}">{{realtype}}新闻</option>
	{{/each}}
	</script>
	<script type="text/javascript" src="jsp/js/util/main.js"></script>
	<script src="jsp/js/util/handlebars-v4.0.5.js"></script>
	<script type="text/javascript" src="js/fenye.js"></script>
	<script type="text/javascript" src="jsp/js/news/mylist.js"></script>
</body>
</html>

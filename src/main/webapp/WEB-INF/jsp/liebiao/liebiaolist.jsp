<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<div class="rt_content">
		<div class="page_title">
			<h2 class="fl">列表权限</h2>
			<a id="addliebiao" href="javaScript:return void(0);"
				class="fr top_rt_btn add_icon">添加菜单</a> <a id="fenpeiliebiao"
				href="javaScript:return void(0);" style="margin: 0 10px"
				class="fr top_rt_btn add_icon">分配权限</a>
			<script type="text/javascript">
				$("#addliebiao").attr("href", LIEBIAO_PAGE);
				$("#fenpeiliebiao").attr("href", USER_LIEBIAO_PAGE);
			</script>
		</div>
		<form action="liebiao/SelectFromSqlByTiaojian" method="post">
			<section class="mtb"> <select id="selectjibie" name="jibie"
				class="select">
				<option value="0">菜单级别</option>
				<option value="1">一级菜单</option>
				<option value="2">二级菜单</option>
			</select> <input type="text" name="name" class="textbox textbox_225"
				placeholder="输入关键词..." /> <input type="submit" value="查询"
				class="group_btn" /> </section>
		</form>
		<table class="table">
			<tr>
				<th style="width: 60px">序号</th>
				<th>菜单名称</th>
				<th style="width: 160px">菜单级别</th>
				<th style="width: 300px">菜单链接</th>
				<th style="width: 80px">菜单图标</th>
				<th>菜单图标属性</th>
				<th>操作</th>
			</tr>
			<tbody id="tablelsw">
				<c:if test="${size==0 }">
					<td colspan="7" style="text-align: center;color: red;">暂时没有数据</td>
				</c:if>
				<c:if test="${size!=0 }">
					<c:forEach var="bean" items="${liebiaolist}" varStatus="status">
						<tr>
							<td class="center"><a>${status.index + 1}</a>
							</td>
							<td class="center">${bean.name }</td>
							<td class="center"><c:if test="${bean.jibie==1 }">
															一级菜单
														</c:if> <c:if test="${bean.jibie==2 }">
															二级菜单
														</c:if></td>
							<c:if test="${bean.nameurl==null }">
								<td class="center">一级菜单，无链接</td>
							</c:if>
							<c:if test="${bean.nameurl=='' }">
								<td class="center">一级菜单，无链接</td>
							</c:if>
							<c:if test="${bean.nameurl!=null }">
								<c:if test="${bean.nameurl!='' }">
									<td class="center">${bean.nameurl }</td>
								</c:if>
							</c:if>
							<td class="center" style="background-color: RGB(25,169,123)"><img
								src="${bean.nameicon }" alt="暂无该图片" class="icon1"></td>
							<td class="center">${bean.nameicon }</td>
							<td class="center"><a id="bianji"
								href="liebiao/UpdateToPage?id=${bean.id}" title="编辑"
								class="link_icon">&#101;</a> <a id="shanchu"
								href="liebiao/DeleteFromSql?id=${bean.id}" title="删除"
								class="link_icon">&#100;</a></td>
						</tr>
					</c:forEach>
				</c:if>
			</tbody>
		</table>
		<aside class="paging"> <span id="spanFirst"><a
			style="color:#8B8386;">第一页</a> </span> <span id="spanPre"><a
			style="color:#8B8386;">上一页</a> </span> <span id="spanNext"><a>下一页</a>
		</span> <span id="spanLast"><a>最后一页</a> </span> 第<span id="spanPageNum"></span>页/共<span
			id="spanTotalPage"></span>页  共<span id="alltiao"></span>条</aside>
	</div>
	</section>
	<script type="text/javascript" src="js/fenye.js"></script>
</body>
</html>

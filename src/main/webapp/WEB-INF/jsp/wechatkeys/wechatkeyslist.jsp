<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<div class="rt_content">
		<div class="page_title">
			<h2 class="fl">微信关键字回复</h2>
			<a id="jiansuogengxin" href="wechatkeys/ToAddWeChatKeysPage"
				class="fr top_rt_btn add_icon">关键字添加</a>
		</div>
		<form action="wechatkeys/SelectAllByKeyClass" method="post">
			<section class="mtb"> <section class="mtb"> <select
				id="selectjibie" name="jibie" class="select">
				<option value="0">关键字类型</option>
				<option value="1">数字关键字</option>
				<option value="2">系统正文关键字</option>
				<option value="3">符号关键字</option>
				<option value="4">文字关键字</option>
			</select> <input type="text" name="name" class="textbox textbox_225"
				placeholder="输入关键词Key..." /> <input type="submit" value="查询"
				class="group_btn" /> </section>
		</form>
		<table class="table">
			<tr>
				<th style="width: 60px">序号</th>
				<th>关键字key</th>
				<th>回复内容</th>
				<th>关键字类型</th>
				<th>状态</th>
				<th>操作</th>
			</tr>
			<tbody id="tablelsw">
				<c:if test="${size==0 }">
					<td colspan="6" style="text-align: center;color: red;">暂时没有数据</td>
				</c:if>
				<c:if test="${size!=0 }">
					<c:forEach var="bean" items="${weChatKeyslist}" varStatus="status">
						<tr>
							<td class="center"><a>${status.index + 1}</a>
							</td>
							<td class="center">${bean.keyvalue }</td>
							<td class="center">
								<c:if test="${bean.keyclass==2 }"><pre>调用方法：${bean.context }()</pre></c:if>
								<c:if test="${bean.keyclass!=2 }"><pre>${bean.context }</pre></c:if>
							</td>
							<td class="center">
								<c:if test="${bean.keyclass==1 }">数字关键字</c:if>
								<c:if test="${bean.keyclass==2 }">系统正文关键字</c:if>
								<c:if test="${bean.keyclass==3 }">符号关键字</c:if>
								<c:if test="${bean.keyclass==4 }">文字关键字</c:if>
							</td>
							<td class="center">
								<c:if test="${bean.judge==0 }">
									<font style="color: red">暂停运作</font>
								</c:if> <c:if test="${bean.judge==1 }">
									<font style="color: blue">正在运作</font>
								</c:if>
							</td>
							<td class="center">
								<a id="bianji" href="wechatkeys/ToUpdateWeChatKeysPage?keyvalue=${bean.keyvalue}" title="编辑" class="link_icon">&#101;</a>
								<a id="shanchu" href="wechatkeys/DeleteWeChatKeys?id=${bean.id}" title="删除" class="link_icon">&#100;</a> 
								<c:if test="${bean.judge==0 }">
									<a id="bianji" href="wechatkeys/YunzuoWeChatKeys?keyvalue=${bean.keyvalue}&cs=0" title="启动运作">
										<img src="images/kaishiyunzuo.png" width="16px" style="margin-top: -5px" />
									</a>
								</c:if> 
								<c:if test="${bean.judge==1 }">
									<a id="bianji" href="wechatkeys/YunzuoWeChatKeys?keyvalue=${bean.keyvalue}&cs=1" title="停止运作">
										<img src="images/tingzhiyunzuo.png" width="16px" style="margin-top: -5px" />
									</a>
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
			id="spanTotalPage"></span>页  共<span id="alltiao"></span>条</aside>
	</div>
	<div style="height: 60px;width: 100%"></div>
	</section>
	<script type="text/javascript" src="js/fenye.js"></script>
</body>
</html>

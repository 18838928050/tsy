<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<div class="rt_content">
		<div class="page_title">
			<h2 class="fl">微信用户管理</h2>
			<a id="jiansuogengxin" href="wechat/Jiansuo"
				class="fr top_rt_btn add_icon">检索更新</a>
		</div>
		<form action="wechat/SelectAllByNickName" method="post">
			<section class="mtb"> <input type="text" name="nickname"
				class="textbox textbox_225" placeholder="输入昵称..." /> <input
				type="submit" value="查询" class="group_btn" /> </section>
		</form>
		<table class="table">
			<tr>
				<th style="width: 60px">序号</th>
				<th style="width: 300px">标识符</th>
				<th>昵称</th>
				<th style="width: 60px">头像</th>
				<th style="width: 200px">所在城市</th>
				<th style="width: 80px">性别</th>
				<th>关注</th>
				<th>冻结</th>
				<th>操作</th>
			</tr>
			<tbody id="tablelsw">
				<c:if test="${size==0 }">
					<td colspan="9" style="text-align: center;color: red;">暂时没有数据</td>
				</c:if>
				<c:if test="${size!=0 }">
					<c:forEach var="bean" items="${weChatlist}" varStatus="status">
						<tr>
							<td class="center"><a>${status.index + 1}</a></td>
							<td class="center">${bean.openid }</td>
							<td class="center">${bean.nickname }</td>
							<td class="center"><img src="${bean.headimgurl }" width="30px" />
							</td>
							<td class="center">${bean.country }->${bean.province }->${bean.city }</td>
							<td class="center"><c:if test="${bean.sex==1 }">男</c:if> <c:if
									test="${bean.sex==2 }">女</c:if> <c:if test="${bean.sex==0 }">未知</c:if>
							</td>
							<td class="center"><c:if test="${bean.subscribe==0 }">
									<font style="color: red;">未关注</font>
								</c:if> <c:if test="${bean.subscribe!=0 }">
									<font style="color: blue;">正在关注</font>
								</c:if></td>
							<td class="center"><c:if test="${bean.frozen==0 }">
									<font style="color: red;">冻结</font>
								</c:if> <c:if test="${bean.frozen==1 }">
									<font style="color: blue;">正常</font>
								</c:if></td>
							<td class="center"><c:if test="${bean.frozen==0 }">
									<a id="jiechu" href="wechat/DongjieWeChatUser?openid=${bean.openid}&cs=0"
										title="解除冻结"><img src="images/jiedong.png" style="width: 25px;border: 0"/>
									</a>
								</c:if> <c:if test="${bean.frozen==1 }">
									<a id="dongjie" href="wechat/DongjieWeChatUser?openid=${bean.openid}&cs=1"
										title="冻结用户"><img src="images/dongjie.png" style="width: 25px;border: 0"/>
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
	</section>
	<script type="text/javascript" src="js/fenye.js"></script>
</body>
</html>

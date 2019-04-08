<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<form action="wechatkeys/ToUpdateWeChatKeysSql" id="updateForm" method="post">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">微信关键字修改</h2>
			</div>
			<ul class="ulColumn2">
				<li>
					<span class="item_name" style="width:130px;">关键字key：</span>
					<span name="keyclass" style="color: red;">${bean.keyvalue}</span>
				</li>
				<li>
					<span class="item_name" style="width:130px;">关键字类型：</span>
					<span style="color: red;"> 
						<c:if test="${bean.keyclass==1 }">数字关键字</c:if>
						<c:if test="${bean.keyclass==2 }">系统正文关键字</c:if> 
						<c:if test="${bean.keyclass==3 }">符号关键字</c:if> 
						<c:if test="${bean.keyclass==4 }">文字关键字</c:if> 
					</span>
				</li>
				<li>
					<span class="item_name" style="width:130px;">回复内容：</span> 
					<textarea id="context" name="context" onblur="add()" placeholder="摘要信息" class="textarea" style="width:230px;height:100px;">${bean.context}</textarea>
				</li>
				<input type="hidden" name="keyclass" value="${bean.keyclass }" />
				<input type="hidden" name="id" value="${bean.id }" />
				<input type="hidden" name="judge" value="${bean.judge }" />
				<li>
					<input type="submit" class="link_btn" value="修改并提交" />
				</li>
			</ul>
		</div>
	</form>
	</section>
	<script type="text/javascript">
		$(document).ready(function() {
			$(".link_btn").click(function() {
				var context = $.trim($("#context").val());
				if (context == null || context == "") {
					alert("回复内容不能为空");
					return false;
				}
			});
		});
	</script>
</body>
</html>

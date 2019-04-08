<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<div class="rt_content">
		<div class="page_title">
			<h2 class="fl">列表修改</h2>
		</div>
		<c:if test="${bean.jibie==1 }">
			<form action="liebiao/UpdateToSql" id="addForm" method="post">
				<ul class="ulColumn2">
					<li><span class="item_name" style="width:130px;">菜单名称：</span>
						<input type="text" class="textbox textbox_225"
						value="${bean.name }" id="name" name="name" /></li>
					<li><span class="item_name" style="width:130px;">菜单图标属性class：</span>
						<input type="text" class="textbox textbox_225"
						value="${bean.nameicon }" id="nameicon" name="nameicon" /></li>
					<input type="hidden" id="jibie" name="jibie" value="${bean.jibie }"
						class="form-control">
					<input type="hidden" id="belongto" name="belongto"
						value="${bean.belongto }" class="form-control">
					<input type="hidden" id="id" name="id" value="${bean.id }"
						class="form-control">
					<input type="hidden" id="nameurl" name="nameurl"
						value="${bean.nameurl }" class="form-control">
					<li><input type="submit" class="link_btn" value="修改保存"
						onclick="javaScript:return addtosql();" /></li>
				</ul>
			</form>
		</c:if>
		<c:if test="${bean.jibie==2 }">
			<form action="liebiao/UpdateToSql" id="addForm" method="post">
				<ul class="ulColumn2">
					<li><span class="item_name" style="width:130px;">菜单名称：</span>
						<input type="text" class="textbox textbox_225"
						value="${bean.name }" id="name1" name="name" /></li>
					<li><span class="item_name" style="width:130px;">菜单图标属性class：</span>
						<input type="text" class="textbox textbox_225"
						value="${bean.nameicon }" id="nameicon1" name="nameicon" /></li>
					<li><span class="item_name" style="width:130px;">菜单链接：</span>
						<input type="text" id="nameurl1" name="nameurl"
						value="${bean.nameurl }" class="textbox textbox_225"></li>
					<input type="hidden" id="jibie1" name="jibie"
						value="${bean.jibie }" class="form-control">
					<input type="hidden" id="belongto1" name="belongto"
						value="${bean.belongto }" class="form-control">
					<input type="hidden" id="id1" name="id" value="${bean.id }"
						class="form-control">
					<li><input type="submit" class="link_btn" value="修改保存"
						onclick="javaScript:return addtosql();" /></li>
				</ul>
			</form>
		</c:if>
	</div>
	</section>
	<script type="text/javascript">
		function addtosql() {
			var name = document.getElementById("name");//菜单名称
			var nameicon = document.getElementById("nameicon");//菜单链接
			if (name.value == "" || name.value == null) {
				alert("请填写菜单名称...");
				return false;
			} else if (nameicon.value == "" || nameicon.value == null) {
				alert("请填写该菜单的图标...");
				return false;
			}
	
		}
		function addtosql1() {
			var name = document.getElementById("name1");//菜单名称
			var nameicon = document.getElementById("nameicon1");//菜单链接
			var nameurl1 = document.getElementById("nameurl1");//菜单链接
	
			if (name.value == "" || name.value == null) {
				alert("请填写菜单名称...");
				return false;
			} else if (nameicon.value == "" || nameicon.value == null) {
				alert("请填写该菜单的图标...");
				return false;
			} else if (nameurl1.value == null || nameurl1.value == "") {
				alert("请输入链接");
				return false;
			}
	
		}
	</script>
</body>
</html>

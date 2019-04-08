<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<form action="liebiao/AddToSql" id="addForm" method="post">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">列表添加</h2>
			</div>
			<ul class="ulColumn2">
				<li><span class="item_name" style="width:130px;">菜单名称：</span> <input
					type="text" class="textbox textbox_225" value="" id="name"
					name="name" />
				</li>
				<li><span class="item_name" style="width:130px;">菜单图标属性class：</span>
					<input type="text" class="textbox textbox_225" value=""
					id="nameicon" name="nameicon" />
				</li>
				<li><span class="item_name" style="width:130px;">菜单级别：</span> <select
					id="jibie" name="jibie" class="select textbox textbox_225" onchange="changer()">
						<option value="1">一级菜单</option>
						<option value="2">二级菜单</option>
				</select>
				</li>
				<li id="lianjie" style="display: none;"><span class="item_name"
					style="width:130px;">菜单链接：</span> <input type="text" id="nameurl"
					name="nameurl" class="textbox textbox_225">
				</li>
				<li id="belong" style="display: none;"><span class="item_name"
					style="width:130px;">所属一级菜单：</span> <select id="belongto"
					name="belongtoid" class="select textbox textbox_225">
						<c:if test="${size==0}">
							<option>暂无数据</option>
						</c:if>
						<c:if test="${size!=0}">
							<c:forEach var="bean" items="${liebiaolist}"
								varStatus="status">
								<option value="${bean.id }">${bean.name }</option>
							</c:forEach>
						</c:if>
				</select>
				</li>
				<li><input type="submit" class="link_btn" value="添加"
					onclick="javaScript:return addtosql();" />
				</li>
			</ul>
		</div>
	</form>
	</section>
	<script type="text/javascript">
		function changer() {
			var erjicaidan = document.getElementById("belong");
			var jibie = document.getElementById("jibie");
			var lianjie = document.getElementById("lianjie");
	
			if (jibie.value == 1) {
				erjicaidan.style.display = "none";
				lianjie.style.display = "none";
			} else if (jibie.value == 2) {
				if (erjicaidan.style.display == "none") {
					erjicaidan.style.display = "";
				}
				if (lianjie.style.display == "none") {
					lianjie.style.display = "";
				}
			}
	
		}
	
		function addtosql() {
			var name = document.getElementById("name");//菜单名称
			var nameurl = document.getElementById("nameurl");//菜单链接
			var nameicon = document.getElementById("nameicon");//菜单图标属性
			var jibie = document.getElementById("jibie");//一级菜单内容
			var belongto = document.getElementById("belongto");//二级菜单内容
	
			if (name.value == "" || name.value == null) {
				alert("请填写菜单名称...");
				return false;
			} else if (nameicon.value == "" || nameicon.value == null) {
				alert("请填写该菜单的图标...");
				return false;
			} else if (jibie.value == 2) {
				if (nameurl.value == "" || nameurl.value == null) {
					alert("请填写菜单链接...");
					return false;
				} else if (belongto.value == "暂无数据" || belongto.value == ""
						|| belongto.value == null) {
					alert("请选择该二级菜单的父菜单\n如果没有一级菜单，请先添加一级菜单");
					return false;
				}
			}
	
		}
	</script>
</body>
</html>

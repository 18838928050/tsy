<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body>
	<section class="rt_wrap content mCustomScrollbar">
	<form action="wechatkeys/ToAddWeChatKeysPage" id="addForm"
		method="post">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">关键字添加</h2>
			</div>
			<ul class="ulColumn2">
				<li><span class="item_name" style="width:130px;">关键字KEY：</span>
					<input type="text" class="textbox textbox_225" value="" placeholder="关键字KEY" id="keyvalue" name="keyvalue" onblur="add()" />
					<br /> <span id="keyvalue2" class="errorTips">关键字KEY不能为空...</span>
				</li>
				<li><span class="item_name" style="width:130px;">关键字类型：</span>
					<select id="keyclass" name="keyclass" class="select textbox textbox_225">
						<option value="0">关键字类型</option>
						<option value="1">数字关键字</option>
						<option value="2">系统正文关键字</option>
						<option value="3">符号关键字</option>
						<option value="4">文字关键字</option>
					</select> <br /> 
					<span id="keyclass2" class="errorTips">请选择关键字类型...</span>
				</li>
				<li id="neirong">
					<span class="item_name" style="width:130px;">回复内容：</span> 
						<textarea id="context" name="context" onblur="add()" placeholder="摘要信息" class="textarea" style="width:230px;height:100px;"></textarea> <br /> 
					<span id="context2" class="errorTips">回复内容不能为空...</span>
				</li>
				<li><input type="button" class="link_btn" value="添加并启用" /></li>
			</ul>
		</div>
	</form>
	</section>
	
	<script type="text/javascript">
	//关键字添加表单提交验证
	function add() {
		var keyvalue = $.trim($("#keyvalue").val());
		var context = $.trim($("#context").val());

		if (keyvalue == null || keyvalue == "") {
			document.getElementById("keyvalue2").style.display = "block";
			return false;
		} else {
			document.getElementById("keyvalue2").style.display = "none";
		}

		if (context == null || context == "") {
			document.getElementById("context2").style.display = "block";
			return false;
		} else {
			document.getElementById("context2").style.display = "none";
		}
	}
	$(document).ready(function() {
		$(".link_btn").click(function() {
			var keyvalue = $.trim($("#keyvalue").val());
			var context = $.trim($("#context").val());
			var keyclass = $.trim($("#keyclass").val());
			$.ajax({
				type : "post",
				url : WECHAT_GUANJIANZI_ADD_SQL,
				data : {
					keyvalue : keyvalue,
					context : context,
					keyclass : keyclass,
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("系统繁忙,请稍后尝试...");
					} else if (str == '0200') {
						alert("关键字KEY不能为空...");
					} else if (str == '0201') {
						alert("关键字类型不能为空...");
					} else if (str == '0202') {
						alert("请选择关键字类型...");
					} else if (str == '0203') {
						alert("请输入回复内容...");
					} else if (str == '1096') {
						alert("新的关键字添加成功");
						$("#addForm").submit();
					} else if (str == 'keyclassISWRONG') {
						alert("系统出错");
					} else if (str == '1097') {
						alert("该关键字KEY已存在");
					} else {
						alert("繁忙");
					}
				}
			});
		});
		$("#keyclass").change(function() {
			//添加所需要执行的操作代码
			var keyclass = $.trim($("#keyclass").val());
			if (keyclass == 0) {
				document.getElementById("keyclass2").style.display = "block";
			} else {
				document.getElementById("keyclass2").style.display = "none";
			}
		});
	});
</script>
</body>
</html>

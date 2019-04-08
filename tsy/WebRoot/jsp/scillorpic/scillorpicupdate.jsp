<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<body style="overflow: scroll;">
	<section class="rt_wrap content">
	<form action="scillorpic/UpdateToSql" method="post"
		enctype="multipart/form-data">
		<div class="rt_content">
			<div class="page_title">
				<h2 class="fl">通知栏公告编辑</h2>
			</div>
			<ul class="ulColumn2">
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><span
					class="item_name">文章标题</span></li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><input
					type="text" class="textbox textbox_225"
					value="${item.scollorPicName }" id="scollorPicName"
					name="scollorPicName" style="width: 60%" /></li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><span
					class="item_name"
					style="font-size: 12px;color: gray;font-weight: 2">摘要：（默认自动提取您文章的前100字显示在博客首页作为文章摘要，您也可以在这里自行编辑
						）</span></li>
				<li style="text-align: left;margin-left: 400px"><textarea
						id="scollorPicIntroduce" name="scollorPicIntroduce"
						class="textarea" style="height:100px;width:60%;">${item.scollorPicIntroduce }</textarea>
				</li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li style="text-align: left;margin-left: 400px"><span
					class="item_name">文章内容</span>
				</li>
				<li style="text-align: left;margin-left: 400px"><script
						id="editor1" type="text/plain"
						style="width:78%;height:600px;margin-top:0;"></script></li>
				<input style="display: none;" id="scollorPicContent"
					name="scollorPicContent" value="${item.scollorPicContent }" />
				<input style="display: none;" name="id" value="${item.id }" />
				<input style="display: none;" name="scollorPicPath"
					value="${item.scollorPicPath }" />
				<input style="display: none;" name="scollorPicData"
					value="${item.scollorPicData }" />
				<input style="display: none;" name="scollorPicUserid"
					value="${item.scollorPicUserid }" />
				<input style="display: none;" name="scollorPicDisplay"
					value="${item.scollorPicDisplay }" />
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
				<li><input type="submit" onclick="javaScript:return bianji()"
					class="link_btn" value="提交" />
				</li>
				<li style="text-align: left;margin-left: 400px">
					<hr style="width: 60%;color: black;height: 1px;margin-left: 0px" />
				</li>
			</ul>
		</div>
		<div style="height: 200px"></div>
	</form>
	</section>
	<script type="text/javascript">
		/**
		 *修改到sql
		 **/
		function bianji() {
			var scollorPicName = $("#scollorPicName").val();
			var content = escape(UE.getEditor('editor1').getContent());
			var chucontent = UE.getEditor('editor1').getContentTxt();
			if (scollorPicName == "" || scollorPicName == null) {
				alert("请输入标题");
				return false;
			} else if (content == "" || content == null) {
				alert("请输入文章内容");
				return false;
			} else if (chucontent.length < 150) {
				alert("文本内容不得小于150字");
				return false;
			} else {
				if ($("#scollorPicIntroduce").val() == null
						|| "" == $("#scollorPicIntroduce").val()) {
					$("#scollorPicIntroduce").val(chucontent.substring(0, 100));
				} else if ($("#scollorPicIntroduce").val() != chucontent.substring(
						0, 100)) {
					$("#scollorPicIntroduce").val(chucontent.substring(0, 100));
				}
				$("#scollorPicContent").val(content);
				return true;
			}
		}
	</script>
	<script src="js/ueditor.config.js"></script>
	<script src="js/ueditor.all.min.js">
		
	</script>
	<script type="text/javascript">
		//实例化编辑器
		//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
		var ue1 = UE.getEditor('editor1');
		window.onload = function() {
			try {
				UE.getEditor('editor1').ready(function() {
					UE.getEditor('editor1').setContent(unescape($("#scollorPicContent").val())); //赋值给UEditor
				});
			} catch (e) {

			}
		};
	</script>
</body>
</html>

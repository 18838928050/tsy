<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/phonemodel.jspf"%>
<body>
	<div style="width: 100%;height: 100%;">
		<div name="cont"
			style="width: 100%;height: 100%;overflow-y: scroll;overflow-x: hidden;"
			class="text-justify">${WeChatKnowledge.content }</div>
	</div>
	<script type="text/javascript">
		var myscroll;
		function loaded() {
			myscroll = new iScroll("tab");
		}
		window.addEventListener("DOMContentLoaded", loaded, false);
		$(window).load(function() {
			$("img").addClass("img-responsive center-block");

		});
		var cont = document.getElementsByName("cont");//获取cont
		for ( var i = 0; i < cont.length; i++) {
			cont[i].innerHTML = unescape(cont[i].innerHTML);
		}
	</script>
</body>
</html>

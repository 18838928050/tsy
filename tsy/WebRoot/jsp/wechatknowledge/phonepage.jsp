<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/phonepage.jspf"%>
<body>
	<div style="text-align: center;" class="sq-head">HTML</div>
	<i class="am-header-icon am-icon-trash"></i>
	<div class="content-list" id="outer">
		<div class="list-left" id="tab">
			<ul id="wrapper">
				<c:if test="${size==0 }">
					<li><a style="position: relative;">暂无数据</a></li>
				</c:if>
				<c:if test="${size!=0 }">
					<c:forEach var="bean" items="${WeChatKnowledge_list}"
						varStatus="status">
						<li><a
							href="WeChatKnowledge/ToPageZhanshiItem?id=${bean.id }"
							target="PhonePageitem">${bean.name }</a>
						</li>
					</c:forEach>
				</c:if>
			</ul>
		</div>
		<div class="list-right" id="content">
			<iframe frameborder="0" width="100%" height="100%" scrolling="no"
				id="PhonePageitem" name="PhonePageitem" style="overflow-x: hidden;"></iframe>
		</div>
	</div>
	<script>
		var myscroll;
		var right;
		function loaded() {
			myscroll = new iScroll("tab");
			right = new iScroll("content");
		}
		window.addEventListener("DOMContentLoaded", loaded, false);
		//tab切换
		$(function() {
			window.onload = function() {
				var $li = $('#tab li');
				var $ul = $('#content ul');
				$li.click(function() {
					var $this = $(this);
					var $t = $this.index();
					$li.removeClass();
					$this.addClass('current');
					$ul.css('display', 'none');
					$ul.eq($t).css('display', 'block');
				})
			}
		});
	</script>
</body>
</html>

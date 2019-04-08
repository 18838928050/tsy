<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ include file="/jsp/public/commons.jspf"%>
<%@ include file="/jsp/public/news.jspf"%>
<style>
a{color:#0c0c0c;}
#main {

	font-size: 14;
	width: 89%;
	align-content: center;
	margin: 0 57px 0 0;
	height: 95%;
	overflow: auto;
	position: relative;
	left: 171px;
}

#titile {
	position: relative;

left: 40%;

top: 7%;

font-style: blod;

font-size: 18px;

font-weight: bold;

font-style: red;

color: #a82020;
}

#date {
	
}

#digest {
	text-indent:27px
}

#content {
	text-indent:27px
}

#wc {
	position: relative;


left: 715px;
}

#author {
	position: relative;


left: 615px;
}

#ngs {
	position: relative;
	top: 10%;
	left: 16%;
}

#con {
	position: absolute;

top: 18%;

width: 76%;

left: 13%;
}
#hr{
height: 2px;

border: none;

    border-top-color: currentcolor;
    border-top-style: none;
    border-top-width: medium;

border-top: 1px solid #153511;

    border-top-color: rgb(21, 53, 17);
    border-top-style: solid;
    border-top-width: 1px;

width: 77%;

position: relative;

top: 74px;


}
</style>
<body background="images/02.jpg"

style=" background-repeat:no-repeat ;

background-size:100% 100%; 

background-attachment:fixed;">

	<div id="main" >
		<div id="titile"></div>
		<div id="ngs">

			<label id="date">发布时间:<a id="date2"/> </label>  <label id="author">作者:<span id="author1"/></label><label id="wc">字数:<span id="wc1"/></label>
		</div>
		<hr id="hr" />
		<div id="con">

			<div id="content"></div>
		</div>



	</div>

</body>

<script type="text/javascript">
	
	function add() {
		$.ajax({
			type : "get",
			url : "http://is.snssdk.com/api/news/feed/v51/?category=news_finance&callback=?",
			dataType : 'json',
			success : function(data) {
				console.log(data);
				var data = data.data;
				$("#titile").html(data.title);
				$("#date2").html(data.date.curr);
				$("#content").html(data.content);
				$("#wc1").html(data.wc);
				$("#author1").html(data.author);
				
				
			},
			error : function(data) {
				console.log(data);
			}
		});

	};

	add(); 
	
</script>
</html>

var id = null;
function init(){
	receiveData();
	HeightInit('index-iframe', 800,'content',"index-iframe","content");
	eventInit();
	dataInit();
}

function dataInit(){
	id = $.req("id");
	AjaxUtil.request({url:path+"/post/admTsy/TsyScillor/queryTsyScillorItemsByIdToTen",params:{id:id},type:'json',callback:function(json){
			var source = $("#select-ol").html();  
			var template = Handlebars.compile(source);
			$("#nameUl").html(template(json));
			$("#nameUl").find(".rowLi:first").click();
		}
	});
}

function eventInit(){
	$('body').on('click', '.rowLi', function(e){
		var id = $(this).attr("rowId");
		$('#index-iframe').contents().find("#rowId").val(id); 
		AjaxUtil.request({url:path+"/post/admTsy/TsyScillor/queryTsyScillorItemsContentById",params:{id:id},type:'json',callback:function(json){
				$('html,body').animate({scrollTop:0},1000);//回到顶端
				$('#index-iframe').contents().find("#contentN").html(unescape(json.bean.content));
				$('#index-iframe').contents().find("#name").html(json.bean.name);
				$('#index-iframe').contents().find("#data").html("时间 : "+json.bean.data.substring(0,10));
				$('#index-iframe').contents().find("#ydl").html("阅读量 : "+json.bean.ydl);
				$('#title').html(json.bean.name);
				HeightInit('index-iframe', 800,'content',"index-iframe","content");
			}
		});
	});
	/*查看更多*/
	$('body').on('click', '#selectAll', function(e){
		location.href = "noticeAll.html";
	});
}
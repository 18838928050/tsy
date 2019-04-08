
var rowId = null;

var type = null;

$(function(e){
	init();
});

function init(){
	receiveData();
	type = $.req("type");
	rowId = $.req("rowId");
	dataInit();
	eventInit();
}

function dataInit(){
	if(type=="2"){//文章
		AjaxUtil.request({url:path+"/post/admTsy/queryWechatKnowledgeByRowId",params:{rowId:rowId},type:'json',callback:function(json){
			$("#title").html(json.bean.name);
			$("#content").html(unescape(json.bean.content));
		}
		});
	}else if(type=="1"){//公告
		AjaxUtil.request({url:path+"/post/admTsy/TsyScillor/queryTsyScillorItemsContentById",params:{id:rowId},type:'json',callback:function(json){
			$("#content").html(unescape(json.bean.content));
			$('#title').html(json.bean.name);
		}
	});
	}
}

function eventInit(){
	$('body').on('click', '#back', function(e){
		window.history.back();
	});
}



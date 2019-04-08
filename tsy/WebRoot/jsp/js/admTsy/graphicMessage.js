
var rowId = null;

$(function(){
	init();
});

function init(){
	receiveData();
	dataInit();
	eventInit();
}

function dataInit(){
	rowId = $.req("rowId");
	AjaxUtil.request({url:path+"/post/admTsy/graphicMessage/selectByIdToOne",params:{rowId:encode64(""+rowId)},type:'json',callback:function(json){
		$("#title").html(json.bean.graphic_message_title);
		$("#content").html(json.bean.graphic_message_content);
		}
	});
}


function eventInit(){
	
}




var graphicMessageJieshaoPic = null;

var graphicMessageFatherid = "0";

$(function(){
	init();
});

function init(){
	dataInit();
	eventInit();
	initEdit();
}

function dataInit(){
	
}

function eventInit(){
	$('body').on('click', '#save', function(e){
		saveBean()
	});
	$('body').on('click', '#cancle', function(e){
		cancleBtn();
	});
	
	$('body').on('click', '#topOne', function(e){
		var clickId = $(this).attr("id");
		if(itemClick(clickId)){
			var id = $(this).attr("id");
			ItemShow(id);
		}
	});
	
	$('body').on('click', '.bot-ul>li>a[class=Grapnic-Message-item]', function(e){
		var clickId = $(this).attr("id");
		if(itemClick(clickId)){
			var id = $(this).attr("id");
			ItemShow(id);
		}
	});
	
	$('body').on('click', '#addItem', function(e){
		var clickId = $(this).attr("id");
		if(itemClick(clickId)){
			$(".bot-ul").find("li[id=addItemLi]").remove();
			if($(".bot-ul").find("li").length<=3){
				$(".bot-ul").html($(".bot-ul").html()+returnGraphicMessageItem()+returnGraphicMessageAddBtn());
			}else{
				$(".bot-ul").html($(".bot-ul").html()+returnGraphicMessageItem());
			}
		}
	});
	
	$('#imgFm').fileupload({
        dataType: 'json',
        url: path + '/post/TsyOptions/uploadGraphicMessage',
        done: function (e, data) {
        	graphicMessageJieshaoPic = data.result.bean.optionPath;
			$("#imghide").hide();
			$("#imgshow").show();
			$("#imgshowON").attr("src",path + "/" + data.result.bean.optionPath);
        },
        progressall: function (e, data) {
        },
        add: function (e, data) {
        	var type = data.files[0].name.split(".")[1];
        	if(type=="jpg"||type=="png"||type=="jpeg"){
    			data.submit();
        	}else{
        		qiao.bs.msg({msg:'文件类型不正确，请确认后选择',type:'danger'});
        	}
        }
    }); 
}

function cancleBtn(){
	location.href = "graphicmessage.html";
}

function saveBean(){
	var graphicMessageTitle = $("#titleName").val();
	var graphicMessageContent = encodeURIComponent(UE.getEditor('editor').getContent());
	var top = $("#NowConsoleId").attr("ConsoleId");
	var rowId = $("#"+top).attr("rowId");
	if(isNull(graphicMessageTitle)){
		qiao.bs.msg({msg:'请填写标题',type:'danger'});
		return;
	}
	if(isNull(graphicMessageContent)){
		qiao.bs.msg({msg:'请填写内容',type:'danger'});
		return;
	}
	if(isNull($("#imgFm").val())&&isNull(graphicMessageJieshaoPic)){
		qiao.bs.msg({msg:'请选择图文封面',type:'danger'});
		return;
	}
	if(rowId==""||rowId==""){//添加
		var params = {
				graphicMessageTitle:graphicMessageTitle,
				graphicMessageContent:graphicMessageContent,
				graphicMessageType:"2",
				graphicMessageJieshaoPic:graphicMessageJieshaoPic,
				graphicMessageFatherid:graphicMessageFatherid,
		};
		AjaxUtil.request({url:path+"/post/GraphicMessage/insertGraphicMessage",params:params,type:'json',callback:function(json){
				$("#"+top).attr("rowId",json.bean.id);
				$("#"+top+" img").attr("src",path + "/" + json.bean.graphicMessageJieshaoPic);
				$("#"+top+" font").html(json.bean.graphicMessageTitle);
				$("#"+top+" p").html(json.bean.graphicMessageTitle);
				if(graphicMessageFatherid=="0"){
					graphicMessageFatherid = json.bean.id;
				}
				qiao.bs.msg({msg:'保存成功',type:'success'});
			}
		});
	}else{//修改
		var params = {
				graphicMessageTitle:graphicMessageTitle,
				graphicMessageContent:graphicMessageContent,
				graphicMessageJieshaoPic:graphicMessageJieshaoPic,
				id:rowId
		};
		AjaxUtil.request({url:path+"/post/GraphicMessage/updateGraphicMessage",params:params,type:'json',callback:function(json){
				$("#"+top).attr("rowId",json.bean.id);
				$("#"+top+" img").attr("src",path + "/" +  json.bean.graphic_message_jieshao_pic);
				$("#"+top+" font").html(json.bean.graphic_message_title);
				$("#"+top+" p").html(json.bean.graphic_message_title);
				qiao.bs.msg({msg:'保存成功',type:'success'});
			}
		});
	}
}

function ItemShow(id){
	$("#NowConsoleId").attr("ConsoleId",id);
	var top = $("#NowConsoleId").attr("ConsoleId");
	var rowId = $("#"+top).attr("rowId");
	if(rowId!=null&&rowId!=""){
		AjaxUtil.request({url:path+"/post/GraphicMessage/queryGraphicMessageById",params:{id:rowId},type:'json',callback:function(json){
			$("#titleName").val(json.bean.graphic_message_title);
			UE.getEditor('editor').setContent(json.bean.graphic_message_content);
			graphicMessageJieshaoPic = json.bean.graphic_message_jieshao_pic;
			$("#imghide").hide();
			$("#imgshow").show();
			$("#imgshowON").attr("src",path + "/" + json.bean.graphic_message_jieshao_pic);
		}
		});
	}else{
		$("#titleName").val("");
		UE.getEditor('editor').setContent("");
		graphicMessageJieshaoPic = "";
		$("#imghide").show();
		$("#imgshow").hide();
		$("#imgshowON").attr("src","");
	}
}

function itemClick(clickId){
	var top = $("#NowConsoleId").attr("ConsoleId");
	var rowId = $("#"+top).attr("rowId");
	if(rowId==""||rowId==null){
		if(top==clickId){
			return true;
		}else{
			qiao.bs.msg({msg:'请先保存当前图文消息',type:'danger'});
			return false;
		}
	}
	return true;
}

function initEdit(){
	//实例化编辑器
	//建议使用工厂方法getEditor创建和引用编辑器实例，如果在某个闭包下引用该编辑器，直接调用UE.getEditor('editor')就能拿到相关的实例
	var ue = UE.getEditor('editor');
}

function returnGraphicMessageItem(){
	var id = "li" + $(".bot-ul").find("li").length;
	return '<li><a id="'+id+'" href="javascript:;" class="Grapnic-Message-item" rowId=""><p>标题内容</p><img src="" /></a></li>';
}

function returnGraphicMessageAddBtn(){
	return '<li id="addItemLi"><a id="addItem" href="javascript:;"><div style="width: 50%;height: 100%;margin-left: 25%;"><img alt="" src="../../../images/tianjia.png" style="width: 50%;height: 70%;margin-left: 25%;margin-top: 6%;float: left;"/></div></a></li>';
}




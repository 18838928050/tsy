
var textShow = -1;
var page = 1;

$(function(){
	init();
});

function init(){
	$("#content").hide();
	dataInit();
	eventInit();
	
}

function dataInit(){
	AjaxUtil.request({url:path+"/post/GraphicMessage/queryGraphicMessage",params:{graphicMessageJudgeSend:textShow,page:page},type:'json',callback:function(json){
			Handlebars.registerHelper("comparePic", function(v1,options){
				return path +　"/" + v1;
			});
			Handlebars.registerHelper("compare", function(v1,v2,options){
				if(v1 > v2){
		　　　　　　return options.fn(this);
		　　　　}else{
		　　　　　　return options.inverse(this);
		　　　　}
			});
			var source = $("#select-ol").html();  
			var template = Handlebars.compile(source);
			if(textShow==-1){
				if(json.rows.length==0&&page==1){
					$("#showImg").html(returnNoData());
				}else{
					$("#showImg").html(template(json)+returnLoad());
					$(".itemClick")[0].click();
				}
			}else if(textShow==0){
				if(json.rows.length==0&&page==1){
					$("#showImgNoSend").html(returnNoData());
				}else{
					$("#showImgNoSend").html(template(json)+returnLoad());
					$(".itemClick")[0].click();
				}
			}else if(textShow==1){
				if(json.rows.length==0&&page==1){
					$("#showImgSend").html(returnNoData());
				}else{
					$("#showImgSend").html(template(json)+returnLoad());
					$(".itemClick")[0].click();
				}
			}else{
				
			}
		}
	});
}

function eventInit(){
	$('body').on('click', '.item', function(e){
		textShow = $(this).attr("textShow");
		page = 1;
		dataInit();
	});
	
	$('body').on('click', '#loadMore', function(e){
		page++;
		AjaxUtil.request({url:path+"/post/GraphicMessage/queryGraphicMessage",params:{graphicMessageJudgeSend:textShow,page:page},type:'json',callback:function(json){
				Handlebars.registerHelper("comparePic", function(v1,options){
					return path +　"/" + v1;
				});
				Handlebars.registerHelper("compare", function(v1,v2,options){
					if(v1 > v2){
			　　　　　　return options.fn(this);
			　　　　}else{
			　　　　　　return options.inverse(this);
			　　　　}
				});
				var source = $("#select-ol").html();  
				var template = Handlebars.compile(source);
				if(textShow==-1){
					$("div[id=loadMore]").remove();
					if(json.rows.length==0){
						qiao.bs.msg({msg:'暂无更多数据',type:'danger'});
					}else{
						$("#showImg").html($("#showImg").html()+template(json)+returnLoad());
					}
				}else if(textShow==0){
					$("div[id=loadMore]").remove();
					if(json.rows.length==0){
						qiao.bs.msg({msg:'暂无更多数据',type:'danger'});
					}else{
						$("#showImgNoSend").html($("#showImgNoSend").html()+template(json)+returnLoad());
					}
				}else if(textShow==1){
					$("div[id=loadMore]").remove();
					if(json.rows.length==0){
						qiao.bs.msg({msg:'暂无更多数据',type:'danger'});
					}else{
						$("#showImgSend").html($("#showImgSend").html()+template(json)+returnLoad());
					}
				}else{
					
				}
			}
		});
	});
	
	$('body').on('click', '.itemClick', function(e){
		var rowId = $(this).attr("rowId");
		$("#content").show();
		AjaxUtil.request({url:path+"/post/GraphicMessage/queryGraphicMessageById",params:{id:rowId},type:'json',callback:function(json){
			$("#messageTitle").html(json.bean.graphic_message_title);
			$("#messageContent").html(json.bean.graphic_message_content);
			$("#messageUrl").html(path+"/jsp/html/admTsy/graphicMessage.html?rowId="+json.bean.id);
			$("#edit").attr("rowId",json.bean.id);
			$("#delete").attr("rowId",json.bean.id);
			if(json.bean.graphic_message_fatherid!="0"){
				$("#delete").hide();
			}else{
				$("#send").attr("rowId",json.bean.id);
				$("#delete").show();
			}
			}
		});
	});
	
	$('body').on('click', '#edit', function(e){
		var rowId = $(this).attr("rowId");
		location.href = "editgraphicmessage.html?id="+rowId;
	});
	
	$('body').on('click', '#delete', function(e){
		var rowId = $(this).attr("rowId");
		AjaxUtil.request({url:path+"/post/GraphicMessage/deleteGraphicMessageById",params:{id:rowId},type:'json',callback:function(json){
				if(json.returnCode=="0"){
					qiao.bs.msg({msg:'删除成功',type:'success'});
					page = 1;
					dataInit();
				}
			}
		});
	});
	
	$('body').on('click', '#send', function(e){
		AjaxUtil.request({url:path+"/post/GraphicMessage/sendMessage",params:{rowId:$(this).attr("rowId")},type:'json',callback:function(json){
				if(json.returnCode=="0"){
					qiao.bs.msg({msg:'发送成功，成功发送'+json.bean.sendNum+'人,共'+json.bean.allUser+'人',type:'success'});
					page = 1;
					dataInit();
				}else{
					qiao.bs.msg({msg:'数据操作失败',type:'danger'});
				}
			}
		});
	});
	
	$('body').on('click', '#addGraphicMessage', function(e){
		location.href = "addgraphicmessage.html";
	});
}

function returnNoData(){
	return '<img src="../../../images/noData.png" style="margin-left: 230px;"/><br/><font style="margin-left: 325px;color: cadetblue;font-family: inherit;">暂无数据</font>';
}

function returnLoad(){
	return '<div class="ui basic button update" id="loadMore" style="text-align:center;margin-left:210px;width: 35%;">加载更多</div>';
}


$(function(){
	init();
});
function init(){
	createDialog();
	eventInit();
	dataInit();
}

function createDialog(){
	$("#dialog").dialog({
		modal : true,
		autoOpen : false,
		width : 1000,
		closed : false,
		cache : false,
		height : 600,
		title : "内容",
		show : {
			effect : "blind",
			duration : 1000
		},
		hide : {
			effect : "blind",
			duration : 500
		},
		buttons : {
			"关闭" : function() {
				$("#dialog").dialog("close");
			}
		},
	});
}

function eventInit(){
	$("#select").on('click',select);
}

function dataInit(){
	AjaxUtil.request({url:path+"/post/news/queryRealType",params:{},type:'json',callback:function(json){
		var source = $("#select-temp").html();  
	    var template = Handlebars.compile(source);
	    $("#realtype-select").html(template(json)); 
		}
	});
}

function select(){
	var params = {
			title:$("#title").val(),
			realtype:$("#realtype-select").val()
	};
	AjaxUtil.request({url:path+"/post/news/queryMyList",params:params,type:'json',callback:function(json){
			if(json.total==0){
				$("#tablelsw").html('<td colspan="9" style="text-align: center;color: red;">暂时没有数据</td>');
			}else{
				Handlebars.registerHelper("compare", function(v1,options){
					if(v1==0){
						return "未发送";
					}else{
						return "已发送";
					}
				});
				Handlebars.registerHelper("compare1", function(v1,options){
					return v1+1;
				});
				var source = $("#address-template").html();  
			    var template = Handlebars.compile(source);
			    $("#tablelsw").html(template(json));;  
			    hide();
			}
		}
	});
}

function jiazaiUrl(url) {
	$("#dialog").dialog("open");
	$("#load").attr("src", url);
}
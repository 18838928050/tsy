
var nowArr = ["241","242","243","210","6","4","5","7","180","244","185","186","187"];

var content = null;

var page = 1;

function init(){
	receiveData();
	content = $.req("searchContent");
	$("#searchContent").val(content);
	dataInit();
	eventInit();
}

function dataInit(){
	AjaxUtil.request({url:path+"/post/admTsy/wechatSearch",params:{searchContent:content,page:page},type:'json',callback:function(json){
		if(page!=1){
			$("#rowIdUrl").find("#moreLoad").remove();
			if(json.rows.length==0){
				qiao.bs.msg({msg:"暂无更多数据...",type:'success'});
				return ;
			}
		}
	    Handlebars.registerHelper("compareType", function(v1,options){
			if(v1=="vedio"){
				return "视频";
			}else if(v1=="article"){
				return "文章";
			}
		});
	    Handlebars.registerHelper("compareTitle", function(v1,options){
	    	var reg = new RegExp("(" + content.toUpperCase() + ")", "g");
			return v1.toUpperCase().replace(reg, "<font color=red><b>$1</b></font>");
		});
	    var source = $("#search-tab").html();  
	    var template = Handlebars.compile(source);
	    $("#rowIdUrl").html($("#rowIdUrl").html()+template(json));

	}
	});
}

function eventInit(){
	
	$('body').on('click', '#loadMoreZl', function(e){
		page++;
		dataInit();
	});

	$('body').on('click', '#rowIdUrl>li>div>div>input', function(e){
		var rowId = $(this).attr("rowId");
		var fatherId = $(this).attr("searchFatherId");
		var searchToType = $(this).attr("searchToType");
		if(searchToType=="vedio"){
			location.href = "vedio.html?rowId="+rowId;
		}else{
			if(nowArr.indexOf(fatherId)!=-1){
				location.href = "content.html?type="+fatherId+"&rowId="+rowId;
			}else{
				qiao.bs.msg({msg:"该内容当前处于未开放模式,暂时无法查看,请等待官方通知...",type:'success'});
			}
		}
		
	});

}



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
	AjaxUtil.request({url:path+"/post/admTsy/wechatNetSearch",params:{searchContent:content,page:page},type:'json',callback:function(json){
		if(page!=1){
			$("#rowIdUrl").find("#moreLoad").remove();
			if(json.rows.length==0){
				qiao.bs.msg({msg:"暂无更多数据...",type:'success'});
				return ;
			}
		}
	    Handlebars.registerHelper("compareTitle", function(v1,options){
//	    	var reg = new RegExp("(" + content.toUpperCase() + ")", "g");
//			return v1.toUpperCase().replace(reg, "<font color=red><b>$1</b></font>");
	    	return v1;
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

	$('body').on('click', '.selectById', function(e){
		var rowId = $(this).attr("rowHTTP");
		window.open(rowId);
	});
	
	$('body').on('click', '.selectByHis', function(e){
		var rowId = $(this).attr("rowHTTP");
		window.open(rowId);
	});

}


function init(){
	dataInit();
	eventInit();
}

function dataInit(){
	AjaxUtil.request({url:path+"/post/admTsy/TsyScillor/queryTsyScillorItemsAll",params:{},type:'json',callback:function(json){
			Handlebars.registerHelper("compare2", function(v1,options){
				return v1.substring(0,10);
			});
			var source = $("#select-ol").html();  
		    var template = Handlebars.compile(source);
		    $("#content").html(template(json)); 
		}
	});
}

function eventInit(){
	$('body').on('click', '.nameId', function(e){
		var id = $(this).attr("sc_Id");
		location.href = "noticeItem.html?id="+id;
	});
}
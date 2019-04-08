
var id = null;

$(function(e){
	init();
});

function init(){
	dataInit();
}

function dataInit(){
	AjaxUtil.request({url:path+"/post/admTsy/TsyScillor/list",params:{},type:'json',callback:function(json){
		Handlebars.registerHelper("compare2", function(v1,options){
			return path+"/"+v1;
		});
		var source = $("#select-temp").html();  
	    var template = Handlebars.compile(source);
	    $("#scollorId").html(template(json));
	    Handlebars.registerHelper("compareOn", function(v1,options){
			v1++;
			if(v1==1){
				return "on";
			}else{
				return "";
			}
		});
	    Handlebars.registerHelper("compareIndex", function(v1,options){
			return v1++;
		});
		var source = $("#select-temp-ol").html();  
	    var template = Handlebars.compile(source);
	    $("#scroll_position").html(template(json));
	    scolor();
	    loadMaxTen();
		}
	});
}

function loadMaxTen(){
	AjaxUtil.request({url:path+"/post/admTsy/queryMaxIntegralTopTen",params:{},type:'json',callback:function(json){
	    var source = $("#select-Top").html();  
	    var template = Handlebars.compile(source);
	    $("#topTen").html(template(json));
	    loadNewFive();
		}
	});
}

function loadNewFive(){
	AjaxUtil.request({url:path+"/post/admTsy/queryWechatKnowledgeNewFifth",params:{},type:'json',callback:function(json){
	    var source = $("#select-New").html();  
	    var template = Handlebars.compile(source);
	    $("#newFive").html(template(json));
	    eventInit();
		}
	});
}

function eventInit(){
	
	$('body').on('click', '#topTen>li>a', function(e){
		var rowId = $(this).attr("rowId");
		location.href = "content.html?type=2&rowId="+rowId;
	});
	
	$('body').on('click', '#newFive>li>p>a', function(e){
		var rowId = $(this).attr("rowId");
		location.href = "content.html?type=2&rowId="+rowId;
	});
	
	$('body').on('click', '#scollorId>li>a', function(e){
		var rowId = $(this).attr("rowId");
		location.href = "content.html?type=1&rowId="+rowId;
	});
}

function scolor(){
	var slider = Swipe(document.getElementById('scroll_img'), {
		  auto: 3000,
		  continuous: true,
		  callback: function(pos) {
		    var i = bullets.length;
		    while (i--) {
		      bullets[i].className = ' ';
		    }
		    bullets[pos].className = 'on';
		  }
	});
	var bullets = document.getElementById('scroll_position').getElementsByTagName('li');
	$('.scroll_position_bg').css({
	   width:$('#scroll_position').width()
	});
}



var jPlayerPlay;

var rowId = null;

var page = 1;

function init(){
	dataInit();
}

function dataInit(){
	loadVedioTopFifteen();
}

function loadVedioTopFifteen(){
	AjaxUtil.request({url:path+"/post/admTsy/queryVedio",params:{page:page},type:'json',callback:function(json){
		    Handlebars.registerHelper("vedioPic", function(v1,options){
		    	var picPath = path + "/" + v1;
		    	if(isHasImg(picPath)){
		    		return picPath;
		    	}
				return path + "/images/noshow.png";
			});
		    Handlebars.registerHelper("vedioUploadUser", function(v1,options){
				return v1.substring(0,3)+"****"+v1.substring(7,11);
			});
		    Handlebars.registerHelper("vadioUploadTime", function(v1,options){
				return datetimeFormat_1(v1);
			});
		    var source = $("#vedio-content").html();  
		    var template = Handlebars.compile(source);
		    $("#content").html(template(json));
	    eventInit();
		}
	});
}

function eventInit(){
	
	$('body').on('click', 'dt>p>a', function(e){
		var rowId = $(this).attr("vedioId");
		location.href = "vedio.html?rowId="+rowId;
	});
	
	$('body').on('click', '#loadmoreVedio', function(e){
		loadmoreVedio();
	});
	
}

function loadmoreVedio(){
	page++;
	AjaxUtil.request({url:path+"/post/admTsy/queryVedio",params:{page:page},type:'json',callback:function(json){
		if(json.rows.length==0){
			$("#loadmoreVedio").hide();
			qiao.bs.msg({msg:"暂无更多数据...",type:'success'});
			return false;
		}else{
			Handlebars.registerHelper("vedioPic", function(v1,options){
				var picPath = path + "/" + v1;
				if(isHasImg(picPath)){
					return picPath;
				}
				return path + "/images/noshow.png";
			});
			Handlebars.registerHelper("vedioUploadUser", function(v1,options){
				return v1.substring(0,3)+"****"+v1.substring(7,11);
			});
			Handlebars.registerHelper("vadioUploadTime", function(v1,options){
				return datetimeFormat_1(v1);
			});
			var source = $("#vedio-content").html();  
			var template = Handlebars.compile(source);
			$("#content").html( $("#content").html()+template(json));
		}
	}
	});
}


function isHasImg(pathImg){
    var ImgObj=new Image();
    ImgObj.src= pathImg;
     if(ImgObj.fileSize > 0 || (ImgObj.width > 0 && ImgObj.height > 0))
     {
       return true;
     } else {
       return false;
    }
}

function datetimeFormat_1(longTypeDate){  
    var datetimeType = "";  
    var date = new Date();  
    date.setTime(longTypeDate);  
    datetimeType+= date.getFullYear();   //年  
    datetimeType+= "-" + getMonth(date); //月   
    datetimeType += "-" + getDay(date);   //日  
    datetimeType+= "&nbsp;&nbsp;" + getHours(date);   //时  
    datetimeType+= ":" + getMinutes(date);      //分
    datetimeType+= ":" + getSeconds(date);      //分
    return datetimeType;
} 
//返回 01-12 的月份值   
function getMonth(date){  
    var month = "";  
    month = date.getMonth() + 1; //getMonth()得到的月份是0-11  
    if(month<10){  
        month = "0" + month;  
    }  
    return month;  
}  
//返回01-30的日期  
function getDay(date){  
    var day = "";  
    day = date.getDate();  
    if(day<10){  
        day = "0" + day;  
    }  
    return day;  
}
//返回小时
function getHours(date){
    var hours = "";
    hours = date.getHours();
    if(hours<10){  
        hours = "0" + hours;  
    }  
    return hours;  
}
//返回分
function getMinutes(date){
    var minute = "";
    minute = date.getMinutes();
    if(minute<10){  
        minute = "0" + minute;  
    }  
    return minute;  
}
//返回秒
function getSeconds(date){
    var second = "";
    second = date.getSeconds();
    if(second<10){  
        second = "0" + second;  
    }  
    return second;  
}

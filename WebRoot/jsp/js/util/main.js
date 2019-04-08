var path = getRootPath();

//base64加密开始  
var keyStr = "ABCDEFGHIJKLMNOP" + "QRSTUVWXYZabcdef" + "ghijklmnopqrstuv"  
        + "wxyz0123456789+/" + "=";  

function encode64(input) {  
    var output = "";  
    var chr1, chr2, chr3 = "";  
    var enc1, enc2, enc3, enc4 = "";  
    var i = 0;  
    do {  
        chr1 = input.charCodeAt(i++);  
        chr2 = input.charCodeAt(i++);  
        chr3 = input.charCodeAt(i++);  
        enc1 = chr1 >> 2;  
        enc2 = ((chr1 & 3) << 4) | (chr2 >> 4);  
        enc3 = ((chr2 & 15) << 2) | (chr3 >> 6);  
        enc4 = chr3 & 63;  
        if (isNaN(chr2)) {  
            enc3 = enc4 = 64;  
        } else if (isNaN(chr3)) {  
            enc4 = 64;  
        }  
        output = output + keyStr.charAt(enc1) + keyStr.charAt(enc2)  
                + keyStr.charAt(enc3) + keyStr.charAt(enc4);  
        chr1 = chr2 = chr3 = "";  
        enc1 = enc2 = enc3 = enc4 = "";  
    } while (i < input.length);  

    return output;  
}  
// base64加密结束  

function getRootPath(){  
    var curWwwPath=window.document.location.href;  
    var pathName=window.document.location.pathname;  
    var pos=curWwwPath.indexOf(pathName);  
    var localhostPaht=curWwwPath.substring(0,pos);  
    var projectName=pathName.substring(0,pathName.substr(1).indexOf('/')+1);  
    return(localhostPaht+projectName);//http://127.0.0.1:8080/tsy
}  

//根据内容获取数组下表
Array.prototype.indexOf = function(val) {
	for (var i = 0; i < this.length; i++) {
		if (this[i] == val) return i;
	}
	return -1;
};
//数组转化成字符串,中间用val这个对象的内容隔开
Array.prototype.toString = function(val){
	var str = "";
	for (var i = 0; i < this.length; i++) {
		if (this[i] != null && this[i] != "") 
			str = str + this[i] + val;
	}
	return str;
}
//根据内容移除数组指定项
Array.prototype.remove = function(val) {
	var index = this.indexOf(val);
	if (index > -1) {
		this.splice(index, 1);
	}
};
//打开新的界面
var creatNewPage = function(href,params){
	if($.isEmptyObject(params)){//为空
		window.location.href = encodeURI(href);
	}else{//不为空
		var entrys = [];  
        for(var i in params) {  
            entrys.push(i + '=' + params[i]);  
        }
		window.location.href = encodeURI(href + '?' + entrys.join('&'));
	}
}
//接收参数
var receiveData = function(e){
	var url = decodeURI(window.location.href);  
	var index = url.indexOf('?');  
	if (index < 0) {  
	    return;  
	}  
	var parameters = {};  
	var entrys = url.substring(++index, url.length).split('&');  
	for (var i = 0, len = entrys.length; i < len; i++) {  
	    var entry = entrys[i].split('=');  
	    parameters[entry[0]] = entry[1];  
	}  
	$.data(document, 'parameters', parameters);
}
//获取值
$.req = function(key) {  
    var parameters = $.data(document, 'parameters');  
    return parameters === undefined ? undefined : parameters[key];  
} 

//判断对象是否为空
function isEmptyObject(e) {  
    var t;  
    for (t in e)  
        return !1;  
    return !0  
}  

//判断是否是数字
function isNumber(str){
	if(!isNaN(str)){
		return true;
	}else{
		return false;
	}
}

//判断是否为链接
function isURL(str_url) {
	if(str_url==""||str_url==null){
		return true; 
	}else{
		var strRegex = '^((https|http|ftp|rtsp|mms)?://)'
			+ '?(([0-9a-z_!~*\'().&=+$%-]+: )?[0-9a-z_!~*\'().&=+$%-]+@)?' //ftp的user@ 
			+ '(([0-9]{1,3}.){3}[0-9]{1,3}' // IP形式的URL- 199.194.52.184 
			+ '|' // 允许IP和DOMAIN（域名） 
			+ '([0-9a-z_!~*\'()-]+.)*' // 域名- www. 
			+ '([0-9a-z][0-9a-z-]{0,61})?[0-9a-z].' // 二级域名 
			+ '[a-z]{2,6})' // first level domain- .com or .museum 
			+ '(:[0-9]{1,4})?' // 端口- :80 
			+ '((/?)|' // a slash isn't required if there is no file name 
			+ '(/[0-9a-z_!~*\'().;?:@&=+$,%#-]+)+/?)$'; 
		var re=new RegExp(strRegex); 
		//re.test() 
		if (re.test(str_url)) { 
			return true; 
		} else { 
			return false; 
		} 
	}
}

//判断内容是否为空
function isNull(str){
	if(str==null||str==""||str==''){
		return true;
	}else{
		return false;
	}
}

//ajax请求
var AjaxUtil = {
	// 基础选项  
	options: {
		method: "post", // 默认提交的方法,get post  
		url: "", // 请求的路径 required  
		params: {}, // 请求的参数  
		type: 'text', // 返回的内容的类型,text,xml,json  
		callback: function() {} // 回调函数 required  
	},

	// 创建XMLHttpRequest对象  
	createRequest: function() {
		var xmlhttp;
		try {
			xmlhttp = new ActiveXObject("Msxml2.XMLHTTP"); // IE6以上版本  
		} catch(e) {
			try {
				xmlhttp = new ActiveXObject("Microsoft.XMLHTTP"); // IE6以下版本  
			} catch(e) {
				try {
					xmlhttp = new XMLHttpRequest();
					if(xmlhttp.overrideMimeType) {
						xmlhttp.overrideMimeType("text/xml");
					}
				} catch(e) {
					alert("您的浏览器不支持Ajax");
				}
			}
		}
		return xmlhttp;
	},
	// 设置基础选项  
	setOptions: function(newOptions) {
		for(var pro in newOptions) {
			this.options[pro] = newOptions[pro];
		}
	},
	// 格式化请求参数  
	formateParameters: function() {
		var paramsArray = [];
		var params = this.options.params;
		for(var pro in params) {
			var paramValue = params[pro];
			/*if(this.options.method.toUpperCase() === "GET")  
			{  
			    paramValue = encodeURIComponent(params[pro]);  
			}*/
			paramsArray.push(pro + "=" + paramValue);
		}
		return paramsArray.join("&");
	},

	// 状态改变的处理  
	readystatechange: function(xmlhttp) {
		// 获取返回值  
		var returnValue;
		if(xmlhttp.readyState == 4 && (xmlhttp.status == 200 || xmlhttp.status == 0)) {
			switch(this.options.type) {
				case "xml":
					returnValue = xmlhttp.responseXML;
					break;
				case "json":
					var jsonText = xmlhttp.responseText;
					if(jsonText) {
						returnValue = eval("(" + jsonText + ")");
					}
					break;
				default:
					returnValue = xmlhttp.responseText;
					break;
			}
			if(returnValue) {
				this.options.callback.call(this, returnValue);
			} else {
				this.options.callback.call(this);
			}
		}
	},

	// 发送Ajax请求  
	request: function(options) {
		var ajaxObj = this;
		// 设置参数  
		ajaxObj.setOptions.call(ajaxObj, options);
		// 创建XMLHttpRequest对象  
		var xmlhttp = ajaxObj.createRequest.call(ajaxObj);
		// 设置回调函数  
		xmlhttp.onreadystatechange = function() {
			ajaxObj.readystatechange.call(ajaxObj, xmlhttp);
		};
		// 格式化参数  
		var formateParams = ajaxObj.formateParameters.call(ajaxObj);
		// 请求的方式  
		var method = ajaxObj.options.method;
		var url = ajaxObj.options.url;
		if("GET" === method.toUpperCase()) {
			url += "?" + formateParams;
		}
		// 建立连接  
		xmlhttp.open(method, url, true);
		if("GET" === method.toUpperCase()) {
			xmlhttp.send(null);
		} else if("POST" === method.toUpperCase()) {
			// 如果是POST提交，设置请求头信息  
			xmlhttp.setRequestHeader("Content-Type", "application/x-www-form-urlencoded");
			xmlhttp.send(formateParams);
		}
	}
};

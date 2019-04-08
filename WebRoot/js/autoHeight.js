var browserVersion = window.navigator.userAgent.toUpperCase();
var isOpera = browserVersion.indexOf("OPERA") > -1 ? true : false;
var isFireFox = browserVersion.indexOf("FIREFOX") > -1 ? true : false;
var isChrome = browserVersion.indexOf("CHROME") > -1 ? true : false;
var isSafari = browserVersion.indexOf("SAFARI") > -1 ? true : false;
var isIE = (!!window.ActiveXObject || "ActiveXObject" in window);
var isIE9More = (!-[1, ] == false);

function reinitIframe(iframeId, minHeight, divid) {
	try {
		var iframe = document.getElementById(iframeId);
		var divheight = document.getElementById(divid);
		var bHeight = 0;
		if(isChrome == false && isSafari == false)
			bHeight = iframe.contentWindow.document.body.scrollHeight;

		var dHeight = 0;
		if(isFireFox == true)
			dHeight = iframe.contentWindow.document.documentElement.offsetHeight + 2;
		else if(isIE == false && isOpera == false)
			dHeight = iframe.contentWindow.document.documentElement.scrollHeight;
		else //ie[6-最高]、OPERA
			bHeight += 3;

		var height = Math.max(bHeight, dHeight);
		if(height < minHeight) height = minHeight;
		iframe.style.height = height + "px";
		divheight.style.height = (height + 50) + "px";
	} catch(ex) {}
}

function startInit(iframeId, minHeight, divid) {
//	reinitIframe(iframeId, minHeight, divid);
	window.setInterval("reinitIframe('" + iframeId + "'," + minHeight + ",'" + divid + "')", 100);
}

function HeightInit(iframeId, minHeight, divid,iframeId,childDomId){
	reinitIframeHeight(iframeId, minHeight, divid,iframeId,childDomId);
}

function reinitIframeHeight(iframeId, minHeight, divid,iframeId,childDomId) {
	try {
		var iframe = document.getElementById(iframeId);
		var divheight = document.getElementById(divid);
		var height = $('#'+iframeId).contents().find("#"+childDomId).height();
		iframe.style.height = (height + 100) + "px";
		divheight.style.height = (height + 100) + "px";
	} catch(ex) {}
}
$(function() {
	$(".btn3").each(function() {
		ps();
	});
	//弹出垂直菜单
	addEvent();
});

/**
 * 按钮监听事件
 */
function addEvent() {
	$(".menu").click(function() {
		if ($(this).hasClass("cura")) {
			$(this).children(".new-sub").hide(); //当前菜单下的二级菜单隐藏
			$(".menu").removeClass("cura"); //同一级的菜单项
		} else {
			$(".menu").removeClass("cura"); //移除所有的样式
			$(this).addClass("cura"); //给当前菜单添加特定样式
			$(".menu").children(".new-sub").slideUp("fast"); //隐藏所有的二级菜单
			$(this).children(".new-sub").slideDown("fast"); //展示当前的二级菜单
		}
	});
//	$('body').on('click', '.xiang-q', function(e){
//		var id = $(this).attr("Sc_id").replace("scId","");
//		location.href = "noticeItem.html?id="+id;
//	});
	dataInit();
}

function dataInit(){
	AjaxUtil.request({url:path+"/post/GraphicMessage/queryGraphicMessageByFirst",params:{},type:'json',callback:function(json){
		var source = $("#select-Top").html();  
	    var template = Handlebars.compile(source);
	    $("#graphicMessage").html(template(json));
		}
	});
}

/**
 * 判断是否需要添加一级按钮
 */
function ps() {
	var y = $(".menu").length;
	if (y == 1) {
		for ( var i = 0; i < y; i++) {
			var aElement = $(".btn3 .menu")[i];
			aElement.style.width = "50%";
			//alert(aElement.innerHTML);
		}
		addLi('50');
	} else if (y == 2) {
		for ( var i = 0; i < y; i++) {
			var aElement = $(".btn3 .menu")[i];
			aElement.style.width = "33.3%";
			//alert(aElement.innerHTML);
		}
		addLi('33.3');
	} else if (y == 3) {
		for ( var i = 0; i < y; i++) {
			var aElement = $(".btn3 .menu")[i];
			aElement.style.width = "33.3%";
			//alert(aElement.innerHTML);
		}
	} else {
		addLi('100');
	}
}

//判断是否需要添加二级加号按钮菜单
function ps1(uliditem, ipd, selectid) {
	//alert(uliditem+"##"+ipd+"##"+selectid);
	var len = document.getElementById(uliditem).getElementsByTagName("li").length;
	var len1 = document.getElementById(uliditem).getElementsByTagName("img").length;
	if (len < 5 && len1 == 0) {
		addLi2(uliditem, ipd, selectid);
	}
}

/**
 * 添加二级菜单按钮
 */
function addLi2(id1, ipd, selectid) {
	var ulid = document.getElementById(id1);
	var str = "<li id='" + id1 + "li'><a onclick='adderjicaidan(\"" + id1 + "\",\"" + ipd + "\",\"" + selectid + "\")'><img src='images/tianjia.png' width='30px' style='margin-top:5px'/></a></li>";
	ulid.innerHTML = str + ulid.innerHTML;
}

function adderjicaidan(id1, ipd, selectid) {
	var len = document.getElementById(id1).getElementsByTagName("li").length - 1;
	var ulid = document.getElementById(id1);
	var menuname = "子菜单" + ipd + "" + len;
	$.ajax({
		type : "post",
		url : "menu/AddMenu",
		data : {
			menuname : menuname,
			menulevel : "2",
			menubolong : selectid
		},
		timeout : 2000,
		dataType : 'json',
		success : function(data) {
			var str = data.message;
			if (str == '0404') {
				alert("请求失败");
			} else if (str == '1096') {
				var selid = data.selectid;
				var str1 = "<li><a onclick='Select(\"" + selid + "\",\"" + 2 + "\",\"" + 0 + "\",\"" + menuname + "\");'>子菜单" + ipd + "" + len + "</a></li>";
				$("#" + id1 + "li").remove();
				ulid.innerHTML = ulid.innerHTML + str1;
				ps1(id1, ipd, selectid);
				alert("添加成功");
				if (len == 0) {
					location.reload();
				}
			}
		},
		error : function(data) {
			alert("请求服务器失败");
		}
	});
}

/**
 * 添加一级菜单按钮
 */
function addLi(wid) {
	var mbtn3 = document.getElementById("btn3");
	var str = "<div id='cli' onclick='addyijicaidan()' class='menu cli' style='width:"+ wid+ "%;'><div class='bt-name'><img src='images/tianjia.png' width='30px' style='margin-top:5px'/></div></div>";
	mbtn3.innerHTML = mbtn3.innerHTML + str;
}

/**
 * 添加新的一级菜单
 */
function addyijicaidan() {
	var y = $(".menu").length;
	var mbtn3 = document.getElementById("btn3");
	var cli = document.getElementById("cli");
	var menuname = "新建菜单" + y;
	var uid = "ul" + y;
	$.ajax({
		type : "post",
		url : "menu/AddMenu",
		data : {
			menuname : menuname,
			menulevel : "1",
			menubolong : "0"
		},
		timeout : 2000,
		dataType : 'json',
		success : function(data) {
			var str = data.message;
			if (str == '0404') {
				alert("请求失败");
			} else if (str == '1096') {
				var selid = data.selectid;
				var str1 = "<div class='menu' "+ "onclick='ps1(\""+ uid+ "\",\""+ y+ "\",\""+ selid+ "\");'><div class='bt-name'><a id='"+ selid+ "' "+ "onclick='Select(\""+ selid+ "\",\""+ 1+ "\",\""+ 0+ "\",\""+ menuname+ "\");'>新建菜单"+ y+ "</a></div><div class='sanjiao '></div><div class='new-sub'><ul id='ul" + y + "'></ul><div class='tiggle'></div><div class='innertiggle'></div></div></div>";
				$("#cli").remove();
				mbtn3.innerHTML = mbtn3.innerHTML + str1;
				ps1(uid, (y - 1), selid);
				ps();
				//弹出垂直菜单
				addEvent();
				alert("添加成功");
			}
		},
		error : function(data) {
			alert("请求服务器失败");
		}
	});
}

/**
 * 查询单个信息
 * selectid 被选中按钮id
 * menulevel 被选中按钮级别
 * hadchild 被选中按钮是否有子按钮
 */
function Select(selectid, menulevel, hadchild, menuname) {
	var rebackint = -1;
	var rebackcontext = "";
	if (menulevel == 1) {
		if (hadchild == 0) {
			$.ajax({
				type : "post",
				url : "menu/SelectMenu",
				data : {
					id : selectid
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("服务器请求异常");
					} else if (str == '0506') {
						//没有子菜单
						$("#menuname").val(menuname);
						$("#selectid").val(selectid);
						$("#caidanneirong").show();
						$("#xitong").show();
						$("#mingcheng").show();
						$("#caidanneirong").show();
						$("#wanchenganniu").show();
						document.getElementById("key").innerHTML = data.menukey;
						rebackint = data.rebackint;
						rebackcontext = data.rebackcontext;
						xianshi(rebackint, rebackcontext);
					}
				},
				error : function(data) {
					alert("请求服务器失败");
				}
			});
		} else {
			//有子菜单
			$("#menuname").val(menuname);
			$("#selectid").val(selectid);
			$("#caidanneirong").hide();
			$("#xitong").hide();
			$("#mingcheng").show();
			$("#wanchenganniu").show();
			$("#caidanneirong").hide();
			document.getElementById("key").innerHTML = "";
		}
	} else if (menulevel == 2) {
		$.ajax({
			type : "post",
			url : "menu/SelectMenu",
			data : {
				id : selectid
			},
			timeout : 2000,
			dataType : 'json',
			success : function(data) {
				var str = data.message;
				if (str == '0404') {
					alert("服务器请求异常");
				} else if (str == '0506') {
					$("#menuname").val(menuname);
					$("#selectid").val(selectid);
					$("#caidanneirong").show();
					$("#xitong").show();
					$("#mingcheng").show();
					$("#caidanneirong").show();
					$("#wanchenganniu").show();
					document.getElementById("key").innerHTML = data.menukey;
					rebackint = data.rebackint;
					rebackcontext = data.rebackcontext;
					xianshi(rebackint, rebackcontext);
				}
			},
			error : function(data) {
				alert("请求服务器失败");
			}
		});
	}
}

/**
 * 上面那个方法调用
 */
function xianshi(rebackint, rebackcontext) {
	//清空图文消息选中项
	$("input[name='graphicMessageId']").removeAttr('checked');
	$("#context").val("");
	if (rebackint == 1 || rebackint == 0) {
		cdzerotofive();
		chongzhi("ca1");
		if(rebackint == 1){
			$("input:radio[rowId='"+rebackcontext+"']").eq(0).prop("checked",true);
		}
	} else if (rebackint == 2) {
		cdzerotofive();
		chongzhi("ca2");
	} else if (rebackint == 3) {
		cdzerotofive();
		chongzhi("ca3");
	} else if (rebackint == 4) {
		cdzerotofive();
		chongzhi("ca4");
	} else if (rebackint == 5) {
		cdzerotofive();
		chongzhi("ca5");
		$("#context").val(formatStr(rebackcontext));
	} else if (rebackint == 6) {
		cdsix();
		$("#wytext").val(rebackcontext);
	}
}

/**
 *上面的方法0-5使用
 */
function cdzerotofive() {
	$("#xiaoxi").show();
	$("#wangye").hide();
	$("#wytext").val("");
	if ($("#Like1").checked) {
		//选中了
	} else {
		//没有选中
		$("#Like2").removeAttr("checked");
		$("#Like1").prop('checked', true);
	}
}

/**
 *上面的方法6使用
 */
function cdsix() {
	$("#xiaoxi").hide();
	$("#wangye").show();
	if ($("#Like2").checked) {
	} else {
		//没有选中
		$("#Like1").removeAttr("checked");
		$("#Like2").prop('checked', 'true');
	}
	$("#context").val("");
}

/**
 *删除菜单
 */
function deletecaidan() {
	var deleteid = $("#selectid").val();
	if (deleteid == -1) {
		alert("请选择要删除的菜单");
	} else {
		$.ajax({
			type : "post",
			url : "menu/DeleteMenu",
			data : {
				id : deleteid
			},
			timeout : 2000,
			dataType : 'json',
			success : function(data) {
				var str = data.message;
				if (str == '1075') {
					alert("数据异常");
				} else if (str == '0404') {
					alert("删除失败");
				} else if (str == '1076') {
					alert("删除成功");
				}
				location.reload();
			},
			error : function(data) {
				alert("请求服务器失败");
			}
		});
	}
}

/**
 *发送消息和跳转网页之间跳转
 */
function tiaozhuan(cspid) {
	if (cspid == 1) {
		$("#xiaoxi").show();
		$("#wangye").hide();
	} else {
		$("#xiaoxi").hide();
		$("#wangye").show();
	}
}

/**
 *修改菜单信息
 */
function updateMenu() {
	var selid = $("#selectid").val();
	var rebackint = -1;
	if ($("#caidanneirong").is(":visible")) {
		if ($("#tuwen").is(":visible")) {
			//图文消息
			rebackint = 1;
			var menuname = $("#menuname").val();
			var checkId = $("input[name='graphicMessageId']:checked").attr("rowId");
			if(checkId==null||checkId==""||checkId=="undefined"){
				alert("请选择图文消息...");
				return false;
			}
			$.ajax({
				type : "post",
				url : "menu/UpdateMenu",
				data : {
					id : selid,
					rebackint : rebackint,
					rebackcontext: checkId,
					menuname:menuname
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("数据异常");
					} else if (str == '1085') {
						alert("保存失败");
					} else if (str == '1086') {
						alert("保存成功");
					}
				},
				error : function(data) {
					alert("请求服务器失败");
				}
			});
		} else if ($("#tupian").is(":visible")) {
			//图片消息
			rebackint = 2;
			alert("暂未开放");
			return false;
			$.ajax({
				type : "post",
				url : "menu/UpdateMenu",
				data : {
					id : selid,
					rebackint : rebackint
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("数据异常");
					} else if (str == '1085') {
						alert("保存失败");
					} else if (str == '1086') {
						alert("保存成功");
					}
				},
				error : function(data) {
					alert("请求服务器失败");
				}
			});
		} else if ($("#yuyin").is(":visible")) {
			//语音
			rebackint = 3;
			alert("暂未开放");
			return false;
			$.ajax({
				type : "post",
				url : "menu/UpdateMenu",
				data : {
					id : selid,
					rebackint : rebackint
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("数据异常");
					} else if (str == '1085') {
						alert("保存失败");
					} else if (str == '1086') {
						alert("保存成功");
					}
				},
				error : function(data) {
					alert("请求服务器失败");
				}
			});
		} else if ($("#shipin").is(":visible")) {
			//视频
			rebackint = 4;
			alert("暂未开放");
			return false;
			$.ajax({
				type : "post",
				url : "menu/UpdateMenu",
				data : {
					id : selid,
					rebackint : rebackint
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("数据异常");
					} else if (str == '1085') {
						alert("保存失败");
					} else if (str == '1086') {
						alert("保存成功");
					}
				},
				error : function(data) {
					alert("请求服务器失败");
				}
			});
		} else if ($("#wenzi").is(":visible")) {
			//文字
			var context = $("#context").val();
			var menuname = $("#menuname").val();
			if(context==""||context==null){
				alert("请填写文字信息...");
				return false;
			}
			rebackint = 5;
			$.ajax({
				type : "post",
				url : "menu/UpdateMenu",
				data : {
					id : selid,
					rebackint : rebackint,
					rebackcontext : context,
					menuname : menuname
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("数据异常");
					} else if (str == '1085') {
						alert("保存失败");
					} else if (str == '1086') {
						alert("保存成功");
					}
				},
				error : function(data) {
					alert("请求服务器失败");
				}
			});
		} else if ($("#wangye").is(":visible")) {
			//网页
			var context = $("#wytext").val();
			var menuname = $("#menuname").val();
			if(context==""||context==null){
				alert("请填写链接...");
				return false;
			}
			rebackint = 6;
			$.ajax({
				type : "post",
				url : "menu/UpdateMenu",
				data : {
					id : selid,
					rebackint : rebackint,
					rebackcontext : context,
					menuname : menuname
				},
				timeout : 2000,
				dataType : 'json',
				success : function(data) {
					var str = data.message;
					if (str == '0404') {
						alert("数据异常");
					} else if (str == '1085') {
						alert("网页链接失败");
					} else if (str == '1086') {
						alert("网页链接成功");
					}
				},
				error : function(data) {
					alert("请求服务器失败");
				}
			});
		}
	} else {
		//有子菜单的一级菜单修改名称
		var menuname = $("#menuname").val();
		rebackint = 0;
		$.ajax({
			type : "post",
			url : "menu/UpdateMenu",
			data : {
				id : selid,
				rebackint : rebackint,
				menuname : menuname
			},
			timeout : 2000,
			dataType : 'json',
			success : function(data) {
				var str = data.message;
				if (str == '0404') {
					alert("数据异常");
				} else if (str == '1085') {
					alert("保存失败");
				} else if (str == '1086') {
					alert("保存成功");
				}
			},
			error : function(data) {
				alert("请求服务器失败");
			}
		});
	}
	window.location.href = "menu/ToListPage";
}

function chongzhi(csid) {
	$("#imgca1").attr("src", "images/tuwen.png");
	$("#imgca2").attr("src", "images/tupian.png");
	$("#imgca3").attr("src", "images/yuyin.png");
	$("#imgca4").attr("src", "images/shipin.png");
	$("#imgca5").attr("src", "images/wenzi.png");
	$("#tuwen").hide();
	$("#tupian").hide();
	$("#yuyin").hide();
	$("#shipin").hide();
	$("#wenzi").hide();
	for ( var i = 1; i <= 5; i++) {
		if ($("#ca" + i).hasClass("xscolor")) {
			$("#ca" + i).removeClass("xscolor"); //移除所有的样式
			$("#ca" + i).addClass("nxscolor");
		}
	}
	dingxiang(csid);
}

function dingxiang(csid) {
	if (csid == "ca1") {
		$("#imgca1").attr("src", "images/tuwen1.png");
		$("#ca1").removeClass("nxscolor"); //移除所有的样式
		$("#ca1").addClass("xscolor");
		$("#tuwen").show();
	} else if (csid == "ca2") {
		$("#imgca2").attr("src", "images/tupian1.png");
		$("#ca2").removeClass("nxscolor"); //移除所有的样式
		$("#ca2").addClass("xscolor");
		$("#tupian").show();
	} else if (csid == "ca3") {
		$("#imgca3").attr("src", "images/yuyin1.png");
		$("#ca3").removeClass("nxscolor"); //移除所有的样式
		$("#ca3").addClass("xscolor");
		$("#yuyin").show();
	} else if (csid == "ca4") {
		$("#imgca4").attr("src", "images/shipin1.png");
		$("#ca4").removeClass("nxscolor"); //移除所有的样式
		$("#ca4").addClass("xscolor");
		$("#shipin").show();
	} else if (csid == "ca5") {
		$("#imgca5").attr("src", "images/wenzi1.png");
		$("#ca5").removeClass("nxscolor"); //移除所有的样式
		$("#ca5").addClass("xscolor");
		$("#wenzi").show();
	}
}

/*
 *限制一级菜单input输入字数4个汉字
 */
var kp = function(txt) {
	var v = txt.value;
	var reg = /^[a-zA-Z\d`~@#\$%\^&\*\(\)\-_=\+\[\]\{\}\\\|;\:'",<\.>\/\?]{0,8}$/;
	var vv = v.replace(/[^\x00-\xff]/g, "aa");
	if (!reg.test(vv)) {
		txt.value = txt.pv ? txt.pv : "";
	} else {
		txt.pv = v;
	}
};

/*
 *限制一级菜单input输入字数8个汉字
 */
var kp1 = function(txt) {
	var v = txt.value;
	var reg = /^[a-zA-Z\d`~@#\$%\^&\*\(\)\-_=\+\[\]\{\}\\\|;\:'",<\.>\/\?]{0,16}$/;
	var vv = v.replace(/[^\x00-\xff]/g, "aa");
	if (!reg.test(vv)) {
		txt.value = txt.pv ? txt.pv : "";
	} else {
		txt.pv = v;
	}
};
/**
 *转换
 */
function formatStr(str) {
	return str.replace(/-/g, "\r\n");
}
/**
 *发布
 */
function fabu() {
	$.ajax({
		type : "post",
		url : "menu/FabuMenu",
		data : {},
		timeout : 2000,
		dataType : 'json',
		success : function(data) {
			var str = data.message;
			if (str == '0404') {
				alert("服务器请求异常");
			} else if (str == '1055') {
				alert("数据异常");
			} else if (str == '1056') {
				alert("请求成功");
			}
		},
		error : function(data) {
			alert("请求服务器失败");
		}
	});
}
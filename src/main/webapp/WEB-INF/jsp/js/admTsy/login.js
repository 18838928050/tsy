$(function(){
	initMAIN();
});

function initMAIN(){
	loginInit();
}

function loginInit(){
	if($("#bs-example-navbar-collapse-1")){
		$("#bs-example-navbar-collapse-1").load("login.html",function(responseTxt,statusTxt,xhr){
			$(".menulogin").hide();
			$(".menumation").hide();
			loginEventInit();
		});
	}
}

function loginEventInit(){
	AjaxUtil.request({url:getRootPath()+"/post/admTsy/SessionAlife",params:{},type:'json',callback:function(json){
			if(json.returnCode=="1341"){
				$(".menulogin").show();
				$(".menumation").hide();
			}else{
				$(".menumation").show();
				$(".menulogin").hide();
			}
			menuInit();
			init();
			generateCaptcha();
			Yz();
		}
	});
}

function Yz(){
	$('#LoginG').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			Username: {
				message: 'The username is not valid',
				validators: {
					notEmpty: {
						message: '账户不能为空'
					},
					stringLength: {
						min: 5,
						max: 10,
						message: '账号长度 5-10'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: '只接受数字和字母 '
					}
				}
			},
			Password: {
				validators: {
					notEmpty: {
						message: '密码不能为空'
					}
				}
			},
			captcha: {
				validators: {
					callback: {
						message: '验证码错误',
						callback: function(value, validator) {
							var items = $('#captchaOperation').html().split(' '),
								sum = parseInt(items[0]) + parseInt(items[2]);
							return value == sum;
						}
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		var mhUserNumber = $("#Username").val();
		var mhUserPassword = $("#Password").val();
		var params = {
				mhUserNumber:mhUserNumber,
				mhUserPassword:mhUserPassword
		};
		AjaxUtil.request({url:getRootPath()+"/post/admTsy/AdmTsyLogin",params:params,type:'json',callback:function(json){
				if(json.returnCode=="-9999"){
					qiao.bs.msg({msg:json.returnMessage,type:'warning'});
				}else{
					window.location.reload();
				}
			}
		});
		return false;
	});
	$('#LoginS').bootstrapValidator({
		feedbackIcons: {
			valid: 'glyphicon glyphicon-ok',
			invalid: 'glyphicon glyphicon-remove',
			validating: 'glyphicon glyphicon-refresh'
		},
		fields: {
			Username2: {
				message: 'The username is not valid',
				validators: {
					notEmpty: {
						message: '账户不能为空'
					},
					stringLength: {
						min: 5,
						max: 10,
						message: '账号长度 5-10'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: '只接受数字和字母 '
					}
				}
			},
			Password2: {
				validators: {
					notEmpty: {
						message: '密码不能为空'
					},
					stringLength: {
						min: 6,
						max: 20,
						message: '账号长度 6-20'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: '只接受数字和字母 '
					}
				}
			},
			Password3: {
				validators: {
					notEmpty: {
						message: '密码不能为空'
					},
					stringLength: {
						min: 6,
						max: 20,
						message: '账号长度 6-20'
					},
					regexp: {
						regexp: /^[a-zA-Z0-9_\.]+$/,
						message: '只接受数字和字母 '
					},
					identical: {
						field: 'Password2',
						message: '*两次输入密码不一致'
					}
				}
			},
			captcha2: {
				validators: {
					callback: {
						message: '验证码错误',
						callback: function(value, validator) {
							var items = $('#captchaOperation2').html().split(' '),
								sum = parseInt(items[0]) + parseInt(items[2]);
							return value == sum;
						}
					}
				}
			}
		}
	}).on('success.form.bv', function(e) {
		var mhUserNumber = $("#Username2").val();
		var mhUserPassword = $("#Password2").val();
		var params = {
				mhUserNumber:mhUserNumber,
				mhUserPassword:mhUserPassword
		};
		AjaxUtil.request({url:getRootPath()+"/post/admTsy/AdmTsyRegist",params:params,type:'json',callback:function(json){
				if(json.returnCode=="-9999"){
					qiao.bs.msg({msg:json.returnMessage,type:'warning'});
				}else{
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
				}
			}
		});
		return false;
	});
}

function randomNumber(min, max) {
	return Math.floor(Math.random() * (max - min + 1) + min);
}
function generateCaptcha() {
	$('#captchaOperation').html([randomNumber(1, 50), '+', randomNumber(1, 50), '='].join(' '));
	$('#captchaOperation2').html([randomNumber(1, 50), '+', randomNumber(1, 50), '='].join(' '));
}
//AjaxUtil.request({url:getRootPath()+"/post/admTsy/AdmTsyLoginMation",params:{},type:'json',callback:function(json1){
//	var qrcode = new QRCode(document.getElementById("qrcode"), {
//        width : 300,//设置宽高
//        height : 300
//    });
//	qrcode.makeCode(json1.bean.url);
//	}
//});

function menuInit(){
	/*搜索*/
	$('body').on('click', '#netSearch', function(e){
		var content = $("#searchContent").val();
		if(content==""||content==null){
			qiao.bs.msg({msg:"请输入搜索内容...",type:'danger'});
		}else{
			AjaxUtil.request({url:getRootPath()+"/post/admTsy/SessionAlife",params:{},type:'json',callback:function(json){
				if(json.returnCode=="1341"){
					$('#myModal').modal('show');
					qiao.bs.msg({msg:"您还未登录,请先登录...",type:'danger'});
				}else{
					location.href="netsearch.html?searchContent="+content;
				}
			}
			});
		}
	});
	$('body').on('click', '#wechatSearch', function(e){
		var content = $("#searchContent").val();
		if(content==""||content==null){
			qiao.bs.msg({msg:"请输入搜索内容...",type:'danger'});
		}else{
			location.href="search.html?searchContent="+content;
		}
	});
	/*前三个一次按钮*/
	$('body').on('click', '#menuzy', function(e){
		location.href = "index.html";
	});
	$('body').on('click', '#menuabout', function(e){
		location.href = "about.html";
	});
	$('body').on('click', '#menuvedio', function(e){
		location.href = "vedioList.html";
	});
	/*框架*/
	$('body').on('click', '#menuspring', function(e){
		location.href = "content.html?type=241";
	});
	$('body').on('click', '#menuStruts', function(e){
		location.href = "content.html?type=242";
	});
	$('body').on('click', '#menuHibernate', function(e){
		location.href = "content.html?type=243";
	});
	$('body').on('click', '#menuKjAll', function(e){
		location.href = "content.html?type=210";
	});
	/*前台*/
	$('body').on('click', '#menuJavaScript', function(e){
		location.href = "content.html?type=6";
	});
	$('body').on('click', '#menuhtml', function(e){
		location.href = "content.html?type=4";
	});
	$('body').on('click', '#menuCSS', function(e){
		location.href = "content.html?type=5";
	});
	$('body').on('click', '#menuJquery', function(e){
		location.href = "content.html?type=7";
	});
	$('body').on('click', '#menuBootStrap', function(e){
		location.href = "content.html?type=180";
	});
	/*后台*/
	$('body').on('click', '#menujava', function(e){
		location.href = "content.html?type=244";
	});
	
	/*登陆*/
	$('body').on('click', '#menumation', function(e){
		location.href = "mation.html";
	});
	$('body').on('click', '#menuexit', function(e){
		AjaxUtil.request({url:getRootPath()+"/post/admTsy/AdmTsyExit",params:{},type:'json',callback:function(json){
				if(json.returnCode=="0"){
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
					location.href = "index.html";
				}
			}
		});
	});
	$('body').on('click', '#menulogin', function(e){
		$('#myModal').modal('show');
	});
	
	/*微信专区*/
	$('body').on('click', '#menuRz', function(e){
		location.href = "content.html?type=185";
	});
	$('body').on('click', '#menuDt', function(e){
		location.href = "content.html?type=186";
	});
	$('body').on('click', '#menuOther', function(e){
		location.href = "content.html?type=187";
	});
}
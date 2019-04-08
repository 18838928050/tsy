var fileId = new Array();

var mainVedioId = null;

var id = null;

$(function(){
	init();
});

function init(){
	receiveData();
	dataInit();
	eventInit();
}

function dataInit(){
	reloadOptionsClass();
	$('#sel_search_orderstatus5').multiselect({
		enableClickableOptGroups: true,
		inheritClass: true,
		enableCollapsibleOptGroups: true,
		includeSelectAllOption: false,
		enableFiltering: true,
		buttonWidth:'80%',
		maxHeight: '200px',
		nonSelectedText: '请选择',
        onChange: function(option, checked) {
            
        }
	});
	$('#customType').multiselect({
		enableClickableOptGroups: true,
		inheritClass: true,
		enableCollapsibleOptGroups: true,
		includeSelectAllOption: false,
		enableFiltering: true,
		buttonWidth:'100%',
		maxHeight: '200px',
		nonSelectedText: '请选择',
        onChange: function(option, checked) {
            
        }
	});
	$('#sel_search_orderstatus6').multiselect({
		enableClickableOptGroups: true,
		inheritClass: true,
		enableCollapsibleOptGroups: true,
		includeSelectAllOption: true,
		enableFiltering: true,
		buttonWidth:'80%',
		maxHeight: '200px',
		nonSelectedText: '请选择',
        onChange: function(option, checked) {
            
        }
	});
	$('#addMain').bootstrapValidator({
        message: 'This value is not valid',
        feedbackIcons: {
            valid: 'glyphicon glyphicon-ok',
            invalid: 'glyphicon glyphicon-remove',
            validating: 'glyphicon glyphicon-refresh'
        },
        fields: {
        	option_title: {
                message: '标题验证失败',
                validators: {
                    notEmpty: {message: '标题不能为空'}
                }
            },
            option_introduce: {
                message: '简介验证失败',
                validators: {
                    notEmpty: { message: '简介不能为空'}
                }
            },
            sel_search_orderstatus5: {
                message: '模块验证失败',
                validators: {
                    notEmpty: {message: '请选择模块'}
                }
            },
            sel_search_orderstatus6: {
                message: '标签验证失败',
                validators: {
                    notEmpty: {message: '请选择标签'}
                }
            }
        }
    }).on('success.form.bv', function (e) {
		if(mainVedioId==""||mainVedioId==null){
			qiao.bs.msg({msg:'请选择要上传的文件',type:'danger'});
		}else{
			var params = {
					id:id,
					optionId:fileId.toString(","),//附件id
					optionVedio:mainVedioId,//主文件id
					optionTitle:$("#option_title").val(),//标题
					optionIntroduce:$("#option_introduce").val(),//介绍
					optionLabel:$("#sel_search_orderstatus6").val().toString(","),//标签
					optionClass:$("#sel_search_orderstatus5").val(),//分类
			};
			AjaxUtil.request({url:path+"/post/TsyUserCustomOptions/updateVedioById",params:params,type:'json',callback:function(json){
				if(json.returnCode=="0"){
					qiao.bs.msg({msg:json.returnMessage,type:'success'});
					location.href = "wechatVedioList.html"; 
				}else{
					qiao.bs.msg({msg:'修改失败',type:'warning'});
				}
			}
		});
		}
		return false;
	});
}

function eventInit(){
	$(".progress-main").hide();
	$('body').on('click', '.delete', function(e){
		var insertId = $(this).attr("insertId");
		fileId.remove(insertId);
		$(this).parent().parent().remove();
		Tx();
	});
	$('body').on('click', '.addModel', function(e){
		$('#myModal').modal('show');
	});
	$('body').on('click', '#optionsList', function(e){
		$('#optionsAll').modal('show');
		$('#massage').bootstrapTable('refresh', {url: path+'/post/TsyOptions/queryOptionsList'});  
	});
	$('body').on('click', '#cancle', function(e){
		location.href = "wechatVedioList.html"; 
	});
	$('body').on('click', '.addBq', function(e){
		$('#myModal2').modal('show');
	});
	$('body').on('click', '#model_submit', function(e){
		var customTitle = $("#customTitle").val();
		var customType = $("#customType").val();
		if(customTitle==""||customTitle==null){
			qiao.bs.msg({msg:'请填写模块名称',type:'danger'});
			return ;
		}
		if(customType==""||customType==null){
			qiao.bs.msg({msg:'模块类型不正确，请重新选择',type:'danger'});
			return ;
		}
		var params = {
			customTitle:customTitle,
			customType:customType
		};
		AjaxUtil.request({url:path+"/post/Class/addTsyUserCustomOptionsClass",params:params,type:'json',callback:function(json){
				if(json.returnCode=="1096"){
					qiao.bs.msg({msg:'新模块添加成功',type:'success'});
					$('#myModal').modal('hide');
					$("#customTitle").val("");
					reloadOptionsClass();
				}else{
					qiao.bs.msg({msg:'新模块添加失败',type:'warning'});
				}
			}
		});
	});
	$('body').on('click', '#Bq_submit', function(e){
		var labelTitle = $("#labelTitle").val();
		if(labelTitle==""||labelTitle==null){
			qiao.bs.msg({msg:'请填写标签名称',type:'danger'});
			return ;
		}
		AjaxUtil.request({url:path+"/post/Class/addTsyUserCustomOptionsLabel",params:{labelTitle:labelTitle},type:'json',callback:function(json){
				if(json.returnCode=="1096"){
					qiao.bs.msg({msg:'新标签添加成功',type:'success'});
					$('#myModal2').modal('hide');
					$("#labelTitle").val("");
					reloadOptionsLabel();
				}else{
					qiao.bs.msg({msg:'新标签添加失败',type:'warning'});
				}
			}
		});
	});
	upLoadFile();
}

function reloadOptionsClass(){
	AjaxUtil.request({url:path+"/post/Class/queryTsyUserCustomOptionsClass",params:{},type:'json',callback:function(json){
            var newProducts = new Array();
            var obj = new Object();
            $.each(json.rows, function(index, html) {
                obj = {
                    label : html.custom_title,
                    value : html.id
                };
                newProducts.push(obj);
            });
            $("#sel_search_orderstatus5").multiselect('dataprovider', newProducts);
            reloadOptionsLabel();
		}
	});
}

function reloadOptionsLabel(){
	AjaxUtil.request({url:path+"/post/Class/queryTsyUserCustomOptionsLabel",params:{},type:'json',callback:function(json){
            var newProducts = new Array();
            var obj = new Object();
            $.each(json.rows, function(index, html) {
                obj = {
                    label : html.label_title,
                    value : html.id
                };
                newProducts.push(obj);
            });
            $("#sel_search_orderstatus6").multiselect('dataprovider', newProducts);
            loadMainData();
		}
	});
}


function loadMainData(){
	id = $.req("id");
	var params = {
		id:id,	
	};
	AjaxUtil.request({url:path+"/post/TsyUserCustomOptions/queryVedioListById",params:params,type:'json',callback:function(jsonMain){
        	$("#option_title").val(jsonMain.bean.option_title);
        	$("#option_introduce").val(jsonMain.bean.option_introduce);
        	var sceneIdArr = new Array();
        	sceneIdArr.push(jsonMain.bean.option_class);
        	$('#sel_search_orderstatus5 option').each(function(i,content){
            	if(sceneIdArr.indexOf(content.value)>=0){
                    this.selected=true;
                }
            });
        	//设置选中值后，需要刷新select控件
        	$("#sel_search_orderstatus5").multiselect('refresh');
        	
        	sceneIdArr = jsonMain.bean.option_label.split(",");
        	$('#sel_search_orderstatus6 option').each(function(i,content){
            	if(sceneIdArr.indexOf(content.value)>=0){
                    this.selected=true;
                }
            });
        	//设置选中值后，需要刷新select控件
        	$("#sel_search_orderstatus6").multiselect('refresh');
        	
        	mainVedioId = jsonMain.bean.option_vedio;
        	$(".items-vedio").html($(".items-vedio").html()+uploadModelTwo().replace("{imgName}", jsonMain.bean.fileName+"."+jsonMain.bean.fileType).replace("{imgSize}", jsonMain.bean.fileSize));
        	$(".items-vedio .item").last().find(".progress-bar-success").css('width',100 + '%');
        	$(".items-vedio .item").last().find(".progress-bar-success").html(100 + '%');  
        	TxVidoe();
        	fileId = jsonMain.bean.option_id.split(",");
        	for(var i = 0;i<fileId.length;i++){
        		if(fileId[i]!=""&&fileId[i]!=null){
        			$(".items").html($(".items").html()+uploadModel().replace("{imgName}", jsonMain.bean.optionName.split(",")[i]+"."+jsonMain.bean.optionType.split(",")[i]).replace("{imgSize}", jsonMain.bean.optionSize.split(",")[i]));
        			$(".items .item").last().find(".delete").show();
        			$(".items .item").last().find(".delete").attr("insertId",jsonMain.bean.option_id.split(",")[i]);
        			$(".items .item").last().find(".progress-bar-success").css('width',100 + '%');
        			$(".items .item").last().find(".progress-bar-success").html(100 + '%');  
        			if(jsonMain.bean.optionType.split(",")[i]=="mp4"||jsonMain.bean.optionType.split(",")[i]=="rmvb"||
        					jsonMain.bean.optionType.split(",")[i]=="avi"||jsonMain.bean.optionType.split(",")[i]=="3gp"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/vedio.png");
        			}else if(jsonMain.bean.optionType.split(",")[i]=="gif"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/GIF.png");
        			}else if(jsonMain.bean.optionType.split(",")[i]=="png"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/png.png");
        			}else if(jsonMain.bean.optionType.split(",")[i]=="jpg"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/jpg.png");
        			}else if(jsonMain.bean.optionType.split(",")[i]=="pptx"||jsonMain.bean.optionType.split(",")[i]=="ppt"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/ppt.png");
        			}else if(jsonMain.bean.optionType.split(",")[i]=="docx"||jsonMain.bean.optionType.split(",")[i]=="doc"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/word.png");
        			}else if(jsonMain.bean.optionType.split(",")[i]=="xls"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/excel.png");
        			}else if(jsonMain.bean.optionType.split(",")[i]=="zip"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/ZIP.png");
        			}else if(jsonMain.bean.optionType.split(",")[i]=="pdf"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/pdf.png");
        			}else if(jsonMain.bean.optionType.split(",")[i]=="rar"){
        				$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/rar.png");
        			}
        		}
        	}
        	Tx();
        	reloadOptionList();
		}
	});
}

function reloadOptionList(){
	//1.初始化Table
    var oTable = new TableInit();
    oTable.Init();
}
function upLoadFile(){
    $('#files').fileupload({
        dataType: 'json',
        url: path + '/post/TsyOptions/upload',
        done: function (e, data) {
        	$(".items .item").last().find(".delete").show();
        	$("#weixin_upload").show();
        	$(".items .item").last().find(".ts").html("");
        	if(data.result.bean.optionType=="mp4"||data.result.bean.optionType=="rmvb"||
        			data.result.bean.optionType=="avi"||data.result.bean.optionType=="3gp"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/vedio.png");
        	}else if(data.result.bean.optionType=="gif"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/GIF.png");
        	}else if(data.result.bean.optionType=="png"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/png.png");
        	}else if(data.result.bean.optionType=="jpg"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/jpg.png");
        	}else if(data.result.bean.optionType=="pptx"||data.result.bean.optionType=="ppt"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/ppt.png");
        	}else if(data.result.bean.optionType=="docx"||data.result.bean.optionType=="doc"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/word.png");
        	}else if(data.result.bean.optionType=="xls"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/excel.png");
        	}else if(data.result.bean.optionType=="zip"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/ZIP.png");
        	}else if(data.result.bean.optionType=="pdf"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/pdf.png");
        	}else if(data.result.bean.optionType=="rar"){
        		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/rar.png");
        	}
        	fileId.push(data.result.bean.id);
        	$(".items .item").last().find(".delete").attr("insertId",data.result.bean.id);
        },
        progressall: function (e, data) {
            var progress = parseInt(data.loaded / data.total * 100, 10);
            $('#weixin_progress').css('width',progress + '%');
            $("#weixin_progress").html(progress + '%');  
            $(".items .item").last().find(".ts").html("正在上传...");
            $(".items .item").last().find(".progress-bar-success").css('width',progress + '%');
            $(".items .item").last().find(".progress-bar-success").html(progress + '%');  
        },
        add: function (e, data) {
        	var type = data.files[0].name.split(".")[1];
        	if(type=="mp4"||type=="rmvb"||type=="avi"||type=="3gp"||
        			type=="gif"||type=="png"||type=="jpg"||type=="pptx"||
        			type=="docx"||type=="xls"||type=="zip"||type=="pdf"||type=="rar"){
	        	for(var i = 0;i<data.files.length;i++){
	        		$(".items").html($(".items").html()+uploadModel().replace("{imgName}", data.files[i].name).replace("{imgSize}", bytesToSize(data.files[i].size)));
	        	}
	        	$("#weixin_upload").hide();
	        	Tx();
	        	data.submit();
        	}else{
        		qiao.bs.msg({msg:'文件类型不正确，请确认后选择',type:'danger'});
        	}
        }
    }); 
}

function TxVidoe(){
	if($(".items-vedio div[class=item]").length==0){
		$(".item-po-vedio").show();
	}else{
		$(".item-po-vedio").hide();
	}
}

function Tx(){
	if($(".items div[class=item]").length==0){
		$(".item-po").show();
		$(".progress-main").hide();
	}else{
		$(".item-po").hide();
		$(".progress-main").show();
	}
}

/**
 * 单位换算
 * @param bytes
 * @returns {String}
 */
function bytesToSize(bytes) {
    if (bytes === 0) return '0 B';
    var k = 1000, // or 1024
        sizes = ['B', 'KB', 'MB', 'GB', 'TB', 'PB', 'EB', 'ZB', 'YB'],
        i = Math.floor(Math.log(bytes) / Math.log(k));
    return (bytes / Math.pow(k, i)).toPrecision(3) + ' ' + sizes[i];
}

/**
 * 获取唯一值
 * @returns
 */
function guid() {  
    return 'xxxxxxxx-xxxx-4xxx-yxxx-xxxxxxxxxxxx'.replace(/[xy]/g, function(c) {  
        var r = Math.random()*16|0, v = c == 'x' ? r : (r&0x3|0x8);  
        return v.toString(16);  
    });  
}

/**
 * 模板1
 * @returns {String}
 */
function uploadModel(){
	var str = '<div class="item"><div class="item-first-div"><img class="item-first-div-img img-thumbnail" src="../../../images/item-po-img.png" data-holder-rendered="true">'
		+'</div><div class="item-second-div"><font class="item-second-div-name">{imgName}</font></div><div class="item-third-div"><font class="item-third-div-name">{imgSize}</font>'
		+'</div><div class="item-fourth-div"><div class="progress progress-striped active progress-item" role="progressbar" aria-valuemin="10" aria-valuemax="100" aria-valuenow="0">'
		+'<div class="progress-bar progress-bar-success" style="width:0%;"></div></div></div><div class="item-fifth-div">'
		+'<button type="button" class="btn btn-danger item-fifth-div-btn delete" style="display:none">删除</button><span class="ts"></span></div></div>';
	return str;
}

/**
 * 模板2
 * @returns {String}
 */
function uploadModelTwo(){
	var str = '<div class="item"><div class="item-first-div"><img class="item-first-div-img img-thumbnail" src="../../../images/item-po-img.png" data-holder-rendered="true">'
		+'</div><div class="item-second-div"><font class="item-second-div-name">{imgName}</font></div><div class="item-third-div"><font class="item-third-div-name">{imgSize}</font>'
		+'</div><div class="item-fourth-div"><div class="progress progress-striped active progress-item" role="progressbar" aria-valuemin="10" aria-valuemax="100" aria-valuenow="0">'
		+'<div class="progress-bar progress-bar-success" style="width:0%;"></div></div></div><div class="item-fifth-div">'
		+'<span class="ts"></span></div></div>';
	return str;
}

var TableInit = function () {
    var oTableInit = new Object();
    //初始化Table
    oTableInit.Init = function () {
        $('#massage').bootstrapTable({
            url: path+'/post/TsyOptions/queryOptionsList',             //请求后台的URL（*）
            method: 'post',                     //请求方式（*）
            striped: true,                      //是否显示行间隔色
            cache: true,                       //是否使用缓存，默认为true，所以一般情况下需要设置一下这个属性（*）
            pagination: true,                   //是否显示分页（*）
            sortable: false,                    //是否启用排序
            sortOrder: "asc",                   //排序方式
            queryParams: oTableInit.queryParams,//传递参数（*）
            contentType: "application/x-www-form-urlencoded",         //发送到服务器的数据编码类型
            sidePagination: "server",           //分页方式：client客户端分页，server服务端分页（*）
            pageNumber:1,                       //初始化加载第一页，默认第一页
            pageSize: 5,                       //每页的记录行数（*）
            pageList: [5],        //可供选择的每页的行数（*）
            uniqueId: "id",                     //每一行的唯一标识，一般为主键列
            onCheck: function (value, row, index) {
            	fileId.push(value.id);
            	$(".items").html($(".items").html()+uploadModel().replace("{imgName}", value.option_name+"."+value.option_type).replace("{imgSize}", value.option_size));
            	$(".items .item").last().find(".delete").show();
            	$(".items .item").last().find(".delete").attr("insertId",value.id);
            	$(".items .item").last().find(".progress-bar-success").css('width',100 + '%');
                $(".items .item").last().find(".progress-bar-success").html(100 + '%');  
                if(value.option_type=="mp4"||value.option_type=="rmvb"||
            			value.option_type=="avi"||value.option_type=="3gp"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/vedio.png");
            	}else if(value.option_type=="gif"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/GIF.png");
            	}else if(value.option_type=="png"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/png.png");
            	}else if(value.option_type=="jpg"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/jpg.png");
            	}else if(value.option_type=="pptx"||value.option_type=="ppt"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/ppt.png");
            	}else if(value.option_type=="docx"||value.option_type=="doc"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/word.png");
            	}else if(value.option_type=="xls"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/excel.png");
            	}else if(value.option_type=="zip"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/ZIP.png");
            	}else if(value.option_type=="pdf"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/pdf.png");
            	}else if(value.option_type=="rar"){
            		$(".items .item").last().find(".item-first-div-img").attr("src",path + "/images/rar.png");
            	}
                Tx();
            },
            onUncheck: function (value, row, index) {
            	var insertId = $('.delete[insertId="'+value.id+'"]').attr("insertId");
        		fileId.remove(insertId);
        		$('.delete[insertId="'+value.id+'"]').parent().parent().remove();
        		Tx();
            },
            columns: [{
                checkbox: true,
                formatter:stateFormatter,
            },{
                field: 'option_name',
                title: '名称',
                align: 'center',
                width: '90',
                formatter: function (value, row, index) {
                	return '<div style="word-wrap:break-word;">'+value+'</div>';
                }
            }, {
                field: 'option_pic_path',
                title: '展示',
                align: 'center',
                width: '90',
                formatter: function (value, row, index) {
                	return '<img style="width:70px;height:40px" src='+path+'\\'+value+'/>';
                }
            }, {
                field: 'option_size',
                title: '大小',
                align: 'center',
                width: '50',
                formatter: function (value, row, index) {
                	return value;
                }
            }, {
                field: 'option_type',
                title: '类型',
                align: 'center',
                width: '50',
            }, {
	            field: 'operate',
	            title: '操作',
	            width: '100',
	            align: 'center',
	            events: EvenInit,
	            formatter: operateFormatter
            }],
            onLoadSuccess: function(){  //加载成功时执行
//            	console.log("加载成功");
            	$(".fixed-table-container").css("margin-top","50px");
            },
            onLoadError: function(){  //加载失败时执行
            	console.log("加载数据失败", {time : 1500, icon : 2});
            }
        });
    };
    //得到查询的参数
    oTableInit.queryParams = function (params) {
        var temp = {
        	limit: params.limit,   //页面大小
            offset: params.offset,  //页码
        };
        return temp;
    };
    return oTableInit;
}

function stateFormatter(value, row, index) {
    if (fileId.indexOf(row.id)!=-1) {
        return {
            checked: true
        };
    }else{
    	return {
            checked: false
        };
    }
}

window.EvenInit = {
    'click .RoleOfA': function (e, value, row, index) {
        AjaxUtil.request({url:path+"/post/TsyOptions/dalete",params:{id:row.id},type:'json',callback:function(json){
        	if(json.returnCode=="1076"){
        		qiao.bs.msg({msg:json.returnMessage,type:'success'});
        		$('#massage').bootstrapTable('refresh', {url: path+'/post/TsyOptions/queryOptionsList'});  
        	}else{
        		qiao.bs.msg({msg:'数据异常，删除失败',type:'danger'});
        	}
        }
        });
    }
};
function operateFormatter(value, row, index) {
	var str = [ '<button type="button" class="RoleOfA btn btn-default  btn-sm" style="margin-right:15px;">删除</button>',];
    return str.join('');
}
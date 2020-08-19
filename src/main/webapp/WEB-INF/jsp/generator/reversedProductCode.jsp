<%@ page language="Java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
		<base href="<%=basePath%>">
		<!-- jsp文件头和头部 -->
		<%@ include file="../index/top.jsp"%>
		<!-- 下拉框 -->
		<link rel="stylesheet" href="static/ace/css/chosen.css" />
		<link rel="stylesheet" href="static/ace/css/select2.css" />

		<style type="text/css">
			#dialog-add,#dialog-message,#dialog-comment{width:100%; height:100%; position:fixed; top:0px; z-index:10000; display:none;}
			.commitopacity{position:absolute; width:100%; height:100%; background:#7f7f7f; filter:alpha(opacity=50); -moz-opacity:0.5; -khtml-opacity: 0.5; opacity: 0.5; top:0px; z-index:99999;}
			.commitbox{width:95%; padding-left:42px; padding-top:5px; position:absolute; top:0px; z-index:99999;}
			.commitbox_inner{width:96%; height:235px;  margin:6px auto; background:#efefef; border-radius:5px;}
			.commitbox_top{width:100%; height:233px; margin-bottom:10px; padding-top:10px; background:#FFF; border-radius:5px; box-shadow:1px 1px 3px #e8e8e8;}
			.commitbox_top textarea{width:95%; height:165px; display:block; margin:0px auto; border:0px;}
			.commitbox_cen{width:95%; height:40px; padding-top:10px;}
			.commitbox_cen div.left{float:left;background-size:15px; background-position:0px 3px; padding-left:18px; color:#f77500; font-size:16px; line-height:27px;}
			.commitbox_cen div.left img{width:30px;}
			.commitbox_cen div.right{float:right; margin-top:7px;}
			.commitbox_cen div.right span{cursor:pointer;}
			input[type=checkbox].ace.ace-switch.ace-switch-4:checked + .lbl::before {
				text-indent: 12px;
			}
			input[type=checkbox].ace.ace-switch.ace-switch-4 + .lbl::before {
				content: "是\a0\a0\a0\a0\a0\a0\a0\a0\a0\a0\a0否";
				font-size: 12px;
				line-height: 21px;
				height: 24px;
				overflow: hidden;
				border-radius: 12px;
				background-color: #8b9aa3;
				border: 1px solid #8b9aa3;
				color: #FFF;
				width: 56px;
				text-indent: -18px;
				text-shadow: 0 0 0 #FFF;
				display: inline-block;
				position: relative;
				box-shadow: none;
				-webkit-transition: all .3s ease;
				-o-transition: all .3s ease;
				transition: all .3s ease;
			}
			.inputStyle
			{
				text-align:center;
				border-color:white;
				border-width:0px;
				width: 99%;
			}

			.trStyle
			{
				background-color: rgba(42, 71, 75, 0.57);
			}
		</style>
	</head>
<body class="no-skin">
	<div class="main-container" id="main-container">
		<div class="main-content">
			<form action="createCode/proCode" name="FForm" id="FForm" method="post">
				<input type="hidden" name="zindex" id="zindex" value="0">
				<input type="hidden" name="FIELDLIST" id="FIELDLIST" value="">
				<input type="hidden" name="faobject" id="faobject" value="">
				<div id="zhongxin">
					<table style="margin-top: 10px; width: 99%">
						<tr>
							<td style="width:85px;text-align: right;">所属子系统：</td>
							<td><div class="nav-search"><input class="nav-search-input" type="text" name="belongSystem" id="belongSystem" value="${pd.BELONGSYSTEM }" placeholder="输入子系统名称" style="width:150px;" title="子系统名称"/></div></td>
							<td style="width:85px;text-align: right;">业务条线：</td>
							<td>
								<select name="businessLine" id="businessLine" data-placeholder="请选择业务条线"  style="vertical-align:top;width: 70px;">
									<option value="persion" ${pd.BUSINESSLINE == "persion"?"selected":""}>个人</option>
									<option value="corporation" ${pd.BUSINESSLINE == "corporation"?"selected":""}>企业</option>
								</select>
							</td>
							<td style="width:76px;text-align: right;">业务模块：</td>
							<td><div class="nav-search"><input class="nav-search-input" type="text" name="packageName" id="packageName" value="${pd.PACKAGENAME }" placeholder="这里输入所属业务模块即包名(不要输入特殊字符,请用纯字母)" style="width:150px;" title="模块/包名称"/></div></td>
							<td style="width:76px;text-align: right;">模块名称：</td>
							<td><div class="nav-search"><input class="nav-search-input" type="text" name="TITLE" id="TITLE" value="${pd.TITLE }" placeholder="这里输入模块说明内容" style="width:200px;" title="说明"/></div></td>
							<td style="width:76px;text-align: right;">模块类型：</td>
							<td>
								<select name="FHTYPE" id="FHTYPE" data-placeholder="请选择数据库"  style="vertical-align:top;width: 80px;" onchange="selectType(this.value)">
									<option value="single" ${pd.FHTYPE == "single"?"selected":""}>单/多表</option>
									<option value="tree" ${pd.FHTYPE == "tree"?"selected":""}>树形</option>
									<option value="fathertable" ${pd.FHTYPE == "fathertable"?"selected":""}>主表</option>
									<option value="sontable" ${pd.FHTYPE == "sontable"?"selected":""}>明细表</option>
								</select>
							</td>
							<td style="padding-left: 10px;display: none" id="faobjectid_td">
								<select name="faobjectid" id="faobjectid" data-placeholder="请选择" disabled="disabled" style="vertical-align:top;width:150px;background-color: #F5F5F5;" onchange="selectFa(this.value)">
									<option value="">选择主表</option>
									<c:forEach items="${varList}" var="var">
										<option value="${var.CREATECODE_ID }">${var.OBJECTNAME}</option>
									</c:forEach>
								</select>
							</td>

						</tr>
						<tr style="margin-top: 10px;">
							<td style="width:76px;text-align: right;">类名：</td>
							<td><div class="nav-search"><input class="nav-search-input" type="text" name="objectName" id="objectName" value="${pd.OBJECTNAME }" placeholder="类名首字母必须为大写字母或下划线" style="width:150px;" title="类名"/></div></td>
							<td style="width:76px;text-align: right;">表名：</td>
							<td><div class="nav-search"><input class="nav-search-input" type="text" name="tabletop" id="tabletop" value="${pd.TABLENAME}" placeholder="这里输入表名" style="width:150px;" title="表名" readonly/></div></td>
						</tr>
					</table>

					<table id="table_report" class="table table-bordered" style="margin-top: 5px;">
						<thead>
						<tr>
							<th class="center" style="width:40px;">序号</th>
							<th class="center"style="width:150px;">表名</th>
							<th class="center"style="width:135px;">属性名</th>
							<th class="center">注释说明</th>
							<th class="center" style="width:100px;">类型</th>
							<th class="center" style="width:100px;">字典</th>
							<th class="center" style="width:60px;">长度</th>
							<th class="center" style="width:60px;">小数位</th>
							<th class="center" style="width:79px;">表单录入</th>
							<th class="center" style="width:79px;">列表显示</th>
							<th class="center" style="width:79px;">是否必输</th>
							<th class="center" style="width:79px;">是否查询</th>
							<th class="center" style="width:79px;">是否模糊</th>
							<th class="center" style="width:70px;">默认值</th>
							<th class="center" style="width:50px;">操作</th>
						</tr>
						</thead>
						<tbody id="fields"></tbody>
					</table>

					<table id="table_report1" class="table table-striped table-bordered table-hover">
						<tr>
							<td style="text-align: center;" colspan="100">
								<a class="btn btn-success pull-left" title="上移" onclick="up();" style="margin: 5px 2px;">
									<i class="ace-icon fa fa-caret-up icon-only bigger-160" title="上移"></i>
								</a>
								<a class="btn btn-success pull-left" title="下移" onclick="below();" style="margin: 5px 2px;">
									<i class="ace-icon fa fa-caret-down icon-only bigger-160" title="下移"></i>
								</a>
								<a class="btn btn-app btn-primary btn-xs" onclick="selectTableRow();"><i class="ace-icon fa fa-briefcase"></i>获取字段</a>
								<a class="btn btn-app btn-success btn-xs" onclick="addTableRow();"><i class="ace-icon glyphicon glyphicon-plus"></i>添加</a>
								<a class="btn btn-app btn-success btn-xs" onclick="generatingCode();" id="productc"><i class="ace-icon fa fa-print bigger-160"></i>生成</a>
								<a class="btn btn-app btn-danger btn-xs" onclick="top.Dialog.close();"><i class="ace-icon fa fa-reply icon-only"></i>取消</a>
							</td>
						</tr>
					</table>
				</div>

				<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green"><strong id="second_show">10秒</strong></h4></div>

			</form>

		</div>
	</div>

<!-- 页面底部js¨ -->
<%@ include file="../index/foot.jsp"%>
<!-- 下拉框 -->
<script src="static/ace/js/chosen.jquery.js"></script>
<!--提示框-->
<script type="text/javascript" src="static/js/jquery.tips.js"></script>
<%--<script src="/static/js/myjs/productCode.js"></script>--%>
<script type="text/javascript">
	$(top.hangge());//关闭加载状态

	var arField = new Array();
	var index = 0;

	$(reductionFields());
	//修改时还原属性列表
	function reductionFields(){
		var msg = '${msg}';
		if('edit' == msg){
			var nowarField = '${pd.FIELDLIST}';
			var fieldarray = nowarField.split('|@@|');
			for(var i=0;i<fieldarray.length;i++){
				if(fieldarray[i] != ''){
					appendRow(fieldarray[i]);
					arField[i] = fieldarray[i];
				}
			}
		}
	}

	//获取字段
	function selectTableRow(){
		var tabletop = $("#tabletop").val();
		if(tabletop != null && tabletop != "" && tabletop != ''){
			$.ajax({
				type:"post",
				url:"<%=basePath%>createCode/getTableRow?tabletop="+tabletop,
				dataType:"text",
				contentType:"application/json",
				success:function(data){
					if(data != null && data != "" && data != ''){
						$("#fields").children().remove();
						var fieldarray = data.split('|@@|');
						for(var i=0;i<fieldarray.length;i++){
							if(fieldarray[i] != ''){
								appendRow(fieldarray[i]);
								arField[i] = fieldarray[i];
							}
						}
					}
				}
			});
		}
	}

	//初始化(反显)列表
	function appendRow(value){
		index = $("#fields").children().length;
		var fieldarray = value.split('|@|');
		$("#fields").append(
				'<tr onclick="changeTrOrder(this)">'+
				'<td class="center">'+Number(index+1)+'</td>'+
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[10]+'</td>'+	//表名
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[0]+'</td>'+	    //属性名称
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[2]+'</td>'+	    //中文说明
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[1]+'</td>'+	    //字段类型
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[8]+'</td>'+	    //字段码值
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[5]+'</td>'+	    //长度
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[6]+'</td>'+	    //小数点
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input id="fieldIs1'+index+'" name="fieldIs1'+index+'" type="checkbox" '+ (fieldarray[3] == '是'? 'checked value="是"':'value="否"') + ' onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[3]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[3]+'"></td>'+	//表单录入*/
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input  id="fieldIs2'+index+'" name="fieldIs2'+index+'" type="checkbox" '+ (fieldarray[9] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[9]+'<input type="hidden" name="field8'+index+'" value="'+fieldarray[9]+'"></td>'+	//列表显示*/
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input id="fieldIs3'+index+'" name="fieldIs3'+index+'" type="checkbox" '+ (fieldarray[7] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[7]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[7]+'"></td>'+	//是否必输*/
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input id="fieldIs4'+index+'" name="fieldIs4'+index+'" type="checkbox" '+ (fieldarray[11] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[11]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[11]+'"></td>'+	//是否查询*/
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input id="fieldIs5'+index+'" name="fieldIs5'+index+'" type="checkbox" '+ (fieldarray[12] == '是'? 'checked value="是"':'value="否"') + 'onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[12]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[12]+'"></td>'+	//是否模糊查询*/
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >'+fieldarray[4]+'</td>'+	//默认值
				'<td class="center">'+
				'<input type="hidden" name="field'+ index +'" value="'+ value +'">'+
				/*'<a class="btn btn-mini btn-info" title="编辑" onclick="editField('+value+','+index+')"><i class="ace-icon fa fa-pencil-square-o bigger-120"></i></a>&nbsp;'+*/
				'<a class="btn btn-mini btn-danger" title="删除" onclick="removeField('+ index +')"><i class="ace-icon fa fa-trash-o bigger-120"></i></a> '+
				'</td>'+
				'</tr>'
		);
		index++;
		$("#zindex").val(index);
	}

	//添加字段
	function addTableRow(){
		index = $("#fields").children().length;
		$("#fields").append(
				'<tr onclick="changeTrOrder(this)">'+
				'<td class="center">'+Number(index+1)+'</td>'+
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" ></td>'+	//表名
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" ></td>'+	    //属性名称
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" ></td>'+	    //中文说明
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" ></td>'+	    //字段类型
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" ></td>'+	    //字段码值
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >255</td>'+	    //长度
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >0</td>'+	    //小数点
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input id="fieldIs1'+index+'" name="fieldIs1'+index+'" type="checkbox" value="是" checked onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[3]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[3]+'"></td>'+	//表单录入*/
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input  id="fieldIs2'+index+'" name="fieldIs2'+index+'" type="checkbox"value="是" checked onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[9]+'<input type="hidden" name="field8'+index+'" value="'+fieldarray[9]+'"></td>'+	//列表显示*/
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input id="fieldIs3'+index+'" name="fieldIs3'+index+'" type="checkbox" value="是" checked onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				/*'<td class="center">'+fieldarray[7]+'<input type="hidden" name="field3'+index+'" value="'+fieldarray[7]+'"></td>'+	//是否必输*/
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input id="fieldIs4'+index+'" name="fieldIs4'+index+'" type="checkbox" value="否" onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				'<td>' +
				'<label class="pull-right inline">' +
				'<small class="muted smaller-90"><font style="vertical-align: inherit;"></font></small>' +
				'<input id="fieldIs5'+index+'" name="fieldIs5'+index+'" type="checkbox" value="否" onclick="updateFilesO(this)" onchange="getFields(this)" class="ace ace-switch ace-switch-4">' +
				'<span class="lbl middle"></span>' +
				'</label>' +
				'</td>' +
				'<td class="center" onmouseenter="changeToInput(this)" onmouseleave="changeToText(this)" >无</td>'+	//默认值
				'<td class="center" style="width:100px;">'+
				'<input type="hidden" name="field'+ index +'" value="">'+
				/*'<a class="btn btn-mini btn-info" title="编辑" onclick="editField("",'+index+')"><i class="ace-icon fa fa-pencil-square-o bigger-120"></i></a>&nbsp;'+*/
				'<a class="btn btn-mini btn-danger" title="删除" onclick="removeField('+ index +')"><i class="ace-icon fa fa-trash-o bigger-120"></i></a> '+
				'</td>'+
				'</tr>'
		);
		arField[index] = getFields(index);
		index++;
		$("#zindex").val(index);
	}

	//表名校验
	function caseCheck(tableName){
		if(tableName == ''){
			return;
		}
		var reg = /^[a-zA-Z_0-9]+$/;
		if(reg.test(tableName)){   //完全由数字字母下划线 组成
			$("#table").val(tableName.toUpperCase());
		}else{
			$("#table").val("");
			$("#table").tips({
				side:3,
				msg:'输入符合规范的表名',
				bg:'#AE81FF',
				time:2
			});
			$("#table").focus();
		}
	}

	//为保存多表存值传值
	function tackTabletops(tabletops){
		$("#tabletops").val($("#tabletop").val());
	}

	//是否按钮的切值变换
	function updateFilesO(Obj){
		var obj = $(Obj);
		var objVal = obj.val();
		if(objVal == "是"){
			obj.val("否");
		}else if(objVal == "否"){
			obj.val("是");
		}
	}

	map = new Map();
	map.set(1,"table");
	map.set(2,"dname");
	map.set(3,"dbz");
	map.set(4,"dtype");
	map.set(5,"dict");
	map.set(6,"flength");
	map.set(7,"decimal");
	map.set(13,"ddefault");

	//监控所有修改 以进行必要的更新
	function getFields(Obj,change){
		var tdIndex = $(Obj).closest("td").index();
		var trIndex = $(Obj).closest("tr").index();
		var tds = $(Obj).closest("tr").children();
		var table = (tds.eq(1).html());  //表名
		var dname = (tds.eq(2).html());  //属性名
		var dbz	  = (tds.eq(3).html());  //注释说明
		var dtype = (tds.eq(4).html());  //字段类型
		var dict  = (tds.eq(5).html());  //字典
		var flength = (tds.eq(6).html());  //长度
		var decimal = (tds.eq(7).html());  //小数位
		var ddefault = (tds.eq(13).html()); //默认值
		if(change != '' && change != null && change != ""){
			var some = tds.eq(tdIndex).children().val();
			var so = map.get(tdIndex);
			if(so == 'dname'){
				dname = some;
			}else if(so == 'dtype'){
				dtype = some;
			}else if(so == 'dbz'){
				dbz = some;
			}else if(so == 'dict'){
				dict = some;
			}else if(so == 'ddefault'){
				ddefault = some;
			}else if(so == 'flength'){
				flength = some;
			}else if(so == 'decimal'){
				decimal = some;
			}else if(so == 'table'){
				table = some;
			}
		}
		var isQian = (tds.eq(8).find("input").val());      //是否表单录入
		var isShow = (tds.eq(9).find("input").val());      //是否列表显示
		var isRequired = (tds.eq(10).find("input").val()); //是否必输
		var isQuery = (tds.eq(11).find("input").val());    //是否查询
		var isVague = (tds.eq(12).find("input").val());      //是否模糊查询
		var fields = dname + '|@|' + dtype + '|@|' + dbz + '|@|' +isQian + '|@|' + ddefault + '|@|' + flength + '|@|' + decimal + '|@|' + isRequired + '|@|' + dict + '|@|' + isShow + '|@|' + table + '|@|' + isQuery + '|@|' + isVague ;
		$("input[name='field"+ trIndex +"']").val(fields);
		//console.log("fields "+fields);
		arField[trIndex] = fields;
	}

	//删除数组添加元素 并重组列表
	function removeField(index){
		$("#fields").html('');
		arField.splice(index,1);
		for( var i = 0 ; i < arField.length ; i++ ){
			appendRow(arField[i]);
		}
	}

	function changeToInput(Obj){
		var obj = $(Obj);
		var value = obj.html();
		obj.html('<input type="text" class="inputStyle" style="font-weight:bold" onchange="getFields(this,'+'\'change\''+')" value="' + value + '">');
	}

	function changeToText(Obj){
		var obj = $(Obj);
		obj.html(obj.children().val());
	}


	//生成
	function generatingCode(){
		if($("#belongSystem").val()==""){
			$("#belongSystem").tips({
				side:3,
				msg:'输入所属子系统的名称',
				bg:'#AE81FF',
				time:2
			});
			$("#belongSystem").focus();
			return false;
		}
		if($("#TITLE").val()==""){
			$("#TITLE").tips({
				side:3,
				msg:'输入说明',
				bg:'#AE81FF',
				time:2
			});
			$("#TITLE").focus();
			return false;
		}
		if($("#packageName").val()==""){
			$("#packageName").tips({
				side:3,
				msg:'输入包名',
				bg:'#AE81FF',
				time:2
			});
			$("#packageName").focus();
			return false;
		}else{
			var pat = new RegExp("^[A-Za-z]+$");
			if(!pat.test($("#packageName").val())){
				$("#packageName").tips({
					side:3,
					msg:'只能输入字母',
					bg:'#AE81FF',
					time:2
				});
				$("#packageName").focus();
				return false;
			}
		}

		if($("#objectName").val()==""){
			$("#objectName").tips({
				side:3,
				msg:'输入类名',
				bg:'#AE81FF',
				time:2
			});
			$("#objectName").focus();
			return false;
		}else{
			var headstr = $("#objectName").val().substring(0,1);
			var pat = new RegExp("^[a-z0-9]+$");
			if(pat.test(headstr)){
				$("#objectName").tips({
					side:3,
					msg:'类名首字母必须为大写字母或下划线',
					bg:'#AE81FF',
					time:2
				});
				$("#objectName").focus();
				return false;
			}
		}

		if($("#fields").html() == ''){
			$("#table_report").tips({
				side:3,
				msg:'请添加属性',
				bg:'#AE81FF',
				time:2
			});
			return false;
		}
		if($("#tabletop").val()==""){
			$("#tabletop").tips({
				side:3,
				msg:'选择或者输入表名',
				bg:'#AE81FF',
				time:2
			});
			$("#tabletop").focus();
			return false;
		}

		if(!confirm("确定要生成吗?")){
			return false;
		}
		var strArField = '';
		for(var i=0;i<arField.length;i++){
			strArField = strArField + arField[i] + "|@@|";
		}
		$("#FIELDLIST").val(strArField); 	//属性集合
		$("#FForm").submit();				//提交
		$("#objectName").val('');
		$("#fields").html('');
		$("#productc").tips({
			side:3,
			msg:'提交成功,等待下载',
			bg:'#AE81FF',
			time:9
		});
		//window.parent.jzts();
		//$("#zhongxin").hide();
		$("#zhongxin2").show();
		timer(9);
		setTimeout("top.Dialog.close()",10000);
	}

	//倒计时
	function timer(intDiff){
		window.setInterval(function(){
			$('#second_show').html('<s></s>'+intDiff+'秒');
			intDiff--;
		}, 1000);
	}

	trNum = null;
	function up(){
		//console.log("触发上移   " + trNum);
		if(trNum != null && trNum > 0){
			var middle = arField[trNum - 1];
			arField[trNum - 1] = arField[trNum];
			arField[trNum] = middle;

			var tag_u = $("#fields tr:nth-child(" + (trNum+1) + ")");
			tag_u.children().first().html(trNum);
			var tag_d = $("#fields tr:nth-child(" + trNum + ")");
			tag_d.children().first().html(trNum+1);
			tag_u.insertBefore(tag_d);

			var h1 = tag_u.children().last().html().replace("field" + trNum,"field" + (trNum-1)).replace("removeField("+ trNum +")","removeField("+ (trNum-1) +")")
			tag_u.children().last().html(h1);
			var h2 = tag_d.children().last().html().replace("field" + (trNum-1),"field" + trNum).replace("removeField("+ (trNum-1) +")","removeField("+ trNum +")")
			tag_d.children().last().html(h2);
			trNum--;
			//console.log("trNum :" + trNum);
			//nth-child(n) 选择器匹配属于其父元素的第 N 个子元素，不论元素的类型
		}
	}
	function below(){
		//console.log("触发下移    " + trNum);
		var X = $("#fields").children().length - 1;
		if(trNum != null && trNum < X){
			var middle = arField[trNum + 1];
			arField[trNum + 1] = arField[trNum];
			arField[trNum] = middle;
			var tag_u = $("#fields tr:nth-child(" + (trNum+2) + ")");
			tag_u.children().first().html(trNum+1);
			var tag_d = $("#fields tr:nth-child(" + (trNum+1) + ")");
			tag_d.children().first().html(trNum+2);
			tag_u.insertBefore(tag_d);

			var h1 = tag_u.children().last().html().replace("field" + (trNum+1),"field" + (trNum)).replace("removeField("+ (trNum+1) +")","removeField("+ (trNum) +")")
			tag_u.children().last().html(h1);
			var h2 = tag_d.children().last().html().replace("field" + (trNum),"field" + (trNum+1)).replace("removeField("+ (trNum) +")","removeField("+ (trNum+1) +")")
			tag_d.children().last().html(h2);

			trNum++;
			//console.log("trNum :" + trNum);
		}
	}

	//行 单击事件
	function changeTrOrder(Obj){
		trNum = $(Obj).closest("tr").index();
		var obj = $(Obj);
		$("#table_report tr").removeClass("trStyle");
		obj.addClass("trStyle");
	}

	//判断属性名是否重复  暂时没有实现校验
	function isSame(dname,table){
		for(var i=0;i<arField.length;i++){
			var array = arField[i].split('|@|');
			var array0 = array[0]; //字段名
			var array10 = array[10]; //表名
			if(array0 == dname && array10==table){
				return false;
			}
		}
		return true;
	}

	//选择类型
	function selectType(value){
		if("sontable" == value){//明细表
			$("#faobjectid_td").show();
			$("#faobjectid").removeAttr("disabled");
			$("#faobjectid").css("background","white");
		}else{
			$("#faobjectid_td").hide();
			$("#faobjectid").attr("disabled","disabled");
			$("#faobjectid").css("background","#F5F5F5");
			$("#faobjectid").val("");
			inpOpen();
		};
	}

	//选择主表
	function selectFa(CREATECODE_ID){
		if("" != CREATECODE_ID){
			inpClose();
			$.ajax({
				type: "POST",
				url: locat+'/createCode/findById',
				data: {CREATECODE_ID:CREATECODE_ID,tm:new Date().getTime()},
				dataType:'json',
				cache: false,
				success: function(data){
					$("#TITLE").val(data.pd.TITLE + '(明细)');
					$("#packageName").val(data.pd.PACKAGENAME);
					$("#objectName").val(data.pd.OBJECTNAME+"Mx");
					var tb = data.pd.TABLENAME.split("|@|");
					$("#tabletop").val(tb[0]);
					$("#faobject").val(data.pd.OBJECTNAME);
				}
			});
		}else{
			inpOpen();
		}
	}

</script>

</body>
</html>
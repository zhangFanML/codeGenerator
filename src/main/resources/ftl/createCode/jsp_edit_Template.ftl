<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="../../includes/taglibs.jsp"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html lang="en">
	<head>
	<base href="<%=basePath%>">
	<!-- 下拉框 -->
	<link rel="stylesheet" href="static/ace/css/chosen.css" />
	<!-- jsp文件头和头部 -->
	<%@ include file="../../system/index/top.jsp"%>
	<!-- 日期框 -->
	<link rel="stylesheet" href="static/ace/css/datepicker.css" />
</head>
<body class="no-skin">
	<!-- /section:basics/navbar.layout -->
	<div class="main-container" id="main-container">
		<div class="page-content">
			<div class="row">
				<div class="col-xs-12">
					<form action="${objectNameLower}/${r"${msg }"}.do" name="Form" id="Form" method="post" class="form-horizontal" role="form">
						<input type="hidden" name="UUID" id="UUID" value="${r"${pd.UUID}"}"/>
						<input type="hidden" name="BANK_CODE" id="BANK_CODE" value="${r"${pd.BANK_CODE}"}"/>
						<input type="hidden" name="TRUNC_NO" id="TRUNC_NO" value="${r"${pd.TRUNC_NO}"}"/>
						<div id="zhongxin" style="padding-top: 13px;">
							<div class="form-group">
							<#list fieldList as var>
								<#if var[3] == "是">
									<label class="col-sm-2 control-label no-padding-right" for="${var[0] }">${var[2] }:</label>
									<div class="col-sm-4">
									<#if var[1] == 'Date'>
										<input class="span10 date-picker" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" type="text" data-date-format="yyyy-mm-dd" readonly="readonly" placeholder="${var[2] }" title="${var[2] }" style="width:98%;"/>
									<#elseif var[1] == 'Integer'>
										<input type="number" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" datatype="n" placeholder="这里输入${var[2] }" title="${var[2] }" style="width:98%;"/>
									<#elseif var[1] == 'Double'>
										<input type="number" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" datatype="n" placeholder="这里输入${var[2] }" title="${var[2] }" style="width:98%;"/>
									<#elseif var[1] == 'Dictionary'>
										<d:combox dictCode="${var[8] }" id="${var[0] }" name="${var[0] }" defaultValue="${r"${pd."}${var[0] }${r"}"}" clsName="form-control"/>
									<#else>
										<input type="text" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" datatype="*0-${var[5] }" placeholder="这里输入${var[2] }" title="${var[2] }" style="width:98%;"/>
									</#if>
									</div>
								</#if>
							</#list>
							</div>
							<div class="form-actions center">
								<a class="btn btn-mini btn-primary" onclick="save();">保存</a>
								<a class="btn btn-mini btn-danger" onclick="top.Dialog.close();">取消</a>
							</div>
						</div>
						<div class="row" align="center">
							<div id="zhongxin2" class="center" style="display:none"><br/><br/><br/><br/><br/><img src="static/images/jiazai.gif" /><br/><h4 class="lighter block green">提交中...</h4></div>
						</div>
					</form>
				</div>
				<!-- /.col -->
			</div>
			<!-- /.row -->
		</div>
		<!-- /.page-content -->
        <!-- 返回顶部 -->
        <a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
            <i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
        </a>
	</div>
	<!-- /.main-container -->


	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
    <%@ include file="../../system/index/ace_foot.jsp"%>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
		<script type="text/javascript">
		$(top.hangge());
		
		//保存
		function save(){
			$("#Form").submit();
		}
		
		$(function() {
		    $("form").Validform({
				tiptype:5,
				beforeSubmit:function(curform){
					//在验证成功后，表单提交前执行的函数，curform参数是当前表单对象。
					//这里明确return false的话表单将不会提交;	
					$("#zhongxin").hide();
					$("#zhongxin2").show();
				}
			});
			//日期框
			$('.date-picker').datepicker({autoclose: true,todayHighlight: true});
		});
		</script>
</body>
</html>
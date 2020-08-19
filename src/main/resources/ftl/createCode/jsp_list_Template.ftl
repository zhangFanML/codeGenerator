<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
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
		<!-- /section:basics/sidebar -->
		<div class="main-content">
			<div class="main-content-inner">
				<div class="page-content">
					<!-- 检索  -->
					<form action="${objectNameLower}/list.do" method="post" name="searchForm" id="searchForm">
						<div id="accordion" class="accordion-style1 panel-group" style="margin-top:20px;width: 100%;">
							<div class="panel panel-default" style="margin-top: -10px;margin-bottom: -10px;">
								<div class="panel-heading">
									<h4 class="panel-title">
										<a class="accordion-toggle" data-toggle="collapse" data-parent="#accordion" href="#collapsePanel" aria-expanded="false">
											<i class="bigger-110 ace-icon fa fa-angle-right" data-icon-hide="ace-icon fa fa-angle-down" data-icon-show="ace-icon fa fa-angle-right"></i>
											查询条件
										</a>
									</h4>
								</div>
								<div class="panel-collapse collapse in" id="collapsePanel" aria-expanded="true">
									<div class="panel-body form-search">
										<div class="form-group col-xs-12">
									<#list fieldList as var>
										<#if var[3] != "否">
											<div class="col-xs-3">
												<label class="control-label col-sm-4">${var[2]}:</label>
												<div class="col-sm-8">
													<input class="form-control" type="hidden" placeholder="请输入${var[2]}" name="${var[0] }" id="${var[0] }" value="${r"${var."}${var[0]}${r"}"}" title="${var[2]}" />
												</div>
											</div>
										</#if>
									</#list>
									<#list fieldList as var>
										<#if var[3] == "是">
											<label class="control-label col-sm-4" for="${var[0] }">${var[2] }:</label>
											<div class="col-sm-8">
												<#if var[1] == 'Date'>
													<div class="input-group">
														<input class="form-control date-picker" data-date-format="yyyy-mm-dd" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" type="text" readonly="readonly" placeholder="请选择${var[2] }" title="${var[2] }"/>
														<span class="input-group-addon">
															<i class="fa fa-calendar bigger-110"></i>
														</span>
													</div>
												<#elseif var[1] == 'Integer'>
													<input class="form-control"  type="number" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" datatype="n" placeholder="请输入${var[2] }" title="${var[2] }"/>
												<#elseif var[1] == 'Double'>
													<input class="form-control"  type="number" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" datatype="n" placeholder="请输入${var[2] }" title="${var[2] }"/>
												<#elseif var[1] == 'Dictionary'>
													<d:combox dictCode="${var[8] }" id="${var[0] }" name="${var[0] }" defaultValue="${r"${pd."}${var[0] }${r"}"}" clsName="form-control"/>
												<#else>
													<input class="form-control"  type="text" name="${var[0] }" id="${var[0] }" value="${r"${pd."}${var[0] }${r"}"}" datatype="*0-${var[5] }" placeholder="请输入${var[2] }" title="${var[2] }"/>
												</#if>
											</div>
										</#if>
									</#list>
										</div>
									</div>
									<div class="padding_top20">
										<c:if test="${r"${QX.cha == 1 }"}">
										<a class="btn btn-mini btn-info" onclick="tosearch();"  title="查询" style="margin-top: -10px;margin-bottom: -10px;"><i class="ace-icon fa fa-search icon-on-right"></i>
											<span class="bigger-110">查询</span>
										</a>
										</c:if>
                                        <a class="btn btn-mini btn-warning" onclick="reset();"  title="重置" style="margin-top: -10px;margin-bottom: -10px;"><i class="ace-icon fa fa-undo icon-on-right"></i>
                                            <span class="bigger-110">重置</span>
                                        </a>
									</div>
								</div>
							</div>
							<!-- 检索  -->
						</div>
							<table id="simple-table" class="table table-striped table-bordered table-hover" style="margin-top: -10px;margin-bottom: 0px;">
								<thead>
									<tr>
										<th class="center" style="width:35px;">
										<label class="pos-rel"><input type="checkbox" class="ace" id="zcheckbox" /><span class="lbl"></span></label>
										</th>
										<th class="center" style="width:50px;">序号</th>
									<#list fieldList as var>
										<#if var[3] != "否">
										<th class="center">${var[2]}</th>
										</#if>
									</#list>
										<th class="center">操作</th>
									</tr>
								</thead>

								<tbody>
								<!-- 开始循环 -->
								<c:choose>
									<c:when test="${r"${not empty varList}"}">
										<c:if test="${r"${QX.cha == 1 }"}">
										<c:forEach items="${r"${varList}"}" var="var" varStatus="vs">
											<tr>
												<td class='center'>
													<label class="pos-rel"><input type='checkbox' name='ids' value="${r"${var."}UUID${r"}"}" class="ace" /><span class="lbl"></span></label>
												</td>
												<td class='center' style="width: 30px;">${r"${vs.index+1}"}</td>
											<#list fieldList as var>
												<#if var[3] != "否">
												<td class='center'>${r"${var."}${var[0]}${r"}"}</td>
												</#if>
											</#list>
                                                <td class='left'>
                                                    <!-- 操作按钮 -->
                                                    <c:if test="${r"${QX.edit != 1 && QX.del != 1 }"}">
                                                        <span class="label label-large label-grey arrowed-in-right arrowed-in"><i class="ace-icon fa fa-lock" title="无权限"></i></span>
                                                    </c:if>
													<div class="hidden-sm hidden-xs action-buttons">
														<c:if test="${r"${QX.edit == 1 }"}">
														<a class="green" title="编辑" onclick="edit('${r"${var."}UUID${r"}"}');">
															<i class="ace-icon fa fa-edit-plus bigger-130" title="编辑"></i>
														</a>
														</c:if>
														<c:if test="${r"${QX.del == 1 }"}">
														<a class="red"  onclick="del('${r"${var."}UUID${r"}"}');">
                                                            <i class="ace-icon fa fa-trash bigger-130"></i>
														</a>
														</c:if>
													</div>
													<div class="hidden-md hidden-lg">
														<div class="inline pos-rel">
															<button class="btn btn-minier btn-primary dropdown-toggle" data-toggle="dropdown" data-position="auto">
																<i class="ace-icon fa fa-cog icon-only bigger-110"></i>
															</button>

															<ul class="dropdown-menu dropdown-only-icon dropdown-yellow dropdown-menu-right dropdown-caret dropdown-close">
																<c:if test="${r"${QX.edit == 1 }"}">
																<li>
																	<a class="tooltip-success" data-rel="tooltip" onclick="edit('${r"${var."}UUID${r"}"}');" title="修改">
																		<span class="green">
																			<i class="ace-icon fa fa-pencil-square-o bigger-120"></i>
																		</span>
																	</a>
																</li>
																</c:if>
																<c:if test="${r"${QX.del == 1 }"}">
																<li>
																	<a class="tooltip-error" data-rel="tooltip" onclick="del('${r"${var."}UUID${r"}"}');" title="删除">
																		<span class="red">
																			<i class="ace-icon fa fa-trash-o bigger-120"></i>
																		</span>
																	</a>
																</li>
																</c:if>
															</ul>
														</div>
													</div>
												</td>
											</tr>

										</c:forEach>
										</c:if>
										<c:if test="${r"${QX.cha == 0 }"}">
											<tr>
												<td colspan="100" class="center">您无权查看</td>
											</tr>
										</c:if>
									</c:when>
									<c:otherwise>
										<tr class="main_info">
											<td colspan="100" class="center" >没有相关数据</td>
										</tr>
									</c:otherwise>
								</c:choose>
								</tbody>
							</table>
						<div class="page-header position-relative">
							<table style="width:100%;">
								<tr>
									<td style="vertical-align:top;">
										<c:if test="${r"${QX.add == 1 }"}">
										<a class="btn btn-mini btn-success" onclick="add();">新增</a>
										</c:if>
										<c:if test="${r"${QX.del == 1 }"}">
										<a class="btn btn-mini btn-danger" onclick="makeAll('确定要删除选中的数据吗?');" title="批量删除" ><i class='ace-icon fa fa-trash-o bigger-120'></i></a>
										</c:if>
									</td>
									<td style="vertical-align:top;"><div class="pagination" style="float: right;padding-top: 0px;margin-top: 0px;">${r"${page.pageStr}"}</div></td>
								</tr>
							</table>
						</div>
					</form>
				</div>
				<!-- /.page-content -->
			</div>
		</div>
		<!-- /.main-content -->

		<!-- 返回顶部 -->
		<a href="#" id="btn-scroll-up" class="btn-scroll-up btn btn-sm btn-inverse">
			<i class="ace-icon fa fa-angle-double-up icon-only bigger-110"></i>
		</a>

	</div>
	<!-- /.main-container -->

	<!-- basic scripts -->
	<!-- 页面底部js¨ -->
	<%@ include file="../../system/index/foot.jsp"%>
	<!-- 删除时确认窗口 -->
	<script src="static/ace/js/bootbox.js"></script>
	<!-- ace scripts -->
	<script src="static/ace/js/ace/ace.js"></script>
	<!-- 下拉框 -->
	<script src="static/ace/js/chosen.jquery.js"></script>
	<!-- 日期框 -->
	<script src="static/ace/js/date-time/bootstrap-datepicker.js"></script>
	<!--提示框-->
	<script type="text/javascript" src="static/js/jquery.tips.js"></script>
	<script type="text/javascript">
		$(top.hangge());//关闭加载状态
		//检索
		function tosearch(){
			top.jzts();
			$("#searchForm").submit();
		}
        //重置
        function reset(){
            $("#searchForm")[0].reset();
        }
		$(function() {
		
			//日期框
			$('.date-picker').datepicker({
				autoclose: true,
				todayHighlight: true
			});
			
			//下拉框
			if(!ace.vars['touch']) {
				$('.chosen-select').chosen({allow_single_deselect:true}); 
				$(window)
				.off('resize.chosen')
				.on('resize.chosen', function() {
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				}).trigger('resize.chosen');
				$(document).on('settings.ace.chosen', function(e, event_name, event_val) {
					if(event_name != 'sidebar_collapsed') return;
					$('.chosen-select').each(function() {
						 var $this = $(this);
						 $this.next().css({'width': $this.parent().width()});
					});
				});
				$('#chosen-multiple-style .btn').on('click', function(e){
					var target = $(this).find('input[type=radio]');
					var which = parseInt(target.val());
					if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
					 else $('#form-field-select-4').removeClass('tag-input-style');
				});
			}
			
			
			//复选框全选控制
			var active_class = 'active';
			$('#simple-table > thead > tr > th input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				$(this).closest('table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) $(row).addClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', true);
					else $(row).removeClass(active_class).find('input[type=checkbox]').eq(0).prop('checked', false);
				});
			});
		});
		
		//新增
		function add(){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="新增";
			 diag.URL = '<%=basePath%>${objectNameLower}/goAdd.do';
             diag.Width = 800;
             diag.Height = 550;
			 diag.Modal = true;				//有无遮罩窗口
			 diag.ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 if('${r"${page.currentPage}"}' == '0'){
						 top.jzts();
						 setTimeout("self.location=self.location",100);
					 }else{
						 nextPage(${r"${page.currentPage}"});
					 }
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//删除
		function del(Id){
			bootbox.confirm("确定要删除吗?", function(result) {
				if(result) {
					top.jzts();
					var url = "<%=basePath%>${objectNameLower}/delete.do?UUID="+Id+"&tm="+new Date().getTime();
					$.get(url,function(data){
						nextPage(${r"${page.currentPage}"});
					});
				}
			});
		}
		
		//修改
		function edit(Id){
			 top.jzts();
			 var diag = new top.Dialog();
			 diag.Drag=true;
			 diag.Title ="编辑";
			 diag.URL = '<%=basePath%>${objectNameLower}/goEdit.do?UUID='+Id;
			 diag.Width = 800;
			 diag.Height = 550;
			 diag.Modal = true;				//有无遮罩窗口
			 diag.ShowMaxButton = true;	//最大化按钮
		     diag.ShowMinButton = true;		//最小化按钮 
			 diag.CancelEvent = function(){ //关闭事件
				 if(diag.innerFrame.contentWindow.document.getElementById('zhongxin').style.display == 'none'){
					 nextPage(${r"${page.currentPage}"});
				}
				diag.close();
			 };
			 diag.show();
		}
		
		//批量操作
		function makeAll(msg){
			bootbox.confirm(msg, function(result) {
				if(result) {
					var str = '';
					for(var i=0;i < document.getElementsByName('ids').length;i++){
					  if(document.getElementsByName('ids')[i].checked){
					  	if(str=='') str += document.getElementsByName('ids')[i].value;
					  	else str += ',' + document.getElementsByName('ids')[i].value;
					  }
					}
					if(str==''){
						bootbox.dialog({
							message: "<span class='bigger-110'>您没有选择任何内容!</span>",
							buttons: 			
							{ "button":{ "label":"确定", "className":"btn-sm btn-success"}}
						});
						$("#zcheckbox").tips({
							side:1,
				            msg:'点这里全选',
				            bg:'#AE81FF',
				            time:8
				        });
						return;
					}else{
						if(msg == '确定要删除选中的数据吗?'){
							top.jzts();
							$.ajax({
								type: "POST",
								url: '<%=basePath%>${objectNameLower}/deleteAll.do?tm='+new Date().getTime(),
						    	data: {DATA_IDS:str},
								dataType:'json',
								//beforeSend: validateData,
								cache: false,
								success: function(data){
									 $.each(data.list, function(i, list){
											nextPage(${r"${page.currentPage}"});
									 });
								}
							});
						}
					}
				}
			});
		};
		
		//导出excel
		function toExcel(){
			window.location.href='<%=basePath%>${objectNameLower}/excel.do';
		}
	</script>


</body>
</html>
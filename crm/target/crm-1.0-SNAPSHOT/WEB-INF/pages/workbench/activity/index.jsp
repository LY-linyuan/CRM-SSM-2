<%@ page contentType="text/html;charset=UTF-8" language="java" %><html>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
String base = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/";
%>
<head>
	<base href="<%=base%>">
<meta charset="UTF-8">

<link href="jquery/bootstrap_3.3.0/css/bootstrap.min.css" type="text/css" rel="stylesheet" />
<link href="jquery/bootstrap-datetimepicker-master/css/bootstrap-datetimepicker.min.css" type="text/css" rel="stylesheet" />
<link rel="stylesheet" type="text/css" href="jquery/bs_pagination-master/css/jquery.bs_pagination.min.css">

<script type="text/javascript" src="jquery/jquery-1.11.1-min.js"></script>
<script type="text/javascript" src="jquery/bootstrap_3.3.0/js/bootstrap.min.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/js/bootstrap-datetimepicker.js"></script>
<script type="text/javascript" src="jquery/bootstrap-datetimepicker-master/locale/bootstrap-datetimepicker.zh-CN.js"></script>

<script type="text/javascript" src="jquery/bs_pagination-master/js/jquery.bs_pagination.min.js"></script>
<script type="text/javascript" src="jquery/bs_pagination-master/localization/en.js"></script>

<script type="text/javascript">

	$(function(){

		$("#creatActivityBtn").click(function () {
			$("#createActivityModal").modal("show");
			return;
		})
		$("#saveCreateActivityBtn").click(function () {
			var owner = $("#create-marketActivityOwner").val();
			var name = $.trim($("#create-marketActivityName").val());
			var startDate = $("#create-startTime").val();
			var endDate = $("#create-endTime").val();
			var cost = $.trim($("#create-cost").val());
			var description = $.trim($("#create-describe").val());
			if (owner === '') {
				alert("所有者不能为空!");
				return;
			}
			if (name === '') {
				alert("名称不能为空!");
				return;
			}
			if (startDate != '' && endDate != '') {
				if (startDate > endDate) {
					alert("结束日期不能比开始日期小!");
					return;
				}
			}

			var regExp = /^(([1-9]\d*)|0)$/;
			if (!regExp.test(cost)) {
				alert("费用只能为非负整数!");
				return;
			}
			$.ajax({
				type : 'POST',
				url : 'workbench/activity/saveCreatActivity',
				data : {
					owner : owner,
					name : name,
					startDate : startDate,
					endDate : endDate,
					cost : cost,
					description : description
				},
				dataType : 'json',
				success : function (data) {
					if (data.code === '1') {
						$('#createActivityModal').modal('hide');
						// 重置表单
						$('#createActivityForm').get(0).reset();
						// 刷新市场活动列表 稍后做
						selectActivityByConditionOfPageAndCount(1, $('#demo_pag1').bs_pagination('getOption', 'rowsPerPage'));
						$('#checkboxAllBtn').prop('checked', false);
					} else {
						alert(data.message);
						$('#createActivityModal').modal('show');
					}
				},
			})
		})
		$('.myDate').datetimepicker({
			language : 'zh-CN',	// 语言
			format : 'yyyy-mm-dd', //日期格式
			minView : 'month', //可以选择的最小视图
			initialDate : new Date(), // 初始化显示的日期
			autoclose : true, // 设置选择日期之后是否自动关闭日历 默认值是false
			todayBtn : true, // 设置是否显示"今天"按钮  默认值是false
			clearBtn : true // 设置是否显示"清空"按钮  默认值是false
		})

		selectActivityByConditionOfPageAndCount(1, 10);

		function selectActivityByConditionOfPageAndCount (pageNo, pageSize) {
			var selectActivityName = $('#selectActivityName').val();
			var selectActivityOwner = $('#selectActivityOwner').val();
			var startDate = $('#startTime').val();
			var endDate = $('#endTime').val();
			$.ajax({
				url : 'workbench/activity/selectActivityByConditionOfPageAndCount',
				type : 'POST',
				data : {
					name : selectActivityName,
					owner : selectActivityOwner,
					startDate : startDate,
					endDate : endDate,
					pageNo : pageNo,
					pageSize : pageSize
				},
				dataType : 'json',
				success : function (data) {
					var html = '';
					$.each(data.activityList, function (index, activity) {
						html += '<tr class="active">';
						html += '	<td><input type="checkbox" value="' + activity.id + '" /></td>';
						/*html += '	<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'detail.html\';">' + activity.name + '</a></td>';*/
						html += '	<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href=\'workbench/activity/detailActivity?activityId=' + activity.id + '\';">' + activity.name + '</a></td>';
						html += '	<td>' + activity.owner + '</td>';
						html += '	<td>' + activity.startDate + '</td>';
						html += '	<td>' + activity.endDate + '</td>';
						html += '</tr>';
					})
					$('#selectActivityBody').html(html);
					// $('#selectActivityByConditionCount').text(data.totalRows);
					var totalPages = 1;
					if (data.totalRows % pageSize == 0) {
						totalPages = data.totalRows / pageSize;
					} else {
						totalPages = parseInt(data.totalRows / pageSize) + 1;
					}
					$('#demo_pag1').bs_pagination({
						currentPage : pageNo, // 当前页号相当于pageNo
						rowsPerPage : pageSize, // 每页显示记录条数 相当于pageSize
						totalRows : data.totalRows, // 总条数
						totalPages : totalPages, // 总页数必填参数
						visiblePageLinks: 5, // 最多可以显示的卡片数
						showGoToPage: true, // 是否显示"跳转到"选项 默认是true
						showRowsPerPage: true, // 是否显示"每页显示条数"信息 默认是true
						showRowsInfo: true, // 是否显示"记录"信息 默认是true
						onChangePage :function (event, pageObj) {
							selectActivityByConditionOfPageAndCount(pageObj.currentPage, pageObj.rowsPerPage);
							$('#checkboxAllBtn').prop('checked', false);
						}
					})
				}
			})

		}

		$('#selectActivityByConditionOfPageAndCountBtn').click(function () {
			selectActivityByConditionOfPageAndCount(1, $('#demo_pag1').bs_pagination('getOption', 'rowsPerPage'));
		});

		$('#checkboxAllBtn').click(function () {
			$('#selectActivityBody input[type="checkbox"]').prop('checked', this.checked);
		})

		$('#selectActivityBody').on('click', 'input[type="checkbox"]', function () {

			if ($('#selectActivityBody input[type="checkbox"]').size() == $('#selectActivityBody input[type="checkbox"]:checked').size()) {
				$('#checkboxAllBtn').prop('checked', true);
			} else {
				$('#checkboxAllBtn').prop('checked', false);
			}
		})

		$('#deleteActivityByIdsBtn').click(function () {
			var checkedIds =$('#selectActivityBody input[type="checkbox"]:checked');
			if (checkedIds.size() === 0) {
				alert("请选择要删除的市场活动记录");
				return;
			}
			if (window.confirm("您确定要删除吗？")) {
				var ids = '';
				$.each(checkedIds, function () {
					ids += 'id=' + this.value + "&";
				});
				ids = ids.substring(0, ids.length - 1);
				$.ajax({
					url : 'workbench/activity/deleteActivityByIds',
					type : 'POST',
					data : ids,
					dataType : 'json',
					success : function (data) {
						if (data.code === '1') {
							selectActivityByConditionOfPageAndCount(1, $('#demo_pag1').bs_pagination('getOption', 'rowsPerPage'));
							$('#checkboxAllBtn').prop('checked', false);
						} else {
							alert(data.message);
						}
					}
				})


			}

		})


		$('#editSelectActivityById').click(function () {
			var checkboxChecked = $('#selectActivityBody input[type="checkbox"]:checked');
			if (checkboxChecked.size() === 0) {
				alert('请选择需要修改的市场活动');
				return;
			} else if(checkboxChecked.size() > 1) {
				alert('一次只能修改一条市场活动');
				return;
			} else {
				var id = checkboxChecked[0].value;
				$.ajax({
					url : 'workbench/activity/selectActivityById',
					type : 'POST',
					data : {
						id : id,
					},
					dataType : 'json',
					success : function (data) {
						$('#edit-marketActivityId').val(data.id);
						$('#edit-marketActivityOwner').val(data.owner);
						$('#edit-marketActivityName').val(data.name);
						$('#edit-startTime').val(data.startDate);
						$('#edit-endTime').val(data.endDate);
						$('#edit-cost').val(data.cost);
						$('#edit-describe').val(data.description);
						$('#editActivityModal').modal('show');
					}
				})
			}



		})

		$('#updateActivityById').click(function () {
			var id = $('#edit-marketActivityId').val();
			var owner = $('#edit-marketActivityOwner').val();
			var name = $('#edit-marketActivityName').val();
			var startDate = $('#edit-startTime').val();
			var endDate = $('#edit-endTime').val();
			var cost = $('#edit-cost').val();
			var description = $('#edit-describe').val();
			if (owner === '') {
				alert("所有者不能为空!");
				return;
			}
			if (name === '') {
				alert("名称不能为空!");
				return;
			}
			if (startDate != '' && endDate != '') {
				if (startDate > endDate) {
					alert("结束日期不能比开始日期小!");
					return;
				}
			}

			var regExp = /^(([1-9]\d*)|0)$/;
			if (!regExp.test(cost)) {
				alert("费用只能为非负整数!");
				return;
			}
			$.ajax({
				url : 'workbench/activity/updateActivityById',
				type : 'POST',
				data : {
					id : id,
					owner : owner,
					name : name,
					startDate : startDate,
					endDate : endDate,
					cost : cost,
					description : description,
				},
				dataType : 'json',
				success : function (data) {
					if (data.code === '1') {
						selectActivityByConditionOfPageAndCount($('#demo_pag1').bs_pagination('getOption', 'currentPage'), $('#demo_pag1').bs_pagination('getOption', 'rowsPerPage'));
						$('#checkboxAllBtn').prop('checked', false);
						$('#editActivityModal').modal('hide');
					} else {
						alert(data.message);
						$('#editActivityModal').modal('show');
					}
				}
			});

		})

		$('#exportActivityAllBtn').click(function () {
			window.location.href = 'workbench/activity/exportAllActivities';
		})

		$('#importActivityBtn').click(function () {
			var activityFileName = $('#activityFile').val();
			var suffix = activityFileName.substr(activityFileName.lastIndexOf(".") + 1).toLocaleLowerCase();
			if (suffix != 'xls') {
				alert('只支持excel文件！');
				return;
			}
			var activityFile = $('#activityFile')[0].files[0];
			if (activityFile.size > 1024 * 1024 * 5) {
				alert('文件大小不能超过5MB！');
				return;
			}
			var formData = new FormData();
			formData.append("activityFile", activityFile);
			$.ajax({
				url : 'workbench/activity/importActivityList',
				data : formData,
				processData : false, // 设置ajax想后台提交参数之前，是否吧参数统一转换成字符串 默认是true
				contentType : false, // 设置ajax想后台提交参数之前,是否吧所有的参数统一安urlencoded编码 默认是tyue
				type : 'POST',
				dataType : 'json',
				success : function (data) {
					if (data.code == 1) {
						alert('成功导入: ' + data.message + '条市场活动！');
						selectActivityByConditionOfPageAndCount(1, $('#demo_pag1').bs_pagination('getOption', 'rowsPerPage'));
						$('#checkboxAllBtn').prop('checked', false);
						$('#importActivityModal').modal('hide');
					} else {
						alert(data.message);
						$('#importActivityModal').modal('show');
						return;
					}
				}
			})

		})


	});

</script>
</head>
<body>
	<!-- 创建市场活动的模态窗口 -->
	<div class="modal fade" id="createActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel1">创建市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" id="createActivityForm" role="form">

						<div class="form-group">
							<label for="create-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="create-marketActivityOwner">
								  <c:forEach items="${userList}" var="user">
									  <option value="${user.id}">${user.name}</option>
								  </c:forEach>
								</select>
							</div>
                            <label for="create-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-marketActivityName">
                            </div>
						</div>

						<div class="form-group">
							<label for="create-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-startTime" readonly>
							</div>
							<label for="create-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="create-endTime" readonly>
							</div>
						</div>
                        <div class="form-group">

                            <label for="create-cost" class="col-sm-2 control-label">成本</label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="create-cost">
                            </div>
                        </div>
						<div class="form-group">
							<label for="create-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="create-describe"></textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="saveCreateActivityBtn">保存</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 修改市场活动的模态窗口 -->
	<div class="modal fade" id="editActivityModal" role="dialog">
		<div class="modal-dialog" role="document" style="width: 85%;">
			<div class="modal-content">
				<div class="modal-header">
					<button type="button" class="close" data-dismiss="modal">
						<span aria-hidden="true">×</span>
					</button>
					<h4 class="modal-title" id="myModalLabel2">修改市场活动</h4>
				</div>
				<div class="modal-body">

					<form class="form-horizontal" role="form">

						<div id="edit-marketActivityId" hidden></div>
						<div class="form-group">
							<label for="edit-marketActivityOwner" class="col-sm-2 control-label">所有者<span style="font-size: 15px; color: red;">*</span></label>
							<div class="col-sm-10" style="width: 300px;">
								<select class="form-control" id="edit-marketActivityOwner">
									<c:forEach items="${userList}" var="user">
										<option value="${user.id}">${user.name}</option>
									</c:forEach>
								</select>
							</div>
                            <label for="edit-marketActivityName" class="col-sm-2 control-label">名称<span style="font-size: 15px; color: red;">*</span></label>
                            <div class="col-sm-10" style="width: 300px;">
                                <input type="text" class="form-control" id="edit-marketActivityName" value="发传单">
                            </div>
						</div>

						<div class="form-group">
							<label for="edit-startTime" class="col-sm-2 control-label">开始日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="edit-startTime" readonly value="2020-10-10">
							</div>
							<label for="edit-endTime" class="col-sm-2 control-label">结束日期</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control myDate" id="edit-endTime" readonly value="2020-10-20">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-cost" class="col-sm-2 control-label">成本</label>
							<div class="col-sm-10" style="width: 300px;">
								<input type="text" class="form-control" id="edit-cost" value="5,000">
							</div>
						</div>

						<div class="form-group">
							<label for="edit-describe" class="col-sm-2 control-label">描述</label>
							<div class="col-sm-10" style="width: 81%;">
								<textarea class="form-control" rows="3" id="edit-describe">市场活动Marketing，是指品牌主办或参与的展览会议与公关市场活动，包括自行主办的各类研讨会、客户交流会、演示会、新产品发布会、体验会、答谢会、年会和出席参加并布展或演讲的展览会、研讨会、行业交流会、颁奖典礼等</textarea>
							</div>
						</div>

					</form>

				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
					<button type="button" id="updateActivityById" class="btn btn-primary">更新</button>
				</div>
			</div>
		</div>
	</div>

	<!-- 导入市场活动的模态窗口 -->
    <div class="modal fade" id="importActivityModal" role="dialog">
        <div class="modal-dialog" role="document" style="width: 85%;">
            <div class="modal-content">
                <div class="modal-header">
                    <button type="button" class="close" data-dismiss="modal">
                        <span aria-hidden="true">×</span>
                    </button>
                    <h4 class="modal-title" id="myModalLabel">导入市场活动</h4>
                </div>
                <div class="modal-body" style="height: 350px;">
                    <div style="position: relative;top: 20px; left: 50px;">
                        请选择要上传的文件：<small style="color: gray;">[仅支持.xls]</small>
                    </div>
                    <div style="position: relative;top: 40px; left: 50px;">
                        <input type="file" id="activityFile">
                    </div>
                    <div style="position: relative; width: 400px; height: 320px; left: 45% ; top: -40px;" >
                        <h3>重要提示</h3>
                        <ul>
                            <li>操作仅针对Excel，仅支持后缀名为XLS的文件。</li>
                            <li>给定文件的第一行将视为字段名。</li>
                            <li>请确认您的文件大小不超过5MB。</li>
                            <li>日期值以文本形式保存，必须符合yyyy-MM-dd格式。</li>
                            <li>日期时间以文本形式保存，必须符合yyyy-MM-dd HH:mm:ss的格式。</li>
                            <li>默认情况下，字符编码是UTF-8 (统一码)，请确保您导入的文件使用的是正确的字符编码方式。</li>
                            <li>建议您在导入真实数据之前用测试文件测试文件导入功能。</li>
                        </ul>
                    </div>
                </div>
                <div class="modal-footer">
                    <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
                    <button id="importActivityBtn" type="button" class="btn btn-primary">导入</button>
                </div>
            </div>
        </div>
    </div>


	<div>
		<div style="position: relative; left: 10px; top: -10px;">
			<div class="page-header">
				<h3>市场活动列表</h3>
			</div>
		</div>
	</div>
	<div style="position: relative; top: -20px; left: 0px; width: 100%; height: 100%;">
		<div style="width: 100%; position: absolute;top: 5px; left: 10px;">

			<div class="btn-toolbar" role="toolbar" style="height: 80px;">
				<form class="form-inline" role="form" style="position: relative;top: 8%; left: 5px;">

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">名称</div>
				      <input class="form-control" type="text" id="selectActivityName">
				    </div>
				  </div>

				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">所有者</div>
				      <input class="form-control" type="text" id="selectActivityOwner">
				    </div>
				  </div>


				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">开始日期</div>
					  <input class="form-control" type="text" id="startTime" />
				    </div>
				  </div>
				  <div class="form-group">
				    <div class="input-group">
				      <div class="input-group-addon">结束日期</div>
					  <input class="form-control" type="text" id="endTime">
				    </div>
				  </div>

				  <button class="btn btn-default" type="button" id="selectActivityByConditionOfPageAndCountBtn">查询</button>

				</form>
			</div>
			<div class="btn-toolbar" role="toolbar" style="background-color: #F7F7F7; height: 50px; position: relative;top: 5px;">
				<div class="btn-group" style="position: relative; top: 18%;">
				  <button type="button" class="btn btn-primary" id="creatActivityBtn"><span class="glyphicon glyphicon-plus"></span> 创建</button>
				  <button type="button" class="btn btn-default" id="editSelectActivityById"><span class="glyphicon glyphicon-pencil"></span> 修改</button>
				  <button type="button" class="btn btn-danger" id="deleteActivityByIdsBtn" ><span class="glyphicon glyphicon-minus"></span> 删除</button>
				</div>
				<div class="btn-group" style="position: relative; top: 18%;">
                    <button type="button" class="btn btn-default" data-toggle="modal" data-target="#importActivityModal" ><span class="glyphicon glyphicon-import"></span> 上传列表数据（导入）</button>
                    <button id="exportActivityAllBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（批量导出）</button>
                    <button id="exportActivityXzBtn" type="button" class="btn btn-default"><span class="glyphicon glyphicon-export"></span> 下载列表数据（选择导出）</button>
                </div>
			</div>
			<div style="position: relative;top: 10px;">
				<table class="table table-hover">
					<thead>
						<tr style="color: #B3B3B3;">
							<td><input type="checkbox" id="checkboxAllBtn" /></td>
							<td>名称</td>
                            <td>所有者</td>
							<td>开始日期</td>
							<td>结束日期</td>
						</tr>
					</thead>
					<tbody id="selectActivityBody">
						<%--<tr class="active">
							<td><input type="checkbox" /></td>
							<td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>
                            <td>zhangsan</td>
							<td>2020-10-10</td>
							<td>2020-10-20</td>
						</tr>
                        <tr class="active">
                            <td><input type="checkbox" /></td>
                            <td><a style="text-decoration: none; cursor: pointer;" onclick="window.location.href='detail.html';">发传单</a></td>
                            <td>zhangsan</td>
                            <td>2020-10-10</td>
                            <td>2020-10-20</td>
                        </tr>--%>
					</tbody>
				</table>
				<div id="demo_pag1"></div>
			</div>

			<%--<div style="height: 50px; position: relative;top: 30px;">
				<div>
					<button type="button" class="btn btn-default" style="cursor: default;">共<b id="selectActivityByConditionCount"></b>条记录</button>
				</div>
				<div class="btn-group" style="position: relative;top: -34px; left: 110px;">
					<button type="button" class="btn btn-default" style="cursor: default;">显示</button>
					<div class="btn-group">
						<button type="button" class="btn btn-default dropdown-toggle" data-toggle="dropdown">
							10
							<span class="caret"></span>
						</button>
						<ul class="dropdown-menu" role="menu">
							<li><a href="#">20</a></li>
							<li><a href="#">30</a></li>
						</ul>
					</div>
					<button type="button" class="btn btn-default" style="cursor: default;">条/页</button>
				</div>
				<div style="position: relative;top: -88px; left: 285px;">
					<nav>
						<ul class="pagination">
							<li class="disabled"><a href="#">首页</a></li>
							<li class="disabled"><a href="#">上一页</a></li>
							<li class="active"><a href="#">1</a></li>
							<li><a href="#">2</a></li>
							<li><a href="#">3</a></li>
							<li><a href="#">4</a></li>
							<li><a href="#">5</a></li>
							<li><a href="#">下一页</a></li>
							<li class="disabled"><a href="#">末页</a></li>
						</ul>
					</nav>
				</div>
			</div>--%>

		</div>

	</div>
</body>
</html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="../inc.jsp"></jsp:include>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>



<c:if test="${fn:contains(sessionInfo.resourceList, '/meetingfile/download')}">
	<script type="text/javascript">
		$.canDownload = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/meetingfile/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/meetingfile/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>


	<script type="text/javascript">
	var meetingfileDataGrid;
	
	$(function() {
		meetingfileDataGrid = $('#dataGridMeetingFile').datagrid({
			url : '${ctx}/meetingfile/dataGrid',
			fit : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'uploaddate',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				width : '100',
				title : '会议编号',
				field : 'meetid',
				sortable : true
			}, {
				width : '80',
				title : '会议名称',
				field : 'meetingname',
			},{
				width : '200',
				title : '文件名',
				field : 'filnameurl',
			},{
				width : '130',
				title : '上传时间',
				field : 'uploaddate',
				sortable : true
			},{
				width : '80',
				title : '上传人',
				field : 'uploaduser',
			}, {
				field : 'action',
				title : '操作',
				width : 120,
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if ($.canDownload) {
					str += $.formatString('<a href="javascript:void(0)" onclick="downloadFileByForm(\'{0}\');" >下载</a>', row.filename);
					}
					if ($.canEdit) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="editMeetingFileFun(\'{0}\');" >编辑</a>', row.id);
					}
					if ($.canDelete) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="deleteMeetingFileFun(\'{0}\');" >删除</a>', row.id);
					}
					return str;
				}
			}] ],
			toolbar : '#toolbarUser'
		});
	});
	
	function addMeetingFileFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 270,
			href : '${ctx}/meetingfile/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_meetingfileDataGrid= meetingfileDataGrid;//因为添加成功之后，需要刷新这个meetingfileDataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#meetingfileAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function downloadFileByForm(fileName) {
        var url = '${ctx}/meetingfile/downloadMeetingfile';
        var form = $("<form></form>").attr("action", url).attr("method", "post");
        form.append($("<input></input>").attr("type", "hidden").attr("name", "fileName").attr("value", fileName));
        form.appendTo('body').submit().remove();
    }
	
	
	

	
	function deleteMeetingFileFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = meetingfileDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			meetingfileDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前会议文件？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/meetingfile/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						meetingfileDataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function editMeetingFileFun(id) {
		if (id == undefined) {
			var rows = meetingfileDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			meetingfileDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 500,
			height : 260,
			href : '${ctx}/meetingfile/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_meetingfileDataGrid = meetingfileDataGrid;
					var f = parent.$.modalDialog.handler.find('#meetingfileEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function searchFun() {
		meetingfileDataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	
	function cleanFun() {
		$('#searchForm').form('clear');
		meetingfileDataGrid.datagrid('load', {});
	}
	</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 100px;overflow: hidden;background-color: #fff">
		<form id="searchForm">
			<table>
				<tr>
					<td colspan="6">
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFun();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFun();">清空</a>
					</td>
				</tr>
				<tr>
					<th>会议编号:</th>
					<td><input name="meetid"  class="easyui-textbox" prompt="请输入会议编号" /></td>
					<th>会议名称:</th>
					<td><input name="meetingname" class="easyui-textbox"  prompt="请输入会议名称"/></td>
					<th>文件名:</th>
					<td><input name="filename" class="easyui-textbox"   prompt="请输入文件名"/></td>
				</tr>
				<tr>
					<th>上传人:</th>
					<td><input name="uploaduser" class="easyui-textbox"   prompt="请输入上传人"/></td>
					<th>上传时间:</th>
					<td colspan="3">
						<input name="uploaddate" placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />至<input name="uploaddateend"  placeholder="点击选择时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" readonly="readonly" />
					</td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'会议文件管理'" >
		<table id="dataGridMeetingFile" data-options="fit:true,border:false"></table>
	</div>
	
	
	<div id="toolbarUser" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/meetingfile/add')}">
			<a onclick="addMeetingFileFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</div>

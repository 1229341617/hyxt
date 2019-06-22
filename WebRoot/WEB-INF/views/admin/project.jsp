<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="../inc.jsp"></jsp:include>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:if test="${fn:contains(sessionInfo.resourceList, '/project/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/project/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<script type="text/javascript">
	var projectDataGrid;

	$(function() {
		projectDataGrid = $('#dataGridProject').datagrid({
			url : '${ctx}/project/dataGrid',
			fit : true,
			nowrap : false,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdatetime',
			sortOrder : 'desc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [  {
				width : '80',
				title : '工程类型',
				field : 'type',
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '高速公路';
					case 1:
						return '干线公路';
					case 2:
						return '港口';
					case 3:
						return '航道';
					case 4:
						return '航电枢纽';
					case 5:
						return '信息化建设';
					}
				}
			},{
				width : '80',
				title : '路线编号',
				field : 'xmbh',
				sortable : true
			},{
				width : '100',
				title : '名称',
				field : 'name'
			},{
				width : '60',
				title : '部门ID',
				field : 'organizationId',
				hidden : true
			},{
				width : '80',
				title : '主管单位',
				field : 'organizationName'
			},{
				width : '70',
				title : '项目法人',
				field : 'xmfr'
			},{
				width : '70',
				title : '建设性质',
				field : 'jsxz'
			},{
				width : '140',
				title : '创建时间',
				field : 'createdatetime',
				sortable : true
			},{
				width : '100',
				title : '投资金额(万元)',
				field : 'tzje',
				sortable : true
			},{
				width : '120',
				title : 'IP',
				field : 'ip',
			}, {
				width : '60',
				title : '端口号',
				field : 'dkh'
			}, {
				width : '80',
				title : '用户名',
				field : 'yhm'
			},{
				width : '140',
				title : '账户启用时间',
				field : 'accountOpenTime'
			},{
				width : '140',
				title : '账户停用时间',
				field : 'accountStopTime'
			},{
				width : '60',
				title : '排序号',
				field : 'seq',
				sortable : true
			},{
				width : '60',
				title : '状态',
				field : 'state',
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '启用';
					case 1:
						return '停用';
					}
				}
			},{
				width : '60',
				title : '详情',
				field : '详情',
				formatter : function(value, row, index) {
					return $.formatString('<a href="javascript:void(0)" onclick="xqFun(\'{0}\');" >详情</a>', row.id);
				}
			},{
				field : 'action',
				title : '操作',
				width : 100,
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if ($.canEdit) {
						str += $.formatString('<a href="javascript:void(0)" onclick="editProjectFun(\'{0}\');" >编辑</a>', row.id);
					}
					if(row.name != "南益高速公路"){
						if ($.canDelete) {
							str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
							str += $.formatString('<a href="javascript:void(0)" onclick="deleteProjectFun(\'{0}\');" >删除</a>', row.id);
						}
					}
					return str;
				}
			}] ],
			toolbar : '#toolbarProject'
		});
	});
	
	function xqFun(id) {
		$('#xmglxq').dialog({
			title: '项目管理',
			width: "500px",
			height: "300px",
			closed: false,
			cache: false,
			href: '${ctx}/project/xmglxq?id=' + id,
			//iconCls:'icon-ok',
			modal: true
			});
		progressClose();
	}
	
	function addProjectFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 550,
			href : '${ctx}/project/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_projectDataGrid= projectDataGrid;//因为添加成功之后，需要刷新这个projectDataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#projectAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function deleteProjectFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = projectDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			projectDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前项目？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/project/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						projectDataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function editProjectFun(id) {
		if (id == undefined) {
			var rows = projectDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			projectDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 500,
			height : 550,
			href : '${ctx}/project/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_projectDataGrid= projectDataGrid;//因为添加成功之后，需要刷新这个projectDataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#projectEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	$('#proOrganizationId').combotree({
		url : '${ctx}/organization/tree',
		parentField : 'pid',
		lines : true,
		panelHeight : 'auto',
		value : '${project.organizationId}'
	});
	
	function searchFunProject() {
		projectDataGrid.datagrid('load', $.serializeObject($('#searchFormProject')));
	}
	function cleanFunProject() {
		$('#searchFormProject input').val('');
		projectDataGrid.datagrid('load', {}); 
	}
</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: auto; overflow: hidden;background-color: #fff">
		<form id="searchFormProject">
			<table>
				<tr>
					<th>工程类型:</th>
					<td><select name="type" class="easyui-combobox" panelHeight="auto" style="width:142px">
						<!-- <option value="">--请选择工程类型--</option> -->
						<option></option>
						<option value="0">高速公路</option>
						<option value="1">干线公路</option>
						<option value="2">港口</option>
						<option value="3">航道</option>
						<option value="4">航电枢纽</option>
						<option value="5">信息化建设</option>
				    </select></td>
					<th>项目名称:</th>
					<td><input name="name" class="easyui-textbox" placeholder="请输入用户姓名"/></td>
					<th>所属部门:</th>
					<td><input id="proOrganizationId" name="organizationId" class="easyui-textbox" /></td>
					<!-- <th>项目法人:</th>
					<td><input name="xmfr" class="easyui-textbox" placeholder=""/></td> -->
					<th>建设性质:</th>
					<td><select name="jsxz" class="easyui-combobox" panelHeight="auto" style="width:142px">
						<!-- <option value="">--请选择建设性质--</option> -->
						<option></option>
						<option value="新建项目">新建项目</option>
						<option value="扩建项目">扩建项目</option>
						<option value="停建项目">停建项目</option>
				    </select></td>
					<th>生效日期:</th>
					<td><input name="accountOpenTime" class="easyui-datetimebox" /></td>
					<th>停用日期:</th>
					<td><input name="accountStopTime" class="easyui-datetimebox" /></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunProject();">查询</a></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFunProject();">清空</a></td>
					<!-- <td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunProject();">查询</a>
						<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFunProject();">清空</a></td> -->
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'项目列表'" >
		<table id="dataGridProject" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbarProject" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/project/add')}">
			<a onclick="addProjectFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</div>
<div id="xmglxq"></div>
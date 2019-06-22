<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="../inc.jsp"></jsp:include>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:if test="${fn:contains(sessionInfo.resourceList, '/role/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/role/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/role/grant')}">
	<script type="text/javascript">
		$.canGrant = true;
	</script>
</c:if>

	<script type="text/javascript">
	var roleDataGrid;

	$(function() {
		var organizationTree;
		organizationTree = $('#organizationTreeRole').tree({
			url : '${ctx}/organization/tree',
			parentField : 'pid',
			lines : true,
			onClick : function(node) {
				roleDataGrid.datagrid('load', {
					organizationId: node.id
				});
			}
		});
		
		roleDataGrid = $('#dataGrid').datagrid({
			url : '${ctx}/role/dataGrid',
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'id',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				width : '100',
				title : 'id',
				field : 'id',
				sortable : true
			}, {
				width : '80',
				title : '名称',
				field : 'name',
				sortable : true
			},{
				width : '80',
				title : '部门ID',
				field : 'organizationId',
				hidden : true
			}, {
				width : '80',
				title : '所属部门',
				field : 'organizationName'
			}, {
				width : '80',
				title : '角色代码',
				field : 'roleId',
				sortable : true
			} , {
				width : '80',
				title : '排序号',
				field : 'seq',
				sortable : true
			}, {
				width : '80',
				title : '是否默认',
				field : 'isdefault',
				sortable : true,
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '默认';
					case 1:
						return '否';	
					}
				}
			}, {
				width : '200',
				title : '描述',
				field : 'description'
			} , {
				field : 'action',
				title : '操作',
				width : 120,
				formatter : function(value, row, index) {
					var str = '&nbsp;';
						if ($.canGrant) {
							str += $.formatString('<a href="javascript:void(0)" onclick="grantRoleFun(\'{0}\');" >授权</a>', row.id);
						}
					if(row.isdefault!=0){
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						if ($.canEdit) {
							str += $.formatString('<a href="javascript:void(0)" onclick="editRoleFun(\'{0}\');" >编辑</a>', row.id);
						}
						str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
						if ($.canDelete) {
							str += $.formatString('<a href="javascript:void(0)" onclick="deleteRoleFun(\'{0}\');" >删除</a>', row.id);
						}
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbarRole'
		});
	});
	
	function addRoleFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 300,
			href : '${ctx}/role/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_roleDataGrid = roleDataGrid;//因为添加成功之后，需要刷新这个roleDataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#roleAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function deleteRoleFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = roleDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			roleDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前角色？', function(b) {
			if (b) {
				progressLoad();
				$.post('${ctx}/role/delete', {
					id : id
				}, function(result) {
					if (result.success) {
						parent.$.messager.alert('提示', result.msg, 'info');
						roleDataGrid.datagrid('reload');
					}
					progressClose();
				}, 'JSON');
			}
		});
	}
	
	function editRoleFun(id) {
		if (id == undefined) {
			var rows = roleDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			roleDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 500,
			height : 300,
			href : '${ctx}/role/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_roleDataGrid = roleDataGrid;//因为添加成功之后，需要刷新这个roleDataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#roleEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function grantRoleFun(id) {
		if (id == undefined) {
			var rows = roleDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			roleDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		
		parent.$.modalDialog({
			title : '授权',
			width : 500,
			height : 500,
			href : '${ctx}/role/grantPage?id=' + id,
			buttons : [ {
				text : '授权',
				handler : function() {
					parent.$.modalDialog.openner_roleDataGrid = roleDataGrid;//因为添加成功之后，需要刷新这个roleDataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#roleGrantForm');
					f.submit();
				}
			} ]
		});
	}
	
	</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<!-- <div data-options="region:'center',fit:true,border:false">
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div> -->
	<div data-options="region:'center',border:true,title:'角色列表'" >
		<table id="dataGrid" data-options="fit:true,border:false"></table>
	</div>
	<div data-options="region:'west',border:true,split:false,title:'组织机构'"  style="width:180px;overflow: hidden; ">
		<ul id="organizationTreeRole"  style="width:160px;margin: 10px 10px 10px 10px">
		</ul>
	</div>
	<div id="toolbarRole" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/role/add')}">
			<a onclick="addRoleFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
</div>

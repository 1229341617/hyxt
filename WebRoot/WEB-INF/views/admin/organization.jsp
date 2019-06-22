<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="../inc.jsp"></jsp:include>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<c:if test="${fn:contains(sessionInfo.resourceList, '/organization/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/organization/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>

<script type="text/javascript">
	var treeGrid;
	$(function() {
		treeGrid = $('#treeGridO').treegrid({
			url : '${ctx}/organization/treeGrid',
			idField : 'id',
			treeField : 'name',
			parentField : 'pid',
			fit : true,
			fitColumns : false,
			border : false,
			frozenColumns : [ [ {
				title : 'id',
				field : 'id',
				width : 10,
				hidden : true
			} ] ],
			columns : [ [ {
				field : 'code',
				title : '编号',
				width : 50
			},{
				field : 'name',
				title : '部门名称',
				width : 180
			}, {
				field : 'seq',
				title : '排序',
				width : 30
			}, {
				field : 'icon',
				title : '图标',
				width : 80
			},  {
				width : '120',
				title : '创建时间',
				field : 'createdatetime'
			},{
				field : 'pid',
				title : '上级资源ID',
				width : 150,
				hidden : true
			}, {
				field : 'address',
				title : '地址',
				width : 120
			} , {
				field : 'shortname',
				title : '核算简称',
				width : 50
			}, {
				field : 'isaccount',
				title : '核算单位?',
				width : 50,
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '否';
					case 1:
						return '是';
					}
				}
			},{
				field : 'action',
				title : '操作',
				width : 80,
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if ($.canEdit) {
					str += $.formatString('<a href="javascript:void(0)" onclick="editOrganizationFun(\'{0}\');" >编辑</a>', row.id);
					}
					if ($.canDelete) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="deleteOrganizationFun(\'{0}\');" >删除</a>', row.id);
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbarO'
		});
	});
	
	function editOrganizationFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.modalDialog({
				title : '编辑',
				width : 500,
				height : 300,
				href : '${ctx}/organization/editPage?id=' + node.id,
				buttons : [ {
					text : '编辑',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#organizationEditForm');
						f.submit();
					}
				} ]
			});
		}
	}
	
	function deleteOrganizationFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function(b) {
				if (b) {
					progressLoad();
					$.post('${ctx}/organization/delete', {
						id : node.id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							treeGrid.treegrid('reload');
						}else{
							parent.$.messager.alert('提示', result.msg, 'info');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
	}
	
	function addOrganizationFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 300,
			href : '${ctx}/organization/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#organizationAddForm');
					f.submit();
				}
			} ]
		});
	}
	</script>


	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"  style="overflow: hidden;">
			<table id="treeGridO"></table>
		</div>
		
		<div id="toolbarO" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/organization/add')}">
			<a onclick="addOrganizationFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>
	</div>

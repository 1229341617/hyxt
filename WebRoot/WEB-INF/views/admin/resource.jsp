<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="../inc.jsp"></jsp:include>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:if test="${fn:contains(sessionInfo.resourceList, '/resource/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/resource/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>
<title>资源管理1</title>
<script type="text/javascript">
	var treeGrid;
	$(function() {
		
		treeGrid = $('#treeGrid').treegrid({
			url : '${ctx}/resource/treeGrid',
			idField : 'id',
			treeField : 'name',
			parentField : 'pid',
			fit : true,
			fitColumns : false,
			border : false,
			frozenColumns : [ [ {
				title : '编号',
				field : 'id',
				width : 40
			} ] ],
			columns : [ [ {
				field : 'name',
				title : '资源名称',
				width : 250
			}, {
				field : 'url',
				title : '资源路径',
				width : 200
			}, {
				field : 'seq',
				title : '排序',
				width : 40
			}, {
				field : 'icon',
				title : '图标',
				width : 100
			}, {
				field : 'resourcetype',
				title : '资源类型',
				width : 80,
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '菜单';
					case 1:
						return '按钮';
					case 2:
						return '选项卡';
					}
				}
			}, {
				field : 'pid',
				title : '上级资源ID',
				width : 150,
				hidden : true
			}, {
				field : 'cstate',
				title : '状态',
				width : 40,
				formatter : function(value, row, index) {
					switch (value) {
					case 0:
						return '正常';
					case 1:
						return '停用';
					}
				}
			}, {
				field : 'action',
				title : '操作',
				width : 80,
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					if ($.canEdit) {
					str += $.formatString('<a href="javascript:void(0)" onclick="editResourceFun(\'{0}\');" >编辑</a>', row.id);
					}
					if ($.canDelete) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="deleteResourceFun(\'{0}\');" >删除</a>', row.id);
					}
					return str;
				}
			} ] ],
			toolbar : '#toolbarResource'
		});
	});
	
	function editResourceFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.modalDialog({
				title : '编辑',
				width : 500,
				height : 350,
				href : '${ctx}/resource/editPage?id=' + id,
				buttons : [ {
					text : '编辑',
					handler : function() {
						parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
						var f = parent.$.modalDialog.handler.find('#resourceEditForm');
						f.submit();
					}
				} ]
			});
		}
	}
	
	function deleteResourceFun(id) {
		if (id != undefined) {
			treeGrid.treegrid('select', id);
		}
		var node = treeGrid.treegrid('getSelected');
		if (node) {
			parent.$.messager.confirm('询问', '您是否要删除当前资源？删除当前资源会连同子资源一起删除!', function(b) {
				if (b) {
					progressLoad();
					$.post('${pageContext.request.contextPath}/resource/delete', {
						id : node.id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							treeGrid.treegrid('reload');
							parent.layout_west_tree.tree('reload');
						}
						progressClose();
					}, 'JSON');
				}
			});
		}
	}
	
	function addResourceFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 500,
			height : 350,
			href : '${ctx}/resource/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_treeGrid = treeGrid;//因为添加成功之后，需要刷新这个treeGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#resourceAddForm');
					f.submit();
				}
			} ]
		});
	}
	</script>

	<div class="easyui-layout" data-options="fit:true,border:false">
		<div data-options="region:'center',border:false"  style="overflow: hidden;">
			<table id="treeGrid"></table>
		</div>
	</div>
	
	<div id="toolbarResource" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/resource/add')}">
			<a onclick="addResourceFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">添加</a>
		</c:if>
	</div>

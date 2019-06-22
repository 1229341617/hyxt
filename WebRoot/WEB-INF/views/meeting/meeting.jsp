<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<jsp:include page="../inc.jsp"></jsp:include>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>



<c:if test="${fn:contains(sessionInfo.resourceList, '/meeting/edit')}">
	<script type="text/javascript">
		$.canEdit = true;
	</script>
</c:if>
<c:if test="${fn:contains(sessionInfo.resourceList, '/meeting/delete')}">
	<script type="text/javascript">
		$.canDelete = true;
	</script>
</c:if>


	<script type="text/javascript">
	var meetingDataGrid;
	
	$(function() {
		meetingDataGrid = $('#dataGridMeeting').datagrid({
			url : '${ctx}/meeting/dataGrid',
			fit : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			sortName : 'createdate',
			sortOrder : 'asc',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				width : '100',
				title : '会议编号',
				field : 'meetid',
				sortable : true
			}, {
				width : '120',
				title : '会议名称',
				field : 'meetname'
			},{
				width : '110',
				title : '会议室',
				field : 'meetingroom',
			},{
				width : '140',
				title : '开始时间',
				field : 'startdate',
				sortable : true
			},{
				width : '140',
				title : '结束时间',
				field : 'finishdate',
				sortable : true
			},{
                width : '80',
                title : '发起人',
                field : 'username'
            }, {
                width : '70',
                title : '文件预览</span>',
                field : 'isView',
                formatter : function(value, row, index) {
                	if(value == '1'){
						var str = '&nbsp;';
						str += $.formatString('<a href="javascript:void(0)" onclick="viewMeetingFilesFun(\'{0}\');" >预览文件</a>', row.id);
						return str;
                	}
				}
            },{
				width : '130',
				title : '操作',
				field : 'action',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="viewMeetingFun(\'{0}\');" >查看</a>', row.id);
					if ($.canEdit) {
						str+= "&nbsp;&nbsp;|&nbsp;&nbsp;";
					str += $.formatString('<a href="javascript:void(0)" onclick="editMeetingFun(\'{0}\');" >编辑</a>', row.id);
					}
					if ($.canDelete) {
					str += '&nbsp;&nbsp;|&nbsp;&nbsp;';
					str += $.formatString('<a href="javascript:void(0)" onclick="deleteMeetingFun(\'{0}\');" >删除</a>', row.id);
					}
					return str;
				}
			}] ],
			toolbar : '#toolbarMeeting'
		});
	});
	
	function addMeetingFun() {
		parent.$.modalDialog({
			title : '添加',
			width : 580,
			height : 320,
			href : '${ctx}/meeting/addPage',
			buttons : [ {
				text : '添加',
				handler : function() {
					parent.$.modalDialog.openner_meetingDataGrid= meetingDataGrid;//因为添加成功之后，需要刷新这个meetingDataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#meetingAddForm');
					f.submit();
				}
			} ]
		});
	}
	
	function deleteMeetingFun(id) {
		if (id == undefined) {//点击右键菜单才会触发这个
			var rows = meetingDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {//点击操作里面的删除图标会触发这个
			meetingDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.messager.confirm('询问', '您是否要删除当前会议？', function(b) {
			if (b) {
					progressLoad();
					$.post('${ctx}/meeting/delete', {
						id : id
					}, function(result) {
						if (result.success) {
							parent.$.messager.alert('提示', result.msg, 'info');
							meetingDataGrid.datagrid('reload');
						}
						progressClose();
					}, 'JSON');
			}
		});
	}
	
	function editMeetingFun(id) {
		if (id == undefined) {
			var rows = meetingDataGrid.datagrid('getSelections');
			id = rows[0].id;
		} else {
			meetingDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
		}
		parent.$.modalDialog({
			title : '编辑',
			width : 580,
			height : 320,
			href : '${ctx}/meeting/editPage?id=' + id,
			buttons : [ {
				text : '编辑',
				handler : function() {
					parent.$.modalDialog.openner_meetingDataGrid= meetingDataGrid;//因为添加成功之后，需要刷新这个meetingDataGrid，所以先预定义好
					var f = parent.$.modalDialog.handler.find('#meetingEditForm');
					f.submit();
				}
			} ]
		});
	}
	
	function viewMeetingFun(id) {
		if (id == undefined) {
            var rows = meetingDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            meetingDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : id + '--会议详情',
            width : 580,
            height : 320,
            href : '${ctx}/meeting/viewPage?id=' + id,
            buttons : [ {
                text : '关闭',
                handler : function() {
                    parent.$.modalDialog.openner_meetingDataGrid= meetingDataGrid;//因为添加成功之后，需要刷新这个meetingDataGrid，所以先预定义好
                    parent.$.modalDialog.handler.dialog('close');
                }
            } ]
        });
    }
    
	function viewMeetingFilesFun(id) {
		if (id == undefined) {
            var rows = meetingDataGrid.datagrid('getSelections');
            id = rows[0].id;
        } else {
            meetingDataGrid.datagrid('unselectAll').datagrid('uncheckAll');
        }
        parent.$.modalDialog({
            title : '文件预览',
            width : 250,
            height : 320,
            href : '${ctx}/meetingfile/viewfiles?id=' + id,
            buttons : [ {
                text : '关闭',
                handler : function() {
                    parent.$.modalDialog.handler.dialog('close');
                }
            } ]
        });
    }
	
	function searchFun() {
		meetingDataGrid.datagrid('load', $.serializeObject($('#searchForm')));
	}
	function cleanFun() {
		$('#searchForm input').val('');
		meetingDataGrid.datagrid('load', {});
	}
	</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 40px; margin:10px 2px; overflow: hidden;background-color: #fff">
		<form id="searchForm">
			<table cellspacing="10">
				<tr >
					<th>搜索:</th>
					<td><input name="queryword" class="easyui-textbox inp" data-options="prompt:'编号、名称、发起人'"/></td>
					<th>会议室:</th>
                    <td>
                    <select name="meetingroom" style="height:30px;width:200px;">
                        <option value="">请选择会议室</option>
                        <option value="第一会议室">第一会议室</option>
                        <option value="第二会议室">第二会议室</option>
                    </select>
                    </td>
					<th>开始时间:</th>
                    <td><input name="startdate" class="easyui-datetimebox" value="" style="width:200px"></td>
                    <th>结束时间:</th>
                    <td><input name="finishdate" class="easyui-datetimebox" value="" style="width:200px"></td>
                    
					<td><a href="#" onClick="searchFun();" class="easyui-linkbutton" data-options="iconCls:'icon-search'" style="width:80px">查询</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'会议列表'" >
		<table id="dataGridMeeting" data-options="fit:true,border:false"></table>
	</div>
	<div id="toolbarMeeting" style="display: none;">
		<c:if test="${fn:contains(sessionInfo.resourceList, '/meeting/add')}">
			<a onclick="addMeetingFun();" href="javascript:void(0);" class="easyui-linkbutton" data-options="plain:true,iconCls:'icon-add'">新增</a>
		</c:if>
	</div>
</div>

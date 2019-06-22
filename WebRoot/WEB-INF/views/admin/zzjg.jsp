<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>


	<script type="text/javascript">
	var zzjgDataGrid;
	$(function() {
		zzjgDataGrid = $('#dataGridZzjg').datagrid({
			url : '${ctx}/user/dataGrid',
			fit : true,
			striped : true,
			rownumbers : true,
			pagination : true,
			singleSelect : true,
			idField : 'id',
			pageSize : 50,
			pageList : [ 10, 20, 30, 40, 50, 100, 200, 300, 400, 500 ],
			columns : [ [ {
				width : '100',
				title : '登录名',
				field : 'loginname',
				sortable : true
			},{
				width : '80',
				title : '姓名',
				field : 'name',
				sortable : true
			},{
				width : '80',
				title : '部门ID',
				field : 'organizationId',
				hidden : true
			},{
				width : '120',
				title : '所属部门',
				field : 'organizationName'
			},{
				width : '200',
				title : '角色',
				field : 'roleNames'
			},{
				width : '120',
				title : '电话',
				field : 'phone',
				sortable : true
			},{
				width : '160',
				title : '电子邮箱',
				field : 'email',
				sortable : true
			},{
				width : 100,
				title : '操作',
				field : 'action',
				formatter : function(value, row, index) {
					var str = '&nbsp;';
					
					str += $.formatString('<a href="javascript:void(0)" onclick="changeRole(\'{0}\');" );" >切换用户</a>', row.id);
					
					return str;
				}
			}] ],
			
		 	onClickRow : function(index, row){
		 		var organizationTreeGrant;
				organizationTreeGrant = $('#organizationTreeGrant').tree({
					url : '${ctx}/resource/allTree?flag=true',
					parentField : 'pid',
					lines : true,
					checkbox : true,
					onClick : function(node) {
					},
					onLoadSuccess : function(node, data) {
						progressLoad();
						$.post( '${ctx}/role/getList', {
							//id : '${user.id}'
							userId : row.id
						}, function(result) {
							if (result.resourceIds!= undefined) {
								var ids;
								ids = $.stringToList(result.resourceIds);
								if (ids.length > 0) {
									for ( var i = 0; i < ids.length; i++) {
										if (organizationTreeGrant.tree('find', ids[i])) {
											organizationTreeGrant.tree('check', organizationTreeGrant.tree('find', ids[i]).target);
										}
									}
								}
							}
						}, 'json');
						progressClose();
					},
					cascadeCheck : false
				});  
		    }
			
		});
	
	});
	
	function changeRole(id){
		//alert(id);
		window.location.href='${ctx}/admin/index1?userId='+id;
		
		
	}
	
	
	function searchFunTxl() {
		zzjgDataGrid.datagrid('load', $.serializeObject($('#searchFormTxl')));
	}
	function cleanFunTxl() {
		$('#searchFormTxl input').val('');
		zzjgDataGrid.datagrid('load', {});
	}
	</script>

<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'north',border:false" style="height: 30px; overflow: hidden;background-color: #fff">
		<form id="searchFormTxl">
			<table>
				<tr>
					<th>姓名:</th>
					<td><input name="name" placeholder="请输入用户姓名"/></td>
					<th>电话号码:</th>
					<td><input name="phone" placeholder="请输入用户电话"/></td>
					<td><a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-search',plain:true" onclick="searchFunTxl();">查询</a>
					<a href="javascript:void(0);" class="easyui-linkbutton" data-options="iconCls:'icon-cancel',plain:true" onclick="cleanFunTxl();">清空</a></td>
				</tr>
			</table>
		</form>
	</div>
	<div data-options="region:'center',border:true,title:'用户列表'" >
		<table id="dataGridZzjg" data-options="fit:true,border:false"></table>
	</div>
	
	
	<div data-options="region:'east',split:true" title="用户权限" style="overflow:hidden;width:28%;background-color:#F0F8FF">
	    <div id="p" class="easyui-panel" style="width:99%;height:100%;" data-options="tools:''">
	    	<%-- <input name="id" type="hidden"  value="${user.id}" readonly="readonly"> --%>
		    <ul id="organizationTreeGrant"></ul>
		    <!-- <input id="resourceIds01" name="resourceIds" type="hidden" /> -->
	    </div>
	</div>
	
</div>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	$(function() {
		$('#projectOrganizationId').combotree({
			url : '${ctx}/organization/tree',
			parentField : 'pid',
			lines : true,
			panelHeight : 'auto',
			value : '${project.organizationId}'
		});
		
		$('#projectEditForm').form({
			url : '${ctx}/project/edit',
			onSubmit : function() {
				progressLoad();
				var isValid = $(this).form('validate');
				if (!isValid) {
					progressClose();
				}
				return isValid;
			},
			success : function(result) {
				progressClose();
				result = $.parseJSON(result);
				if (result.success) {
					parent.$.modalDialog.openner_projectDataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_projectDataGrid这个对象，是因为project.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		$("#projectType").val('${project.type}');
		$("#projectJsxz").val('${project.jsxz}');
		$("#projectState").val('${project.state}');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="projectEditForm" method="post">
			<div class="light-info" style="overflow: hidden;padding: 3px;">
				<div>密码不修改请留空。</div>
			</div>
			<table class="grid">
				<tr>
					<td>用户代码</td>
					<td><input name="yhdm" type="text" class="easyui-validatebox" data-options="required:true" value="${project.yhdm}"></td>
					<td>授权代码</td>
					<td><input name="sqm" type="text" class="easyui-validatebox" data-options="required:true" value="${project.sqm}"></td>
				</tr>
				<tr>
					<%-- <td>项目服务器IP</td>
					<td><input name="xmfwqip" type="text" class="easyui-validatebox" data-options="required:true" value="${project.xmfwqip}"></td> --%>
					<td>项目服务器MAC</td>
					<td><input name="xmfwqmac" type="text" class="easyui-validatebox" data-options="required:true" value="${project.xmfwqmac}"></td>
					<td>排序号</td>
					<td><input name="seq" value="${project.seq}" class="easyui-numberspinner" data-options="required:true,editable:true"></td>
				</tr>
				<tr>
					<td>IP</td>
					<td><input type="text" name="ip" class="easyui-validatebox" data-options="required:true" value="${project.ip}"/></td>
					<td>端口号</td>
					<td><input type="text" name="dkh" class="easyui-numberbox" data-options="required:true" value="${project.dkh}"/></td>
				</tr>
				<tr>
					<td>工程类型</td>
					<td><select id="projectType" name="type" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0" selected="selected">高速公路</option>
							<option value="1" >干线公路</option>
							<option value="2" >港口</option>
							<option value="3" >航道</option>
							<option value="4" >航电枢纽</option>
							<option value="5" >信息化建设</option>
					</select></td>
					<td>路线编号</td>
					<td><input name="id" type="hidden"  value="${project.id}">
					<input name="xmbh" type="text" class="easyui-validatebox" data-options="required:true" value="${project.xmbh}"/></td>
				</tr>
				<tr>
					<td>名称</td>
					<td><input name="name" type="text" class="easyui-validatebox" data-options="required:true" value="${project.name}"/></td>
					<td>主管单位</td>
					<td><select id="projectOrganizationId" name="organizationId" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true"></select></td>
				</tr>
				<tr>
					<td>项目法人</td>
					<td><input name="xmfr" type="text" class="easyui-validatebox" data-options="required:true" value="${project.xmfr}"/></td>
					<td>建设性质</td>
					<td><select id="projectJsxz" name="jsxz" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="新建项目">新建项目</option>
						<option value="扩建项目">扩建项目</option>
						<option value="停建项目">停建项目</option>
					</select></td>
				</tr>
				<tr>
					<td>状态</td>
					<td><select id="projectState" name="state" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0" selected="selected">启用</option>
							<option value="1" >停用</option>
					</select></td>
					<td>投资金额(万元)</td>
					<td><input type="text" name="tzje" class="easyui-numberbox" data-options="required:true" value="${project.tzje}"/></td>
				</tr>
				<tr>
					<td>用户名</td>
					<td><input type="text" name="yhm" class="easyui-validatebox" data-options="required:true" value="${project.yhm}"/></td>
					<td>密码</td>
					<td><input type="text" name="password"/></td>
				</tr>
				<tr>
					<td>账户启用时间</td>
					<td><input type="text" name="accountOpenTime" class="easyui-datetimebox" data-options="required:true" value="${project.accountOpenTime}"/></td>
					<td>账户停用时间</td>
					<td><input type="text" name="accountStopTime" class="easyui-datetimebox" data-options="required:true" value="${project.accountStopTime}"/></td>
				</tr>
				<tr>
					<td>起点位置</td>
					<td><input name="qdwz" type="text" class="easyui-validatebox" data-options="required:true" value="${project.qdwz}"></td>
					<td>终点位置</td>
					<td><input name="zdwz" type="text" class="easyui-validatebox" data-options="required:true" value="${project.zdwz}"></td>
				</tr>
				<tr>
					<td>起点桩号</td>
					<td><input name="qdzh" type="text" class="easyui-validatebox" data-options="required:true" value="${project.qdzh}"></td>
					<td>终点桩号</td>
					<td><input name="zdzh" type="text" class="easyui-validatebox" data-options="required:true" value="${project.zdzh}"></td>
				</tr>
				<tr>
					<td>路线名称</td>
					<td><input name="lxcd" type="text" class="easyui-validatebox" data-options="required:true" value="${project.lxcd}"></td>
					<td>公路等级</td>
					<td><input name="gldj" type="text" class="easyui-validatebox" data-options="required:true" value="${project.gldj}"></td>
				</tr>
				<tr>
					<td>设计交通量</td>
					<td><input name="sjjtl" type="text" class="easyui-validatebox" data-options="required:true" value="${project.sjjtl}"></td>
					<td>设计车道数</td>
					<td><input name="sjcds" type="text" class="easyui-numberbox" data-options="required:true" value="${project.sjcds}"></td>
				</tr>
				<tr>
					<td>设计速度</td>
					<td><input name="sjsd" type="text" class="easyui-validatebox" data-options="required:true" value="${project.sjsd}"></td>
					<td>设计荷载</td>
					<td><input name="sjhz" type="text" class="easyui-validatebox" data-options="required:true" value="${project.sjhz}"></td>
				</tr>
				<tr>
					<td>概算</td>
					<td><input name="gs" type="text" class="easyui-validatebox" data-options="required:true" value="${project.gs}"></td>
					<td>预算</td>
					<td><input name="ys" type="text" class="easyui-validatebox" data-options="required:true" value="${project.ys}"></td>
				</tr>
				<tr>
					<td>决算</td>
					<td><input name="js" type="text" class="easyui-validatebox" data-options="required:true" value="${project.js}"></td>
					<td>工程概况说明</td>
					<td><input name="gcgksm" type="text" class="easyui-validatebox" data-options="required:true" value="${project.gcgksm}"></td>
				</tr>
				<tr>
					<td>建设单位</td>
					<td><input name="jsdw" type="text" class="easyui-validatebox" data-options="required:true" value="${project.jsdw}"></td>
					<td>建设工期(月)</td>
					<td><input name="jsgq" type="text" class="easyui-validatebox" data-options="required:true" value="${project.jsgq}"></td>
				</tr>
			</table>
		</form>
	</div>
</div>
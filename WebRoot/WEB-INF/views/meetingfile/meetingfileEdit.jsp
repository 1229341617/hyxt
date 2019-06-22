<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">
	$(function() {
		$('#meetid').combobox({
			width:140,
			height:29,
			editable:false,
			panelHeight:'auto',
			url:'${ctx}/meetingfile/meetinglistDataGrid',
			valueField:'id',
			textField:'meetid',
			onChange:function(){
				var meetId = $("input[name='meetid']").attr('value');
				$.get('${ctx}/meetingfile/getMeetnameById',{id:meetId},function(data){
					$("input[name='meetingname']").val(data.substring(1,data.length - 1));
				});
			},
			onLoadSuccess:function(){
				$('#meetid').combobox('select','${meetingfile.meetid}');
			}
		});
		
		
		$('#selectFile').filebox({
			buttonText:'上传',
			buttonAlign:'right',
			multiple:'true',
			prompt:'请选择文件'
		});
	
		var date =  new Date();
		var y = date.getFullYear();
		var M = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
		var d = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
		var h = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
		var m = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
		var s = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
		$("input[name='uploaddate']").prop("value", y + "-" + M + "-" + d + " " + h + ":" + m + ":" + s);
	
	
		$('#meetingfileEditForm').form({
			url : '${ctx}/meetingfile/edit',
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
					parent.$.modalDialog.openner_meetingfileDataGrid.datagrid('reload');
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('错误', result.msg, 'error');
				}
			}
		});
		
		$("input[name='meetingname']").val('${meetingfile.meetingname}');
		$("input[name='uploaddate']").val('${meetingfile.uploaddate}');
		$("input[name='fileurl']").val('${meetingfile.fileurl}');
		$("input[name='uploaduser']").val('${meetingfile.uploaduser}');
		$("input[name='comments']").val('${meetingfile.comments}');
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="meetingfileEditForm" method="post" enctype="multipart/form-data">
			<input type="hidden" name="meetfileId" value="${meetingfile.id }"/>
			<input type="hidden" name="beforeFilename" value="${meetingfile.filename }"/>
			<table class="grid">
				<tr>
					<td>会议编号</td>
					<td>
						<select id="meetid" name="meetid" class="easyui-combobox" data-options="required:true" value="${meetingfile.meetid }"></select>
					</td>
					<td>会议名称</td>
					<td><input name="meetingname" type="text" readonly="readonly"></td>
				</tr>
				<tr>
					<td>文件名</td>
					<td><input type="text" id="selectFile" name="filename" class="easyui-filebox" required="required"></td>
					<td>上传时间</td>
					<td><input name="uploaddate" type="text" placeholder="点击选择上传时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true"></td>
				</tr>
				<tr>
					<td>文件链接</td>
					<td><input name="fileurl" type="text" placeholder="请输入文件链接" class="easyui-textbox" value=""></td>
					<td>上传人</td>
					<td><input name="uploaduser" value="${sessionInfo.name }" type="text" placeholder="请输入上传人" class="easyui-textbox" data-options="readonly:true" value=""></td>
				</tr>
				<tr>
					<td>备注</td>
					<td><input name="comments" type="text" placeholder="请输入备注" class="easyui-textbox" value=""></td>
				</tr>
			</table>
			
			
			
			
			<%-- <table class="grid">
				<tr>
					<td>登录名</td>
					<td><input name="id" type="hidden"  value="${user.id}">
					<input name="loginname" type="text" placeholder="请输入登录名称" class="easyui-validatebox" data-options="required:true" value="${user.loginname}"></td>
					<td>姓名</td>
					<td><input name="name" type="text" placeholder="请输入姓名" class="easyui-validatebox" data-options="required:true" value="${user.name}"></td>
				</tr>
				<tr>
					<td>密码</td>
					<td><input type="text" name="password"/></td>
					<td>性别</td>
					<td><select name="sex" id="sex"  class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0">男</option>
							<option value="1">女</option>
					</select></td>
				</tr>
				<tr>
					<td>年龄</td>
					<td><input type="text" name="age" value="${user.age}" class="easyui-numberbox"/></td>
					<td>用户类型</td>
					<td><select id="usertype" name="usertype"  class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0">管理员</option>
							<option value="1">用户</option>
							<option value="2">供应商</option>
					</select></td>
				</tr>
				<tr>
					<td>部门</td>
					<td><select id="organizationId" name="organizationId" style="width: 140px; height: 29px;" class="easyui-validatebox" data-options="required:true"></select></td>
					<td>角色</td>
					<td><input  id="roleIds" name="roleIds" style="width: 140px; height: 29px;"/></td>
				</tr>
				<tr>
					<td>电话</td>
					<td>
						<input type="text" name="phone" class="easyui-numberbox" value="${user.phone}"/>
					</td>
					<td>电子邮箱</td>
					<td>
						<input type="text" name="email" class="easyui-validatebox" value="${user.email}"/>
					</td>
				</tr>
				<tr>
					<td>用户类型</td>
					<td colspan="3">
						<select id="state" name="state" value="${user.state}" class="easyui-combobox" data-options="width:140,height:29,editable:false,panelHeight:'auto'">
							<option value="0">正常</option>
							<option value="1">停用</option>
						</select>
					</td>
				</tr>
			</table> --%>
		</form>
	</div>
</div>
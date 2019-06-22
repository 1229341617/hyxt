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
		
		
		
		$('#meetingfileAddForm').form({
			url : '${ctx}/meetingfile/add',
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
					parent.$.messager.alert('提示', result.msg, 'info');
				} else {
					parent.$.messager.alert('提示', result.msg, 'warning');
				}
			}
			
		});
	});
	
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="meetingfileAddForm" method="post" enctype="multipart/form-data">
			<table class="grid">
				<tr>
					<td>会议编号</td>
					<td>
						<select id="meetid" name="meetid" class="easyui-combobox" data-options="required:true"></select>
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
		</form>
	</div>
</div>
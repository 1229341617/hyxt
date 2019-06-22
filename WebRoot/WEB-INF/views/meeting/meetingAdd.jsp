<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">


	$(function() {
		
		$('#meetingAddForm').form({
			url : '${ctx}/meeting/add',
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
					parent.$.modalDialog.openner_meetingDataGrid.datagrid('reload');//之所以能在这里调用到parent.$.modalDialog.openner_meetingDataGrid这个对象，是因为meeting.jsp页面预定义好了
					parent.$.modalDialog.handler.dialog('close');
				} else {
					parent.$.messager.alert('提示', result.msg, 'warning');
				}
			}
			
		});
		
	});
</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="" style="overflow: hidden;padding: 3px;">
		<form id="meetingAddForm" method="post">
			<table class="grid"  style="border-collapse: separate; border-spacing: 3px; ">
				<tr>
					<td>会议编号</td>
					<td><input name="meetid" readonly style="height:30px;width:200px;" class="easyui-textbox " data-options="required:true" value="${meetid }"></td>
					<td>会议名称</td>
					<td><input name="meetname" style="height:30px;width:200px;" class="easyui-textbox" 
					data-options="required:true,  prompt:'输入会议名称'" value=""></td>
				</tr>
				<tr>
				    <th>会议开始时间:</th>
                    <td>
                    	<input name="starttime" type="text" placeholder="点击选择会议开始时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="height:100,required:true">
                	</td>
                    <th>会议结束时间:</th>
                    <td>
                    	<input name="finishtime" type="text" placeholder="点击选择会议结束时间" onclick="WdatePicker({readOnly:true,dateFmt:'yyyy-MM-dd HH:mm:ss'})" data-options="required:true">
                 	</td>
                    
				</tr>
				<tr>
					<th>会议室:</th>
                    <td>
                    <select name="meetingroom" style="height:30px;width:200px;">
                        <option value="第一会议室">第一会议室</option>
                        <option value="第二会议室">第二会议室</option>
                    </select>
                    </td>
                    
                    <th>发起人:</th>
                    <td>
                    <select style="height:30px;width:200px;" name="userid"  >
                        <c:forEach items="${users}" var="user">
                            <option value="${user.id }">${user.name}</option>
                        </c:forEach>
                    </select>
                    </td>
				</tr>
				<tr>
					<td>与会人员</td>
					<td>
					<input class="easyui-textbox" style="width:200px;height:60px;" name="meetingperson"
					data-options="multiline:true,prompt:'必须输入(人员以逗号隔开)', required:true" value=""/>
					</td>
					
                    <td>备注</td>
                    <td><input name="comments" class="easyui-textbox" style="width:200px;height:60px;" 
                    data-options="multiline:true,  prompt:'备注'" value=""/></td>
                </tr>
                <tr>
                	<td>文件是否预览</td>
                	<td>
                		<select name="isView" class="easyui-combobox" data-options="width:200,height:29,editable:false,panelHeight:'auto'">
							<option value="1" selected="selected">是</option>
							<option value="0">否</option>
						</select>
                	</td>
                </tr>
			</table>
		</form>
	</div>
</div>
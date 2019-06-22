<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Map"%>
<%@page import="java.util.Date"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>

<script>
  
	function uploadFile() {
		$('#meetingfileUpload').form('submit', {                
			url: '${ctx}/meetingfile/uploadFile?para=announce',
			method:'post',
			success: function (result) {
				$.messager.alert('提示','上传成功！');
				parent.$.modalDialog.handler.dialog('close');	
				
				$("#flowcheck_announce").find("iframe")[0].contentWindow.setfile(result)

			}
		});
	}

</script>

<div class="easyui-panel" style="width:100%;max-width:400px;padding:30px 60px;">
	<form id="meetingfileUpload" method="post" enctype="multipart/form-data">
	    <input class="easyui-filebox" name="uploadFile" id="uploadFile" data-options="prompt:'选择文件',buttonText:'&nbsp;选&nbsp;择&nbsp;',onChange:function(){uploadFile()},required:true" style="width:300px" />
	</form>
</div>

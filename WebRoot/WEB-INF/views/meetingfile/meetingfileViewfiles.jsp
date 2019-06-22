<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<script type="text/javascript">

</script>
<div class="easyui-layout" data-options="fit:true,border:false">
	<div data-options="region:'center',border:false" title="${ls[0].meetingname}-会议文件列表" style="overflow: auto;padding: 3px;">
		<form id="userAddForm" method="post">
			<table class="grid">
				<c:if test="${empty ls }">暂无文件</c:if>
				<c:forEach items="${ls }" varStatus="v" var="file">
					<tr>
						<td><input class="easyui-textbox"  readonly="readonly" name="filename" value="文件${v.count}: ${file.filename }" type="text" data-options="width:240,required:true" value=""></td>
					</tr>
				</c:forEach>
			</table>
		</form>
	</div>
</div>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<jsp:include page="../inc.jsp"></jsp:include>
<!DOCTYPE html>
<html>
<head>	
	<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
	<meta name="viewport" content="width=480" />
	<meta name="apple-mobile-web-app-capable" content="yes">
	<meta name="apple-mobile-web-app-status-bar-style" content="black">
	<meta name="apple-mobile-web-app-title" content="">
	<meta name="format-detection" content="telephone=no">
	<title>文件预览</title>
	
	<script type="text/javascript">
		/(iPhone|iPad|iPhone OS|Phone|iPod|iOS)/i.test(navigator.userAgent)&&(head=document.getElementsByTagName("head"),viewport=document.createElement("meta"),viewport.name="viewport",viewport.content="target-densitydpi=device-dpi, width=480px, user-scalable=no",head.length>0&&head[head.length-1].appendChild(viewport));
	
		function logout() {
            $.messager.confirm('提示', '确定要退出?', function (r) {
                if (r) {
                    progressLoad();
                    $.post('${ctx}/admin/logout', function (result) {
                        if (result.success) {
                            progressClose();
                            window.location.href = '${ctx}/admin/index';
                        }
                    }, 'json');
                }
            });
        }
	</script>
</head>
	<c:if test="${empty mfs }"><font size="4em">暂无个人会议文件信息</font></c:if>
	<c:forEach items="${mfs }" varStatus="vs" var="files">
		<table border="0" cellspacing="100px" cellpadding="0"  width="100%">
			<tr align="center">
				<td><font size="5em" style="font-weight:bold">${files[0].meetingname }-会议文件列表</font></td>
			</tr>
			<c:forEach items="${files }" varStatus="v" var="file">
				<p>
					<tr>
						<td><u><font size="5em">文件${v.count}:    ${file.filename }</font></u></td>
					</tr>
				</p>
			</c:forEach>
		</table><br/><br/><br/><hr/>
	</c:forEach>
	
	<!-- <a style="color:blue;text-decoration:none;" href="javascript:void(0)" onclick="logout()">安全退出</a> -->
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
<jsp:include page="inc.jsp"></jsp:include>
<meta charset="utf-8">
<title>用户登录</title>
<meta name="keywords" content="">
<meta name="description" content="">
<meta name="viewport" content="width=device-width">
<link href="${ctx}/style/css/loginstyle.css" rel="stylesheet" type="text/css">

<style type="text/css">
 body {
	width: 100%;
	height: 100%;
	padding: 0;
	margin: 0;
    background-image:url(${ctx}/style/images/login.jpg);
}
.login_div {
	width: 400px;
	height: 420px;
	position:relative;
	margin:0 auto;
    top:200px;

	
	background: rgba(255, 255, 255, .5);
	border-radius: 10px;
}
.input_block {
	display: block;
	height: 60px;
	width: 320px;
	padding: 20px 20px 20px 50px;
	background-color: #fff;
	border: none;
	border-radius: 7px;
	margin: 0 40px 30px 40px;
	font-size: 23px;
	color: #333;
	background-position: 20px center;
	background-repeat: no-repeat;
	background-size: 20px;
}
#username {
	background-image: url(${ctx}/style/images/username.png);
}
#password {
	background-image: url(${ctx}/style/images/password.png);
}
.btn_block {
	display: block;
	height: 60px;
	width: 320px;
	background: #069fef;
	color: #fff;
	border: none;
	border-radius: 7px;
	margin: 0 40px 0 40px;
	font-size: 23px;
	font-family: 微软雅黑;	
}
.log_div{margin:0 auto;width:1049px;position:relative;top:100px;}
#log_title{font-size:40px;font-family:'楷体';font-weight:bold;color:#fff;background:#5599FF;}

</style>
</head>
<body onkeydown="javascript:if(event.keyCode==13){document.getElementById('input1').click();}">
<form id="loginform" role="form" method="post">
	<div class="login_div" >
		
		<div style="height:90px;line-height:90px;color:#0000FF;font-size:26px;font-family:微软雅黑,新宋体;text-align:center;"></div>
		
		<input type="text" class="input_block" id="username" name="loginname" placeholder="用户名" maxlength="20">
		
		<input type="password" class="input_block" id="password" name='password' placeholder="密码" maxlength="20">
		
		<button type="submit" id="input1" class="btn_block" >登录</button>

	</div>
</form>
	<!-- <div class="login-box">
		<form  method="post" id="loginform">
			<div class="name">
				<label>管理员账号：</label>
				<input type="text" name="loginname" id="" tabindex="1" autocomplete="off" value="admin" />
			</div>
			<div class="password">
				<label>密码：</label>
				<input type="password" name="password" value="" maxlength="16" id="" tabindex="2"/>
			</div>
			<div class="login">
				<button type="submit" tabindex="5">登录</button>
			</div>
		</form>
	</div> -->
		<script type="text/javascript">
		   
		   window.onload = function(){
			var sessionInfo_userId = '${sessionInfo.id}';
			if (sessionInfo_userId) {//如果登录,直接跳转到index页面
				window.location.href='${ctx}/admin/index';
			}
			
			$(function() {
				
				$('#loginform').form({
				    url:'${ctx}/admin/login',
				    
				    success:function(result){
				    	result = $.parseJSON(result);
				    	//progressClose();
				    	if (result.success) {
				    		window.location.href='${ctx}/admin/index';
				    	}else{
				    		$.messager.show({
				    			title:'提示',
				    			msg:'<div class="light-info"><div class="light-tip icon-tip"></div><div>'+result.msg+'</div></div>',
				    			showType:'show'
				    		});
				    	}
				    }
				});
			});
			
			function submitForm(){
				$('#loginform').submit();
			}
			
			function clearForm(){
				$('#loginform').form('clear');
			}
		
		}
		</script>
	</body>
</html>
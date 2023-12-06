
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>登陆</title>
<link href="css/index.css" rel="stylesheet">
</head>

<body>

	<form class="layui-form myform" action="login.do" method="post">
		<div class="demo-login-container">

			<!-- 	用户名 -->
			<div class="layui-form-item">
				<div class="layui-input-wrap">
					<div class="layui-input-prefix">
						<i class="layui-icon layui-icon-username"></i>
					</div>
					<input type="email" name="emailAddr" value="" lay-verify="required|email"
						placeholder="邮箱" lay-reqtext="请填写邮箱" autocomplete="off"
						class="layui-input" lay-affix="clear">
				</div>
			</div>

			<!--     密码 -->
			<div class="layui-form-item">
				<div class="layui-input-wrap">
					<div class="layui-input-prefix">
						<i class="layui-icon layui-icon-password"></i>
					</div>
					<input type="password" name="password" value=""
						lay-verify="required" placeholder="密   码" lay-reqtext="请填写密码"
						autocomplete="off" class="layui-input" lay-affix="eye" required>
				</div>
			</div>

			<!--     选择用户类型 -->
			<div class="layui-form-item">
				<select name="usertype">
					<option value="guardian">家长</option>
					<option value="teacher">老师</option>
					<option value="admin">管理员</option>
				</select>
			</div>

			<div class="layui-form-item">
				<button class="layui-btn layui-btn-fluid" type="submit">登录</button>
			</div>

			<div class="layui-form-item demo-login-other">
				<a href="#forget" style="float: right; margin-top: 7px;">忘记密码？</a> <a
					href="${pageContext.request.contextPath }/turnToRegisterSelectTypeJSP.do">注册帐号</a>
			</div>


		</div>
	</form>

	<script src="layui/layui.js"></script>
	<%if (request.getAttribute("error") != null && (Boolean)request.getAttribute("error")) {%>
	<script>
		window.alert("邮箱或密码错误！！");
	</script>
	<%
	}
	%>

</body>
</html>
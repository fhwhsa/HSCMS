<%@page import="javax.swing.text.Document"%>
<%@page import="com.fws.mvc.utils.JdbcTools"%>
<%@page import="com.fws.mvc.utils.SendEmail"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>老师账号注册</title>
<link href="css/register.css" rel="stylesheet">
</head>
<body>

	<!-- 预加载 -->
	<script>
		function func() {
			console.log('func');
			var _count = document.getElementById("count"); //获取验证码按钮
			var time = 59;
			// 禁用按钮
			_count.disabled = true;
			// 开启定时器
			var timer = setInterval(function() {
				// 判断剩余秒数
				if (time == 0) {
					// 清除定时器和复原按钮
					clearInterval(timer);
					_count.disabled = false;
					_count.innerHTML = '获取验证码';
				} else {
					_count.innerHTML = time + '秒后重新获取';
					time--;
				}
			}, 1000);
		}
	</script>

	<form class="layui-form" action="registerTeacher.do" method="post" id="myform">
		<div class="demo-reg-container">

			<!--   	验证邮箱 -->
			<div class="layui-form-item">
				<div class="layui-row">
					<div class="layui-col-xs7">
						<div class="layui-input-wrap">
							<div class="layui-input-prefix">
								<i class="layui-icon layui-icon-cellphone"></i>
							</div>
							<input type="email" name="emailAddr" placeholder="邮箱"
								autocomplete="off" class="layui-input" id="reg-emailAddr"
								required>
						</div>
					</div>

					<!-- 延时代码 -->
					<div class="layui-col-xs5">
						<div style="margin-left: 11px;">
							<button type="button"
								class="layui-btn layui-btn-fluid layui-btn-primary" id="count"
								onclick="send()">获取验证码</button>
						</div>
					</div>
				</div>
			</div>
			
			<!-- 是否调用计时函数判断 -->
			<%if ((Boolean) request.getAttribute("flag")) {%>
				<script>func();</script>
			<%}%>

			<!--     填写收到的验证码 -->
			<div class="layui-form-item">
				<div class="layui-input-wrap">
					<div class="layui-input-prefix">
						<i class="layui-icon layui-icon-vercode"></i>
					</div>
					<input type="number" name="verCode" placeholder="验证码"
						lay-reqtext="请填写验证码" autocomplete="off" class="layui-input"
						required>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-input-wrap">
					<div class="layui-input-prefix">
						<i class="layui-icon layui-icon-password"></i>
					</div>
					<input type="password" name="password" placeholder="密码"
						autocomplete="off" class="layui-input" id="reg-password"
						lay-affix="eye" required>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-input-wrap">
					<div class="layui-input-prefix">
						<i class="layui-icon layui-icon-password"></i>
					</div>
					<input type="password" name="confirmPassword" placeholder="确认密码"
						autocomplete="off" class="layui-input" lay-affix="eye"
						lay-verify="confirmPassword" id="reg-confirmPassword" required>
				</div>
			</div>

			<div class="layui-form-item">
				<div class="layui-input-wrap">
					<div class="layui-input-prefix">
						<i class="layui-icon layui-icon-username"></i>
					</div>
					<input type="text" name="userName" placeholder="昵称"
						autocomplete="off" class="layui-input" lay-affix="clear" id="reg-userName" required>
				</div>
			</div>

			<div class="layui-form-item">
				<button class="layui-btn layui-btn-fluid" lay-submit onclick="save()">注册</button>
			</div>

			<div class="layui-form-item demo-reg-other">
				<a href="${pageContext.request.contextPath }/index.jsp" onclick="clearSession();">登录已有帐号</a>
			</div>
		</div>
	</form>

	<script src="layui/layui.js"></script>
	<script src="js/registerJS/registerTeacher.js"></script>
</body>
</html>
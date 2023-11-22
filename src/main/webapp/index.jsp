<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>Demo</title>
<link href="css/index.css" rel="stylesheet">
</head>
<body>
	<script src="layui/layui.js"></script>
	<div class="container">
		<form class="layui-form myform" method="post" action="login.do">
			<h2>登陆</h2>
			<div class="demo-login-container">
				<div class="layui-form-item">
					<div class="layui-input-wrap">
						<div class="layui-input-prefix">
							<i class="layui-icon layui-icon-username"></i>
						</div>
						<input type="text" name="username" value="" lay-verify="required"
							placeholder="用户名" lay-reqtext="请填写用户名" autocomplete="off"
							class="layui-input" lay-affix="clear">
					</div>
				</div>
				<div class="layui-form-item">
					<div class="layui-input-wrap">
						<div class="layui-input-prefix">
							<i class="layui-icon layui-icon-password"></i>
						</div>
						<input type="password" name="password" value=""
							lay-verify="required" placeholder="密   码" lay-reqtext="请填写密码"
							autocomplete="off" class="layui-input" lay-affix="eye">
					</div>
				</div>


				<div class="layui-form-item demo-login-other">
					<a href="#reg">注册帐号</a>&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&emsp;&ensp;<a
						href="#reg">忘记密码</a>
				</div>
				<div class="layui-form-item">
					<button class="layui-btn layui-btn-fluid" type="submit">登录</button>
				</div>
			</div>
		</form>
	</div>
</body>
</html>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<title>用户类型</title>
<link href="css/index.css" rel="stylesheet">
</head>

<body>
	<script src="layui/layui.js"></script>

	<form class="layui-form myform" action="turnToForgetPasswdJSP.do" method="post">
		<div class="demo-login-container">

			<h2 style="text-align: center;">请选择用户类型</h2>
			<br>
			<!--     选择用户类型 -->
			<div class="layui-form-item">
				<select name="userType">
					<option value="Guardian">家长</option>
					<option value="Teacher">老师</option>
				</select>
			</div>

			<div class="layui-form-item">
				<button class="layui-btn layui-btn-fluid" type="submit">确认</button>
			</div>


		</div>
	</form>

</body>
</html>
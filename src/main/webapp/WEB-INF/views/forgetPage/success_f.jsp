<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>操作成功</title>
  <!-- 请勿在项目正式环境中引用该 layui.css 地址 -->
  <link href="layui/css/layui.css" rel="stylesheet">
</head>
<body>
	<div style="position:absolute;
             top: 20%;
             left: 50%;
             transform: translate(-50%,-50%);">
		<i class="layui-icon layui-icon-face-smile" style="font-size: 210px; color: green;"></i> 
	</div>
	
	<div style="position:absolute;
             top: 40%;
             left: 50%;
             transform: translate(-50%,-50%);">
		<p style="text-align: center;">修改成功
		<a href="${pageContext.request.contextPath }/index.jsp" style="color: blue;">点击返回登陆界面</a></p>	
	</div>
</body>
</html>
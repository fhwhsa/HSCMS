<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>加入班级_T</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="layui/css/layui.css" rel="stylesheet">
<style type="text/css">
.demo-login-container {
	width: 320px;
	margin: 21px auto 0;
}

.demo-login-other .layui-icon {
	position: relative;
	display: inline-block;
	margin: 0 2px;
	top: 2px;
	font-size: 26px;
}
</style>
</head>
<body>
	<div class="layui-layout layui-layout-admin">
		<div class="layui-header">
			<div class="layui-logo layui-hide-xs layui-bg-black">layout
				demo</div>
			<!-- 头部区域（可配合layui 已有的水平导航） -->

			<ul class="layui-nav layui-layout-right">
				<li class="layui-nav-item layui-hide layui-show-sm-inline-block">
					<a href="javascript:;"> <img
						src="//unpkg.com/outeres@0.0.10/img/layui/icon-v2.png"
						class="layui-nav-img"> 账号
				</a>
					<dl class="layui-nav-child">
						<dd>
							<a href="javascript:;">Your Profile</a>
						</dd>
						<dd>
							<a href="javascript:;">Settings</a>
						</dd>
						<dd>
							<a href="${pageContext.request.contextPath }/index.jsp">注销</a>
						</dd>
					</dl>
				</li>
				<li class="layui-nav-item" lay-header-event="menuRight" lay-unselect>
					<a href="javascript:;"> <i
						class="layui-icon layui-icon-more-vertical"></i>
				</a>
				</li>
			</ul>
		</div>

		<div class="layui-side layui-bg-black">
			<div class="layui-side-scroll">
				<!-- 左侧导航区域（可配合layui已有的垂直导航） -->
				<ul class="layui-nav layui-nav-tree" lay-filter="test">
					<li class="layui-nav-item"><a href="${pageContext.request.contextPath }/turnToCreateClassJSP.tdo">创建班级</a></li>
					<li class="layui-nav-item"><a href="${pageContext.request.contextPath }/turnToSelectCreateClassJSP.tdo">班级管理</a></li>
					<li class="layui-nav-item"><a href="${pageContext.request.contextPath }/turnToJoinClassPage.tdo">加入班级</a></li>
					<li class="layui-nav-item"><a href="#">我的班级</a></li>

				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">
				<form class="layui-form myform" action="submitApplication.tdo"
					method="post">
					<div class="demo-login-container">
						<p style="text-align: center;">${requestScope.mes }</p>
						<br>
						<!-- 	用户名 -->
						<div class="layui-form-item">
							<div class="layui-input-wrap">
								<div class="layui-input-prefix">
									<i class="layui-icon layui-icon-username"></i>
								</div>
								<input type="number" name="classNo" value="" lay-verify="required"
									placeholder="班级编号" lay-reqtext="班级编号" autocomplete="off"
									class="layui-input" lay-affix="clear">
							</div>
						</div>

						<div class="layui-form-item">
							<button class="layui-btn layui-btn-fluid" type="submit">提交申请</button>
						</div>

					</div>
				</form>
			</div>
		</div>

		<div class="layui-footer">
			<!-- 底部固定区域 -->
			${sessionScope.announcement }
		</div>

	</div>

	<script src="layui/layui.js"></script>
	<script src="js/adminPageJS/mainPage.js"></script>

</body>
</html>
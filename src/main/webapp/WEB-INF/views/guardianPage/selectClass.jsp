<%@page import="com.fws.mvc.bean.ClassInfo"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>班级选择_G</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="layui/css/layui.css" rel="stylesheet">
<link href="css/selectClass.css" rel="stylesheet">
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
						<!-- 						<dd>
							<a href="javascript:;">修改名字</a>
						</dd> -->
						<dd>
							<a href="javascript:;">修改密码</a>
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
					<li class="layui-nav-item"><a
						href="${pageContext.request.contextPath }/turnToJoinClassPage.gdo">加入班级</a></li>
					<li class="layui-nav-item"><a
						href="${pageContext.request.contextPath }/turnToSelectClassJSP.gdo">我的班级</a></li>
				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->

			<%List<ClassInfo> records = (List<ClassInfo>)request.getAttribute("records"); %>

			<div class="layui-bg-gray" style="padding: 16px; ">
				<div class="layui-row layui-col-space15">

					<%for (ClassInfo record : records) { %>
					<div class="layui-col-md6 myDiv" onclick="window.location.href='turnToMyClassJSP.gdo?selectedClassNo=<%=record.getClassNo() %>';">
						<div class="layui-card">
							<div class="layui-card-header">
								班级编号：<%=record.getClassNo() %></div>
							<div class="layui-card-body">
								班级名称：<%=record.getClassName() %><br> 创建时间：<%=record.getCreateTimeStamp() %>
							</div>
						</div>
					</div>
					<%} %>

					<%if (records.size() == 0) { %>
					没有加入的班级，<a
						href="${pageContext.request.contextPath }/turnToJoinClassPage.gdo">点此前往加入班级</a>
					<%} %>

				</div>
			</div>

		</div>

		<div class="layui-footer">
			<!-- 底部固定区域 -->
			${sessionScope.announcement }
		</div>

	</div>

	<script src="layui/layui.js"></script>
</body>
</html>


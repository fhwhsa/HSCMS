<%@page import="java.util.List"%>
<%@page import="com.fws.mvc.bean.RegistrationRecord"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>注册审核</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="layui/css/layui.css"
	rel="stylesheet">
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
						href="${pageContext.request.contextPath }/turnToAnnoMJSP.ado">发布公告</a></li>
					<li class="layui-nav-item layui-this"><a
						href="${pageContext.request.contextPath }/turnToRegistMJSP.ado">注册审核</a></li>
					<li class="layui-nav-item"><a
						href="${pageContext.request.contextPath }/turnToClassMJSP.ado">班级审核</a></li>
				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">
				<table class="layui-table" page:
					true, limit: 6, limits:[6]}" id="ID-table-demo-theads-1">
					<thead>
						<tr>
							<th lay-data="{width:80}" rowspan="2">名称</th>
							<th lay-data="{align:'center'}">邮箱地址</th>
							<th
								lay-data="{fixed: 'right', width: 100, align: 'center', toolbar: '#templet-demo-theads-tool'}"
								rowspan="2">操作</th>
						</tr>
					</thead>


					<%List<RegistrationRecord> records = (List<RegistrationRecord>)request.getAttribute("records"); %>

					<% for (RegistrationRecord record : records) { %>
					<tr>
						<th><%=record.getName() %></th>
						<th><%=record.getEmailAddr() %></th>
						<th>
							<div class="layui-clear-space">
								<a class="layui-btn layui-btn-primary layui-btn-xs"
									href="refusedRegistration.ado?emailAddr=<%=record.getEmailAddr() %>">拒绝</a>
								<a class="layui-btn layui-btn-primary layui-btn-xs"
									href="approvedRegistration.ado?emailAddr=<%=record.getEmailAddr() %>">同意</a>
							</div>
						</th>
					</tr>
					<%} %>



				</table>
				
				<% if (records.size() == 0) { %>
					<p style="text-align: center; font-weight: bold;">暂无注册申请</p>
				<% } %>
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
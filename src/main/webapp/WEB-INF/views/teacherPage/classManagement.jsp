<%@page import="com.fws.mvc.bean.ClassAnnoMap"%>
<%@page import="com.fws.mvc.bean.ClassInfo"%>
<%@page import="com.fws.mvc.bean.ClassApplicationRecord"%>
<%@page import="com.fws.mvc.bean.UserClassMap"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>班级管理_T</title>
<meta name="renderer" content="webkit">
<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="layui/css/layui.css" rel="stylesheet">
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

				<div class="layui-tab layui-tab-brief">
					<ul class="layui-tab-title">

						<a
							href="${pageContext.request.contextPath }/changeManagementPage.tdo?page=0">
							<li <%if (request.getAttribute("page").equals("0")) {%>
							class="layui-this" <%}%>>班级信息</li>
						</a>

						<a
							href="${pageContext.request.contextPath }/changeManagementPage.tdo?page=1">
							<li <%if (request.getAttribute("page").equals("1")) {%>
							class="layui-this" <%}%>>班级审核</li>
						</a>

						<a
							href="${pageContext.request.contextPath }/changeManagementPage.tdo?page=2">
							<li <%if (request.getAttribute("page").equals("2")) {%>
							class="layui-this" <%}%>>班级成员</li>
						</a>

						<a
							href="${pageContext.request.contextPath }/changeManagementPage.tdo?page=3">
							<li <%if (request.getAttribute("page").equals("3")) {%>
							class="layui-this" <%}%>>发布通知</li>
						</a>
					</ul>




					<div class="layui-tab-content">

						<!-- 班级信息 -->
						<%
						if (request.getAttribute("page").equals("0")) {
						%>
						<div class="layui-tab-item  layui-show">
							<%
							ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
							%>
							<p>
								班级编号：<%=currClassInfo.getClassNo()%></p>
							<br>
							<p>
								班级名称：<%=currClassInfo.getClassName()%></p>
						</div>
						<%
						}
						%>

						<!-- 班级审核 -->
						<%
						if (request.getAttribute("page").equals("1")) {
						%>
						<div class="layui-tab-item  layui-show">
							<table class="layui-table" page:
								true, limit: 6, limits:[6]}" id="ID-table-demo-theads-1">
								<thead>
									<tr>
										<th lay-data="{width:80}" rowspan="2">申请人姓名</th>
										<th lay-data="{align:'center'}">申请人邮箱</th>
										<th
											lay-data="{fixed: 'right', width: 100, align: 'center', toolbar: '#templet-demo-theads-tool'}"
											rowspan="2">操作</th>
									</tr>
								</thead>


								<%
								List<ClassApplicationRecord> records = (List<ClassApplicationRecord>) request.getAttribute("records");
								%>

								<%
								for (ClassApplicationRecord record : records) {
								%>
								<tr>
									<th><%=record.getName()%></th>
									<th><%=record.getEmailAddr()%></th>
									<th>
										<div class="layui-clear-space">
											<a class="layui-btn layui-btn-primary layui-btn-xs"
												href="${pageContext.request.contextPath }/refusedApplication.tdo?emailAddr=<%=record.getEmailAddr() %>&name=<%=record.getName() %>">拒绝</a>
											<a class="layui-btn layui-btn-primary layui-btn-xs"
												href="${pageContext.request.contextPath }/approvedApplication.tdo?emailAddr=<%=record.getEmailAddr() %>&name=<%=record.getName() %>">同意</a>
										</div>
									</th>
								</tr>
								<%
								}
								%>
							</table>
						</div>
						<%
						}
						%>



						<!-- 班级成员 -->
						<%
						if (request.getAttribute("page").equals("2")) {
						%>
						<div class="layui-tab-item  layui-show ">
							<table class="layui-table" page:
								true, limit: 6, limits:[6]}" id="ID-table-demo-theads-1">
								<thead>
									<tr>
										<th lay-data="{width:80}" rowspan="2">姓名</th>
										<th lay-data="{align:'center'}">邮箱地址</th>
									</tr>
								</thead>

								<%
								List<UserClassMap> records = (List<UserClassMap>) request.getAttribute("records");
								%>

								<%
								for (UserClassMap record : records) {
								%>
								<tr>
									<th><%=record.getName()%></th>
									<th><%=record.getEmailAddr()%></th>
								</tr>
								<%
								}
								%>

							</table>
						</div>
						<%
						}
						%>



						<!-- 发布通知 -->
						<%
						if (request.getAttribute("page").equals("3")) {
						%>
						<div class="layui-tab-item  layui-show">
							<label class="layui-form-label">发布通知：</label>
							<form class="layui-form" action="postAnno.tdo" method="post">
								<div class="layui-form-item layui-form-text">
									<div class="layui-input-block">
										<textarea name="context" placeholder="请输入内容"
											class="layui-textarea"></textarea>
									</div>
								</div>
								<div class="layui-form-item">
									<div class="layui-input-block">
										<button type="submit" class="layui-btn" lay-submit
											lay-filter="demo1">立即提交</button>
										<button type="reset" class="layui-btn layui-btn-primary">重置</button>
									</div>
								</div>
							</form>

							<br>

							<% List<ClassAnnoMap> records = (List<ClassAnnoMap>) request.getAttribute("records"); %>
							<% for (ClassAnnoMap record : records) { %>
								<div style="display: flex;">
									<div class="layui-card"
										style="background-color: #F5F5F5; width: 95%;">
										<div class="layui-card-header"><%=record.getCreateTimeStamp() %></div>
										<div class="layui-card-body"><%=record.getContext() %></div>
									</div>
									<div class="layui-card" style="margin-top: 2%; margin-left: 2%;">
										<button class="layui-btn" onclick='window.location.href="deleteAnno.tdo?id=<%=record.getId() %>"'>删除</button>
									</div>
								</div>
							<% } %>

						</div>
						<%
						}
						%>



					</div>
				</div>

			</div>
		</div>

		<div class="layui-footer">
			<!-- 底部固定区域 -->
			${sessionScope.announcement }
		</div>

	</div>

	<script src="layui/layui.js"></script>
	<script src="js/teacherPageJS/mainPage.js"></script>
	<script type="js/teacherPageJS/classManagement.js"></script>
</body>
</html>
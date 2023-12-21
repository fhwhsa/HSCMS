<%@page import="com.fws.mvc.bean.ClassAnnoMap"%>
<%@page import="com.fws.mvc.bean.UserClassMap"%>
<%@page import="java.util.List"%>
<%@page import="com.fws.mvc.bean.ClassInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>主页_G</title>
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
					<li class="layui-nav-item"><a
						href="${pageContext.request.contextPath }/turnToJoinClassPage.gdo">加入班级</a></li>
					<li class="layui-nav-item"><a
						href="${pageContext.request.contextPath }/turnToSelectClassJSP.gdo">我的班级</a></li>

				</ul>
			</div>
		</div>

		<div class="layui-body">
			<!-- 内容主体区域 -->
			<div style="padding: 15px;">

				<div class="layui-tab layui-tab-brief">
					<ul class="layui-tab-title">

						<a
							href="${pageContext.request.contextPath }/changeManagementPage.gdo?page=0">
							<li <%if (request.getAttribute("page").equals("0")) {%>
							class="layui-this" <%}%>>班级信息</li>
						</a>

						<a
							href="${pageContext.request.contextPath }/changeManagementPage.gdo?page=1">
							<li <%if (request.getAttribute("page").equals("1")) {%>
							class="layui-this" <%}%>>班级成员</li>
						</a>

						<a
							href="${pageContext.request.contextPath }/changeManagementPage.gdo?page=2">
							<li <%if (request.getAttribute("page").equals("2")) {%>
							class="layui-this" <%}%>>班级通知</li>
						</a>

						<a
							href="${pageContext.request.contextPath }/changeManagementPage.gdo?page=3">
							<li <%if (request.getAttribute("page").equals("3")) {%>
							class="layui-this" <%}%>>站内交流</li>
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
							String createrName = (String) request.getSession().getAttribute("createrName");
							String createrEmailAddr = (String) request.getSession().getAttribute("createrEmailAddr");
							%>
							<p>
								班级编号：<%=currClassInfo.getClassNo()%></p>
							<br>
							<p>
								班级名称：<%=currClassInfo.getClassName()%></p>
							<br>
							<p>
								老师姓名：<%=createrName %></p>
							<br>
							<p>
								老师邮箱：<%=createrEmailAddr %></p>
						</div>
						<%
						}
						%>



						<!-- 班级成员 -->
						<%
						if (request.getAttribute("page").equals("1")) {
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


						<!-- 班级通知 -->
						<%
						if (request.getAttribute("page").equals("2")) {
						%>
						<div class="layui-tab-item  layui-show ">

							<form action="filterClassAnno.gdo" method="get">
								<div class="layui-form-item">

									<div class="layui-inline">
										<label class="layui-form-label">内容</label>
										<div class="layui-input-inline">
											<input type="text" name="contxt" lay-verify="context"
												placeholder="输入要查询的内容" autocomplete="off" class="layui-input">
										</div>
									</div>

									<div class="layui-inline">
										<label class="layui-form-label">日期</label>
										<div class="layui-input-inline layui-input-wrap">
											<div class="layui-input-prefix">
												<i class="layui-icon layui-icon-date"></i>
											</div>
											<input type="text" name="date" id="date" lay-verify="date"
												placeholder="yyyy-MM-dd" autocomplete="off"
												class="layui-input">
										</div>
									</div>

									<div class="layui-inline">
										<button type="submit" class="layui-btn" lay-submit>筛选</button>
									</div>
								</div>
							</form>


							<%List<ClassAnnoMap> records = (List<ClassAnnoMap>)request.getAttribute("records"); %>
							<%for (ClassAnnoMap record : records) { %>
							<div class="layui-card" style="background-color: #F5F5F5;">
								<div class="layui-card-header"><%=record.getCreateTimeStamp() %></div>
								<div class="layui-card-body">
									<%=record.getContext() %>
								</div>
							</div>
							<%} %>

							<%if (records.size() == 0) { %>
							<p style="text-align: center;">暂无通知
							</h1>
							<%} %>
						</div>
						<%
						}
						%>



						<!-- 站内交流 -->

					</div>
				</div>
			</div>

			<div class="layui-footer">
				<!-- 底部固定区域 -->
				${sessionScope.announcement }
			</div>

		</div>

		<script src="layui/layui.js"></script>
		<script src="js/adminPageJS/mainPage.js"></script>
		<script>
	layui.use(['laydate'], function(){
	  var laydate = layui.laydate;
	  
	  // 日期
	  laydate.render({
	    elem: '#date'
	  });
	  
	});
	</script>
</body>
</html>

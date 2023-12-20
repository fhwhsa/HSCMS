package com.fws.mvc.service;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.bean.UserClassMap;
import com.fws.mvc.daoArc.ClassAnnoMapDaoArc;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.daoArc.UserClassMapDaoArc;
import com.fws.mvc.utils.JdbcTools;

public class TeacherService {

	private static ClassInfoDaoArc classInfoDaoArc = null;
	private static UserClassMapDaoArc userClassMAPDaoArc = null;
	private static ClassAnnoMapDaoArc classAnnoMapDaoArc = null;
	
	public TeacherService() {
		if (classInfoDaoArc == null)
			classInfoDaoArc = new ClassInfoDaoArc();
		if (userClassMAPDaoArc == null)
			userClassMAPDaoArc = new UserClassMapDaoArc();
		if (classAnnoMapDaoArc == null)
			classAnnoMapDaoArc = new ClassAnnoMapDaoArc();
	}
	
	
/* 创建班级 ***************************************************************************************************************************************/
	
	public Boolean createClass(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Boolean res = false;
		String name = request.getParameter("className");
		name = new String(name.getBytes("iso-8859-1"), "utf-8");
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			connection.setAutoCommit(false);
			
			if (classInfoDaoArc.findByName(connection, name)) 
				request.setAttribute("error", "已使用的班级名");
			else {
				res = true;
				classInfoDaoArc.addApplication(connection, new ClassInfo("auto", name, (String)request.getSession().getAttribute("currName"), (String)request.getSession().getAttribute("currEmailAddr")) );
				connection.commit();
				request.setAttribute("message", "请等待审核。");
			}			
		} catch (Exception e) {
			connection.rollback();
			request.setAttribute("error", e.getMessage());
			res = false;
		} finally {
			connection.setAutoCommit(true);
			JdbcTools.releaseSources(connection);
		}
		return res;
	}
	
/*************************************************************************************************************************************************/
	
	
	
	
	
/* 班级管理 ****************************************************************************************************************************/
	
	// 读取创建的班级列表
	public void initCreateClassData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String emailAddr = (String) request.getSession().getAttribute("currEmailAddr");
		List<ClassInfo> records = null;
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			records = classInfoDaoArc.getCreateClassRecordsList(connection, emailAddr);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		request.setAttribute("records", records);
	}
	
	// 读取选择的班级
	public void initClassManagementData(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String selectedClassNo = request.getParameter("selectedClassNo");
		request.getSession().setAttribute("currClassNo", selectedClassNo);
		request.setAttribute("page", "1");
	}
	
	// 功能选择，初始化数据
	public void changeManagementPageService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		if (page.equals("3"))
			return;
		
		Connection connection = null;
		String selectedClassNo = (String) request.getSession().getAttribute("currClassNo");
		try {
			connection = JdbcTools.getConnectionByPools();
			if (page.equals("1")) { // 审核
				
			}
			else { // 班级成员
				List<UserClassMap> records = userClassMAPDaoArc.getClassMembers(connection, selectedClassNo);
				System.out.println(records);
				request.setAttribute("records", records);
			}
		} catch (Exception e) {
			// TODO: handle exception
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
	// 发布通知
	public void postNoticeService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String context = request.getParameter("context");
		context = new String(context.getBytes("iso-8859-1"), "utf-8");
		String classNo = (String) request.getSession().getAttribute("currClassNo");
		
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			classAnnoMapDaoArc.addAnno(connection, classNo, context);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
/*************************************************************************************************************************************************/
	
}

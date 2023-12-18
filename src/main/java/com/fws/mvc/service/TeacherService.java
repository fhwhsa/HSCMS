package com.fws.mvc.service;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.utils.JdbcTools;

public class TeacherService {

	private static ClassInfoDaoArc classInfoDaoArc = null;
	
	public TeacherService() {
		if (classInfoDaoArc == null)
			classInfoDaoArc = new ClassInfoDaoArc();
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
		System.out.println(selectedClassNo);
	}
	
}

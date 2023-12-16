package com.fws.mvc.service;

import java.sql.Connection;

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
	
}

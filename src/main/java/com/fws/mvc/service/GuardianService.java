package com.fws.mvc.service;

import java.sql.Connection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.bean.ClassAnnoMap;
import com.fws.mvc.bean.ClassApplicationRecord;
import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.bean.UserClassMap;
import com.fws.mvc.daoArc.ClassAnnoMapDaoArc;
import com.fws.mvc.daoArc.ClassApplicationRecordDaoArc;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.daoArc.UserClassMapDaoArc;
import com.fws.mvc.utils.JdbcTools;

public class GuardianService {

	private ClassInfoDaoArc classInfoDaoArc = null;
	private ClassApplicationRecordDaoArc classApplicationRecordDaoArc = null;
	private UserClassMapDaoArc userClassMapDaoArc = null;
	private ClassAnnoMapDaoArc classAnnoMapDaoArc = null;

	public GuardianService() {
		if (classInfoDaoArc == null)
			classInfoDaoArc = new ClassInfoDaoArc();
		if (classApplicationRecordDaoArc == null)
			classApplicationRecordDaoArc = new ClassApplicationRecordDaoArc();
		if (userClassMapDaoArc == null)
			userClassMapDaoArc = new UserClassMapDaoArc();
		if (classAnnoMapDaoArc == null)
			classAnnoMapDaoArc = new ClassAnnoMapDaoArc();
	}
	
/* 加入班级 ****************************************************************************************************************************************/

	public void submitApplicationService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String classNo = request.getParameter("classNo");
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			if (!classInfoDaoArc.isExist(connection, classNo)) 
				request.setAttribute("mes", "不存在的班级编号");
			else {
				String emailAddr = (String) request.getSession().getAttribute("currEmailAddr");
				String name = (String) request.getSession().getAttribute("currName");
				ClassApplicationRecord record = new ClassApplicationRecord(emailAddr, name, classNo);
				if (classApplicationRecordDaoArc.hasSubmit(connection, record)) 
					request.setAttribute("mes",	"审核重复提交");
				else {
					classApplicationRecordDaoArc.addApplicationRecord(connection, record);
					request.setAttribute("mes", "申请已提交，请等待审核");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
/*************************************************************************************************************************************************/

	
	
	
	
/* 我的班级跳转处理 *********************************************************************************************************************************/
	
	// 获取加入的班级信息
	public void initJoinedClassRecordList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = null;
		List<ClassInfo> records = null;
		String emailAddr = (String) request.getSession().getAttribute("currEmailAddr");
		try {
			connection = JdbcTools.getConnectionByPools();
			records = classInfoDaoArc.getJoinedClassRecordsList(connection, emailAddr);
			request.setAttribute("records", records);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
	// 转我的班级功能页
	public void initMyClassPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String selectedClassNo = request.getParameter("selectedClassNo");	
		ClassInfo currClassInfo = null;
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			currClassInfo = classInfoDaoArc.getClassInfo(connection, selectedClassNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		request.setAttribute("currClassInfo", currClassInfo);
		request.getSession().setAttribute("currClassInfo", currClassInfo);
		request.setAttribute("page", "0");
	}
	
	// 我的班级功能页功能选择
	public void changeManagementPageService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		Connection connection = null;
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		try {
			connection = JdbcTools.getConnectionByPools();
			
			if (page.equals("1")) { // 班级成员
				List<UserClassMap> records = userClassMapDaoArc.getClassMembers(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			}
			else if (page.equals("2")) {
				List<ClassAnnoMap> records = classAnnoMapDaoArc.getAnnoList(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			} 
			else {
				
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		
	}
	
/*************************************************************************************************************************************************/
	
}

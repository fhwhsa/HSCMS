package com.fws.mvc.service;

import java.sql.Connection;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.bean.ClassAnnoMap;
import com.fws.mvc.bean.ClassApplicationRecord;
import com.fws.mvc.bean.ClassCommRecords;
import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.bean.User;
import com.fws.mvc.bean.UserClassMap;
import com.fws.mvc.dao.ClassCommRecordsDao;
import com.fws.mvc.daoArc.ClassAnnoMapDaoArc;
import com.fws.mvc.daoArc.ClassApplicationRecordDaoArc;
import com.fws.mvc.daoArc.ClassCommRecordsDaoArc;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.daoArc.UserClassMapDaoArc;
import com.fws.mvc.daoArc.UserDaoArc;
import com.fws.mvc.utils.JdbcTools;

public class GuardianService {

	private ClassInfoDaoArc classInfoDaoArc = null;
	private ClassApplicationRecordDaoArc classApplicationRecordDaoArc = null;
	private UserClassMapDaoArc userClassMapDaoArc = null;
	private ClassAnnoMapDaoArc classAnnoMapDaoArc = null;
	private GlobalVarDaoArc globalVarDaoArc = null;
	private UserDaoArc userDaoArc = null;
	private ClassCommRecordsDaoArc classCommRecordsDaoArc = null;

	
	public GuardianService() {
		if (classInfoDaoArc == null)
			classInfoDaoArc = new ClassInfoDaoArc();
		if (classApplicationRecordDaoArc == null)
			classApplicationRecordDaoArc = new ClassApplicationRecordDaoArc();
		if (userClassMapDaoArc == null)
			userClassMapDaoArc = new UserClassMapDaoArc();
		if (classAnnoMapDaoArc == null)
			classAnnoMapDaoArc = new ClassAnnoMapDaoArc();
		if (globalVarDaoArc == null)
			globalVarDaoArc = new GlobalVarDaoArc();
		if (userDaoArc == null)
			userDaoArc = new UserDaoArc();
		if (classCommRecordsDaoArc == null)
			classCommRecordsDaoArc = new ClassCommRecordsDaoArc();
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
					connection.setAutoCommit(false);
					classApplicationRecordDaoArc.addApplicationRecord(connection, record);
					connection.commit();
					request.setAttribute("mes", "申请已提交，请等待审核");
				}
			}
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.setAutoCommit(true);
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
	
	// 转我的班级功能页（初始化数据，默认跳转班级信息功能）
	public void initMyClassPage(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String selectedClassNo = request.getParameter("selectedClassNo");	
		ClassInfo currClassInfo = null;
		User creater = null;
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			currClassInfo = classInfoDaoArc.getClassInfo(connection, selectedClassNo);
			creater = userDaoArc.getUser(connection, currClassInfo.getCreaterEmailAddr(), "Teacher");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		request.getSession().setAttribute("createrName", creater.getName());
		request.getSession().setAttribute("createrEmailAddr", creater.getEmailAddr());
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
			else if (page.equals("2")) { // 班级通知
				List<ClassAnnoMap> records = classAnnoMapDaoArc.getAnnoList(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			} 
			else if (page.equals("3")) { // 班级交流
				List<ClassCommRecords> records = classCommRecordsDaoArc.getAllRecords(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		
	}
	
/*************************************************************************************************************************************************/
	
	
	
	
	
/* 班级通知的多条件查询 ******************************************************************************************************************************/

	public void getClassAnnoListByFilter(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String page = "2";
		request.setAttribute("page", page);
		Connection connection = null;
		List<ClassAnnoMap> records = null;
		String filterContext = "%" + request.getParameter("contxt") + "%";
		String filterDate = "%" + request.getParameter("date") + "%";
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		try {
			connection = JdbcTools.getConnectionByPools();
			records = classAnnoMapDaoArc.getAnnoListByFilter(connection, currClassInfo.getClassNo(), filterContext, filterDate);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		request.setAttribute("records", records);
	}
	
/*************************************************************************************************************************************************/

	
	
	
	
/* 班级交流 ******************************************************************************************************************************************/
	
	// 发布信息（班级交流）
	public void postCommRecordService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("page", new String("3"));
		String context = request.getParameter("context-post");
		context = new String(context.getBytes("iso-8859-1"), "utf-8");
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		Connection connection = null;
		List<ClassCommRecords> records = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			connection.setAutoCommit(false);
			classCommRecordsDaoArc.addRecord(connection, new ClassCommRecords(currClassInfo.getClassNo(), 
					(String)request.getSession().getAttribute("currName"), (String)request.getSession().getAttribute("currEmailAddr"),
					context, new Date()));
			records = classCommRecordsDaoArc.getAllRecords(connection, currClassInfo.getClassNo());
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.setAutoCommit(true);
			JdbcTools.releaseSources(connection);
		}
		request.setAttribute("records", records);
	}
	
	// 信息筛选
	public void filteCommRecordsService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("page", new String("3"));
		String filterContext = "%" + request.getParameter("contxt-filter") + "%";
		String filterDate = "%" + request.getParameter("date") + "%";
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		Connection connection = null;
		List<ClassCommRecords> records = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			records = classCommRecordsDaoArc.getAllRecordsByFilter(connection, currClassInfo.getClassNo(), filterContext, filterDate);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		request.setAttribute("records", records);
	}
	
/****************************************************************************************************************************************************/
	
	
	// 刷新系统公告
	public void refreshSysAnnoService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			request.getSession().setAttribute("announcement", globalVarDaoArc.getSysAnnoContext(connection));
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
}

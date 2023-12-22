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
import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.daoArc.UserClassMapDaoArc;
import com.fws.mvc.utils.JdbcTools;

public class TeacherService {

	private static ClassInfoDaoArc classInfoDaoArc = null;
	private static UserClassMapDaoArc userClassMapDaoArc = null;
	private static ClassAnnoMapDaoArc classAnnoMapDaoArc = null;
	private static ClassApplicationRecordDaoArc classApplicationRecordDaoArc = null;
	private static GlobalVarDaoArc globalVarDaoArc = null;
	
	public TeacherService() {
		if (classInfoDaoArc == null)
			classInfoDaoArc = new ClassInfoDaoArc();
		if (userClassMapDaoArc == null)
			userClassMapDaoArc = new UserClassMapDaoArc();
		if (classAnnoMapDaoArc == null)
			classAnnoMapDaoArc = new ClassAnnoMapDaoArc();
		if (classApplicationRecordDaoArc == null)
			classApplicationRecordDaoArc = new ClassApplicationRecordDaoArc();
		if (globalVarDaoArc == null)
			globalVarDaoArc = new GlobalVarDaoArc();
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
	
	
	
	
	
/* 班级管理跳转处理 **********************************************************************************************************************************/
	
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
		Connection connection = null;
		ClassInfo currClassInfo = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			currClassInfo = classInfoDaoArc.getClassInfo(connection, selectedClassNo);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		request.getSession().setAttribute("currClassInfo", currClassInfo);
		request.setAttribute("page", "0");
	}
	
	// 功能选择，初始化数据
	public void changeManagementPageService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		if (page.equals("0"))
			return;
		
		Connection connection = null;
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		try {
			connection = JdbcTools.getConnectionByPools();
			if (page.equals("1")) { // 审核
				List<ClassApplicationRecord> records = classApplicationRecordDaoArc.getApplicationRecordsList(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			}
			else if (page.equals("2")) { // 班级成员
				List<UserClassMap> records = userClassMapDaoArc.getClassMembers(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			}
			else { // 通知
				List<ClassAnnoMap> records = classAnnoMapDaoArc.getAnnoList(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
/*************************************************************************************************************************************************/
	
	
	
	
	
/* 通知模块 ***************************************************************************************************************************************/
	
	// 发布通知
	public void postAnnoService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("page", new String("3"));
		String context = request.getParameter("context");
		context = new String(context.getBytes("iso-8859-1"), "utf-8");
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		List<ClassAnnoMap> records = null;
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			connection.setAutoCommit(false);
			classAnnoMapDaoArc.addAnno(connection, currClassInfo.getClassNo(), context);
			records = classAnnoMapDaoArc.getAnnoList(connection, currClassInfo.getClassNo());
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.setAutoCommit(true);
			JdbcTools.releaseSources(connection);
		}
		request.setAttribute("records", records);
	}
	
	
	// 删除通知
	public void deleteAnnoService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setAttribute("page", new String("3"));
		String id = request.getParameter("id");
		Connection connection = null;
		List<ClassAnnoMap> records = null;
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		try {
			connection = JdbcTools.getConnectionByPools();
			connection.setAutoCommit(false);
			classAnnoMapDaoArc.deleteAnno(connection, id);
			records = classAnnoMapDaoArc.getAnnoList(connection, currClassInfo.getClassNo());
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.setAutoCommit(true);
			JdbcTools.releaseSources(connection);
		}
		request.setAttribute("records", records);
	}
	
/*************************************************************************************************************************************************/

	
	
	
/* 审核模块 ***************************************************************************************************************************************/
	
	// 通过申请
	public void approvedApplicationService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = null;
		String name = request.getParameter("name");
		String emailAddr = request.getParameter("emailAddr");
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		ClassApplicationRecord record = new ClassApplicationRecord(emailAddr, name, currClassInfo.getClassNo());
		try {
			connection = JdbcTools.getConnectionByPools();
			connection.setAutoCommit(false);
			classApplicationRecordDaoArc.deleteRecord(connection, record);
			classApplicationRecordDaoArc.addUserClassMap(connection, record);
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.setAutoCommit(true);
			JdbcTools.releaseSources(connection);
			reGetRecordList(request, response);
		}
	}
	
	// 拒绝申请
	public void refusedApplicationService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = null;
		String name = request.getParameter("name");
		String emailAddr = request.getParameter("emailAddr");
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		ClassApplicationRecord record = new ClassApplicationRecord(emailAddr, name, currClassInfo.getClassNo());
		try {
			connection = JdbcTools.getConnectionByPools();
			connection.setAutoCommit(false);
			classApplicationRecordDaoArc.deleteRecord(connection, record);
		} catch (Exception e) {
			connection.rollback();
			e.printStackTrace();
		} finally {
			connection.setAutoCommit(true);
			JdbcTools.releaseSources(connection);
			reGetRecordList(request, response);
		}
	}
	
	// 重新获取list审核记录
	public void reGetRecordList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Connection connection = null;
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		try {
			connection = JdbcTools.getConnectionByPools();
			List<ClassApplicationRecord> records = classApplicationRecordDaoArc.getApplicationRecordsList(connection, currClassInfo.getClassNo());
			request.setAttribute("records", records);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
/*************************************************************************************************************************************************/

	
	
	
	
/* 加入班级 ****************************************************************************************************************************************/

	public void submitApplicationService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String classNo = request.getParameter("classNo");
		String emailAddr = (String) request.getSession().getAttribute("currEmailAddr");
		Connection connection = null;
		List<ClassInfo> records = null;
		Boolean flag = true;
		try {
			connection = JdbcTools.getConnectionByPools();
			records = classInfoDaoArc.getCreateClassRecordsList(connection, emailAddr);
			if (!classInfoDaoArc.isExist(connection, classNo)) {
				request.setAttribute("mes", "不存在的班级编号！");
				flag = false;
			}
			else {
				for (ClassInfo record : records) {
					if (record.getClassNo().equals(classNo)) {
						request.setAttribute("mes", "不可以加入自己创建的班级！");
						flag = false;
						break;
					}
				}
			}
			
			if (flag) {
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

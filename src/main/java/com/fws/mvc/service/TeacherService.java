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
import com.fws.mvc.daoArc.ClassAnnoMapDaoArc;
import com.fws.mvc.daoArc.ClassApplicationRecordDaoArc;
import com.fws.mvc.daoArc.ClassCommRecordsDaoArc;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.daoArc.UserClassMapDaoArc;
import com.fws.mvc.daoArc.UserDaoArc;
import com.fws.mvc.utils.JdbcTools;

public class TeacherService {

	private static ClassInfoDaoArc classInfoDaoArc = null;
	private static UserClassMapDaoArc userClassMapDaoArc = null;
	private static ClassAnnoMapDaoArc classAnnoMapDaoArc = null;
	private static ClassApplicationRecordDaoArc classApplicationRecordDaoArc = null;
	private static GlobalVarDaoArc globalVarDaoArc = null;
	private static UserDaoArc userDaoArc = null;
	private static ClassCommRecordsDaoArc classCommRecordsDaoArc = null;
	
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
		if (userDaoArc == null)
			userDaoArc = new UserDaoArc();
		if (classCommRecordsDaoArc == null)
			classCommRecordsDaoArc = new ClassCommRecordsDaoArc();
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
			else if (page.equals("3")) { // 通知
				List<ClassAnnoMap> records = classAnnoMapDaoArc.getAnnoList(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			}
			else { // 站内交流
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

	// 提交加入申请
	public void submitApplicationService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String classNo = request.getParameter("classNo");
		Connection connection = null;
		List<ClassInfo> records = null;
		String emailAddr = (String) request.getSession().getAttribute("currEmailAddr");
		Boolean flag = true;
		try {
			connection = JdbcTools.getConnectionByPools();
			if (!classInfoDaoArc.isExist(connection, classNo)) {
				request.setAttribute("mes", "不存在的班级编号！");
				flag = false;
			}
			else {
				// 检查是否为自己创建的班级
				records = classInfoDaoArc.getCreateClassRecordsList(connection, emailAddr);
				for (ClassInfo record : records) {
					if (record.getClassNo().equals(classNo)) {
						request.setAttribute("mes", "不可以加入自己创建的班级！");
						flag = false;
						break;
					}
				}
				
				// 检查是否为已经加入的班级
				records = classInfoDaoArc.getJoinedClassRecordsList(connection, emailAddr);
				for (ClassInfo record : records) {
					if (record.getClassNo().equals(classNo)) {
						request.setAttribute("mes", "班级已加入！");
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
	
	
	
	
	
/* 我的班级跳转处理 **************************************************************************************************************************/
	
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
	
	// 选择班级后转我的班级功能页（初始化数据，默认跳转班级信息功能）
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
	public void changeMyClassPageService(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String page = request.getParameter("page");
		request.setAttribute("page", page);
		Connection connection = null;
		ClassInfo currClassInfo = (ClassInfo) request.getSession().getAttribute("currClassInfo");
		try {
			connection = JdbcTools.getConnectionByPools();
			
			if (page.equals("1")) { // 班级成员
				System.out.println("page1");
				List<UserClassMap> records = userClassMapDaoArc.getClassMembers(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			}
			else if (page.equals("2")) { // 班级通知
				System.out.println("page2");
				List<ClassAnnoMap> records = classAnnoMapDaoArc.getAnnoList(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			} 
			else if (page.equals("3")) { // 班级交流
				System.out.println("page3");
				List<ClassCommRecords> records = classCommRecordsDaoArc.getAllRecords(connection, currClassInfo.getClassNo());
				request.setAttribute("records", records);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		
	}
	
	
/*****************************************************************************************************************************************/
	
	
	
	

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
		// 该函数可能由班级管理或我的班级调用的servlet调用，因为功能块数不同，所以需要进行判断
		String jumpPath = request.getParameter("jumpPath");
		request.setAttribute("page", new String(jumpPath.equals("myClass.jsp") ? "3" : "4"));
		
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
		String jumpPath = request.getParameter("jumpPath");
		request.setAttribute("page", new String(jumpPath.equals("myClass.jsp") ? "3" : "4"));
		
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

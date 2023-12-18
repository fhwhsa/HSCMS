package com.fws.mvc.service;

import java.sql.Connection;
import java.util.List;

import com.fws.mvc.bean.ClassInfo;
import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.daoArc.ClassInfoDaoArc;
import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.daoArc.RegisterDaoArc;
import com.fws.mvc.utils.JdbcTools;
import com.fws.mvc.utils.SendEmail;


// 只有一个管理员，暂未有使用的函数与普通用户冲突，不需要开事务
public class AdminService {

	private static GlobalVarDaoArc globalVarDaoArc = null;
	private static RegisterDaoArc registerDaoArc = null;
	private static ClassInfoDaoArc classInfoDaoArc = null;
	
	public AdminService() {
		// TODO Auto-generated constructor stub
		if (registerDaoArc == null)
			registerDaoArc = new RegisterDaoArc();
		
		if (globalVarDaoArc == null) 
			globalVarDaoArc = new GlobalVarDaoArc();
		
		if (classInfoDaoArc == null)
			classInfoDaoArc = new ClassInfoDaoArc();
	}
	
	
/*	公告模块 ********************************************************************************************/
	public void updateSysAnnoService(String context)  {
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			globalVarDaoArc.updateSysAnnoContext(connection, context);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
	public String getSysAnnoService()  {
		Connection connection = null;
		String res = "";
		try {
			connection = JdbcTools.getConnectionByPools();
			res = globalVarDaoArc.getSysAnnoContext(connection);
		} catch (Exception e) {
			// TODO: handle exception
			res = e.getMessage();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return res;
	}
/*	公告模块 ********************************************************************************************/
	
	
	
	
	

/*	注册审核模块 ********************************************************************************************/	
	public List<RegistrationRecord> getRegisterRecordsService() {
		Connection connection = null;
		List<RegistrationRecord> records = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			records = registerDaoArc.getRegistrationRecordsList(connection);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return records;
	}
	
	// 通过申请
	public void approvedRegistrationService(String emailAddr) {
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			registerDaoArc.approvedRegistrationRecord(connection, emailAddr);
			// 发送邮箱信息告知注册成功
			SendEmail.sendMail(emailAddr, "注册申请结果", "通过");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
	// 拒绝申请
	public void refusedRegistrationService(String emailAddr) {
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			registerDaoArc.deleteRegistrationRecord(connection, emailAddr);
			// 发送邮箱信息告知注册被拒绝
			SendEmail.sendMail(emailAddr, "注册申请结果", "不通过");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
/*	注册审核模块 ********************************************************************************************/	
	
	
	
	
	
/*	班级审核模块 ********************************************************************************************/	
	
	public List<ClassInfo> getClassInfoRecordsService() {
		Connection connection = null;
		List<ClassInfo> records = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			records = classInfoDaoArc.getClassInfoRecordsListFromApplication(connection);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return records;
	}
	
	
	public void approvedClassApplicationService(String classNo, String emailAddr) {
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			classInfoDaoArc.approvedApplication(connection, classNo);
			SendEmail.sendMail(emailAddr, "班级创建申请结果", "通过");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
	public void refusedClassApplication(String classNo, String emailAddr) {
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			classInfoDaoArc.refusedApplication(connection, classNo);
			SendEmail.sendMail(emailAddr, "班级创建申请结果", "不通过");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
/*	班级审核模块 ********************************************************************************************/	
	
}

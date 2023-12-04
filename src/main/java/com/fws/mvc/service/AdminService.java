package com.fws.mvc.service;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

import com.fws.mvc.bean.RegistrationRecord;
import com.fws.mvc.daoArc.AdministratorDaoArc;
import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.utils.JdbcTools;


// 只有一个管理员，暂未有使用的函数与普通用户冲突，不需要开事务
public class AdminService {

	private static GlobalVarDaoArc globalVarDaoArc = null;
	private static AdministratorDaoArc administratorDaoArc = null;
	
	public AdminService() {
		// TODO Auto-generated constructor stub
		if (administratorDaoArc == null)
			administratorDaoArc = new AdministratorDaoArc();
		
		if (globalVarDaoArc == null) 
			globalVarDaoArc = new GlobalVarDaoArc();
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
	public List<RegistrationRecord> getRecordsService() {
		Connection connection = null;
		List<RegistrationRecord> records = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			records = administratorDaoArc.getRegistrationRecordsList(connection);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
		return records;
	}
	
	// 通过申请
	public void approvedService(String emailAddr, String userType) {
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			administratorDaoArc.approvedRegistrationRecord(connection, emailAddr, userType);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
	// 拒绝申请
	public void refusedService(String emailAddr, String userType) {
		Connection connection = null;
		try {
			connection = JdbcTools.getConnectionByPools();
			administratorDaoArc.deleteRegistrationRecord(connection, emailAddr, userType);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
/*	注册审核模块 ********************************************************************************************/	
	
}

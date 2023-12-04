package com.fws.mvc.service;

import java.sql.Connection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fws.mvc.daoArc.GlobalVarDaoArc;
import com.fws.mvc.utils.JdbcTools;

public class GlobalVarService {
	private static GlobalVarDaoArc globalVarDaoArc = null;
	
	public GlobalVarService() {
		// TODO Auto-generated constructor stub
		if (globalVarDaoArc == null) 
			globalVarDaoArc = new GlobalVarDaoArc();
	}
	
	public void updateSysAnno(String context)  {
		Connection connection = null;
		try { // 只能管理员修改，不开事务
			connection = JdbcTools.getConnectionByPools();
			globalVarDaoArc.updateSysAnnoContext(connection, context);
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		} finally {
			JdbcTools.releaseSources(connection);
		}
	}
	
	public String getSysAnno()  {
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
}

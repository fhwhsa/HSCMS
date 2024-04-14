package com.fws.mvc.daoArc;

import java.sql.Connection;
import java.sql.SQLException;

import com.fws.mvc.bean.GlobalVar;
import com.fws.mvc.dao.GlobalVarDao;

public class GlobalVarDaoArc extends CommonDaoArc<GlobalVar> implements GlobalVarDao {

	@Override
	public void updateSysAnnoContext(Connection connection, String context) throws SQLException {
		String sql = "update globalvar set context = ? where type = ?;";
		Object[] params = {context, "sys"};
		update(connection, sql, params);
	}

	
	@Override
	public String getSysAnnoContext(Connection connection) throws SQLException {
		// TODO Auto-generated method stub
		String sql = "select context from globalvar where type = ?;";
		Object[] params = {"sys"};
		String res = this.<String>fetchScaler(connection, sql, params);
		return res;
	}
}